package com.sergiocasero.sample;

import com.sergiocasero.extendedrecycleradapter.ExtendedRecyclerAdapter;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;

/**
 * MyRecyclerAdapter
 */
public class MyRecyclerAdapter extends ExtendedRecyclerAdapter<Car> {


    @Override
    protected ExtendedRecyclerViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_car;
    }

    public class MyViewHolder extends ExtendedRecyclerViewHolder {

        @Bind(R.id.car_id)
        TextView carId;

        @Bind(R.id.car_name)
        TextView carName;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Car item) {
            carId.setText(String.valueOf(item.getId()));
            carName.setText(String.valueOf(item.getName()));
        }
    }
}
