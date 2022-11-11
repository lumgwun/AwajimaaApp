package com.skylightapp.Classes;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;

import com.skylightapp.Interfaces.OnDataChangedListener;
import com.skylightapp.Interfaces.OnPackageChangedListener;
import com.skylightapp.Interfaces.OnPackageExistListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PackageInteractor {
    private static final String TAG = PackageInteractor.class.getSimpleName();
    private static PackageInteractor instance;

    private DatabaseHelper databaseHelper;

    private Context context;
    private Object OnCreatedListener;

    public static PackageInteractor getInstance(Context context) {
        if (instance == null) {
            instance = new PackageInteractor(context);
        }

        return instance;
    }

    private PackageInteractor(Context context) {
        this.context = context;
        databaseHelper = AppController.getDatabaseHelper();
    }

    public void createOrUpdateProfile(final SkyLightPackage lightPackage, final com.skylightapp.Interfaces.OnCreatedListener onPackageCreatedListener) {
        /*Task<Void> task = databaseHelper
                .getDatabaseReference()
                .child(DatabaseHelper.PACKAGE_DB_KEY)
                .child(lightPackage.getUID())
                .setValue(lightPackage);
        task.addOnCompleteListener(task1 -> {
            onPackageCreatedListener.onCreated(task1.isSuccessful());
            //addRegistrationToken(FirebaseInstanceId.getInstance().getToken(), profile.getId());
            LogUtil.logDebug(TAG, "createOrUpdatePackages, success: " + task1.isSuccessful());
        });*/
    }

    public void createOrUpdatePackages(SkyLightPackage skyLightPackage) {
        try {
           // Map<String, Object> savingsValues = (Map<String, Object>) skyLightPackage.getPackageDailyAmount();
            //Map<String, Object> childUpdates = new HashMap<>();
            //childUpdates.put("/" + DatabaseHelper.PACKAGE_DB_KEY + "/" + skyLightPackage.getPackID(), savingsValues);

            //databaseHelper.getDatabaseReference().updateChildren(childUpdates);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Task<Void> removePackage(SkyLightPackage dailyReport) {
        //DatabaseReference postRef = databaseHelper.getDatabaseReference().child(DatabaseHelper.PACKAGE_DB_KEY).child(String.valueOf(dailyReport.getRecordNo()));
        /* return postRef.removeValue(); */
        return null;
    }
    public void getPackageList(final OnPackageChangedListener<SkyLightPackage> onDataChangedListener, long date) {
       /* DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.PACKAGE_DB_KEY);
        Query postsQuery;
        if (date == 0) {
            postsQuery = databaseReference.limitToLast(Utils.SkyLightPackage.POST_AMOUNT_ON_PAGE).orderByChild("createdDate");
        } else {
            postsQuery = databaseReference.limitToLast(Utils.SkyLightPackage.POST_AMOUNT_ON_PAGE).endAt(date).orderByChild("createdDate");
        }

        postsQuery.keepSynced(true);
        postsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
                PackageListResult result = parsePackageList(objectMap);

                if (result.getSkyLightPackageList().isEmpty() && result.isMoreDataAvailable()) {
                    getPackageList(onDataChangedListener, result.getLastItemCreatedDate() - 1);
                } else {
                    onDataChangedListener.onSavingsChanged(parsePackageList(objectMap));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getPostList(), onCancelled", new Exception(databaseError.getMessage()));

                //onDataChangedListener.onCanceled(context.getString(R.string.permission_denied_error));
            }
        });*/
    }
    private PackageListResult parsePackageList(Map<String, Object> objectMap) {
        PackageListResult result = new PackageListResult();
        List<SkyLightPackage> list = new ArrayList<SkyLightPackage>();
        boolean isMoreDataAvailable = true;
        long lastItemCreatedDate = 0;

        if (objectMap != null) {
            isMoreDataAvailable = Utils.SkyLightPackage33.POST_AMOUNT_ON_PAGE == objectMap.size();

            for (String key : objectMap.keySet()) {
                Object obj = objectMap.get(key);
                if (obj instanceof Map) {
                    Map<String, Object> mapObj = (Map<String, Object>) obj;

                    if (!isPackageValid(mapObj)) {
                        LogUtil.logDebug(TAG, "Invalid post, id: " + key);
                        continue;
                    }

                    boolean hasComplain = mapObj.containsKey("hasComplain") && (boolean) mapObj.get("hasComplain");
                    long createdDate = (long) mapObj.get("createdDate");

                    if (lastItemCreatedDate == 0 || lastItemCreatedDate > createdDate) {
                        lastItemCreatedDate = createdDate;
                    }

                    if (!hasComplain) {
                        SkyLightPackage lightPackage = new SkyLightPackage();
                        lightPackage.setRecordPackageId(Integer.parseInt(key));
                        //lightPackage.getPackageDailyAmount(Double.parseDouble((String) mapObj.get("Amount")));
                        lightPackage.setPackageDuration(Integer.parseInt((String) mapObj.get("Duration")));
                        lightPackage.setPackageAmount_collected(Double.parseDouble((String) mapObj.get("Amount collected")));
                        lightPackage.setPackageDateStarted(String.valueOf(createdDate));
                        if (mapObj.containsKey("packageCount")) {
                            //lightPackage.setCommentsCount((long) mapObj.get("commentsCount"));
                        }
                        if (mapObj.containsKey("likesCount")) {
                            //lightPackage.setLikesCount((long) mapObj.get("likesCount"));
                        }
                        if (mapObj.containsKey("watchersCount")) {
                            //lightPackage.setWatchersCount((long) mapObj.get("watchersCount"));
                        }
                        list.add(lightPackage);
                    }
                }
            }

            Collections.sort(list, (lhs, rhs) ->  rhs.getPackageDateStarted().compareTo(lhs.getPackageDateStarted()));

            result.setSkyLightPackageList(list);
            result.setLastItemCreatedDate(lastItemCreatedDate);
            result.setMoreDataAvailable(isMoreDataAvailable);
        }

        return result;
    }
    private boolean isPackageValid(Map<String, Object> objectMap) {
        return objectMap.containsKey("title")
                && objectMap.containsKey("Amount")
                && objectMap.containsKey("Duration")
                && objectMap.containsKey("Amount collected");
    }


    public void getPackageListByUser(final OnDataChangedListener<SkyLightPackage> onDataChangedListener, String userId) {
        /*DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.PACKAGE_DB_KEY);
        Query savingsQuery;
        savingsQuery = databaseReference.orderByChild("authorId").equalTo(userId);

        savingsQuery.keepSynced(true);
        savingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try (PackageListResult result = parsePackageList((Map<String, Object>) dataSnapshot.getValue())) {
                    onDataChangedListener.onListChanged(result.getSkyLightPackageList());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getPostListByUser(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });*/
    }

    public void incrementWatchersCount(String postId) {
        /*DatabaseReference postRef = databaseHelper.getDatabaseReference().child(DatabaseHelper.PACKAGE_DB_KEY + "/" + postId + "/watchersCount");
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(currentValue + 1);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                LogUtil.logInfo(TAG, "Updating Watchers count transaction is completed.");
            }
        });*/
    }

    public void addComplainToPackage(SkyLightPackage skyLightPackage) {

    }


    public void isPackageExistSingleValue(String postId, OnPackageExistListener<SkyLightPackage> onObjectExistListener) {

    }

   /* public ValueEventListener searchPackagesByTitle(String searchText, OnDataChangedListener<SkyLightPackage> onDataChangedListener) {
        return null;
    }

    public ValueEventListener getPackage(String postId, OnPackageChangedListener onPostChangedListener) {
        return null;
    }*/

    public void getSinglePackage(long postId, OnPackageChangedListener onPostChangedListener) {

    }
}
