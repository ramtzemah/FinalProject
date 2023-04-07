package com.example.finalproject.AdminsLogic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FireAdmin extends AppCompatActivity {

    private MaterialButton MB_fire_admin;
    private MaterialButton MB_clean_search;
    private MaterialButton MB_searchAdminBtn;
    private Spinner admins_dropdown;
    private LinearLayout ll_search;
    private LinearLayout ll_fire;
    private Admin tempAdmin;
    private List<String> adminNames;
    private CardView cardView;
    private TextView idNumber;
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private TextView city;
    private TextView age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_admin);
        findView();
        setButtons();
        refreshList();
    }

    private void setButtons() {
        MB_clean_search.setOnClickListener(v -> cleanSearch());
        MB_searchAdminBtn.setOnClickListener(v -> searchAdmin());
        MB_fire_admin.setOnClickListener(v -> fireAdmin());
    }

    private void fireAdmin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("הדחת: " + tempAdmin.getFirstName() + " " + tempAdmin.getLastName()+ " מאחראי אזור: " + tempAdmin.getArea());
        builder.setMessage("האם אתה בטוח שתרצה לבצע פעולה זו?");

        // Set up the buttons
        builder.setPositiveButton("כן!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TemporaryDB.fireAdmin(tempAdmin.getVoterId());
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

    private void searchAdmin() {
        tempAdmin = null;
        String selectedAdmin = admins_dropdown.getSelectedItem().toString();
        if(selectedAdmin.isEmpty()){
            Toast.makeText(this,"אתה חייב לבחור אדמין", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] details= selectedAdmin.split(",");
            String[] adminId= details[1].split("\\s");
            ll_search.setVisibility(View.INVISIBLE);
            ll_fire.setVisibility(View.VISIBLE);
            for(Admin admin : TemporaryDB.getAllAdmins().values()){
                if(String.valueOf(admin.getIdNumber()).equals(adminId[1])){
                    tempAdmin = admin;
                    break;
                }
            }
        }
            presentAdmin(tempAdmin);
        }

    private void presentAdmin(Admin tempAdmin) {
        cardView.setVisibility(View.VISIBLE);
        ll_fire.setVisibility(View.VISIBLE);
        ll_search.setVisibility(View.INVISIBLE);
        idNumber.setText("מספר תעודת זהות: " + tempAdmin.getIdNumber());
        firstName.setText("שם פרטי: " + tempAdmin.getFirstName());
        lastName.setText("שם משפחה: " + tempAdmin.getLastName());
        city.setText("עיר: " + tempAdmin.getCity());
        gender.setText("מין: " + tempAdmin.getGender().toString());
        age.setText("גיל: " + tempAdmin.getAge());
    }

    private void cleanSearch() {
        tempAdmin = null;
        cardView.setVisibility(View.INVISIBLE);
        ll_fire.setVisibility(View.INVISIBLE);
        ll_search.setVisibility(View.VISIBLE);
        admins_dropdown.setSelection(0);
    }

    private void findView() {
        MB_clean_search = findViewById(R.id.MB_clean_search);
        MB_searchAdminBtn = findViewById(R.id.MB_searchAdminBtn);
        MB_fire_admin = findViewById(R.id.MB_fire_admin);
        admins_dropdown = findViewById(R.id.admins_dropdown);
        ll_search = findViewById(R.id.ll_search);
        ll_fire = findViewById(R.id.ll_fire);
        refreshList();
        cardView = findViewById(R.id.cardView);
        idNumber = findViewById(R.id.idNumber);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        city = findViewById(R.id.city);
        age = findViewById(R.id.age);
    }

    private void refreshList() {
        adminNames = new ArrayList<>();
        adminNames.add("");
        for(Admin admin : TemporaryDB.getAllAdmins().values()){
            if(!admin.isAdminLeader()){
//                adminNames.add(admin.getFirstName() + " " + admin.getLastName() + "," + admin.getIdNumber() + "," +admin.getArea());
                adminNames.add("שם: " + admin.getFirstName() + " " + admin.getLastName() + " ," + "ת.ז: " + admin.getIdNumber() + " ," + "אזור: "  +admin.getArea());
            }
        }

// Create an ArrayAdapter with the keysList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, adminNames);

// Set the ArrayAdapter to the spinner
        admins_dropdown.setAdapter(adapter);
    }
}