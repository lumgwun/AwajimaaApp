package com.skylightapp.MarketClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Markets.MarketHub;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.RecyclerViewHolder> {

    private ArrayList<Market> marketArrayList;
    private List<Market> marketList;
    private Context mcontext;
    int resources;
    AppCompatActivity activity;
    private OnItemsClickListener listener;
    private String marketName,marketState,marketLGA,marketType;
    private int commodityCount;
    private int marketImage;

    public MarketAdapter(ArrayList<Market> recyclerDataArrayList, Context mcontext) {
        this.marketArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
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
    public void setWhenClickListener(OnItemsClickListener listener){
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

        }
        holder.txtMarketName.setText(marketName);
        holder.txtType.setText(marketType+""+"/"+""+commodityCount);
        holder.txtLGA.setText(marketLGA);
        holder.txtState.setText(marketState);




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

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMarketName = itemView.findViewById(R.id.marketName);
            txtLGA = itemView.findViewById(R.id.lga_m);
            txtType = itemView.findViewById(R.id.type_market);
            txtState = itemView.findViewById(R.id.market_State);
            marketPicture = itemView.findViewById(R.id.img_market);

        }
    }
    public interface OnItemsClickListener{
        void onItemClick(Market market);
    }

}
