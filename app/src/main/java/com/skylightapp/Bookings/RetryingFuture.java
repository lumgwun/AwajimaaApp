package com.skylightapp.Bookings;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.Futures;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class RetryingFuture {
    static int RUN_FOREVER = Integer.MIN_VALUE;

    private final ScheduledExecutorService executor;

    public RetryingFuture(ScheduledExecutorService executor) {
        this.executor = executor;
    }
    public <T> ListenableFuture<T> runWithRetries(
            Supplier<ListenableFuture<T>> futureSupplier,
            int retries,
            long intervalMillis,
            Predicate<T> successCondition) {
        SettableFuture<T> resultFuture = SettableFuture.create();
        runWithRetriesInternal(resultFuture, futureSupplier, retries, intervalMillis, successCondition);
        return resultFuture;
    }

    private <T> void runWithRetriesInternal(
            final SettableFuture<T> future,
            final Supplier<ListenableFuture<T>> futureSupplier,
            final int retries,
            final long intervalMillis,
            final Predicate<T> successCondition) {
        ListenableFuture<T> immediateFuture;
        try {
            immediateFuture = futureSupplier.get();
        } catch (Exception e) {
            handleFailure(future, futureSupplier, retries, intervalMillis, successCondition, e);
            return;
        }

        Futures.addCallback(
                immediateFuture,
                new FutureCallback<T>() {
                    @Override
                    public void onSuccess(T result) {
                        if (successCondition.test(result)) {
                            future.set(result);
                        } else {
                            RuntimeException exception =
                                    new RuntimeException("Success condition not met, retrying.");
                            handleFailure(
                                    future, futureSupplier, retries, intervalMillis, successCondition, exception);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        handleFailure(future, futureSupplier, retries, intervalMillis, successCondition, t);
                    }
                },
                MoreExecutors.directExecutor());
    }

    private <T> void handleFailure(
            SettableFuture<T> future,
            Supplier<ListenableFuture<T>> futureSupplier,
            int retries,
            long delayInMillis,
            Predicate<T> successCondition,
            Throwable t) {
        if (retries == RUN_FOREVER || retries > 0) {
            ScheduledFuture<?> unused =
                    executor.schedule(
                            () -> {
                                int newRetriesCount = retries == RUN_FOREVER ? RUN_FOREVER : retries - 1;
                                runWithRetriesInternal(
                                        future, futureSupplier, newRetriesCount, delayInMillis, successCondition);
                            },
                            delayInMillis,
                            MILLISECONDS);
        } else {
            future.setException(t);
        }
    }
}
