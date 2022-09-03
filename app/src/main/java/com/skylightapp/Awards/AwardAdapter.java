package com.skylightapp.Awards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;

public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.MyViewHolder> {
    Context mContext;
    AwardListener mListener;
    String prefix = "";
    private List<Award> awardList;
    private ArrayList<Award> awardArrayList;
    private Award award;
    Profile userProfile;
    long profileID, awardGiverID;
    private AwardDAO awardDAO;

    public AwardAdapter(Context context, ArrayList<Award> awardArrayList) {
        this.awardArrayList = awardArrayList;
        this.mContext = context;

    }

    public AwardAdapter(ArrayList<Award> awardArrayList) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_row_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int position) {
        if (awardList.size() <= position) {
            return;
        }
        final Award award = awardList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        viewHolder.awardTittle.setText(MessageFormat.format("Award Tittle:{0}", award.getAwardTittle()));
        viewHolder.textDescription.setText(MessageFormat.format("Award Desc:{0}", award.getAwardDesc()));
        viewHolder.textDate.setText(MessageFormat.format("Date:{0}", award.getAwardDate()));
        viewHolder.startDate.setText(String.valueOf("Start Date:" + award.getAwardStartDate()));
        viewHolder.endDate.setText(MessageFormat.format("End Date:{0}", award.getAwardEndDate()));
        viewHolder.textStatus.setText(MessageFormat.format("Status:{0}", award.getAwardStatus()));
        viewHolder.awardID.setText(MessageFormat.format("Award ID:{0}", award.getAwardID()));
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userProfile != null) {
                    profileID = userProfile.getPID();
                    awardGiverID = award.getAwardGiverProfile().getPID();
                    if (award != null) {
                        if (awardGiverID == profileID) {
                            goDialog(award);
                        }

                    }
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != awardArrayList ? awardArrayList.size() : 0);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setAwardList(ArrayList<? extends Award> codes) {
        awardArrayList.clear();
        awardArrayList.addAll(codes);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(Award award) {
        awardArrayList.add(award);
        notifyDataSetChanged();
    }

    public interface AwardListener {
        void onItemClick(Award award);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        AppCompatImageView icon, addNewNomination;
        AppCompatTextView textView;
        AppCompatTextView awardID;
        TextView textDescription;
        TextView awardTittle;
        TextView textDate;
        TextView textStatus, startDate, endDate;
        Award award;


        MyViewHolder(View itemView) {
            super(itemView);
            icon = (AppCompatImageView) itemView.findViewById(R.id.imageAwardA);
            awardID = itemView.findViewById(R.id.TittleAwardID);
            awardTittle = itemView.findViewById(R.id.TittleAwardA);
            textDescription = itemView.findViewById(R.id.AwardDescript);
            textDate = itemView.findViewById(R.id.awardDate);
            textStatus = itemView.findViewById(R.id.awardStatus);
            startDate = itemView.findViewById(R.id.awardStartDate);
            endDate = itemView.findViewById(R.id.awardEndDate);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userProfile != null) {
                        profileID = userProfile.getuID();
                        awardGiverID = award.getAwardGiverProfile().getuID();
                        if (award != null) {
                            if (awardGiverID == profileID) {
                                goDialog();
                            }

                        }
                    }

                }
            });*/
        }


        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onItemClick(award);
            }

        }

        public void setData(String nominationName) {

        }
    }
    private void  goDialog(Award award){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getApplicationContext());
        builder.setTitle("Choose Award Action");
        builder.setItems(new CharSequence[]
                        {"delete", "Update"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(mContext.getApplicationContext(), "Delete option, selected ", Toast.LENGTH_SHORT).show();
                                deleteFromDB(award);
                                break;
                            case 1:
                                Toast.makeText(mContext.getApplicationContext(), "Delete Choice, made", Toast.LENGTH_SHORT).show();
                                updateInDB(award);
                                break;
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create().show();

    }

    private  void deleteFromDB(Award award){
        DBHelper dbHelper=new DBHelper(mContext.getApplicationContext());
        long profileID = 0, awardGiverID,awardID = 0;
        Profile userProfile= new Profile();
        if(userProfile !=null){
            profileID=userProfile.getPID();
            awardDAO= new AwardDAO(mContext.getApplicationContext());
            awardGiverID =award.getAwardGiverProfile().getPID();
            if(award !=null){
                awardID=award.getAwardID();
                if(awardGiverID ==profileID){
                    awardDAO.deleteAward(awardID);
                }

            }
        }


    }
    private  void updateInDB(Award award){
        DBHelper dbHelper=new DBHelper(mContext.getApplicationContext());
        long profileID = 0, awardGiverID,awardID = 0;
        Profile userProfile= new Profile();
        if(userProfile !=null){
            profileID=userProfile.getPID();
            awardGiverID =award.getAwardGiverProfile().getPID();
            if(award !=null){
                awardID=award.getAwardID();
                if(awardGiverID ==profileID){
                    //dbHelper.updateAward(awardID);
                }

            }
        }


    }
}
