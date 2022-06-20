package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.ExpandableTextView;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.ProfileManager;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.OnTellerReportChangeListener;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.RecyclerViewHolder> implements View.OnClickListener, Filterable {


    private ArrayList<CustomerDailyReport> savings;
    private Context mcontext;
    int resources;
    private List<CustomerDailyReport> list = new ArrayList<>();
    private List<CustomerDailyReport> customerDailyReports = new ArrayList<>();
    private Callback callback;
    private AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private static ProfileManager profileManager;
    private Context context;
    int position;
    private OnItemsClickListener listener;
    private CustomerDailyReport customerDailyReport;

    public SavingsAdapter(ArrayList<CustomerDailyReport> reports, Context mcontext) {
        this.savings = reports;
        this.mcontext = mcontext;
    }
    public SavingsAdapter(View itemView, final Callback callback) {
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

    public SavingsAdapter(Context context, int resources, ArrayList<CustomerDailyReport> savings) {
        this.savings = savings;
        this.mcontext = context;
        this.resources = resources;

    }

    public SavingsAdapter(Context context, ArrayList<CustomerDailyReport> savings) {
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
                long savingsID = obj.getRecordNo();
                //fillSavings(savingsID, comment, expandableTextView, dateTextView);

                if (obj.getImage_id()>0) {

                    //ImageUtil.loadImage(GlideApp.with(context), obj.getSavingsDoc(), avatarImageView);
                }
            }

            @Override
            public void onError(String errorText) {
               // fillSavings("", comment, commentTextView, dateTextView);
            }
        };
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savings_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }
    /*public static void bindData(CustomerDailyReport customerDailyReport) {
        final String authorId = String.valueOf(customerDailyReport.getRecordNo());

        if (authorId != null) {
            profileManager.getProfileSingleValue(authorId, createOnSavingsChangeListener(commentTextView,
                    avatarImageView, customerDailyReport, dateTextView));
        } else {
            fillSavings("", customerDailyReport, commentTextView, dateTextView);
        }

        avatarImageView.setOnClickListener(v -> callback.onAuthorClick(authorId, v));
    }
    private static void fillSavings(long userName, CustomerDailyReport comment, ExpandableTextView commentTextView, AppCompatTextView dateTextView) {
        Spannable contentString = new SpannableStringBuilder(userName + "   " + comment.getText());
        contentString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.highlight_text)),
                0, userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        commentTextView.setText(contentString);

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(context, comment.getCreatedDate());
        dateTextView.setText(date);
    }*/



    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setLongClickable(true);
        //SavingsAdapter.bindData(getItemByPosition(position));
        CustomerDailyReport savings = this.savings.get(position);
        PaymentDoc document = (PaymentDoc) this.savings.get(position);
        PaymentCode savingsCode = (PaymentCode) this.savings.get(position);
        DBHelper dbHelper = new DBHelper(mcontext.getApplicationContext());
        Bitmap docId=dbHelper.getDocPicture(savings.getRecordNo());
        holder.amountRemaining.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", savings.getAmountRemaining())));
        holder.totalAmount.setText(MessageFormat.format("Total: NGN{0}", String.format("%.2f", savings.getTotal())));
        holder.savingsAmount.setText(MessageFormat.format("Package Amount: NGN{0}", String.format("%.2f", savings.getAmount())));
        holder.packageID.setText(MessageFormat.format("Package ID:{0}", String.valueOf(savings.getPackageId())));
        holder.status.setText(MessageFormat.format("Status:{0}", savings.getStatus()));
        holder.savingsID.setText(MessageFormat.format("Start Date:{0}", savings.getRecordNo()));
        holder.date.setText(MessageFormat.format("Savings date:{0}", savings.getRecordDate()));
        holder.daysRemaining.setText(MessageFormat.format("Days Rem:{0}", String.valueOf(savings.getRemainingDays())));
        holder.days.setText(MessageFormat.format("Number of Days:{0}", String.valueOf(savings.getNumberOfDays())));
        holder.savingsCount.setText(MessageFormat.format("Number of Saving:{0}", String.valueOf(savings.getCount())));
        holder.paymentMethod.setText(MessageFormat.format("Method:{0}", document.getPaymentMethod()));
        holder.savingsCode.setText(MessageFormat.format("Savings Code:{0}", String.valueOf(savingsCode.getCode())));
        holder.savingsID.setText(MessageFormat.format("Savings ID:{0}", String.valueOf(savings.getRecordNo())));
        holder.paymentDoc.setImageBitmap( docId);
        holder.days.setText(MessageFormat.format("Number of Days:{0}", String.valueOf(savings.getNumberOfDays())));

        if (savings.getStatus().equalsIgnoreCase("confirmed")) {
            holder.icon.setImageResource(R.drawable.verified_savings);
            holder.savingsID.setTextColor(Color.MAGENTA);
        } else if (savings.getStatus().equalsIgnoreCase(""))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.savingsID.setTextColor(Color.RED);
        } else if (savings.getStatus().equalsIgnoreCase("unconfirmed"))  {
            holder.icon.setImageResource(R.drawable.unverified);
            holder.savingsID.setTextColor(Color.RED);
        }


    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(customerDailyReport);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list = customerDailyReports;
                } else {
                    List<CustomerDailyReport> filteredList = new ArrayList<>();
                    for (CustomerDailyReport customerDailyReport : customerDailyReports) {
                        if (customerDailyReport.getOfficeBranch().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(customerDailyReport);
                        }
                    }
                    list = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<CustomerDailyReport>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView savingsID, savingsCode,savingsCount, date, status, amountRemaining, daysRemaining, savingsAmount;
        public AppCompatImageView icon,paymentDoc;
        private TextView paymentMethod, totalAmount, days, packageID;
        public AppCompatButton btnMore;
        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            icon = convertView.findViewById(R.id.saving_icon);
            savingsCode = convertView.findViewById(R.id.saving_code);
            paymentMethod = convertView.findViewById(R.id.payment_method);
            paymentDoc = convertView.findViewById(R.id.saving_doc);
            packageID = convertView.findViewById(R.id.package_ID);
            savingsID = convertView.findViewById(R.id.saving_id);
            date = convertView.findViewById(R.id.savings_date3);
            savingsAmount = convertView.findViewById(R.id.saving_amount);
            savingsCount = convertView.findViewById(R.id.savings_count2);
            days = convertView.findViewById(R.id.no_of_days);
            totalAmount = convertView.findViewById(R.id.total_paid);
            daysRemaining = convertView.findViewById(R.id.days_rem);
            amountRemaining = convertView.findViewById(R.id.amount_remaining1);
            status = convertView.findViewById(R.id.status2);

        }
    }


    @Override
    public int getItemCount() {
        return (null != savings ? savings.size() : 0);
    }

    public interface Callback {
        void onLongItemClick(View view, int position);

        void onPackageClick(String packageId, View view);
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
