package com.skylightapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skylightapp.R;

public class ImageListAdapter extends ArrayAdapter<String> {
    String[] color_names;
    Integer[] image_id;
    Context context;

    public ImageListAdapter(Activity context, Integer[] image_id, String[] text) {
        super(context, R.layout.item_market, text);
        this.color_names = text;
        this.image_id = image_id;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.item_commodity, null, true);
        TextView textView = single_row.findViewById(R.id.config_title);
        ImageView imageView = single_row.findViewById(R.id.config_icon);

        textView.setText(color_names[position]);
        imageView.setImageResource(image_id[position]);
        return single_row;
    }
}
