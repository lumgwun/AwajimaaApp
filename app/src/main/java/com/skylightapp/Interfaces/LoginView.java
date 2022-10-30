package com.skylightapp.Interfaces;


public interface LoginView extends BaseView {
    void startCreateProfileActivity();

    void signInWithGoogle();

    void signInWithUserNameAndPassword();



    void setProfilePhotoUrl(String url);

    //void firebaseAuthWithCredentials(AuthCredential credential);
}
