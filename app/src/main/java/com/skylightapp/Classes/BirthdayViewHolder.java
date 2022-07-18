package com.skylightapp.Classes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;


public class BirthdayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // TextView references
    private TextView textDateDay;

    private TextView textPhoneNo;
    private TextView textName;
    private TextView celebrantGender;
    private TextView textOffice;
    private CircleImageView celebrantPix;
    private View container;
    private DBHelper dbHelper;
    Birthday birthday;

    public BirthdayViewHolder(View itemView) {
        super(itemView);
        container = itemView;
        celebrantPix = (CircleImageView) itemView.findViewById(R.id.bdPix);
        textName = (TextView) itemView.findViewById(R.id.nameOfUser);
        textDateDay = (TextView) itemView.findViewById(R.id.dob);
        celebrantGender = (TextView) itemView.findViewById(R.id.dobGender);
        textOffice = (TextView) itemView.findViewById(R.id.officeBD);
        textPhoneNo = (TextView) itemView.findViewById(R.id.phoneN);
    }

    public void setTag(Birthday birthday) {
        container.setTag(birthday);

    }

    public void showView() {
        container.setVisibility(View.VISIBLE);
    }

    public void setText(String name) {
        textName.setText(name);
    }

    public void setDOB(String date) {
        textDateDay.setText(date);
    }
    public void setGender(String gender) {
        celebrantGender.setText(gender);
    }
    public void setOffice(String office) {
        textOffice.setText(office);
    }
    public void setPhoneNo(String phone) {
        textPhoneNo.setText(phone);
    }



    @Override
    public void onClick(View view) {
        Birthday birthday = (Birthday) view.getTag();

        int id = view.getId();

    }


}
