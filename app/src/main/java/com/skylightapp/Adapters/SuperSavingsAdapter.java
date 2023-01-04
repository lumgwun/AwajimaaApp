package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.ProfileManager;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Interfaces.OnTellerReportChangeListener;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;



public class SuperSavingsAdapter extends RecyclerView.Adapter<SuperSavingsAdapter.RecyclerViewHolder>{

    private ArrayList<CustomerDailyReport> savings;
    private Context mcontext;
    int resources;
    private List<CustomerDailyReport> list = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static ProfileManager profileManager;
    private Context context;
    int position;
    PaymDocDAO paymDocDAO;
    CustomerDailyReport savings1;
    int savingsID;
    private DBHelper dbHelper;
    String savingsStatus;
    private OnItemsClickListener listener;
    TextViewCompat txtSavingsStatus;
    private  ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<Intent> mGetContent;
    private final MutableLiveData<Bitmap> mThumbnailLiveData = new MutableLiveData();
    private long savingsCode;
    private PaymentDoc document;
    private MarketBizPackage marketBizPackage;
    private double total;

    public SuperSavingsAdapter(ArrayList<CustomerDailyReport> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public SuperSavingsAdapter(View itemView, final Callback callback) {
        super();

        this.callback = callback;
        this.context = itemView.getContext();
        profileManager = ProfileManager.getInstance(itemView.getContext().getApplicationContext());

        //avatarImageView = (AppCompatImageView) itemView.findViewById(R.id.avatarImageView);
        //commentTextView = (ExpandableTextView) itemView.findViewById(R.id.commentText);


        if (callback != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (position != RecyclerView.NO_POSITION) {
                        callback.onLongItemClick(v, position);
                        return true;
                    }

                    return false;
                }
            });
        }
    }

    public SuperSavingsAdapter(Context context, int resources, ArrayList<CustomerDailyReport> savings) {
        this.savings = savings;
        this.mcontext = context;
        this.resources = resources;

    }

    public SuperSavingsAdapter(Context context, ArrayList<CustomerDailyReport> savings) {
        this.savings = savings;
        this.mcontext = context;

    }
    private static OnTellerReportChangeListener<CustomerDailyReport> createOnSavingsChangeListener(final ExpandableTextView expandableTextView,
                                                                                                   final AppCompatImageView avatarImageView,
                                                                                                   final CustomerDailyReport comment,
                                                                                                   final AppCompatTextView dateTextView) {
        return new OnTellerReportChangeListener<CustomerDailyReport>() {
            @Override
            public void onSavingsChanged(CustomerDailyReport obj) {
                long savingsID = obj.getRecordID();
                //fillSavings(savingsID, comment, expandableTextView, dateTextView);

                if (obj.getRecordID()>0) {

                    //ImageUtil.loadImage(GlideApp.with(context), obj.getSavingsDoc(), avatarImageView);
                }
            }

            @Override
            public void onError(String errorText) {
                // fillSavings("", comment, commentTextView, dateTextView);
            }
        };
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savings_row, parent, false);
        return new RecyclerViewHolder(view);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        savings1 = this.savings.get(position);
        if(savings1 !=null){
            document = savings1.getRecordPaymentDoc();
            savingsCode = savings1.getRecordSavingsCode();
            marketBizPackage =savings1.getRecordPackage();

        }
        if(marketBizPackage !=null){
            total= marketBizPackage.getPackageTotalAmount();

        }

        Bitmap docId= null;
        if (savings1 != null) {
            docId = paymDocDAO.getDocPicture(savings1.getRecordID());
        }
        if (savings1 != null) {
            holder.amountRemaining.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", savings1.getRecordAmountRemaining())));
        }
        holder.totalAmount.setText(MessageFormat.format("Total: NGN{0}", String.format("%.2f", total)));
        if (savings1 != null) {
            holder.savingsAmount.setText(MessageFormat.format("Package Amount: NGN{0}", String.format("%.2f", savings1.getRecordAmount())));
        }

        if (savings1 != null) {
            holder.status.setText(MessageFormat.format("Status:{0}", savings1.getRecordStatus()));
        }
        if (savings1 != null) {
            holder.savingsID.setText(MessageFormat.format("Start Date:{0}", savings1.getRecordID()));
        }
        if (savings1 != null) {
            holder.date.setText(MessageFormat.format("Savings date:{0}", savings1.getRecordDate()));
        }
        if (savings1 != null) {
            holder.daysRemaining.setText(MessageFormat.format("Days Rem:{0}", String.valueOf(savings1.getRecordRemainingDays())));
        }
        if (savings1 != null) {
            holder.days.setText(MessageFormat.format("Number of Days:{0}", String.valueOf(savings1.getRecordNumberOfDays())));
        }
        if (savings1 != null) {
            holder.savingsCount.setText(MessageFormat.format("Number of Saving:{0}", String.valueOf(savings1.getCount())));
        }
        holder.paymentMethod.setText(MessageFormat.format("Method:{0}", document.getPaymentMethod()));
        holder.savingsCode.setText(MessageFormat.format("Savings Code:{0}", String.valueOf(savingsCode)));
        if (savings != null) {
            holder.savingsID.setText(MessageFormat.format("Savings ID:{0}", String.valueOf(savings1.getRecordID())));
        }
        holder.paymentDoc.setImageBitmap( docId);
        if (savings != null) {
            holder.days.setText(MessageFormat.format("Number of Days:{0}", String.valueOf(savings1.getRecordNumberOfDays())));
        }

        if (savings1 != null) {
            if (savings1.getRecordStatus().equalsIgnoreCase("verified")) {
                holder.icon.setImageResource(R.drawable.verified_savings);
                holder.savingsID.setTextColor(Color.MAGENTA);
            } else if (savings1.getRecordStatus().equalsIgnoreCase(""))  {
                holder.icon.setImageResource(R.drawable.unverified);
                holder.savingsID.setTextColor(Color.RED);
            } else if (savings1.getRecordStatus().equalsIgnoreCase("Unverified"))  {
                holder.icon.setImageResource(R.drawable.unverified);
                holder.savingsID.setTextColor(Color.RED);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Take Actions on Savings");
                    builder.setIcon(R.drawable.marker_);
                    builder.setItems(new CharSequence[]
                                    {"Delete Savings","Update Savings Status"},
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Toast.makeText(builder.getContext(), " Deleting Savings", Toast.LENGTH_SHORT).show();
                                            areYouSure();

                                            break;

                                        case 1:
                                            Toast.makeText(builder.getContext(), " Updating Savings Status", Toast.LENGTH_SHORT).show();
                                            updateStatus();

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
                                    dialog.cancel();
                                }
                            });

                    builder.create().show();


                }
            });

        }

    }
    private void updateStatus(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext.getApplicationContext());
        builder.setTitle("Update Savings Status");
        dbHelper = new DBHelper(mcontext.getApplicationContext());
        savings1 = this.savings.get(position);
        if(savings1 !=null){
            savingsID=savings1.getRecordID();
        }
        final EditText input = new EditText(mcontext.getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setIcon(R.drawable.ic_icon2);
        builder.setMessage("Enter Confirmed,Verified, or UnConfirmed");

        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        savingsStatus = input.getText().toString();
                        Toast.makeText(builder.getContext(), " Updating Savings Status", Toast.LENGTH_SHORT).show();
                        dbHelper.UpdateSavingsStatus(savingsStatus,savingsID);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();

    }
    public void areYouSure(){
        dbHelper = new DBHelper(mcontext.getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext.getApplicationContext());
        builder.setTitle("Confirmation Actions before Deleting Savings");
        savings1 = this.savings.get(position);
        if(savings1 !=null){
            savingsID=savings1.getRecordID();
        }
        builder.setIcon(R.drawable.marker_);
        builder.setItems(new CharSequence[]
                        {"Yes", "No"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(builder.getContext(), " Deleting Savings", Toast.LENGTH_SHORT).show();
                            dbHelper.deleteSavings(savingsID);
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
                        dialog.cancel();
                    }
                });

        builder.create().show();


    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView savingsID, savingsCode,savingsCount, date, status, amountRemaining, daysRemaining, savingsAmount;
        public AppCompatImageView icon,paymentDoc;
        private TextView paymentMethod, totalAmount, days;
        public AppCompatButton btnMore;
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.imgSavings);
            savingsCode = convertView.findViewById(R.id.paymentCode24);
            paymentMethod = convertView.findViewById(R.id.paymentMethod24);
            paymentDoc = convertView.findViewById(R.id.paymentDoc);
            savingsID = convertView.findViewById(R.id.savings_ID);
            date = convertView.findViewById(R.id.savings_dater);
            savingsCount = convertView.findViewById(R.id.number_No);
            days = convertView.findViewById(R.id.number_day21);
            totalAmount = convertView.findViewById(R.id.totalsaving2s);
            daysRemaining = convertView.findViewById(R.id.daysRem24);
            amountRemaining = convertView.findViewById(R.id.amount_rem24);
            status = convertView.findViewById(R.id.statusSavings);

        }
    }


    @Override
    public int getItemCount() {
        return (null != savings ? savings.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onSavingClick(CustomerDailyReport customerDailyReport, View view);
        void onAuthorClick(String authorId, View view);
    }


    public CustomerDailyReport getItemByPosition(int position) {
        return list.get(position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setList(List<CustomerDailyReport> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(CustomerDailyReport customerDailyReport);
    }
}
