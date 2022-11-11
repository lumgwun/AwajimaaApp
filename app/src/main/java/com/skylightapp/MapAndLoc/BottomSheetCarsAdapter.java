package com.skylightapp.MapAndLoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomSheetCarsAdapter extends RecyclerView.Adapter<BottomSheetCarsAdapter.CarsHolder> {
    private final ArrayList<CarTypes> cars;
    private final CarTypes selectedCarType;
    private final IClickListener clickListener;
    private OnCarClickListener onCarClickListener;
    private int selectedCarPosition;

    public BottomSheetCarsAdapter(List<CarTypes> cars,CarTypes selectedCarType, IClickListener clickListener) {
        super();
        this.cars = (ArrayList<CarTypes>) cars;
        this.selectedCarType = selectedCarType;
        this.clickListener = clickListener;
        this.selectedCarPosition = this.selectedCarIndex();
    }

    public final int getSelectedCarPosition() {
        return this.selectedCarPosition;
    }

    public final void setSelectedCarPosition(int var1) {
        this.selectedCarPosition = var1;
    }

    @NonNull
    @Override
    public CarsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new BottomSheetCarsAdapter.CarsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetCarsAdapter.CarsHolder holder, int position) {
        CarTypes recyclerData = cars.get(position);
        holder.bind(recyclerData, position);



    }


    public int getItemCount() {
        return this.cars.size();
    }
    public  class CarsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView carTitle;

        private final CircleImageView carImage;
        LinearLayout linearLayout;

        public CarsHolder(@NonNull View itemView) {
            super(itemView);

            carTitle = itemView.findViewById(R.id.name_car_ic);
            carImage = itemView.findViewById(R.id.image_car_icon);
            linearLayout = itemView.findViewById(R.id.rootCar);
            itemView.setOnClickListener(this);
            linearLayout.setOnClickListener(this);

            if (selectedCarPosition == getAdapterPosition()) {
                linearLayout.setAlpha(1.0F);

            } else {
                linearLayout.setAlpha(0.4F);
            }
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetCarsAdapter.this.clickListener.onClick(clickListener);
                    //BottomSheetCarsAdapter.this.setSelectedCarPosition(position);
                    BottomSheetCarsAdapter.this.notifyDataSetChanged();

                }
            });



        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onCarClickListener.onListItemClick(clickedPosition);
        }

        public void bind(CarTypes recyclerData, int position) {
            Car car = recyclerData.getCar();
            carTitle.setText(car.getTitle());
            carImage.setImageResource(car.getIconId());
        }
    }


    private int selectedCarIndex() {
        return this.cars.indexOf(this.selectedCarType);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public interface OnCarClickListener{
        void onCarClick(Car car);
        void onListItemClick(int index);
    }


}
