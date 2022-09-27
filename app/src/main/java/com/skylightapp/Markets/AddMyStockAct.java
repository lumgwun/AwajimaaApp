package com.skylightapp.Markets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketStock;
import com.skylightapp.MarketClasses.StockAttrException;
import com.skylightapp.R;

public class AddMyStockAct extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton bttAdd;
    private EditText txtName;
    private EditText txtStock;
    private EditText txtCost;
    private EditText txtPrice;
    private ImageView prodImage;
    private Bitmap photo;

    private DBHelper db;

    private static final int TAKE_PROD_PIC = 1;

    private void close() {
        if (!isChanged()) {
            navigateUpFromSameTask();
        } else
            new AlertDialog.Builder(AddMyStockAct.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.close)
                    .setMessage(R.string.close_without_save)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE)
                                        navigateUpFromSameTask();
                                }
                            }).show();
    }

    private boolean isChanged() {
        if (!txtName.getText().toString().isEmpty())
            return true;

        if (!txtStock.getText().toString().isEmpty())
            return true;

        if (!txtCost.getText().toString().isEmpty())
            return true;

        if (!txtPrice.getText().toString().isEmpty())
            return true;

        if (photo != null)
            return true;

        return false;
    }

    private void navigateUpFromSameTask() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == TAKE_PROD_PIC) && (data != null)
                && (data.hasExtra("data"))) {
            this.photo = data.getParcelableExtra("data");
            prodImage.setImageBitmap(this.photo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        String descript = txtName.getText().toString();
        int stock;
        double cost, price;

        try {
            stock = Integer.valueOf(txtStock.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_invalid_stock_or_too_large,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            cost = Double.valueOf(txtCost.getText().toString()).doubleValue();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_invalid_cost, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            price = Double.valueOf(txtPrice.getText().toString()).doubleValue();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_invalid_price, Toast.LENGTH_SHORT).show();
            return;
        }


        /*
         * Finalmente, si todos los valores son correctos el Product se crea y se intenta
         * añadir a la base de datos
         *
         */
        try {
            MarketStock newProd = new MarketStock(descript, stock, cost, price,	this.photo);
            if (!db.addProduct(newProd))
                Toast.makeText(getApplicationContext(), R.string.error_product_exist, Toast.LENGTH_SHORT)
                        .show();
            else {
                this.finish();
                Log.i("AddProduct", "Description: '" + descript + "'");
            }

        } catch (StockAttrException e) {
            Toast.makeText(getApplicationContext(), e.getDetailMessageId(), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_my_commodity);
        this.bttAdd = (AppCompatButton) findViewById(R.id.bttAddItem);
        this.txtName = (EditText) findViewById(R.id.nameNewText);
        this.txtStock = (EditText) findViewById(R.id.stockNewText);
        this.txtCost = (EditText) findViewById(R.id.costNewText);
        this.txtPrice = (EditText) findViewById(R.id.priceNewText);
        this.prodImage = (ImageView) findViewById(R.id.prodImage);

        this.prodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Llamamos a la actividad externa "cámara"
                startActivityForResult(takePic, TAKE_PROD_PIC);
            }
        });

        bttAdd.setOnClickListener(this);
        this.db = new DBHelper(this);
        this.db.open();
        setupActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                close();
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                close();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.photo = savedInstanceState.getParcelable("photo");

        if(this.photo != null){
            this.prodImage.setImageBitmap(this.photo);
        }
        super.onResume();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("photo", this.photo);
        super.onSaveInstanceState(outState);
    }


    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
}