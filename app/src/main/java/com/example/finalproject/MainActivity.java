package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.finalproject.Activities.VoteActivity;
import com.example.finalproject.AdminsLogic.ResultActivity;
import com.example.finalproject.Calculations.Generators;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView titleTextView;
    private LottieAnimationView israel_flag;
    private PopupWindow popupWindow;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setPopup();
        button.setOnClickListener(v->
                resPage()
        );
        Generators.addVotersToDB();
        Generators.addPartiesToDB();
        Generators.addAreasToDB();
        Generators.addAdminToDB();
        onFlagPressed();
    }

    private void setPopup() {
        // Set the width and height of the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popupView = getLayoutInflater().inflate(R.layout.pop_app, null);

        // Create the PopupWindow instance
        popupWindow = new PopupWindow(popupView, width, height, true);

        // Set any desired animations or other properties
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setFocusable(true);
        closeButton = popupView.findViewById(R.id.closeButton);

    }

    private void onFlagPressed() {
        israel_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the popup window at a specific location on the screen
                popupWindow.showAtLocation(israel_flag, Gravity.CENTER, 10, 10);

            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the popup window when close button is clicked
                popupWindow.dismiss();
            }
        });
    }

    private void resPage() {
        Intent intent = new Intent(MainActivity.this, VoteActivity.class);
        startActivity(intent);
    }

    private void findView() {

        button = findViewById(R.id.button);
        titleTextView = findViewById(R.id.titleTextView);
        israel_flag = findViewById(R.id.israel_flag_animation);
        israel_flag.setRepeatCount(LottieDrawable.INFINITE);
        israel_flag.playAnimation();

    }
}