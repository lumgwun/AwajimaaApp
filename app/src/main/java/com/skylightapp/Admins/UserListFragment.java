package com.skylightapp.Admins;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.CubeOutTransformer;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.User;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.valueOf;

public class UserListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private static final String TAG = UserListFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/UserListAll";

    private RecyclerView recyclerView;
    private static final String PREF_NAME = "skylight";

    private List<Profile> profiles;
    private UserAdapter mAdapter;
    private ProfDAO profileDao;
    SQLiteDatabase sqLiteDatabase;


    DBHelper dbHelper;
    private OnFragmentInteractionListener listener;


    public UserListFragment() {
    }

    public static UserListFragment newInstance(int columnCount) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user_list, container, false);
        profiles = new ArrayList<Profile>();
        dbHelper = new DBHelper(getContext());
        profileDao = new ProfDAO(getContext());

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = dbHelper.getWritableDatabase();

            for ( int j=0; j<11; j++ ){
                profiles.add(new Profile("Ebuka ","Micheal","Rivers", "michael78", "ofa",963));
                profiles.add(new Profile("Joe","1910","Abuja", "oscar45", "ofa",99663));
                profiles.add(new Profile("Igwenga","1901","Kano", "Igwe4", "ofa",89963));
                System.out.println("element " + j + ": " + profiles.get(j) );

            }


            for ( int j=5; j<profiles.size(); j++ ){
                try {

                    profiles = profileDao.getAllProfileUsers();
                    System.out.println("User: " + j + ": " + profiles.get(j) );

                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }


            }


        }




        recyclerView = view.findViewById(R.id.listUsers);
        mAdapter = new UserAdapter(getActivity(), profiles);
        CarouselLayoutManager linearLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new CubeOutTransformer());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);



        return view;
    }


    class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
        private final Context context;
        private final List<Profile> profileList;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView name, dateJoined, phone_of_user,dob,gender,state
                    , email_of_user,userID,nextOfKin,address,userName,password, userType,status,role, ninName;
            public ImageView profilePix;
            protected CardView userCard;
            protected LinearLayoutManager linear2;
            protected LinearLayout layerDateJ,layerEmail,layerDOB,layerGender,layerAddress;
            Button btnUserTx;
            public Profile item;
            ItemListener mListener;

            public MyViewHolder(View view) {
                super(view);
                userCard = view.findViewById(R.id.UserDetails);
                layerDateJ = view.findViewById(R.id.layerDateJ);
                layerEmail = view.findViewById(R.id.layerEmail);
                layerDOB = view.findViewById(R.id.layerDOB);
                layerGender = view.findViewById(R.id.layerGender);
                layerAddress = view.findViewById(R.id.layerAddress);
                profilePix = view.findViewById(R.id.profile_thumbnail);
                name = view.findViewById(R.id.user_allNames);
                phone_of_user = view.findViewById(R.id.user_tel1);
                dateJoined = view.findViewById(R.id.user_joined_date3);
                dob = view.findViewById(R.id.user_dob3);
                email_of_user = view.findViewById(R.id.user_email1);

                gender = view.findViewById(R.id.user_gender3);
                state = view.findViewById(R.id.user_addressState);
                btnUserTx = view.findViewById(R.id.user_tx);
                nextOfKin = view.findViewById(R.id.balance_text);
                address = view.findViewById(R.id.user_address2);
                userName = view.findViewById(R.id.user_username3);
                userType = view.findViewById(R.id.machineType);
                status = view.findViewById(R.id.user_status2);
                ninName = view.findViewById(R.id.user_NINName1);


                Button more = view.findViewById(R.id.more_details1);
                //final LinearLayout savings_layout =view.findViewById(R.id.savings_layout);
                final LinearLayout linear_userName =view.findViewById(R.id.linear_userName);
                //profilePix.setImageBitmap();
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //savings_layout.setVisibility(View.VISIBLE);
                        linear_userName.setVisibility(View.VISIBLE);
                        layerDateJ.setVisibility(View.VISIBLE);
                        layerEmail.setVisibility(View.VISIBLE);
                        layerDOB.setVisibility(View.VISIBLE);
                        layerGender.setVisibility(View.VISIBLE);
                        layerAddress.setVisibility(View.VISIBLE);


                    }
                });
                btnUserTx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();


                    }
                });


                SwitchCompat simpleSwitch = (SwitchCompat)view.findViewById(R.id.profile_action1);

                User user = new User();
                Profile profile = new Profile();
                Customer customer = new Customer();
                if(profile !=null){
                    customer=profile.getProfileCus();

                }


                String userStatusSwitch = null;
                if (simpleSwitch.isChecked())
                    userStatusSwitch = simpleSwitch.getTextOn().toString();
                if(Objects.requireNonNull(userStatusSwitch).contentEquals("on"))
                    status.setVisibility(View.VISIBLE);
                simpleSwitch.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "User approval :" + userStatusSwitch, Toast.LENGTH_LONG).show();
                DBHelper applicationDb = new DBHelper(getContext());
                applicationDb.updateUser1(profile,customer);

                userCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linear_userName.setVisibility(View.VISIBLE);
                        layerDateJ.setVisibility(View.VISIBLE);
                        layerEmail.setVisibility(View.VISIBLE);
                        layerDOB.setVisibility(View.VISIBLE);
                        layerGender.setVisibility(View.VISIBLE);
                        layerAddress.setVisibility(View.VISIBLE);

                    }
                });
                userCard.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        linear_userName.setVisibility(View.GONE);
                        layerDateJ.setVisibility(View.GONE);
                        layerEmail.setVisibility(View.GONE);
                        layerDOB.setVisibility(View.GONE);
                        layerGender.setVisibility(View.GONE);
                        layerAddress.setVisibility(View.GONE);
                        return false;
                    }
                });

            }
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(item);
                }
            }
        }


        public UserAdapter(Context context, List<Profile> profileList) {
            this.context = context;
            this.profileList = profileList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final Profile profile = profileList.get(position);
            final long savingsCount = profile.getProfileDailyReports().size();
            int profileCount = getItemCount();
            Customer customer=new Customer();
            int customerID=0;
            int profileID=0;
            if(profile !=null){
                profileID = profile.getPID();
                customer=profile.getProfileCus();

            }
            if(customer !=null){
                customerID = customer.getCusUID();

            }


            holder.name.setText(MessageFormat.format("{0},{1}", profile.getProfileLastName(), profile.getProfileFirstName()+"--"+profile.getPID()));
            holder.dob.setText(profile.getProfileDateJoined());
            holder.gender.setText(profile.getProfileGender());
            holder.state.setText(profile.getProfileState());
            holder.nextOfKin.setText(profile.getNextOfKin());
            holder.phone_of_user.setText(profile.getProfilePhoneNumber());
            holder.phone_of_user.setText(profile.getProfilePhoneNumber());
            holder.dateJoined.setText(profile.getProfileDateJoined());
            holder.address.setText(valueOf(profile.getProfileAddress()));
            holder.userName.setText(profile.getProfileUserName());
            holder.email_of_user.setText(profile.getProfileEmail());

            holder.ninName.setText(profile.getProfileIdentity());
            holder.userType.setText(MessageFormat.format("Type{0}", profile.getProfileMachine()));
            holder.status.setText(profile.getProfileStatus());
            holder.role.setText(profile.getProfileMachine());
            int finalProfileID = profileID;
            int finalCustomerID = customerID;
            dbHelper = new DBHelper(getContext());
            sqLiteDatabase = dbHelper.getWritableDatabase();
            Customer finalCustomer = customer;

            holder.btnUserTx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDiag(profile, finalCustomer,holder.userCard, finalProfileID, finalCustomerID,position,dbHelper,sqLiteDatabase);



                }
            });



        }

        @Override
        public int getItemCount() {
            return (null != profileList ? profileList.size() : 0);

        }
    }
    private void  doDiag(Profile profile, Customer finalCustomer, CardView userCard, int profileID, int finalCustomerID, int position, DBHelper dbHelper, SQLiteDatabase sqLiteDatabase){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        Bundle userBundle = new Bundle();
        userBundle.putParcelable("Customer",finalCustomer);
        userBundle.putParcelable("Profile",profile);

       builder.setMessage("Choose User Actions")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent userIntent = new Intent(getContext(), AdminCusActionView.class);
                        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        userIntent.putExtras(userBundle);
                        requireContext().startActivity(userIntent);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clearUsers();

                    }
                });

        builder.create().show();


    }

    public interface ItemListener {
        void onItemClick(Profile item);
    }



    public void clearUsers() {
        profiles.clear();
        notifyAll();
    }
    public ArrayList<Profile> getCustomers() {
        return (ArrayList<Profile>) profiles;
    }

    public void setData(ArrayList<Profile> profiles) {
        profiles = profiles;

        Context context = AppController.getInstance();
        SharedPreferences sharedPref = getContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Get users sort preference
        if (Integer.parseInt(sharedPref.getString(context.getString(R.string.pref_sort_by_key), "0")) == 1) {
            sortCustomersByFirstName();
        } else {
            sortCustomersByPhoneNumber();
        }
        notifyAll();
    }


    private void sortCustomersByFirstName() {
        Collections.sort(profiles, new Comparator<Profile>() {
            @Override
            public int compare(Profile b1, Profile b2) {
                return b1.getProfileFirstName().compareTo(b2.getProfileFirstName());
            }
        });
    }
    private void sortCustomersByPhoneNumber() {
        Collections.sort(profiles, new Comparator<Profile>() {
            @Override
            public int compare(Profile b1, Profile b2) {
                return b1.getProfilePhoneNumber().compareTo(b2.getProfilePhoneNumber());
            }
        });
    }
    private void sortCustomersByEmail() {
        Collections.sort(profiles, new Comparator<Profile>() {
            @Override
            public int compare(Profile b1, Profile b2) {
                return b1.getProfileEmail().compareTo(b2.getProfileEmail());
            }
        });
    }
    public ArrayList<Profile> getData() {
        return (ArrayList<Profile>) profiles;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(Profile item);
    }

}
