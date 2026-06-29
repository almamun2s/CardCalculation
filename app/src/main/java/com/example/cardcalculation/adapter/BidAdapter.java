package com.example.cardcalculation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardcalculation.R;
import com.example.cardcalculation.data.model.Bid;

import java.util.List;


public class BidAdapter extends RecyclerView.Adapter<BidAdapter.BidViewHolder>{
    private List<Bid> bids;
    private OnBidClickListener listener;
    public interface OnBidClickListener{
        void onBidClick(Bid bid);
    }

    // Constructor
    public BidAdapter(OnBidClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bid, parent, false);
        return new BidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidAdapter.BidViewHolder holder, int position) {
        Bid bid = bids.get(position);

        holder.team1_point.setText(String.valueOf(bid.getTeam1bid()));
        holder.team2_point.setText(String.valueOf(bid.getTeam2bid()));

        holder.btn_resolve.setOnClickListener(v -> listener.onBidClick(bid));

    }

    @Override
    public int getItemCount() {
        return bids != null ? bids.size() : 0;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
        notifyDataSetChanged();
    }

    static class BidViewHolder extends RecyclerView.ViewHolder {
        TextView team1_point, team2_point;
        Button btn_resolve;

        BidViewHolder(@NonNull View itemView) {
            super(itemView);
            team1_point = itemView.findViewById(R.id.txt_team1_point);
            team2_point = itemView.findViewById(R.id.txt_team2_point);
            btn_resolve = itemView.findViewById(R.id.btn_resolve);
        }
    }
}
