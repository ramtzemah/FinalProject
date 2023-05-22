package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Calculations.Calculation;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etId;
    private ImageButton btnSend;
    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    private DbUtils dbUtils;
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
        String theText = etId.getText().toString();
        if(theText.isEmpty()){
            Toast.makeText(this,"אתה חייב למלא את השדה של התעודת זהות", Toast.LENGTH_SHORT).show();
        }
        //TODO fix it
        else if (!Calculation.isValidId(theText)){

        }
        else {
            dbUtils.getPhoneNumberById(Constant.DataBaseName, Constant.VotersCollection, theText, (success,error) ->{
                    if(success != null){
                        //send to next intent only if ID is valid + ID is in DB
                        String phoneNumber = (String) success;
                        Intent intent = new Intent(LoginActivity.this, SMSActivity.class);
                        intent.putExtra("id",theText);
                        intent.putExtra("phoneNumber",phoneNumber);
                        startActivity(intent);
                        finish();
                    }
            });
        }
    }

    private void findviews() {
        dbUtils = new DbUtils();
        etId = findViewById(R.id.ET_id);
        btnSend = findViewById(R.id.lets_go_button);
    }


}
