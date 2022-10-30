package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.skylightapp.Classes.ContextMenu;
import com.skylightapp.Classes.ErrorHandler;
import com.skylightapp.Classes.MenuObject;
import com.skylightapp.Classes.MenuParams;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class VoiceRecogAct extends AppCompatActivity implements MenuItem.OnMenuItemClickListener{
    private static final int REQUEST_CODE = 1234;
    private ListView wordsList;

    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_voice_recog);

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        Button speakButton = (Button) findViewById(R.id.speakButton);
        wordsList = (ListView) findViewById(R.id.list);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }


    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }


    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.title_activity_voice);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            wordsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                    matches));

            wordsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
                {
                    String address= (String)adapter.getItemAtPosition(position);

                    // goto google map screen
                    /*Intent intent = new Intent(VoiceRecogAct.this, PropLocUpdate.class);
                    // erase current listing in google maps
                    intent.putExtra("GoogleVoiceCommand", address);
                    startActivity(intent);*/
                }
            });

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void parseMenuClick(int position) {
        if (position > 0) {
            try {
                startActivity(ContextMenu.getMenuActivityIntent(this, getResources().getString(R.string.google_map_menu), position));
            } catch (InvalidParameterException e) {
                ErrorHandler.displayException(this, e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "ConMenuDialFrag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        //mMenuDialogFragment = ConMenuDialFrag.newInstance(menuParams);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e) {
            // do nothing
        }
        mToolBarTextView.setText(R.string.title_activity_voice);
    }

    private List<MenuObject> getMenuObjects() {
        return ContextMenu.getMenuObjects(VoiceRecogAct.this, getResources().getString(R.string.google_voice_menu));

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    //@Override
    public void onMenuItemClick(View clickedView, int position) {
        parseMenuClick(position);
    }

}