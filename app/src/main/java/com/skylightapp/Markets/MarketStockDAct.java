package com.skylightapp.Markets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightapp.Classes.AppController;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

public class MarketStockDAct extends AppCompatActivity {
    private MarketStock marketStock;
    private MarketStock productEdited;

    private AppCompatButton bttSave;
    private TextView txtStockNum;

    private EditText txtName;
    private EditText txtCost;
    private EditText txtPrice;

    private EditText txtAdd;
    private EditText txtSell;
    private AppCompatButton bttAdd;
    private AppCompatButton bttSell;

    private ImageView prodImage;
    private ImageView prodExtImage;

    private DBHelper db;

    private View.OnClickListener onbttAddClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                productEdited.addStock(Integer.valueOf(txtAdd.getText().toString()));
                txtStockNum.setText(productEdited.getStock());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_stock_out_of_bounds, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    private View.OnClickListener onBttSellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                productEdited.sellUnits(Integer.valueOf(txtSell.getText().toString()));
                txtStockNum.setText(productEdited.getStock());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_there_are_not_enough, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    private View.OnClickListener onBttSaveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                productEdited = getChangedProduct();
            } catch (StockAttrException e) {
                e.printStackTrace();
            }

            if (productEdited.getName().equals(marketStock.getName())) {
                if (!db.updateProduct(productEdited)) {
                    Toast.makeText(getApplicationContext(), R.string.error_product_data, Toast.LENGTH_SHORT)
                            .show();
                    productEdited = new MarketStock(marketStock);
                } else {
                    finish();
                    Log.i("SavedProduct", "Description: '" + productEdited.getName() + "'");
                }
            } else {
                if (!db.addProduct(productEdited)) {
                    Toast.makeText(getApplicationContext(), R.string.error_product_exist, Toast.LENGTH_SHORT)
                            .show();
                    productEdited = new MarketStock(marketStock);
                } else {
                    Log.i("SavedAndRenamedProduct", "Description: '" + marketStock
                            + "'" + " ->'" + productEdited.getName() + "'");
                    db.deleteMarketStock(marketStock);
                    finish();
                }
            }
        }
    };

    private void close() {
        if (!isChanged()) {
            navigateUpFromSameTask();
        } else
            new AlertDialog.Builder(MarketStockDAct.this)
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

    private MarketStock getChangedProduct() throws StockAttrException {
        MarketStock productEdited = new MarketStock(this.productEdited);
        String name = txtName.getText().toString();
        double cost, price;

        try {
            cost = Double.valueOf(txtCost.getText().toString()).doubleValue();
        } catch (NumberFormatException e) {
            throw new StockAttrException(R.string.error_invalid_cost);
        }

        try {
            price = Double.valueOf(txtPrice.getText().toString()).doubleValue();
        } catch (NumberFormatException e) {
            throw new StockAttrException(R.string.error_invalid_price);
        }

        productEdited.setCost(cost);
        productEdited.setPrice(price);
        productEdited.setName(name);


        return productEdited;
    }

    private boolean isChanged() {
        MarketStock changed;

        try {
            changed = getChangedProduct();
            return !changed.equals(marketStock);
        } catch (StockAttrException e) {
            return false;
        }
    }

    private void navigateUpFromSameTask() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_stock_details);

        setupActionBar();

        this.bttSave = (AppCompatButton) findViewById(R.id.bttSaveItem);
        this.bttSave.setOnClickListener(onBttSaveClick);

        this.txtStockNum = (TextView) findViewById(R.id.textStockNum);

        this.txtName = (EditText) findViewById(R.id.edit_prod_name);
        this.txtCost = (EditText) findViewById(R.id.edit_prod_cost);
        this.txtPrice = (EditText) findViewById(R.id.edit_prod_price);

        this.txtAdd = (EditText) findViewById(R.id.units_to_add);
        this.txtSell = (EditText) findViewById(R.id.units_to_sell);

        this.bttAdd = (AppCompatButton) findViewById(R.id.btt_add_stock);
        this.bttAdd.setOnClickListener(onbttAddClick);

        this.bttSell = (AppCompatButton) findViewById(R.id.btt_sell_units);
        this.bttSell.setOnClickListener(onBttSellClick);

        this.prodImage = (ImageView) findViewById(R.id.prod_det_image);
        this.prodImage.setOnClickListener(zoomImageFromThumb());
        this.prodExtImage = (ImageView) findViewById(R.id.prod_det_exp_image);
        this.prodExtImage.setOnClickListener(zoomOutImage());
        this.db = new DBHelper(this);
        this.db.open();
        this.marketStock = ((AppController) getApplication()).getCurrentProd();
        this.productEdited = new MarketStock(this.marketStock);
        setUI();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.db.close();
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

    /**
     * Actualiza los valores de la vista del producto
     *
     */
    private void setUI() {
        txtName.setText(productEdited.getName());
        txtCost.setText(productEdited.getCost());
        txtPrice.setText(productEdited.getPrice());
        txtStockNum.setText(productEdited.getStock());

        if (productEdited.getPhoto() != null) {
            prodImage.setImageBitmap(productEdited.getPhoto());
            prodExtImage.setImageBitmap(productEdited.getPhoto());
        }

        prodExtImage.setScaleType(ImageView.ScaleType.FIT_XY);
        prodExtImage.setAdjustViewBounds(false);
        prodExtImage.setAdjustViewBounds(true);
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private View.OnClickListener zoomImageFromThumb() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.linear_prod_info).setVisibility(View.GONE);
                prodExtImage.setVisibility(View.VISIBLE);
            }
        };
    }


    private View.OnClickListener zoomOutImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodExtImage.setVisibility(View.GONE);
                findViewById(R.id.linear_prod_info).setVisibility(View.VISIBLE);
            }
        };

    }
}