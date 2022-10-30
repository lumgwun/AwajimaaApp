package com.skylightapp.Classes;

import android.content.Context;

import com.google.android.gms.tasks.Task;

import com.skylightapp.Interfaces.LoginView;
import com.skylightapp.R;


public class LoginPresenter extends BasePresenter<LoginView> {
    boolean exist=false;


    public LoginPresenter(Context context) {
        super(context);
    }

    /*public void checkIsProfileExist(final String userId) {
        ProfileManager.getInstance(context).isProfileExist(userId, exist -> {
            ifViewAttached(view -> {
                if (!exist) {
                    view.startCreateProfileActivity();
                } else {
                    PrefManager.setProfileCreated(context, true);
                   // ProfileInteractor.getInstance(context.getApplicationContext())
                            //.addRegistrationToken(FirebaseInstanceId.getInstance().getToken(), userId);
                }

                view.hideProgress();
                view.finish();
            });
        });
    }*/

    public void onGoogleSignInClick() {
        if (checkInternetConnection()) {
            ifViewAttached(LoginView::signInWithGoogle);
        }
    }



    /*public void handleGoogleSignInResult(GoogleSignInResult result) {
        ifViewAttached(view -> {
            if (result.isSuccess()) {
                view.showProgress();

                GoogleSignInAccount account = result.getSignInAccount();

                if (account != null) {
                    view.setProfilePhotoUrl(buildGooglePhotoUrl(account.getPhotoUrl()));
                }

                AuthCredential credential = null;
                if (account != null) {
                    credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                }
                view.firebaseAuthWithCredentials(credential);

                LogUtil.logDebug(TAG, "firebaseAuthWithGoogle:" + account.getId());

            } else {
                LogUtil.logDebug(TAG, "SIGN_IN_GOOGLE failed :" + result);
                view.hideProgress();
            }
        });
    }



    private String buildGooglePhotoUrl(Uri photoUrl) {
        return String.format(context.getString(R.string.google_large_image_url_pattern),
                photoUrl, Utils.Profile.MAX_AVATAR_SIZE);
    }*/



}
