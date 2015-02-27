package com.bloc.bloquery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloc.bloquery.R;

/**
 * Created by Mark on 2/26/2015.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
       // View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.point_item, viewGroup, false);
        //return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
       // update();
    }

    @Override
    public int getItemCount() {

    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder {

        //Layout file stuff
        //TextView textview
        //...

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            //Findview for layout file stuff
            //textview = (TextView) findViewById(...);
        }

        void update() {

        }
    }
}
