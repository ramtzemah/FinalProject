package com.example.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Entities.AgesBlocks;
import com.example.finalproject.R;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.List;

public class DistributionOfVotersByAgeAdapter extends RecyclerView.Adapter<DistributionOfVotersByAgeAdapter.AgesBlocksViewHolder> {
    private List<AgesBlocks> agesBlocksList;
    private Context context;

    public DistributionOfVotersByAgeAdapter(Context context, List<AgesBlocks> agesBlocks) {
        this.agesBlocksList = agesBlocks;
        this.context = context;
    }

    public void setAgesBlocksList(List<AgesBlocks> agesBlocksList) {
        this.agesBlocksList = agesBlocksList;
    }

    @NonNull
    @Override
    public AgesBlocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_graph, parent, false);
        return new DistributionOfVotersByAgeAdapter.AgesBlocksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AgesBlocksViewHolder holder, int position) {
        AgesBlocks agesBlocks = agesBlocksList.get(position);
        holder.ages.setText(agesBlocks.getFromAge() + "-" + agesBlocks.getUntilAge());
        holder.pb_by_age.setProgressPercentage(32.6,true);
    }

    @Override
    public int getItemCount() {
        return agesBlocksList.size();
    }

    public static class AgesBlocksViewHolder extends RecyclerView.ViewHolder {
        public TextView ages;
        public RoundedProgressBar pb_by_age;

        public AgesBlocksViewHolder(@NonNull View itemView) {
            super(itemView);
            ages = itemView.findViewById(R.id.ages);
            pb_by_age = itemView.findViewById(R.id.pb_by_age);
        }
    }
}
