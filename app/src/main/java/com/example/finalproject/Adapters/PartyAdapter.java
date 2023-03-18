package com.example.finalproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Activities.PartyDetailsActivity;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.R;

import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
    private List<Party> parties;
    private Context context;
    public PartyAdapter(Context context,List<Party> parties) {
        this.parties = parties;
        this.context = context;
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_party, parent, false);
        return new PartyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
        Party party = parties.get(position);
        holder.partyLogo.setImageResource(party.getLogoResourceId());
        holder.partyName.setText(party.getName());
        holder.partyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to launch the PartyDetailsActivity
                Intent intent = new Intent(context, PartyDetailsActivity.class);
                // Pass in the party name and logo as extras in the intent
                intent.putExtra("party_name", party.getName());
                intent.putExtra("party_logo", party.getLogoResourceId());
                intent.putExtra("party_agenda", party.getAgenda());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public static class PartyViewHolder extends RecyclerView.ViewHolder {
        public ImageView partyLogo;
        public TextView partyName;

        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);
            partyLogo = itemView.findViewById(R.id.party_logo);
            partyName = itemView.findViewById(R.id.party_name);
        }
    }
}


