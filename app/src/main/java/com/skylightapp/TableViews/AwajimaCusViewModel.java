package com.skylightapp.TableViews;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AwajimaCusViewModel {
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int CUS_TYPE_COLUMN_INDEX = 4;


    public static final int INDIVIDUAL = 1;
    public static final int BUSINESS = 2;

    public static final int INVESTORS = 1;
    public static final int REGULATORS = 2;

    public static final int LOCAL = 1;
    public static final int INTERNATIONAL = 2;

    public static final int ACTIVE = 1;
    public static final int NOT_ACTIVE = 2;

    private static final int COLUMN_SIZE = 500;
    private static final int ROW_SIZE = 500;

    // Drawables
    @DrawableRes
    private  int mIndividualPhoto;
    @DrawableRes
    private  int mBusinessPhoto;
    @DrawableRes
    private  int mInvestorPhoto;
    @DrawableRes
    private  int mRegulatorPhoto;

    private  int mLocalPhoto;
    @DrawableRes
    private  int mInternationalPhoto;

    private  int mActivePhoto;
    @DrawableRes
    private  int mNonActivePhoto;

    public AwajimaCusViewModel() {

        mIndividualPhoto = R.drawable.ic_admin_user;
        mBusinessPhoto = R.drawable.users_fg;
        mInvestorPhoto = R.drawable.org;
        mRegulatorPhoto = R.drawable.regulator;
    }

    @NonNull
    private List<CusRowHeader> getSimpleRowHeaderList() {
        List<CusRowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            CusRowHeader header = new CusRowHeader(String.valueOf(i), "row " + i);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<CusColumnHeader> getRandomColumnHeaderList() {
        List<CusColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column " + i;
            }

            CusColumnHeader header = new CusColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<CusCell>> getCellListForSortingTest() {
        List<List<CusCell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<CusCell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "cell " + j + " " + i;

                final int random = new Random().nextInt();
                if (j == 0) {
                    text = i;
                } else if (j == 1) {
                    text = random;
                } else if (j == MOOD_COLUMN_INDEX) {
                    text = random % 2 == 0 ? BUSINESS : INDIVIDUAL;
                } else if (j == CUS_TYPE_COLUMN_INDEX) {
                    text = random % 2 == 0 ? INVESTORS : REGULATORS;
                }

                // Create dummy id.
                String id = j + "-" + i;

                CusCell cell;
                if (j == 3) {
                    cell = new CusCell(id, text);
                } else if (j == 4) {
                    // NOTE female and male keywords for filter will have conflict since "female"
                    // contains "male"
                    cell = new CusCell(id, text);
                } else {
                    cell = new CusCell(id, text);
                }
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    @DrawableRes
    public int getDrawable(int value, boolean isGender) {
        if (isGender) {
            return value == INVESTORS ? mIndividualPhoto : mBusinessPhoto;
        } else {
            return value == INDIVIDUAL ? mRegulatorPhoto : mInvestorPhoto;
        }
    }

    @NonNull
    public List<List<CusCell>> getCellList() {
        return getCellListForSortingTest();
    }

    @NonNull
    public List<CusRowHeader> getRowHeaderList() {
        return getSimpleRowHeaderList();
    }

    @NonNull
    public List<CusColumnHeader> getColumnHeaderList() {
        return getRandomColumnHeaderList();
    }
}
