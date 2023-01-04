package com.skylightapp.Classes;

import android.content.Context;
import android.util.Log;


import com.skylightapp.Interfaces.OnDataChangedListener;
import com.skylightapp.Interfaces.OnPackageChangedListener;
import com.skylightapp.Interfaces.OnPackageExistListener;
import com.skylightapp.Interfaces.OnTaskCompleteListener;
import com.skylightapp.MarketClasses.MarketBizPackage;


public class PackageManager extends FirebaseListenersManager {

    private static final String TAG = PackageManager.class.getSimpleName();
    private static PackageManager instance;
    private int newPackageCounter = 0;
    private PackageCounterWatcher postCounterWatcher;
    private PackageInteractor packageInteractor;

    private Context context;

    public static PackageManager getInstance(Context context) {
        if (instance == null) {
            instance = new PackageManager(context);
        }

        return instance;
    }

    private PackageManager(Context context) {
        this.context = context;
        packageInteractor = PackageInteractor.getInstance(context);
    }

    public void createOrUpdatePost(MarketBizPackage lightPackage) {
        try {
            packageInteractor.createOrUpdatePackages(lightPackage);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void getPostsList(OnPackageChangedListener<MarketBizPackage> onDataChangedListener, long date) {
        packageInteractor.getPackageList(onDataChangedListener, date);
    }

    public void getPostsListByUser(OnDataChangedListener<MarketBizPackage> onDataChangedListener, String userId) {
        packageInteractor.getPackageListByUser(onDataChangedListener, userId);
    }

    public void getPost(Context context, String postId, OnPackageChangedListener onPostChangedListener) {
       /* ValueEventListener valueEventListener = packageInteractor.getPackage(postId, onPostChangedListener);
        addListenerToMap(context, valueEventListener);*/
    }

    public void getSinglePostValue(long postId, OnPackageChangedListener onPostChangedListener) {
        packageInteractor.getSinglePackage(postId, onPostChangedListener);
    }


    public void removePost(final MarketBizPackage marketBizPackage, final OnTaskCompleteListener onTaskCompleteListener) {
        packageInteractor.removePackage(marketBizPackage);
    }

    public void addComplain(MarketBizPackage marketBizPackage) {
        packageInteractor.addComplainToPackage(marketBizPackage);
    }


    public void isPostExistSingleValue(String postId, final OnPackageExistListener<MarketBizPackage> onObjectExistListener) {
        packageInteractor.isPackageExistSingleValue(postId, onObjectExistListener);
    }

    public void incrementWatchersCount(String postId) {
        packageInteractor.incrementWatchersCount(postId);
    }

    public void incrementNewPackageCounter() {
        newPackageCounter++;
        notifyPostCounterWatcher();
    }

    public void clearNewPackagesCounter() {
        newPackageCounter = 0;
        notifyPostCounterWatcher();
    }

    public int getNewPackageCounter() {
        return newPackageCounter;
    }

    public void setPostCounterWatcher(PackageCounterWatcher postCounterWatcher) {
        this.postCounterWatcher = postCounterWatcher;
    }

    private void notifyPostCounterWatcher() {
        if (postCounterWatcher != null) {
            postCounterWatcher.onPostCounterChanged(newPackageCounter);
        }
    }

   /* public void getFollowingPosts(String userId, OnDataChangedListener<FollowingPost> listener) {
        FollowInteractor.getInstance(context).getFollowingPosts(userId, listener);
    }*/

    public void searchByTitle(String searchText, OnDataChangedListener<MarketBizPackage> onDataChangedListener) {
        /*closeListeners(context);
        ValueEventListener valueEventListener = packageInteractor.searchPackagesByTitle(searchText, onDataChangedListener);
        addListenerToMap(context, valueEventListener);*/
    }

    public interface PackageCounterWatcher {
        void onPostCounterChanged(int newValue);
    }
}
