package com.skylightapp.Classes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.R;

import java.time.LocalTime;
import java.util.Calendar;



public class HelpFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private MessageDAO messageDAO;
    private ProfDAO profileDao;
    DBHelper helper;
    Profile userProfile;
    Context context;

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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

        View rootView = inflater.inflate(R.layout.leave_message, container, false);

        final TextView notification = rootView.findViewById(R.id.notification);
        final EditText inputId = rootView.findViewById(R.id.inputId);
        final EditText inputMessage = (EditText) rootView.findViewById(R.id.inputMessage);
        final Button confirm = (Button) rootView.findViewById(R.id.confirmButton);
        // if the customer clicks confirm
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationMessage = "";
                Calendar calendar = Calendar.getInstance();
                LocalTime today = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    today = LocalTime.now();
                }
                try {
                    int userId = Integer.parseInt(inputId.getText().toString());
                    String message = inputMessage.getText().toString();
                    ProfDAO profDAO = new ProfDAO(context);
                    DBHelper selector = new DBHelper(context);
                    MessageDAO messageDAO1 = new MessageDAO(context);
                    boolean canLeaveMessage = true;
                    Message message2= new Message();
                    if (!(profDAO.getUserDetails((userId)) instanceof Customer)) {
                        confirmationMessage += context.getString(R.string.cantLeaveMessage);
                        canLeaveMessage = false;
                    }
                    if (canLeaveMessage) {
                        if (profDAO.getUserDetails(userId) != null) {
                            int id = userProfile.leaveMessage(message, userId);
                            messageDAO1.saveNewMessage(userId,userProfile.getPID(),message2,today);
                            confirmationMessage += context.getString(R.string.messageLeft) + String.valueOf(id);
                        }
                    }
                } catch (NumberFormatException e) {
                    confirmationMessage += context.getString(R.string.invalidId);
                }
                notification.setText(confirmationMessage);

            }
        });
        return rootView;
    }
}