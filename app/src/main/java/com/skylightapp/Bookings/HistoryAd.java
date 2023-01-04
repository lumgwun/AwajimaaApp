package com.skylightapp.Bookings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.skylightapp.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import de.halfbit.pinnedsection.PinnedSectionListView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAd extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private Activity activity;
    private ArrayList<History> list;
    private AQuery aQuery;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private ImageOptions imageOptions;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
    private DecimalFormat df;
    TreeSet<Integer> mSeparatorsSet;
    SimpleDateFormat simpleDateFormat;
    private SectionViewHolder sectionHolder;

    public HistoryAd(Activity activity, ArrayList<History> historyList,
                          TreeSet<Integer> mSeparatorsSet) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.list = historyList;

        df = new DecimalFormat("00");
        this.mSeparatorsSet = mSeparatorsSet;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageOptions = new ImageOptions();
        imageOptions.fileCache = true;
        imageOptions.memCache = true;
        imageOptions.fallback = R.drawable.ic_admin_user;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // notifyDataSetInvalidated();
        // notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.history_item, parent,
                            false);
                    holder = new ViewHolder();
                    holder.tvName =  convertView
                            .findViewById(R.id.tvHistoryName);

                    holder.tvDate =  convertView
                            .findViewById(R.id.tvHistoryDate);
                    holder.tvCost =  convertView
                            .findViewById(R.id.tvHistoryCost);
                    holder.ivIcon = (CircleImageView) convertView
                            .findViewById(R.id.ivHistoryIcon);

                    convertView.setTag(holder);
                    break;
                case TYPE_SEPARATOR:
                    sectionHolder = new SectionViewHolder();
                    convertView = inflater.inflate(R.layout.history_date,
                            parent, false);

                    sectionHolder.tv = convertView
                            .findViewById(R.id.tvDate);
                    convertView.setTag(sectionHolder);
                    break;
            }

        } else {
            switch (type) {
                case TYPE_ITEM:
                    holder = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_SEPARATOR:
                    sectionHolder = (SectionViewHolder) convertView.getTag();
                    break;

            }
        }

        switch (type) {
            case TYPE_ITEM:
                aQuery = new AQuery(convertView);
                History history = list.get(position);
                holder.tvName.setText(history.getFirstName() + " "
                        + history.getLastName());
                holder.tvDate.setText(formatTime(history.getDate()));
                // holder.tvDate.setText(history.getDate());
                holder.tvCost.setText(activity.getString(R.string.payment_unit)
                        + history.getTotal());
                aQuery.id(holder.ivIcon).progress(R.id.pBar)
                        .image(history.getPicture(), imageOptions);
                break;
            case TYPE_SEPARATOR:
                Date currentDate = new Date();
                Date returnDate = new Date();
                // Log.i("Return date", "" + list.get(position).getDate());
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                String date = df1.format(currentDate);

                if (list.get(position).getDate().equals(date)) {
                    sectionHolder.tv
                            .setBackgroundResource(R.drawable.ic_hint_line);
                    sectionHolder.tv.setTextColor(activity.getResources().getColor(
                            R.color.white));
                    sectionHolder.tv.setText(activity
                            .getString(R.string.today));
                } else if (list.get(position).getDate()
                        .equals(getYesterdayDateString())) {
                    sectionHolder.tv
                            .setBackgroundResource(R.drawable.ic_orange_line);
                    sectionHolder.tv.setTextColor(activity.getResources().getColor(
                            R.color.red_brown_dark));
                    sectionHolder.tv.setText(activity
                            .getString(R.string.yesterday));
                }

                else {
                    sectionHolder.tv
                            .setBackgroundResource(R.drawable.ic_orange_line);
                    sectionHolder.tv.setTextColor(activity.getResources().getColor(
                            R.color.red_brown_dark));
                    try {
                        returnDate = df1.parse(list.get(position).getDate());

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat pinnedDate = new SimpleDateFormat(
                            "dd-MMM-yyyy");
                    if (returnDate != null) {
                        Log.i("New Date", pinnedDate.format(returnDate));
                    }
                    if (returnDate != null) {
                        sectionHolder.tv.setText(pinnedDate.format(returnDate));
                    }
                }
                break;

        }

        return convertView;
    }

    private class ViewHolder {
        AppCompatTextView tvCost,tvName, tvDate;
        CircleImageView ivIcon;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hb.views.PinnedSectionListView.PinnedSectionListAdapter#
     * isItemViewTypePinned(int)
     */
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == TYPE_SEPARATOR;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    class SectionViewHolder {
        AppCompatTextView tv;
    }

    private String getYesterdayDateString() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    private String formatTime(String strDate) {
        Log.i("String Date", strDate);
        Date time = new Date();
        try {
            time = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        return sdf.format(cal.getTime());

    }
}
