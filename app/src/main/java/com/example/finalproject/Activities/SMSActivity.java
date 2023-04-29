package com.example.finalproject.Activities;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Token;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.R;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class SMSActivity extends AppCompatActivity {
    private EditText digit1, digit2, digit3, digit4, digit5, digit6;
    private Button submitButton;
    private Random random = new Random();
    private int randomNumber;
    private String id,phonenum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        findviews();
        randomNumber = random.nextInt(899999) + 100000;
        id = getIntent().getStringExtra("id");
        Date now = new Date();
        Token token = new Token(id,String.valueOf(randomNumber),now);
        TemporaryDB.addToken(token);
        phonenum = TemporaryDB.getPhoneNumberById(id);
        sendSMS(phonenum,"Your code for vote is " + randomNumber);
        setbutton();
    }



    private void setbutton() {
        submitButton.setOnClickListener(v -> {
            int enterednum = Integer.valueOf(digit6.getText().toString()) * 100000 +
                    Integer.valueOf(digit5.getText().toString()) * 10000 +
                    Integer.valueOf(digit4.getText().toString()) * 1000 +
                    Integer.valueOf(digit3.getText().toString()) * 100 +
                    Integer.valueOf(digit2.getText().toString()) * 10 +
                    Integer.valueOf(digit1.getText().toString());
            String tocheck = String.valueOf(enterednum);
        Token myToken = TemporaryDB.getToken(tocheck);
        if(myToken != null){
            if(myToken.getVoterId().equals(id)){
                Date newnow = new Date();
                if(myToken.getExpirationDate().compareTo(newnow) > 0) {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "failure - the ID and the token aren't synchronized", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "There isn't such TOKEN", Toast.LENGTH_SHORT).show();
        }


        });
    }



    private void findviews() {
        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);
        edtxtBehavior();
        submitButton = findViewById(R.id.submit_button);
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
    private void edtxtBehavior() {
        digit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit6.clearFocus();
                    digit5.requestFocus();
                }
            }
        });
        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit5.clearFocus();
                    digit4.requestFocus();
                }
            }
        });
        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit4.clearFocus();
                    digit3.requestFocus();
                }
            }
        });
        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit3.clearFocus();
                    digit2.requestFocus();
                }
            }
        });
        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit2.clearFocus();
                    digit1.requestFocus();
                }
            }
        });
        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    digit1.clearFocus();
                }
            }
        });

    }
}
