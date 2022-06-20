package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skylightapp.Classes.GridItem;
import com.skylightapp.R;

import java.util.ArrayList;


public class MainGridItemAdapter extends ArrayAdapter<GridItem> {

    public MainGridItemAdapter(@NonNull Context context, ArrayList<GridItem> gridModelArrayList) {
        super(context, 0, gridModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.

            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.main_grid_item, parent, false);
        }
        GridItem gridItems = getItem(position);
        TextView titleTV = listitemView.findViewById(R.id.iditemTitle);

        ImageView gridItemIV = listitemView.findViewById(R.id.idImage);
        titleTV.setText(gridItems.getGridTitle());
        gridItemIV.setImageResource(gridItems.getImgid());
        return listitemView;
    }
}
