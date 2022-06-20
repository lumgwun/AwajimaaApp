package com.skylightapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class NewSkylightPackageSlider extends PagerAdapter implements Filterable, View.OnClickListener {

    private Context Mcontext;
    private List<SkyLightPackModel> theSlideItemsModelClassList = new ArrayList<>();
    private List<SkyLightPackModel> itemsListFilter = new ArrayList<>();
    Context context;
    private ArrayList<SkyLightPackModel> packageList;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private OnItemsClickListener listener;
    Bundle bundle;
    RequestListener<Drawable> imageListener;

    public NewSkylightPackageSlider(Context Mcontext, List<SkyLightPackModel> theSlideItemsModelClassList) {
        this.Mcontext = Mcontext;
        this.theSlideItemsModelClassList = theSlideItemsModelClassList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.slider_layout3,null);
        SkyLightPackModel listModel=theSlideItemsModelClassList.get(position);

        ImageView featured_image = sliderLayout.findViewById(R.id.item_image5);
        TextView itemName = sliderLayout.findViewById(R.id.item_title66);
        TextView description = sliderLayout.findViewById(R.id.item_description66);
        TextView price = sliderLayout.findViewById(R.id.item_daily_price55);
        TextView duration = sliderLayout.findViewById(R.id.duration155);
        bundle=new Bundle();

        sliderLayout.setOnClickListener(this);

        description.setText(theSlideItemsModelClassList.get(position).getDescription());
        itemName.setText(theSlideItemsModelClassList.get(position).getItemName());
        price.setText(MessageFormat.format("N{0}", theSlideItemsModelClassList.get(position).getPrice()));
        duration.setText(theSlideItemsModelClassList.get(position).getDuration());
        //featured_image.setImageResource(theSlideItemsModelClassList.get(position).getItemImage());
        try {
            Glide.with(context)
                    .load(theSlideItemsModelClassList.get(position).getItemImage())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    .listener(imageListener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(featured_image);
            container.addView(sliderLayout);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {

        return (null != theSlideItemsModelClassList ? theSlideItemsModelClassList.size() : 0);
    }


    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                theSlideItemsModelClassList = (List<SkyLightPackModel>) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<SkyLightPackModel> filteredList = new ArrayList<>();
                String searchText = constraint.toString().toLowerCase();
                String[] split = searchText.split(",");
                ArrayList<String> stringArrayList = new ArrayList<>(split.length);
                for (String aSplit : split) {
                    // remove spaces
                    String trim = aSplit.trim();
                    // skip empty entries
                    if (trim.length() > 0)
                        stringArrayList.add(trim);
                }

                for (SkyLightPackModel dataNames : itemsListFilter) {
                    // filter by title
                    if (dataNames.getItemName().toLowerCase().trim().contains(searchText)) {
                        filteredList.add(dataNames);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
                return results;
            }

        };
        return filter;
    }
    public void filterAll( String pack ) {

        itemsListFilter.clear();

        if (pack.length() < 0) {
            itemsListFilter.addAll(packageList);
        } else {
            for (SkyLightPackModel ls : packageList) {

                if (ls.getItemName().contains(pack)) {

                    itemsListFilter.add(ls);

                }

            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnItemsClickListener{
        void onItemClick(SkyLightPackModel lightPackage);
    }


}
