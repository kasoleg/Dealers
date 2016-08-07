package com.ogettoweb.dealers.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.databinding.ItemDealerBinding;
import com.ogettoweb.dealers.models.Dealer;

import java.util.List;

public class DealersAdapter extends RecyclerView.Adapter<DealersAdapter.ViewHolder> {

    private List<Dealer> dealers;

    public DealersAdapter(List<Dealer> dealers) {
        this.dealers = dealers;
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