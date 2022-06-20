package com.skylightapp.Classes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.skylightapp.Interfaces.OnDataChangedListener;
import com.skylightapp.Interfaces.OnTellerReportChangeListener;
import com.skylightapp.Interfaces.OnSavingsExistListener;
import com.skylightapp.Interfaces.OnTaskCompleteListener;
import com.skylightapp.Interfaces.UploadImagePrefix;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SavingsInteractor {
    private static final String TAG = SavingsInteractor.class.getSimpleName();
    private static SavingsInteractor instance;

    private DatabaseHelper databaseHelper;

    private Context context;
    private Object OnCreatedListener;

    public static SavingsInteractor getInstance(Context context) {
        if (instance == null) {
            instance = new SavingsInteractor(context);
        }

        return instance;
    }

    private SavingsInteractor(Context context) {
        this.context = context;
        databaseHelper = AppController.getDatabaseHelper();
    }

    /*public void createOrUpdateSavings(final CustomerDailyReport dailyReport, final OnCreatedListener onSavingsCreatedListener) {
        Task<Void> task = (Task<Void>) databaseHelper
                .getDatabaseReference()
                .child(DatabaseHelper.SAVINGS_KEY)
                .child(String.valueOf(dailyReport.getRecordNo()))
                .setValueAsync(dailyReport);
        task.addOnCompleteListener(task1 -> {
            onSavingsCreatedListener.onCreated(task1.isSuccessful());
            addRegistrationToken(String.valueOf(FirebaseInstanceId.getInstance()), String.valueOf(dailyReport.getRecordNo()));
            LogUtil.logDebug(TAG, "createOrUpdateSavings, success: " + task1.isSuccessful());
        });
    }*/

    public void createOrUpdateSavings(CustomerDailyReport dailyReport) {
        try {
            Map<String, Object> savingsValues = (Map<String, Object>) dailyReport.getSavingsDoc();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + DatabaseHelper.SAVINGS_KEY + "/" + dailyReport.getRecordNo(), savingsValues);

            //databaseHelper.getDatabaseReference().updateChildren(childUpdates);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Task<Void> removePost(CustomerDailyReport dailyReport) {
        //DatabaseReference postRef = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY).child(String.valueOf(dailyReport.getRecordNo()));
        /* return postRef.removeValue(); */
        return null;
    }

    public void incrementWatchersCount(String postId) {
        /*DatabaseReference postRef = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY + "/" + postId + "/watchersCount");
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

    public void getPostList(final OnTellerReportChangeListener<CustomerDailyReport> onDataChangedListener, long date) {
        /*DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.POSTS_DB_KEY);
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
                SavingsListResult result = parsePostList(objectMap);

                if (result.getDailyReports().isEmpty() && result.isMoreDataAvailable()) {
                    getPostList(onDataChangedListener, result.getLastItemCreatedDate() - 1);
                } else {
                    onDataChangedListener.onSavingsChanged(parsePostList(objectMap));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getPostList(), onCancelled", new Exception(databaseError.getMessage()));

                //onDataChangedListener.onCanceled(context.getString(R.string.permission_denied_error));
            }
        });*/
    }

    public void getPostListByUser(final OnDataChangedListener<CustomerDailyReport> onDataChangedListener, String userId) {
        /*DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY);
        Query savingsQuery;
        savingsQuery = databaseReference.orderByChild("authorId").equalTo(userId);

        savingsQuery.keepSynced(true);
        savingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SavingsListResult result = parsePostList((Map<String, Object>) dataSnapshot.getValue());
                onDataChangedListener.onListChanged(result.getDailyReports());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getPostListByUser(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });*/
    }

    /*public ValueEventListener getPost(final String id, final OnSavingsChangeListener listener) {
        DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.POSTS_DB_KEY).child(id);
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (isPostValid((Map<String, Object>) dataSnapshot.getValue())) {
                        CustomerDailyReport post = dataSnapshot.getValue(CustomerDailyReport.class);
                        if (post != null) {
                            post.setId(id);
                        }
                        listener.onObjectChanged(post);
                    } else {
                        listener.onError(String.format(context.getString(R.string.error_general_post), id));
                    }
                } else {
                    listener.onObjectChanged(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getPost(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });

        databaseHelper.addActiveListener(valueEventListener, databaseReference);
        return valueEventListener;
    }*/

    public void getSinglePost(final String id, final OnTellerReportChangeListener listener) {
        /*DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.POSTS_DB_KEY).child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null && dataSnapshot.exists()) {
                    if (isPostValid((Map<String, Object>) dataSnapshot.getValue())) {
                        CustomerDailyReport dailyReport = dataSnapshot.getValue(CustomerDailyReport.class);
                        dailyReport.setRecordNo(Long.parseLong(id));
                        listener.onSavingsChanged(dailyReport);
                    } else {
                        listener.onError(String.format(context.getString(R.string.error), id));
                    }
                } else {
                    listener.onError(context.getString(R.string.savings_was_removed));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "getSinglePost(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });*/
    }

    private SavingsListResult parsePostList(Map<String, Object> objectMap) {
        SavingsListResult result = new SavingsListResult();
        List<CustomerDailyReport> list = new ArrayList<CustomerDailyReport>();
        boolean isMoreDataAvailable = true;
        long lastItemCreatedDate = 0;

        if (objectMap != null) {
            isMoreDataAvailable = Utils.SkyLightPackage.POST_AMOUNT_ON_PAGE == objectMap.size();

            for (String key : objectMap.keySet()) {
                Object obj = objectMap.get(key);
                if (obj instanceof Map) {
                    Map<String, Object> mapObj = (Map<String, Object>) obj;

                    if (!isPostValid(mapObj)) {
                        LogUtil.logDebug(TAG, "Invalid post, id: " + key);
                        continue;
                    }

                    boolean hasComplain = mapObj.containsKey("hasComplain") && (boolean) mapObj.get("hasComplain");
                    long createdDate = (long) mapObj.get("createdDate");

                    if (lastItemCreatedDate == 0 || lastItemCreatedDate > createdDate) {
                        lastItemCreatedDate = createdDate;
                    }

                    if (!hasComplain) {
                        CustomerDailyReport dailyReport = new CustomerDailyReport();
                        dailyReport.setRecordNo(Integer.parseInt(key));
                        dailyReport.setAmount(Double.parseDouble((String) mapObj.get("amount")));
                        dailyReport.setNumberOfDays(Integer.parseInt((String) mapObj.get("numberOfDays")));
                        dailyReport.setSavingsDoc(Uri.parse((String) mapObj.get("imagePath")));
                        dailyReport.setTotal(Double.parseDouble((String) mapObj.get("total")));
                        dailyReport.setRecordDate(String.valueOf(createdDate));
                        dailyReport.setAmountRemaining(Double.parseDouble((String) mapObj.get("amountRemaining")));
                        /*if (mapObj.containsKey("commentsCount")) {
                            dailyReport.setCommentsCount((long) mapObj.get("commentsCount"));
                        }
                        if (mapObj.containsKey("likesCount")) {
                            dailyReport.setLikesCount((long) mapObj.get("likesCount"));
                        }
                        if (mapObj.containsKey("watchersCount")) {
                            dailyReport.setWatchersCount((long) mapObj.get("watchersCount"));
                        }*/
                        list.add(dailyReport);
                    }
                }
            }

            //Collections.sort(list, (lhs, rhs) -> ((Long) rhs.getRecordNo()).compareTo(lhs.getRecordNo()));

            result.setDailyReports(list);
            result.setLastItemCreatedDate(lastItemCreatedDate);
            result.setMoreDataAvailable(isMoreDataAvailable);
        }

        return result;
    }

    private boolean isPostValid(Map<String, Object> savings) {
        return savings.containsKey("title")
                && savings.containsKey("amount")
                && savings.containsKey("imagePath")
                && savings.containsKey("numberOfDays")
                && savings.containsKey("total")
                && savings.containsKey("date")
                && savings.containsKey("amountRemaining");
    }

    public void addComplainToSavings(CustomerDailyReport dailyReport) {
        //databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY).child(String.valueOf(dailyReport.getRecordNo())).child("hasComplain").setValue(true);
    }

    public void isSavingsExistSingleValue(String postId, final OnSavingsExistListener<CustomerDailyReport> onObjectExistListener) {
        /*DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY).child(postId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onObjectExistListener.onDataChanged(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               LogUtil.logError(TAG, "isPostExistSingleValue(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });*/
    }


    public void removeSavings(final CustomerDailyReport dailyReport, final OnTaskCompleteListener onTaskCompleteListener) {
        final DatabaseHelper databaseHelper = AppController.getDatabaseHelper();
        Task<Void> removeImageTask = null;
        if (databaseHelper != null) {
            //removeImageTask = databaseHelper.removeImage(String.valueOf(dailyReport.getRecordNo()));
        }

    }


    public void createOrUpdateSavingsWithImage(Uri imageUri, final com.skylightapp.Interfaces.OnCreatedListener onPostCreatedListener, final CustomerDailyReport dailyReport) {
        DatabaseHelper databaseHelper = AppController.getDatabaseHelper();
        if (dailyReport.getRecordNo()==0) {
            dailyReport.getRecordNo();
        }

        final String amount = ImageUtil.generateImageTitle(UploadImagePrefix.SAVINGS, dailyReport.getRecordNo());
        /*UploadTask uploadTask = databaseHelper.uploadImage(imageUri, amount);

        if (uploadTask != null) {
            uploadTask.addOnFailureListener(exception -> {
                // Handle unsuccessful uploads
                onPostCreatedListener.onCreated(false);

            }).addOnSuccessListener(taskSnapshot -> {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                LogUtil.logDebug(TAG, "successful upload image, image url: " + String.valueOf(downloadUrl));

                dailyReport.setSavingsDoc(Uri.parse(String.valueOf(downloadUrl)));
                dailyReport.setAmount(Double.parseDouble(amount));
                createOrUpdateSavings(dailyReport);

                onPostCreatedListener.onCreated(true);
            });
        }
    }


    public ValueEventListener searchSavingsByTitle(String searchText, OnDataChangedListener<CustomerDailyReport> onDataChangedListener) {
        DatabaseReference reference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY);
        ValueEventListener valueEventListener = getSearchQuery(reference,"title", searchText).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SavingsListResult result = parsePostList((Map<String, Object>) dataSnapshot.getValue());
                onDataChangedListener.onListChanged(result.getDailyReports());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "searchPostsByTitle(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });

        databaseHelper.addActiveListener(valueEventListener, reference);

        return valueEventListener;*/
    }

    /*public ValueEventListener filterSavingsByAmount(int  limit, OnDataChangedListener<CustomerDailyReport> onDataChangedListener) {
        DatabaseReference reference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY);
        ValueEventListener valueEventListener = getFilteredQuery(reference,"likesCount", limit).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SavingsListResult result = parsePostList((Map<String, Object>) dataSnapshot.getValue());
                onDataChangedListener.onListChanged(result.getDailyReports());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "filterPostsByLikes(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });

        databaseHelper.addActiveListener(valueEventListener, reference);

        return valueEventListener;
    }

    private Query getSearchQuery(DatabaseReference databaseReference, String childOrderBy, String searchText) {
        return databaseReference
                .orderByChild(childOrderBy)
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");
    }

    private Query getFilteredQuery(DatabaseReference databaseReference, String childOrderBy, int limit) {
        return databaseReference
                .orderByChild(childOrderBy)
                .limitToLast(limit);
    }*/

    /*public void createOrUpdateSavingsWithImage(final CustomerDailyReport dailyReport, Uri imageUri, final OnCreatedListener onCreatedListener) {
        String imageTitle = Utils.generateImageTitle(UploadImagePrefix.SAVINGS, dailyReport.getRecordNo());
        UploadTask uploadTask = databaseHelper.uploadImage(imageUri, imageTitle);

        if (uploadTask != null) {
            uploadTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUrl = task.getResult().getUploadSessionUri();
                    LogUtil.logDebug(TAG, "successful upload image, image url: " + String.valueOf(downloadUrl));

                    if (downloadUrl != null) {
                        dailyReport.setSavingsDoc(Uri.parse(downloadUrl.toString()));
                    }
                    createOrUpdateSavings(dailyReport, (coop.skylightapp.Interfaces.OnCreatedListener) OnCreatedListener);

                } else {
                    onCreatedListener.onCreated(false);
                    LogUtil.logDebug(TAG, "fail to upload image");
                }

            });
        } else {
            //OnCreatedListener.(false);
            LogUtil.logDebug(TAG, "fail to upload image");
        }
    }*/
    public void updateSavings(String savingsId, String savingsDetails, String packageId, final OnTaskCompleteListener onTaskCompleteListener) {
        /*DatabaseReference mSavingsReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.PACKAGE_DB_KEY).child(packageId).child(savingsId).child("text");
        mSavingsReference.setValue(savingsDetails).addOnSuccessListener(aVoid -> {
            if (onTaskCompleteListener != null) {
                onTaskCompleteListener.onTaskComplete(true);
            }
        }).addOnFailureListener(e -> {
            if (onTaskCompleteListener != null) {
                onTaskCompleteListener.onTaskComplete(false);
            }
            //LogUtil.logError(TAG, "updateComment", e);
        });*/
    }

    public void isSavingsExist(String id, final OnSavingsExistListener<CustomerDailyReport> onObjectExistListener) {
       /* DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child("profiles").child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onObjectExistListener.onDataChanged(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ValueEventListener getSavings(String id, final OnSavingsExistListener<CustomerDailyReport> listener) {
        DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY).child(id);
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CustomerDailyReport customerDailyReport = dataSnapshot.getValue(CustomerDailyReport.class);
                listener.onObjectChanged(customerDailyReport);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.getMessage());
                LogUtil.logError(TAG, "getProfile(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });
        databaseHelper.addActiveListener(valueEventListener, databaseReference);
        return valueEventListener;
    }

    public void getSavingsSingleValue(String id, final OnTellerReportChangeListener<CustomerDailyReport> listener) {
        DatabaseReference databaseReference = databaseHelper.getDatabaseReference().child(DatabaseHelper.SAVINGS_KEY).child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CustomerDailyReport customerDailyReport = dataSnapshot.getValue(CustomerDailyReport.class);
                listener.onSavingsChanged(customerDailyReport);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.getMessage());
                LogUtil.logError(TAG, "getSavingsSingleValue(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });
    }

    public void updatePackageCountAfterRemovingSavings(SkyLightPackage lightPackage) {
        DatabaseReference profileRef = databaseHelper
                .getDatabaseReference()
                .child(DatabaseHelper.PACKAGE_DB_KEY + "/" + lightPackage.getPackageId() + "/likesCount");
        final long packageCount = lightPackage.getCount();

        profileRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue != null && currentValue >= packageCount) {
                    mutableData.setValue(currentValue - packageCount);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                LogUtil.logInfo(TAG, "Updating likes count transaction is completed.");
            }
        });

    }

    public void addRegistrationToken(String token, String userId) {
        DatabaseReference task = databaseHelper
                .getDatabaseReference()
                .child(DatabaseHelper.SAVINGS_KEY)
                .child(userId).child("notificationTokens");
                //.child(token).setValue(true);
        //task.addOnCompleteListener(task1 -> LogUtil.logDebug(TAG, "addRegistrationToken, success: " + task1.isSuccessful()));
    }

    public ValueEventListener searchSavings(String searchText, OnDataChangedListener<Profile> onDataChangedListener) {
        DatabaseReference reference = databaseHelper.getDatabaseReference().child(DatabaseHelper.PROFILES_DB_KEY);
        ValueEventListener valueEventListener = getSearchQuery(reference, searchText).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Profile> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profile profile = snapshot.getValue(Profile.class);
                    list.add(profile);
                }
                onDataChangedListener.onListChanged(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogUtil.logError(TAG, "searchProfiles(), onCancelled", new Exception(databaseError.getMessage()));
            }
        });

        databaseHelper.addActiveListener(valueEventListener, reference);
        return valueEventListener;
    }

    private Query getSearchQuery(DatabaseReference databaseReference, String searchText) {
        return databaseReference
                .orderByChild("savingId")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");*/
    }
}
