package com.skylightapp.TableViews;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.skylightapp.R;

public class AwajimaCusCellViewHolder extends AbstractViewHolder {
    @NonNull
    public final ImageView cell_image;

    public AwajimaCusCellViewHolder(@NonNull View itemView) {
        super(itemView);
        cell_image = itemView.findViewById(R.id.cell_image);
    }

    public void setData(Object data) {
        int mood = (int) data;
        int moodDrawable = mood == AwajimaCusViewModel.BUSINESS ? R.drawable.ic_admin_user : R.drawable.ic_users;

        cell_image.setImageResource(moodDrawable);
    }
}
