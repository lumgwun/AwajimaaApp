package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BranchSuppAct extends AppCompatActivity implements  MessageAdapter.OnItemsClickListener{
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
    AdminUser adminUser;
    Gson gson0,gson1,gson2;
    String json0,json1,json2,officeBranch;
    private OnFragmentInteractionListener listener;
    AppCompatButton btnSearchMessages;
    private static final String PREF_NAME = "awajima";
    SQLiteDatabase sqLiteDatabase;
    private MessageDAO messageDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper= new DBHelper(this);
        setContentView(R.layout.act_admin_support);
        dbHelper = new DBHelper(BranchSuppAct.this);
        setTitle("Supports Messages");
        userProfile= new Profile();
        adminUser= new AdminUser();
        messageDAO= new MessageDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        gson1 = new Gson();
        json1 = userPreferences.getString("LastAdminUserUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);
        btnSearchMessages = findViewById(R.id.btnSearchMessages);
        txtMessageOutCome = findViewById(R.id.txtMessage);
        recyclerView = findViewById(R.id.recycler_view_admin);
        recyclerViewToday = findViewById(R.id.recycler_view_Today);
        messages = new ArrayList<Message>();
        messagesToday = new ArrayList<Message>();
        picker=(DatePicker)findViewById(R.id.PickerAdmin);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this) {
        };
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MessageAdapter(BranchSuppAct.this, this.messages);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        Calendar calendar = Calendar.getInstance();
        /*if(adminUser !=null){
            officeBranch=adminUser.getAdminOffice();
        }*/
        if(userProfile !=null){
            officeBranch=userProfile.getProfOfficeName();
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = mdformat.format(calendar.getTime());
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfMessage = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        btnSearchMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dbHelper !=null) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        messages = messageDAO.getAllMessagesForBranch(officeBranch);
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (dbHelper !=null) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        messagesToday=messageDAO.getMessagesToday(dateOfMessage);
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (dbHelper !=null) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {

                        messageCount=messageDAO.getMessageCountToday(dateOfMessage);
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }





            }
        });



        if(messageCount >0){
            txtMessageOutCome.setText("Messages:"+ messageCount);

        }else if(messageCount ==0){
            txtMessageOutCome.setText("Messages:0");

        }


        mAdapterSelectedDate = new MessageAdapter(this,  messagesToday);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(mAdapterSelectedDate);
        recyclerViewToday.setNestedScrollingEnabled(false);



    }
    private void chooseDate() {
        dateOfMessage = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }

    @Override
    public void onItemClick(Message message) {

    }

    static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
        private final Context context;
        private final List<Message> messageList;
        private OnItemsClickListener listener;

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
        public void setWhenClickListener(OnItemsClickListener listener){
            this.listener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Message message = messageList.get(position);
            if(message !=null){
                holder.purposeOfMessage.setText(MessageFormat.format("Support Type:{0}", message.getMessageType()));
                holder.message.setText(MessageFormat.format("Message:{0}", message.getMessageDetails()));
                holder.sender.setText(MessageFormat.format("Sender:{0}", message.getMessageSender()));
                holder.message_time.setText(MessageFormat.format("Time:{0}", message.getMessageTime()));

            }


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