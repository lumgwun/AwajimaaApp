package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> implements View.OnClickListener {
    private final Context context;
    private final List<Loan> loans;

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView status,  startDate
                , loanBalance,txtLoanID, endDate, txtLoanAmount;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            txtLoanID = view.findViewById(R.id.LoanID);
            txtLoanAmount = view.findViewById(R.id.LoanAmount);
            startDate = view.findViewById(R.id.loanDate1);
            loanBalance = view.findViewById(R.id.balanceLoan24);
            status = view.findViewById(R.id.statusLoan77);
            endDate = view.findViewById(R.id.loanEndDate);
            thumbnail = view.findViewById(R.id.thumbnail_loan);

            Customer customer = new Customer();
            Profile profile = new Profile();
            Loan loan = new Loan();



        }
    }


    public LoanAdapter(Context context, List<Loan> loans) {
        this.context = context;
        this.loans = loans;
    }

    @NonNull
    @Override
    public LoanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loan_item_cus, parent, false);

        return new LoanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoanAdapter.MyViewHolder holder, final int position) {
        final Loan loan = loans.get(position);
        final Profile profile = loan.getProfile();
        final Account account = loan.getAccount();
        final Customer customer = loan.getCustomer();
        holder.txtLoanID.setText(MessageFormat.format("Loan ID:{0}", loan.getLoanId()));
        holder.txtLoanAmount.setText(MessageFormat.format("N{0}", loan.getAmount1()));
        holder.status.setText(loan.getStatus());
        holder.startDate.setText(MessageFormat.format("Start Date:{0}", loan.getStartDate()));
        holder.endDate.setText(("End Date"+loan.getEndDate()));
        holder.loanBalance.setText(MessageFormat.format("NGN{0}", loan.getAccountBalance()));


    }

    @Override
    public int getItemCount() {
        return (null != loans ? loans.size() : 0);
    }
    public interface OnLoanInteractionListener {
        void onItemClick(Loan item);
    }

}

