package com.example.finalproject.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.finalproject.AdminsLogic.ManageSection;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Enums.Gender;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;
import com.tsuryo.androidcountdown.Counter;
import com.tsuryo.androidcountdown.TimeUnits;

import java.util.Calendar;
import java.util.Date;

public class VoteActivity extends AppCompatActivity {
    private MaterialButton MB_voteBtn, MB_manageBtn, MB_partyPlatformBtn;
    private ImageView infoButton;
    private String voterId, adminId;
    private boolean isAdmin = false;
    private DbUtils dbUtils;
    private Admin tempAdmin;
    private Voter tempVoter;
    private Counter mCounter;
    private TextView welcome_text;
    private Area tempArea;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);
        voterId = getIntent().getStringExtra("VoterId");
        findViews();

        handleVoterFlow();
        handleAdminFlow();
        setCounter();
        setButtons();

    }

    private void addtitle() {

        String title;
        if (tempVoter != null) {
            if (tempVoter.getGender() == Gender.זכר) {
                title = "שלום אדון " + tempVoter.getFirstName() + " " + tempVoter.getLastName() + ",\n" + "ברוכים הבאים למערכת בחירות דיגיטליות בישראל - העתיד כבר כאן.";
            } else {
                title = "שלום גברת " + tempVoter.getFirstName() + " " + tempVoter.getLastName() + ",\n"  + "ברוכים הבאים למערכת בחירות דיגיטליות בישראל - העתיד כבר כאן.";
            }
        } else {
            // Handle the case when tempVoter is null
            title = "שלום, ברוכים הבאים למערכת בחירות דיגיטליות בישראל - העתיד כבר כאן";
        }

        welcome_text.setText(title);
    }

    private void handleVoterFlow() {
        dbUtils.getVoterByVoterId(Constant.DataBaseName, Constant.VotersCollection, voterId, (success, error) -> {
            if (success != null) {
                tempVoter = (Voter) success;
                dbUtils.getAreaByAreaName(Constant.DataBaseName, Constant.AreasCollection, tempVoter.getArea(), (result, exception) -> {
                    if(result != null){
                        tempArea = (Area) result;
                    }
                });
                addtitle();
            }
        });
    }

    private void setCounter() {
        String formattedDate = TemporaryDB.dateOfStartVotingAfterFormat();
        mCounter.setDate(formattedDate); //countdown starts

        mCounter.setIsShowingTextDesc(true);
        mCounter.setTextColor(R.color.black);
        mCounter.setMaxTimeUnit(TimeUnits.HOUR);
        mCounter.setTextSize(130);
        mCounter.setTypeFace(ResourcesCompat.getFont(this, com.tsuryo.androidcountdown.R.font.digi));

        mCounter.setListener(new Counter.Listener() {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: Counter - " + millisUntilFinished);
            }

            @Override
            public void onTick(long days, long hours, long minutes, long seconds) {
                Log.d(TAG, "onTick: Counter - " + days + "d " +
                        hours + "h " +
                        minutes + "m " +
                        seconds + "s ");
            }
        });
    }

    private void handleAdminFlow() {
        dbUtils.getAdminByVoterId(Constant.DataBaseName, Constant.AdminsCollection, voterId, (success, error) -> {
            if (success != null) {
                tempAdmin = (Admin) success;
                adminId = tempAdmin.getVoterId();
                isAdmin = true;
                costumeView();

            }
        });
    }

    private void costumeView() {
        MB_manageBtn.setVisibility(View.VISIBLE);
        addtitle();
    }

    private void setButtons() {
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.before(TemporaryDB.startDesiredDate)) {
            MB_voteBtn.setEnabled(false);
            //MB_voteBtn.setBackgroundResource(R.drawable.btn_grey);
        } else {
            MB_voteBtn.setEnabled(true);
            //MB_voteBtn.setBackgroundResource(R.drawable.btn_regular);
        }
        MB_voteBtn.setOnClickListener(v -> toAllParties());
        MB_manageBtn.setOnClickListener(v -> toAdminManageSection());
        MB_partyPlatformBtn.setOnClickListener(v -> toPartiesPlatform());
        infoButton.setOnClickListener(v -> openinfodialog());
    }

    private void openinfodialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
        builder.setTitle("פנייה לתמיכה"); // Dialog title

        // Set the text and style for the explanation
        SpannableStringBuilder explanation = new SpannableStringBuilder();
        String email = "digiVote@support.co.il";
        String phone = "1-800-80-90-10";

        String emailText = "במקרה של בעיה ניתן לפנות במייל";
        String phoneText = "או לחייג למספר " + phone;
        String areaText = "במידה ובכל זאת קיימת תקלה באפליקציה";
        String areaTextCon = "אזורך המוגדר בחוק הינו - " + tempArea.getAreaName() + ", " + tempArea.getDefaultVoteStation();

        SpannableString emailSpannable = new SpannableString(emailText);
        emailSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, emailText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString emailSpannable2 = new SpannableString(email);
        emailSpannable2.setSpan(new AbsoluteSizeSpan(12, true), 0, email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString phoneSpannable = new SpannableString(phoneText);
        phoneSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, phoneText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString areaSpannable = new SpannableString(areaText);
        areaSpannable.setSpan(new AbsoluteSizeSpan(12, true), 0, areaText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString areaSpannable2 = new SpannableString(areaTextCon);
        areaSpannable2.setSpan(new AbsoluteSizeSpan(12, true), 0, areaTextCon.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    private void toPartiesPlatform() {
        Intent intent = new Intent(VoteActivity.this, AllParties.class);
        intent.putExtra("from", "platform");
        intent.putExtra("userId", voterId);
        startActivity(intent);
    }

    private void toAllParties() {
        if(tempVoter.isAlreadyVote()){
            cantVoteExplain("לא ניתן להצביע פעמיים, לכן תועבר כעת למסך צפייה במפלגות\nאנא לחץ OK לאישור");

        }else if(tempVoter.getAge() < 18) {
            cantVoteExplain("לא ניתן להצביע מתחת לגיל 18, לכן תועבר כעת למסך צפייה במפלגות\nאנא לחץ OK לאישור");
        }
        else {
            Intent intent = new Intent(VoteActivity.this, AllParties.class);
            intent.putExtra("from", "vote");
            intent.putExtra("userId", voterId);
            startActivity(intent);
        }
    }

    private void cantVoteExplain(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toPartiesPlatform();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void toAdminManageSection() {
        Intent intent = new Intent(VoteActivity.this, ManageSection.class);
        intent.putExtra("voterId", voterId);
        intent.putExtra("area", tempAdmin.getArea());
        intent.putExtra("isAdminLeader", tempAdmin.isAdminLeader());
        startActivity(intent);
    }

    private void findViews() {
        welcome_text = findViewById(R.id.welcome_text);
        mCounter = findViewById(R.id.counter);
        MB_voteBtn = findViewById(R.id.MB_voteBtn);
        MB_manageBtn = findViewById(R.id.MB_manageBtn);
        MB_partyPlatformBtn = findViewById(R.id.MB_partyPlatformBtn);
        infoButton = findViewById(R.id.infoButton);
        dbUtils = new DbUtils();

    }

    @Override
    protected void onStart() {
        super.onStart();
        handleVoterFlow();
    }
}
