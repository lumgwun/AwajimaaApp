package com.skylightapp.MarketDealFrags;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.skylightapp.Classes.App;
import com.skylightapp.Classes.AppEtherNal;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.BaseAsyncTask22;
import com.skylightapp.MarketClasses.MyPreferences;

import com.skylightapp.MarketClasses.PreferencesManager;
import com.skylightapp.MarketClasses.TestReply;
import com.skylightapp.MarketInterfaces.ServerMethodsConsts;
import com.skylightapp.R;

public class TestResultsFragment extends Fragment {
    private TextView TextEResult;
    private TextView TextIResult;
    private TextView TextSResult;
    private TextView TextNResult;
    private TextView TextJResult;
    private TextView TextPResult;
    private TextView TextFResult;
    private TextView TextTResult;
    private TextView typeTResult;

    private PreferencesManager preferencesManager;
    private MyPreferences myPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);

        preferencesManager = AppEtherNal.getPreferencesManager();
        myPreferences = AppEtherNal.getPreferences();

        GetResults gR = new GetResults(ServerMethodsConsts.RESULTS + "/" + myPreferences.getUserId());
        gR.execute();

        AppCompatButton backButton = (AppCompatButton) view.findViewById(R.id.backFromResults);
        TextEResult = (TextView) view.findViewById(R.id.EText);
        TextIResult = (TextView) view.findViewById(R.id.IText);
        TextSResult = (TextView) view.findViewById(R.id.SText);
        TextNResult = (TextView) view.findViewById(R.id.NText);
        TextTResult = (TextView) view.findViewById(R.id.TText);
        TextFResult = (TextView) view.findViewById(R.id.FText);
        TextPResult = (TextView) view.findViewById(R.id.PText);
        TextJResult = (TextView) view.findViewById(R.id.JText);
        typeTResult = (TextView) view.findViewById(R.id.resultType);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shop = new Intent(getContext(), LoginDirAct.class);
                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(shop);


            }
        });


        return view;
    }
    private class GetResults extends BaseAsyncTask22<Void> {
        ProgressDialog resultProgress;

        public GetResults(String urn) {
            super(urn, null);
            resultProgress = new ProgressDialog(getActivity());
        }



        @Override
        protected void onPreExecute() {
            resultProgress.setTitle("Loading");
            resultProgress.setIndeterminate(true);
            resultProgress.setCancelable(false);
            resultProgress.show();
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                TestReply testReply = App.getGson().fromJson(result, TestReply.class);
                myPreferences.setMbtiType(testReply.getType());
                preferencesManager.savePreferences();
                typeTResult.setText(testReply.getType());
                TextEResult.setText(String.format("Extraversion: %d%%", testReply.getE()));
                TextIResult.setText(String.format("Introversion: %d%%", 100 - testReply.getE()));
                TextSResult.setText(String.format("Sensing: %d%%", 100 - testReply.getN()));
                TextNResult.setText(String.format("Intuition: %d%%", testReply.getN()));
                TextTResult.setText(String.format("Thinking: %d%%", testReply.getT()));
                TextFResult.setText(String.format("Feeling: %d%%", 100 - testReply.getT()));
                TextJResult.setText(String.format("Judging: %d%%", 100 - testReply.getP()));
                TextPResult.setText(String.format("Perceiving: %d%%", testReply.getP()));
            } else {
                Toast.makeText(getActivity(), "server error", Toast.LENGTH_LONG).show();
            }
            if (resultProgress.isShowing())
                resultProgress.dismiss();
        }


    }



}
