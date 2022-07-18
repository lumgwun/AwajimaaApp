package com.skylightapp.Classes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;

public class HorizontalFlipTransformation extends RecyclerView.ItemAnimator implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        page.setTranslationX(-position * page.getWidth());
        page.setCameraDistance(12000);

        if (position < 0.5 && position > -0.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.INVISIBLE);
        }


        if (position < -1) {     // [-Infinity,-1)
            page.setAlpha(0);

        } else if (position <= 0) {    // [-1,0]
            page.setAlpha(1);
            page.setRotationY(180 * (1 - Math.abs(position) + 1));

        } else if (position <= 1) {    // (0,1]
            page.setAlpha(1);
            page.setRotationY(-180 * (1 - Math.abs(position) + 1));

        } else {
            page.setAlpha(0);
        }
    }

    @Override
    public boolean animateDisappearance(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull ItemHolderInfo preLayoutInfo, @Nullable @org.jetbrains.annotations.Nullable ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, @Nullable @org.jetbrains.annotations.Nullable ItemHolderInfo preLayoutInfo, @NonNull @NotNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull ItemHolderInfo preLayoutInfo, @NonNull @NotNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull @NotNull RecyclerView.ViewHolder oldHolder, @NonNull @NotNull RecyclerView.ViewHolder newHolder, @NonNull @NotNull ItemHolderInfo preLayoutInfo, @NonNull @NotNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(@NonNull @NotNull RecyclerView.ViewHolder item) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
