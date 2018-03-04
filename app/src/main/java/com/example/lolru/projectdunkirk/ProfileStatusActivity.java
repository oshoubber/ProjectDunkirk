package com.example.lolru.projectdunkirk;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.*;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileStatusActivity extends AppCompatActivity {
    String firstNameS = ""; // First Name
    String lastNameS = ""; // Last Name
    String ageS = "";   // # people in party
    int selectedPosition = -1; // Personal Status
    boolean[] checkedEmergencies = new boolean[] {false, false, false, false }; // emergency condition
    boolean[] checkedConditions = new boolean[] {false, false, false, false }; // special condition


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_status);

        Button statusButton = (Button) findViewById(R.id.statusButton);
        Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        Button specialButton = (Button) findViewById(R.id.specialButton);
        Button submitButton = (Button) findViewById(R.id.updateButton);

        final TextInputLayout firstName = findViewById(R.id.firstName);
        final TextInputLayout lastName = findViewById(R.id.lastName);
        final TextInputLayout personAge = findViewById(R.id.personAge);

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog1();
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog2();
            }
        });

        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog3();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileStatusActivity.this, "Your profile has been recorded.", Toast.LENGTH_SHORT).show();
                firstNameS = firstName.getEditText().toString();
                lastNameS = lastName.getEditText().toString();
                ageS = personAge.getEditText().toString();

                JSONObject dataPacket = new JSONObject();

                finish();
            }
        });
    }

    void createAlertDialog1() {
        String[] singleChoiceItems = getResources().getStringArray(R.array.SelectEmergencyLevel);
        final AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        dialog1.setTitle(getString(R.string.emergencyLevel));
        dialog1.setSingleChoiceItems(singleChoiceItems, selectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,  int i) {
                selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            }
        })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .show();
    }

    void createAlertDialog2() {
        String[] multiChoiceItems = getResources().getStringArray(R.array.SelectEmergencyCondition);
        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProfileStatusActivity.this);
        dialog2
                .setTitle(getString(R.string.emergencyCondition))
                .setMultiChoiceItems(multiChoiceItems, checkedEmergencies, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedEmergencies[which] = isChecked;
                    }
                })
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }

    void createAlertDialog3() {String[] multiChoiceItems = getResources().getStringArray(R.array.SelectEmergencies);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.emergencyTypes))
                .setMultiChoiceItems(multiChoiceItems, checkedConditions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedConditions[which] = isChecked;
                    }
                })
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }
}
