package com.skylightapp.MarketClasses;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiSelMarketSpinner extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {

    ArrayList<Market> items = null;

    boolean[] selection = null;

    ArrayAdapter adapter;



    public MultiSelMarketSpinner(Context context) {

        super(context);



        adapter = new ArrayAdapter(context,

                android.R.layout.simple_spinner_item);

        super.setAdapter(adapter);

    }



    public MultiSelMarketSpinner(Context context, AttributeSet attrs) {

        super(context, attrs);



        adapter = new ArrayAdapter(context,

                android.R.layout.simple_spinner_item);

        super.setAdapter(adapter);

    }



    @Override

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        if (selection != null && which < selection.length) {

            selection[which] = isChecked;



            adapter.clear();

            adapter.add(buildSelectedItemString());

        } else {

            throw new IllegalArgumentException(

                    "Argument 'which' is out of bounds.");

        }

    }



    @Override

    public boolean performClick() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String[] itemNames = new String[items.size()];



        for (int i = 0; i < items.size(); i++) {

            itemNames[i] = items.get(i).getMarketName();

        }



        builder.setMultiChoiceItems(itemNames, selection, this);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface arg0, int arg1)

            {

                // Do nothing

            }

        });



        builder.show();



        return true;

    }



    @Override

    public void setAdapter(SpinnerAdapter adapter) {

        throw new RuntimeException(

                "setAdapter is not supported by MultiSelectSpinner.");

    }



    public void setItems(ArrayList<Market> items) {

        this.items = items;

        selection = new boolean[this.items.size()];

        adapter.clear();

        adapter.add("");

        Arrays.fill(selection, false);

    }



    public void setSelection(ArrayList<Market> selection) {

        for (int i = 0; i < this.selection.length; i++) {

            this.selection[i] = false;

        }



        for (Market sel : selection) {

            for (int j = 0; j < items.size(); ++j) {

                if (items.get(j).getMarketName().equals(sel.getMarketName())) {

                    this.selection[j] = true;

                }

            }

        }



        adapter.clear();

        adapter.add(buildSelectedItemString());

    }



    private String buildSelectedItemString() {

        StringBuilder sb = new StringBuilder();

        boolean foundOne = false;



        for (int i = 0; i < items.size(); ++i) {

            if (selection[i]) {

                if (foundOne) {

                    sb.append(", ");

                }



                foundOne = true;



                sb.append(items.get(i).getMarketName());

            }

        }



        return sb.toString();

    }



    public ArrayList<Market> getSelectedItems() {

        ArrayList<Market> selectedItems = new ArrayList<>();



        for (int i = 0; i < items.size(); ++i) {

            if (selection[i]) {

                selectedItems.add(items.get(i));

            }

        }



        return selectedItems;

    }
}
