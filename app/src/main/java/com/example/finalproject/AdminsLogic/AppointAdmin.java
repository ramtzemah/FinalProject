package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Calculations.Calculation;
import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AppointAdmin extends AppCompatActivity {
    private EditText id_search;
    private CardView cardView;
    private TextView idNumber;
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private TextView city;
    private TextView age;
    private LinearLayout ll_search;
    private LinearLayout ll_manage;
    private Spinner areasDropdown;
    private MaterialButton MB_searchAdminBtn;
    private MaterialButton MB_clean_search;
    private MaterialButton MB_appointAdminBtn;
    private Voter tempVoter = null;
    private List<String> areaNames;
    private DbUtils dbUtils;
    private ImageButton backButton;
    private String voterId, area;
    private boolean isAdminLeader = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_admin);
        voterId = getIntent().getStringExtra("voterId");
        area = getIntent().getStringExtra("area");
        isAdminLeader = getIntent().getBooleanExtra("isAdminLeader", false);
        dbUtils = new DbUtils();
        findViews();
        setButtons();
        refershList();
    }

    private void setButtons() {
        MB_searchAdminBtn.setOnClickListener(v -> searchAdmin());
        MB_clean_search.setOnClickListener(v -> cleanSearch());
        MB_appointAdminBtn.setOnClickListener(v -> appointAdmin());
        backButton.setOnClickListener(v -> toAdminPage());
    }
    private void toAdminPage() {
        Intent intent = new Intent(AppointAdmin.this, ManageSection.class);
        intent.putExtra("voterId", voterId);
        intent.putExtra("area", area);
        intent.putExtra("isAdminLeader", isAdminLeader);
        startActivity(intent);
        finish();
    }

    private void dialoAappointAdmin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("מינוי: " + tempVoter.getFirstName() + " " + tempVoter.getLastName() + " לאחראי אזור: " + areasDropdown.getSelectedItem().toString());
        builder.setMessage("האם אתה בטוח שתרצה לבצע פעולה זו?");

        // Set up the buttons
        builder.setPositiveButton("כן!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Admin admin = new Admin(tempVoter, areasDropdown.getSelectedItem().toString(), false);
                dbUtils.manageAdmin(Constant.DataBaseName, Constant.AdminsCollection, tempVoter.getVoterId(), admin);
                finish();
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

    private void appointAdmin() {
        if (areasDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(this, "אתה חייב לבחור אזור", Toast.LENGTH_SHORT).show();
        } else {
            dialoAappointAdmin();
        }
    }

    private void cleanSearch() {
        tempVoter = null;
        cardView.setVisibility(View.INVISIBLE);
        ll_manage.setVisibility(View.INVISIBLE);
        ll_search.setVisibility(View.VISIBLE);
        areasDropdown.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        int newMarginTop = -400; // Set your desired margin top value here
        layoutParams.setMargins(layoutParams.leftMargin, newMarginTop, layoutParams.rightMargin, layoutParams.bottomMargin);

        ll_search.setLayoutParams(layoutParams);
        id_search.setText("");
    }

    private void searchAdmin() {
        tempVoter = null;
        String theText = id_search.getText().toString();
        if (theText.isEmpty()) {
            Toast.makeText(this, "אתה חייב למלא את השדה של התעודת זהות", Toast.LENGTH_SHORT).show();
        }
        //TODO fix it
        else if (!Calculation.isValidId(theText)) {

        }
//        else if (theText.length() != 9) {
//            Toast.makeText(this,"תעודת זהות לא תקינה", Toast.LENGTH_SHORT).show();
//        }
        else {
            dbUtils.getVoterById(Constant.DataBaseName, Constant.VotersCollection, theText, (success, error) -> {
                if (success != null) {
                    boolean isAdmin = false;
                    tempVoter = (Voter) success;
                    for (Admin admin : TemporaryDB.getAllAdmins().values()) {
                        if (String.valueOf(admin.getVoterId()).equals(tempVoter.getVoterId())) {
                            isAdmin = true;
                            break;
                        }
                    }
                    if (isAdmin) {
                        Toast.makeText(this, "מי שבחרת כבר מהנל אזור מסויים, אי אפשר להיות מנהל של כמה אזורים", Toast.LENGTH_SHORT).show();
                    } else {
                        presentVoter(tempVoter);
                    }
                } else {
                    Toast.makeText(this, "לא נמצא אף אזרח, אנא בדוק את ה ת.ז ונסה שנית", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void presentVoter(Voter tempVoter) {
        cardView.setVisibility(View.VISIBLE);
        ll_manage.setVisibility(View.VISIBLE);
        ll_search.setVisibility(View.INVISIBLE);
        areasDropdown.setVisibility(View.VISIBLE);
        idNumber.setText("מספר תעודת זהות: " + tempVoter.getIdNumber());
        firstName.setText("שם פרטי: " + tempVoter.getFirstName());
        lastName.setText("שם משפחה: " + tempVoter.getLastName());
        city.setText("עיר: " + tempVoter.getArea());
        gender.setText("מין: " + tempVoter.getGender().toString());
        age.setText("גיל: " + tempVoter.getAge());
    }

    private void findViews() {
        id_search = findViewById(R.id.id_search);
        cardView = findViewById(R.id.cardView);
        idNumber = findViewById(R.id.idNumber);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        city = findViewById(R.id.city);
        age = findViewById(R.id.age);
        areasDropdown = findViewById(R.id.areasDropdown);
        MB_searchAdminBtn = findViewById(R.id.MB_searchAdminBtn);
        MB_appointAdminBtn = findViewById(R.id.MB_appointAdminBtn);
        MB_clean_search = findViewById(R.id.MB_clean_search);
        ll_search = findViewById(R.id.ll_search);
        ll_manage = findViewById(R.id.ll_manage);
        backButton = findViewById(R.id.backButton);
        refershList();
    }

    private void refershList() {
        areaNames = new ArrayList<>();
        areaNames.add("");
        for (Area area : TemporaryDB.getAllAreas().values()) {
            areaNames.add(area.getAreaName());
        }

// Create an ArrayAdapter with the keysList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areaNames);

// Set the ArrayAdapter to the spinner
        areasDropdown.setAdapter(adapter);
    }
}