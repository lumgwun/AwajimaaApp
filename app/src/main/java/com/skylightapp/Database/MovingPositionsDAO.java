package com.skylightapp.Database;

import static com.skylightapp.MapAndLoc.Region.REGION_ID;

import android.content.Context;

import com.skylightapp.MapAndLoc.MovingPosition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MovingPositionsDAO extends DBHelperDAO{

    @Nullable
    public final Void lastMovingPosition;

    public MovingPositionsDAO(Context context) {
        super(context);
        lastMovingPosition = null;
    }

    @NotNull
    public MovingPosition createMovingPosition(@NotNull MovingPosition movingPosition) {
        return null;
    }
}
