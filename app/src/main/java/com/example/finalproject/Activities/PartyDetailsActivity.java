package com.example.finalproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

public class PartyDetailsActivity extends AppCompatActivity {
    private TextView partyNameTextView, party_agenda;
    private ImageView partyLogoImageView;
    private MaterialButton MB_votebtn;
    private String partyName, partyAgenda, partyId;
    private int partyLogo;
    private String source;
    private int canAuthenticate;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_details_activity);

        // Get the extras from the intent
        partyName = getIntent().getStringExtra("party_name");
        partyLogo = getIntent().getIntExtra("party_logo", -1);
        partyAgenda = getIntent().getStringExtra("party_agenda");
        partyId = getIntent().getStringExtra("party_id");
        source = getIntent().getStringExtra("from");
        userId = getIntent().getStringExtra("userId");

        findViews();
        Log.d("pttt", source);
        // Set the party name and logo in the views
        partyNameTextView.setText(partyName);
        partyLogoImageView.setImageResource(partyLogo);
        party_agenda.setText(partyAgenda);
        setButtons();
        BiometricManager biometricManager = BiometricManager.from(this);
        canAuthenticate = biometricManager.canAuthenticate();
    }

    private void setButtons() {
        MB_votebtn.setOnClickListener(v -> voteParty());
    }

    private void voteParty() {
        Intent intent = new Intent(PartyDetailsActivity.this, EndVote.class);
        intent.putExtra("party_id", partyId);
        intent.putExtra("userId", userId);
        PartyDetailsActivity.this.startActivity(intent);
        finish();
        if (canAuthenticate == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הצבעה ל" + partyName);
            builder.setMessage("האם אתה בטוח שתרצה להצביע למפלגה זו?");

            // Set up the buttons
            builder.setPositiveButton("כן!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Biometric Authentication")
                            .setSubtitle("Confirm your identity to vote")
                            .setDescription("Place your finger on the sensor")
                            .setNegativeButtonText("Cancel")
                            .build();

                    BiometricPrompt biometricPrompt = new BiometricPrompt(PartyDetailsActivity.this, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                       //     Toast.makeText(PartyDetailsActivity.this, "Succeeded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PartyDetailsActivity.this, EndVote.class);
                            intent.putExtra("party_id", partyId);
                            intent.putExtra("userId", userId);
                            PartyDetailsActivity.this.startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            Toast.makeText(PartyDetailsActivity.this, "There was an error please try again", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            Toast.makeText(PartyDetailsActivity.this, "The authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    biometricPrompt.authenticate(promptInfo);
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
        } else {
            Toast.makeText(PartyDetailsActivity.this, "You must have a biometric authentication to vote", Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        partyNameTextView = findViewById(R.id.party_name_textview);
        partyLogoImageView = findViewById(R.id.party_logo_imageview);
        party_agenda = findViewById(R.id.party_agenda);
        MB_votebtn = findViewById(R.id.MB_votebtn);
        if (source.equals("vote")) {
            MB_votebtn.setVisibility(View.VISIBLE);
        } else {
            MB_votebtn.setVisibility(View.GONE);
        }
    }
}
