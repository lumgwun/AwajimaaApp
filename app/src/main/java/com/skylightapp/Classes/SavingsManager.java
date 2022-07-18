package com.skylightapp.Classes;

import android.content.Context;

import com.skylightapp.Interfaces.OnDataChangedListener;
import com.skylightapp.Interfaces.OnTaskCompleteListener;


public class SavingsManager extends FirebaseListenersManager {

    private static final String TAG = SavingsManager.class.getSimpleName();
    private static SavingsManager instance;

    private Context context;
    SavingsInteractor commentInteractor;

    public static SavingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SavingsManager(context);
        }

        return instance;
    }

    private SavingsManager(Context context) {
        this.context = context;
        commentInteractor = SavingsInteractor.getInstance(context);
    }


    public void createOrUpdateComment(String commentText, String postId, OnTaskCompleteListener onTaskCompleteListener) {
        //commentInteractor.createComment(commentText, postId, onTaskCompleteListener);
    }

    public void decrementCommentsCount(String postId, OnTaskCompleteListener onTaskCompleteListener) {
        //commentInteractor.decrementCommentsCount(postId, onTaskCompleteListener);
    }

    public void getCommentsList(Context activityContext, String postId, OnDataChangedListener<CustomerDailyReport> onDataChangedListener) {
        //ValueEventListener valueEventListener = commentInteractor.getCommentsList(postId, onDataChangedListener);
        //addListenerToMap(activityContext, valueEventListener);
    }

    public void removeComment(String commentId, final String postId, final OnTaskCompleteListener onTaskCompleteListener) {
       // commentInteractor.removeComment(commentId, postId, onTaskCompleteListener);
    }

    public void updateComment(String commentId, String commentText, String postId, OnTaskCompleteListener onTaskCompleteListener) {
       // commentInteractor.updateComment(commentId, commentText, postId, onTaskCompleteListener);
    }
}
