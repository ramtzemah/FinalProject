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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
    private List<Party> parties;
    private Context context;
    private String source;
    private String userId;
    public PartyAdapter(Context context, Map<String, Party> parties,String source, String userId) {
        this.parties = new ArrayList<Party>();
        for(Party party : parties.values()){
            this.parties.add(party);
        }
        this.context = context;
        this.source = source;
        this.userId = userId;
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
                intent.putExtra("party_id", party.getPartyId());
                intent.putExtra("from",source);
                intent.putExtra("userId",userId);
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


