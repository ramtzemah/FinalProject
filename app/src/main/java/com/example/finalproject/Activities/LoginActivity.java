package com.example.finalproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Calculations.Calculation;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etId;
    private ImageButton btnSend;
    private ImageView helpButton;
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
        Log.d("aaaa","aaaa");
        btnSend.setOnClickListener(v -> toSMS());
        helpButton.setOnClickListener(v -> openHelpDialog());
    }

    private void toSMS() {
        String theText = etId.getText().toString();
        if(theText.isEmpty()){
            Toast.makeText(this,"אתה חייב למלא את השדה של התעודת זהות", Toast.LENGTH_SHORT).show();
        }
        else if (!Calculation.isValidId(theText)){
            Toast.makeText(this,"אנא הזן תעודת זהות תקינה", Toast.LENGTH_SHORT).show();
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
        helpButton = findViewById(R.id.helpButton);
    }
    private void openHelpDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("פנייה לתמיכה"); // Dialog title

        // Set the text and style for the explanation
        SpannableStringBuilder explanation = new SpannableStringBuilder();
        String email = "digiVote@support.co.il";
        String phone = "1-800-80-90-10";

        String emailText = "במקרה של בעיה ניתן לפנות במייל";
        String phoneText = "או לחייג למספר " + phone;
        String ConText = "אם הופנת לחלונית זו";
        String ConTextTwo = "ככל הנראה תעודת הזהות שהוקשה לא נמצאת במאגר.";

        SpannableString emailSpannable = new SpannableString(emailText);
        emailSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, emailText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString emailSpannable2 = new SpannableString(email);
        emailSpannable2.setSpan(new AbsoluteSizeSpan(12, true), 0, email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString phoneSpannable = new SpannableString(phoneText);
        phoneSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, phoneText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString areaSpannable = new SpannableString(ConText);
        areaSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, ConText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString areaSpannable2 = new SpannableString(ConTextTwo);
        areaSpannable2.setSpan(new AbsoluteSizeSpan(12, true), 0, ConTextTwo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        explanation.append(emailSpannable);
        explanation.append("\n");
        explanation.append(emailSpannable2);
        explanation.append("\n");
        explanation.append(phoneSpannable);
        explanation.append("\n\n");
        explanation.append(areaSpannable);
        explanation.append("\n");
        explanation.append(areaSpannable2);

        // Set the explanation text in the dialog
        builder.setMessage(explanation);

        // Set the positive button for OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any actions needed when the OK button is clicked
                //for now nothing needed
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
