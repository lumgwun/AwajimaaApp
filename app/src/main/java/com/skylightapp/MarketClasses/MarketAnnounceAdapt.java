package com.skylightapp.MarketClasses;

import static android.content.Context.MODE_PRIVATE;


import static com.skylightapp.Markets.MarketDetailsAct.MANAGE_ANNOUNCEMENT;
import static com.skylightapp.Markets.MarketDetailsAct.SELECT_ANNOUNCEMENT;


import android.annotation.SuppressLint;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ActionTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.interfaces.TouchListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.skylightapp.Markets.MarketHub;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketAnnounceAdapt extends RecyclerView.Adapter<MarketAnnounceAdapt.RecyclerViewHolder> {

    private ArrayList<MarketAnnouncement> announcements;
    private List<MarketAnnouncement> marketList;
    private ArrayList<AnnounceSData> marketImageUris;

    private Context mcontext;
    int resources;
    AppCompatActivity activity;
    private OnAItemsClickListener listener;
    private String marketAnnouncer, marketTittle, announcementMessages,marketType;
    private int commodityCount;
    private int marketImage;
    private int MODE;
    private int preSelectedPosition = -1;
    private boolean selected=false;
    private static final String PREF_NAME = "skylight";
    private AnnounceSliderA announceSliderA;
    private ArrayList<SlideModel> slideModels;


    public MarketAnnounceAdapt(ArrayList<MarketAnnouncement> recyclerDataArrayList, Context mcontext) {
        this.announcements = recyclerDataArrayList;
        this.mcontext = mcontext;
    }
    public MarketAnnounceAdapt(ArrayList<MarketAnnouncement> addressesModelList, int MODE) {
        this.announcements = addressesModelList;
        this.MODE = MODE;
    }

    public MarketAnnounceAdapt(Context context, int resources, ArrayList<MarketAnnouncement> announcements) {
        this.announcements = announcements;
        this.mcontext = context;
        this.resources = resources;

    }
    public MarketAnnounceAdapt(MarketHub marketHub, List<MarketAnnouncement> testData) {
        this.marketList = testData;
        this.mcontext = marketHub;

    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<MarketAnnouncement> list) {
        this.marketList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void deleteItem(int position) {
        this.announcements.remove(position);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(MarketAnnouncement sliderItem) {
        this.announcements.add(sliderItem);
        notifyDataSetChanged();
    }
    public void setWhenClickListener(OnAItemsClickListener listener){
        this.listener = listener;
    }
    public MarketAnnounceAdapt(Context context, ArrayList<MarketAnnouncement> announcements) {
        this.announcements = announcements;
        this.mcontext = context;

    }

    public MarketAnnounceAdapt(AppCompatActivity activity, ArrayList<MarketAnnouncement> announcements) {
        this.announcements = announcements;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MarketAnnounceAdapt.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announce, parent, false);
        return new MarketAnnounceAdapt.RecyclerViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MarketAnnounceAdapt.RecyclerViewHolder holder, int position) {
        MarketAnnouncement announcement = announcements.get(position);
        if(announcement !=null){
            marketAnnouncer =announcement.getmAAnouncer();
            marketTittle =announcement.getmATittle();
            announcementMessages =announcement.getmAMessage();
            slideModels=announcement.getSlideModels();
            selected=announcement.ismASelected();
        }


        holder.txtTitleOfAnnounce.setText(MessageFormat.format("From{0}", marketTittle));
        holder.txtAnnouncer.setText(MessageFormat.format("Message:{0}", marketAnnouncer));
        holder.txtMessage.setText(MessageFormat.format("{0}", announcementMessages));
        holder.setData(marketAnnouncer, marketTittle, announcementMessages, marketImageUris,selected, position, announcements);
        Glide.with(mcontext).load(marketImageUris).into(holder.marketPicture);
        //holder.sliderView.setImageList(marketImageUris);
        //announceSliderA=new AnnounceSliderA(mcontext.getApplicationContext(), marketImageUris);
        holder.sliderView.setImageList(slideModels);
        holder.sliderView.startSliding(3000);
        holder.sliderView.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        holder.sliderView.animate();

        holder.sliderView.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });
        holder.sliderView.setTouchListener(new TouchListener() {
            @Override
            public void onTouched(@NonNull ActionTypes actionTypes) {
                if (actionTypes == ActionTypes.DOWN){
                    holder.sliderView.stopSliding();
                } else if (actionTypes == ActionTypes.UP ) {
                    holder.sliderView.startSliding(1000);
                }

            }
        });

        /*announceSliderA = new AnnounceSliderA(mcontext.getApplicationContext(), marketImageUris);
        announceSliderA.updateItems(marketImageUris);
        announceSliderA.notifyDataSetChanged();*/


    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != announcements ? announcements.size() : 0);
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView txtTitleOfAnnounce;
        private final AppCompatImageView marketPicture;
        private AppCompatTextView txtMessage, txtAnnouncer;
        private int MODE;
        private LinearLayoutCompat optionContainer;
        private int preSelectedPosition = -1;
        private String sharedPrefRole;
        SharedPreferences userPreferences;
        private Context context;
        ImageSlider sliderView;
        private AnnounceSliderA announceSliderA;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            marketPicture = itemView.findViewById(R.id.img_ann);
            txtTitleOfAnnounce = itemView.findViewById(R.id.m_title_annouce);
            txtMessage = itemView.findViewById(R.id.m_message_announce);
            txtAnnouncer = itemView.findViewById(R.id.m_announcer);
            sliderView = itemView.findViewById(R.id.m_a_slider);
            optionContainer = itemView.findViewById(R.id.anounce_option_);



        }

        public void setData(String marketAnnouncer, String marketTittle, String announcementMessages, ArrayList<AnnounceSData> marketImageUris, boolean selected, int position, ArrayList<MarketAnnouncement> announcements) {
            if(MODE == SELECT_ANNOUNCEMENT) {
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
                            announcements.get(position).setmASelected(true);
                            announcements.get(preSelectedPosition).setmASelected(false);
                            //refreshAnnouncement(preSelectedPosition, position);
                            preSelectedPosition = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_ANNOUNCEMENT) {
                optionContainer.setVisibility(View.GONE);
                marketPicture.setImageResource(R.mipmap.vertical_dots);
                marketPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        //refreshAnnouncement(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = position;

                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }

        }
    }
    public interface OnAItemsClickListener {
        void onTakeItemClick(MarketAnnouncement announcement);
    }
    public interface OnAClickListener {
        void onSelectItemClick(int position);

    }
}
