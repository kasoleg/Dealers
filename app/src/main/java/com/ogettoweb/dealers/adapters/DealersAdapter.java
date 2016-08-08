package com.ogettoweb.dealers.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.activities.BaseActivity;
import com.ogettoweb.dealers.databinding.ItemDealerBinding;
import com.ogettoweb.dealers.fragments.DealerFragment;
import com.ogettoweb.dealers.fragments.DealersFragment;
import com.ogettoweb.dealers.handlers.ItemDealerHandlers;
import com.ogettoweb.dealers.models.Dealer;

import java.util.List;

public class DealersAdapter extends RecyclerView.Adapter<DealersAdapter.ViewHolder> {

    private Activity activity;
    private List<Dealer> dealers;
    private Location location;

    public DealersAdapter(Activity activity, List<Dealer> dealers, Location location) {
        this.activity = activity;
        this.dealers = dealers;
        this.location = location;
    }

    @Override
    public DealersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDealerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_dealer, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(DealersAdapter.ViewHolder holder, int position) {
        final Dealer dealer = dealers.get(position);
        holder.binding.setDealer(dealer);
        holder.binding.setClick(new ItemDealerHandlers() {
            @Override
            public void onDealerClick(View view) {
                ((BaseActivity) activity).pushFragment(DealerFragment.newInstance(dealer, location), true, DealersFragment.class.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dealers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemDealerBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}