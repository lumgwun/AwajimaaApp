package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SuperMessages extends AppCompatActivity implements  MessageAdapter.OnItemsClickListener {
    private RecyclerView recyclerView,recyclerViewToday;
    private ArrayList<Message> messages;
    private ArrayList<Message> messagesToday;
    private MessageAdapter mAdapter;
    private MessageAdapter mAdapterSelectedDate;
    DBHelper dbHelper;
    Context context;
    TextView txtMessageOutCome;
    int messageCount;
    DatePicker picker;
    private Profile userProfile;
    private String dateOfMessage;
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo;
    UserSuperAdmin superAdmin;
    Gson gson0,gson1,gson2;
    String json0,json1,json2,selectedOffice;
    private OnFragmentInteractionListener listener;
    AppCompatButton btnSearchMessages,btnSearchByOffice;
    AppCompatSpinner spnOfficeBranch;
    private SQLiteDatabase sqLiteDatabase;
    private MessageDAO messageDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_messages);

        dbHelper= new DBHelper(this);
        setContentView(R.layout.act_admin_support);
        userProfile= new Profile();
        superAdmin = new UserSuperAdmin();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        gson1 = new Gson();
        json1 = userPreferences.getString("LastAdminUserProfileUsed", "");
        superAdmin = gson1.fromJson(json1, UserSuperAdmin.class);
        btnSearchMessages = findViewById(R.id.btnSearchMessagesSuper);
        spnOfficeBranch = findViewById(R.id.spnOfficeMessages);
        btnSearchByOffice = findViewById(R.id.btnSearchOfficeSuper);
        txtMessageOutCome = findViewById(R.id.txtMessage88);
        recyclerView = findViewById(R.id.recycler_Superview);
        recyclerViewToday = findViewById(R.id.recycler_view_SuperToday);
        messages = new ArrayList<Message>();
        messagesToday = new ArrayList<Message>();
        picker=(DatePicker)findViewById(R.id.messageSuperDatePicker);
        Calendar calendar = Calendar.getInstance();
        try {

            spnOfficeBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedOffice = spnOfficeBranch.getSelectedItem().toString();
                    //selectedOffice = (String) parent.getSelectedItem();
                    Toast.makeText(SuperMessages.this, "Office Branch Selected: "+ selectedOffice,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        try {

            btnSearchByOffice.setOnClickListener(this::searchOfficeMessages);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        /*btnSearchByOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    messages = dbHelper.getAllMessagesForBranch(selectedOffice);
                    messagesToday=dbHelper.getMessagesToday(dateOfMessage);
                    messageCount=dbHelper.getMessageCountToday(dateOfMessage);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }

            }
        });*/

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateString = mdformat.format(calendar.getTime());
        try {
            picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDate();
                }
            });
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {

            dateOfMessage = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }



        try {
            btnSearchMessages.setOnClickListener(this::searchByDate);
            messageDAO= new MessageDAO(this);

            btnSearchMessages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        messages = messageDAO.getAllMessages2();
                    }
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        messagesToday=messageDAO.getMessagesToday(dateOfMessage);
                    }
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        messageCount=messageDAO.getMessageCountToday(dateOfMessage);

                    }


                }
            });
        } catch (Exception e) {
            System.out.println("Oops!");
        }

        try {

            if(messageCount >0){
                txtMessageOutCome.setVisibility(View.VISIBLE);
                txtMessageOutCome.setText("Messages:"+ messageCount);

            }else if(messageCount ==0){
                txtMessageOutCome.setVisibility(View.VISIBLE);
                txtMessageOutCome.setText("Messages:0");


            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }







        mAdapterSelectedDate = new MessageAdapter(this,  messagesToday);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(mAdapterSelectedDate);
        recyclerViewToday.setNestedScrollingEnabled(false);




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this) {
        };
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MessageAdapter(SuperMessages.this, this.messages);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);




    }
    public void searchByDate(View view) {
        messageDAO= new MessageDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messages = messageDAO.getAllMessages2();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messagesToday=messageDAO.getMessagesToday(dateOfMessage);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messageCount=messageDAO.getMessageCountToday(dateOfMessage);

        }

    }

    public void searchOfficeMessages(View view) {
        messageDAO= new MessageDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messages = messageDAO.getAllMessages2();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messagesToday=messageDAO.getMessagesToday(dateOfMessage);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messageCount=messageDAO.getMessageCountToday(dateOfMessage);

        }
    }
    private void chooseDate() {
        try {

            dateOfMessage = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        //dateOfMessage = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    @Override
    public void onItemClick(Message message) {

    }



    static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
        private final Context context;
        private final List<Message> messageList;
        private MessageAdapter.OnItemsClickListener listener;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView purposeOfMessage, message,message_time,sender;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                purposeOfMessage = view.findViewById(R.id.purpose_of_message);
                message = view.findViewById(R.id.message_);
                sender = view.findViewById(R.id.message_sender);
                message_time = view.findViewById(R.id.message_timestamp);
                EditText message_reply = view.findViewById(R.id.message_reply);
                ImageButton message_reply_send = view.findViewById(R.id.message_reply_send);
                thumbnail = view.findViewById(R.id.thumbnail_1);
            }
        }


        public MessageAdapter(Context context, List<Message> messageList) {
            this.context = context;
            this.messageList = messageList;
        }
        public void setWhenClickListener(MessageAdapter.OnItemsClickListener listener){
            this.listener = listener;
        }

        @Override
        public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_row, parent, false);

            return new MessageAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MessageAdapter.MyViewHolder holder, final int position) {
            final Message message = messageList.get(position);
            holder.purposeOfMessage.setText(MessageFormat.format("Support Type:{0}", message.getMessageType()));
            holder.message.setText(MessageFormat.format("Message:{0}", message.getMessageDetails()));
            holder.sender.setText(MessageFormat.format("Sender:{0}", message.getMessageSender()));
            holder.message_time.setText(MessageFormat.format("Time:{0}", message.getMessageTime()));

        }

        @Override
        public int getItemCount() {
            return (null != messageList ? messageList.size() : 0);
        }
        public interface OnItemsClickListener{
            void onItemClick(Message message);
        }

    }


    public interface OnFragmentInteractionListener {


    }
}