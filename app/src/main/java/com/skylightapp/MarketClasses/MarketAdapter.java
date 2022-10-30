package com.skylightapp.MarketClasses;

import static android.content.Context.MODE_PRIVATE;
import static com.skylightapp.Markets.MarketHub.MANAGE_MARKET;
import static com.skylightapp.Markets.MarketHub.SELECT_MARKET;
import static com.skylightapp.Markets.MarketHub.refreshItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Markets.MarketDetailsAct;
import com.skylightapp.Markets.MarketHub;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.RecyclerViewHolder> {

    private ArrayList<Market> marketArrayList;
    private List<Market> marketList;
    private List<Uri> marketImageUris;

    private Context mcontext;
    int resources;
    AppCompatActivity activity;
    private OnMarketItemsClickListener listener;
    private String marketName,marketState,marketLGA,marketType;
    private int commodityCount;
    private int marketImage;
    private int MODE;
    private int preSelectedPosition = -1;
    private boolean selected=false;
    private static final String PREF_NAME = "skylight";


    public MarketAdapter(ArrayList<Market> recyclerDataArrayList, Context mcontext) {
        this.marketArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }
    public MarketAdapter(ArrayList<Market> addressesModelList, int MODE) {
        this.marketArrayList = addressesModelList;
        this.MODE = MODE;
    }

    public MarketAdapter(Context context, int resources, ArrayList<Market> marketArrayList) {
        this.marketArrayList = marketArrayList;
        this.mcontext = context;
        this.resources = resources;

    }
    public MarketAdapter(MarketHub marketHub, List<Market> testData) {
        this.marketList = testData;
        this.mcontext = marketHub;

    }

    public MarketAdapter(MarketDetailsAct context, ArrayList<Market> marketArrayList, int mode) {
        this.marketArrayList = marketArrayList;
        this.MODE = mode;
        this.mcontext = context;
    }

    public void updateItems(List<Market> list) {
        this.marketList = list;
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        this.marketArrayList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Market sliderItem) {
        this.marketArrayList.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setWhenClickListener(OnMarketItemsClickListener listener){
        this.listener = listener;
    }
    public MarketAdapter(Context context, ArrayList<Market> marketArrayList) {
        this.marketArrayList = marketArrayList;
        this.mcontext = context;

    }

    public MarketAdapter(AppCompatActivity activity, ArrayList<Market> marketArrayList) {
        this.marketArrayList = marketArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Market marketData = marketArrayList.get(position);
        if(marketData !=null){
            marketName=marketData.getMarketName();
            marketState=marketData.getMarketState();
            marketLGA=marketData.getMarketLGA();
            commodityCount=marketData.getCommodityCount();
            marketImage=marketData.getMarketLogo();
            marketType=marketData.getMarketType();
            marketImageUris=marketData.getMarketPhotos();
            selected = marketData.getSelected();

        }

        holder.txtMarketName.setText(marketName);
        holder.txtType.setText(marketType+""+"/"+""+commodityCount);
        holder.txtLGA.setText(marketLGA);
        holder.txtState.setText(marketState);
        holder.setData(marketName, marketState, marketLGA, commodityCount,marketImageUris,selected, position,marketArrayList);

    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != marketArrayList ? marketArrayList.size() : 0);
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMarketName;
        private TextView txtType;
        private final ImageView marketPicture;
        private TextView txtLGA;
        private TextView txtState;
        private int MODE;
        private LinearLayoutCompat optionContainer;
        private int preSelectedPosition = -1;
        private String sharedPrefRole;
        SharedPreferences userPreferences;
        private Context context;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMarketName = itemView.findViewById(R.id.marketName);
            txtLGA = itemView.findViewById(R.id.lga_m);
            txtType = itemView.findViewById(R.id.type_market);
            txtState = itemView.findViewById(R.id.market_State);
            marketPicture = itemView.findViewById(R.id.img_market);
            optionContainer = itemView.findViewById(R.id.option_market);

        }
        private void setData(String marketName, String marketState, String marketLGA, int commodityCount, List<Uri> marketImageUris, final Boolean selected, final int position, ArrayList<Market> marketArrayList) {
            if (MODE == SELECT_MARKET) {
                userPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                sharedPrefRole = userPreferences.getString("PROFILE_ROLE", "");
                if(sharedPrefRole !=null) {
                    if(sharedPrefRole.contains("SuperAdmin")){
                        optionContainer.setVisibility(View.VISIBLE);

                    }
                }
                marketPicture.setImageResource(R.mipmap.check);
                if (selected) {
                    marketPicture.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                } else {
                    marketPicture.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedPosition != position) {
                            marketArrayList.get(position).setSelected(true);
                            marketArrayList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_MARKET) {
                optionContainer.setVisibility(View.GONE);
                marketPicture.setImageResource(R.mipmap.vertical_dots);
                marketPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = position;

                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }

        }

    }
    public interface OnMarketItemsClickListener {
        void onItemClick(Market market);
    }
    public interface OnMarketClickListener {
        void onItemClick(int position);

    }

}
