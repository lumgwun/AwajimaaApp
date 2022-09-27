package com.skylightapp.Customers;

import android.content.Context;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class CusSupportMesFrag extends Fragment {


    public static final String KEY = "";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String TAG = CusSupportMesFrag.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/CustomerSupportMessages";

    private RecyclerView recyclerView;
    private ArrayList<Message> messages;
    private MessageAdapter mAdapter;
    DBHelper dbHelper;
    Profile userProfile = new Profile();

    public CusSupportMesFrag() {
        // Required empty public constructor
    }

    public static CusSupportMesFrag newInstance(String param1, String param2) {
        CusSupportMesFrag fragment = new CusSupportMesFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cus_support_, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_customers3);
        int profileID =userProfile.getPID();

        messages = new ArrayList<>();
        mAdapter = new MessageAdapter(getActivity(), messages);
        MessageDAO messageDAO= new MessageDAO(getContext());

        dbHelper = new DBHelper(getContext());

        messages = messageDAO.getMessagesForCurrentProfile(profileID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setClickable(true);




        return view;
    }

    static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
        private final Context context;
        private final List<Message> messageList;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView purposeOfMessage, message,message_time,sender;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                purposeOfMessage = view.findViewById(R.id.purpose_of_message);
                message = view.findViewById(R.id.message_);
                sender = view.findViewById(R.id.message_sender);
                message_time = view.findViewById(R.id.message_timestamp);
                ExtractEditText message_reply = view.findViewById(R.id.message_reply);
                ImageButton message_reply_send = view.findViewById(R.id.message_reply_send);
                thumbnail = view.findViewById(R.id.thumbnail_1);
            }
        }


        public MessageAdapter(Context context, List<Message> messageList) {
            this.context = context;
            this.messageList = messageList;
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
            holder.purposeOfMessage.setText(message.getMessageType());
            holder.message.setText(message.getMessageDetails());
            holder.sender.setText(message.getMessageSender());
            holder.message_time.setText(message.getMessageTime());

        }

        @Override

        public int getItemCount() {
            return (null != messageList ? messageList.size() : 0);

        }


    }
}
