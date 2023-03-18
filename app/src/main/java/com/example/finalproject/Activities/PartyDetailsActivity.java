package com.example.finalproject.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class PartyDetailsActivity extends AppCompatActivity {
    private TextView partyNameTextView,party_agenda;
    private ImageView partyLogoImageView;
    private MaterialButton MB_votebtn;
    private String partyName,partyAgenda;
    private int partyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_details_activity);

        // Get the extras from the intent
        partyName = getIntent().getStringExtra("party_name");
        partyLogo = getIntent().getIntExtra("party_logo", -1);
        partyAgenda = getIntent().getStringExtra("party_agenda");
        findViews();
        // Set the party name and logo in the views
        partyNameTextView.setText(partyName);
        partyLogoImageView.setImageResource(partyLogo);
        party_agenda.setText(partyAgenda);
        setbuttons();
    }

    private void setbuttons() {
        MB_votebtn.setOnClickListener(v -> voteParty());
    }

    private void voteParty() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("הצבעה ל" + partyName);
        builder.setMessage("האם אתה בטוח שתרצה להצביע למפלגה זו?");

        // Set up the buttons
        builder.setPositiveButton("כן!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the OK button is clicked
            }
        });

        builder.setNegativeButton("לא.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the Cancel button is clicked
                dialog.cancel();
            }
        });

        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void findViews() {
        partyNameTextView = findViewById(R.id.party_name_textview);
        partyLogoImageView = findViewById(R.id.party_logo_imageview);
        party_agenda = findViewById(R.id.party_agenda);
        MB_votebtn = findViewById(R.id.MB_votebtn);
    }
}
