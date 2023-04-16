package com.example.finalproject.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalproject.R;

public class LoginActivitiy extends AppCompatActivity {
    private EditText etId;
    private Button btnSend;
    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        findviews();
        setButton();
    }

    private void setButton() {
        btnSend.setOnClickListener(v -> toSMS());
    }

    private void toSMS() {
        //send to next intent only if ID is valid + ID is in DB
        Intent intent = new Intent(LoginActivitiy.this, SMSActivity.class);
        intent.putExtra("id",etId.getText().toString());
        startActivity(intent);
    }

    private void findviews() {
        etId = findViewById(R.id.ET_id);
        btnSend = findViewById(R.id.BTN_send);
    }
    public static boolean isValidId(String id) {
        // Ensure that the ID is 9 digits long
        if (id.length() != 9) {
            return false;
        }

        // Multiply each digit by a weight factor and sum the products
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Integer.parseInt(id.substring(i, i+1));
            int weight = (i % 2 == 0) ? 1 : 2;
            int product = digit * weight;
            if (product > 9) {
                product -= 9;
            }
            sum += product;
        }

        // Check if the sum is divisible by 10
        return (sum % 10 == 0);
    }

}
