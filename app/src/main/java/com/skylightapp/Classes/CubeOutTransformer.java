package com.skylightapp.Classes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

public class CubeOutTransformer extends RecyclerView.ItemAnimator implements ViewPager.PageTransformer {
    public static final float CUBE_ANGLE_45 = 45f, CUBE_ANGLE_60 = 60f, CUBE_ANGLE_90 = 90f;
    public float angleToRotateAt = CUBE_ANGLE_45;

    public CubeOutTransformer() {

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

    public CubeOutTransformer(float cubeAngle) {
        this.angleToRotateAt = cubeAngle;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        int width = page.getWidth();
        int height = page.getHeight();

        if (position <= 0) {
//            If used page.setRotation(0) here, and the right page comes into focus(i.e. swiped left)
//            Then only the right page will be animated(entering the screen) and not the current page (leaving the screen)
//            So here, needed to animate the page leaving the screen
            page.setPivotX(width);
            page.setPivotY(height / 2);
//            page.setRotationY(45f * position);
            page.setRotationY(angleToRotateAt * position);
        } else if (position <= 1) {
//            Log.d("POSITION", position * height + "!!");
            page.setPivotX(0f);
            page.setPivotY(height / 2);
//            page.setRotationY(45f * position);
            page.setRotationY(angleToRotateAt * position);
        } else {
            page.setRotation(0);
        }
    }
}
