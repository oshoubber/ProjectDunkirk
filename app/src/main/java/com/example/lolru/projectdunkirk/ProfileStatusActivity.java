package com.example.lolru.projectdunkirk;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_status);

        Button statusButton = (Button) findViewById(R.id.statusButton);
        Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        Button specialButton = (Button) findViewById(R.id.specialButton);


        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog1();
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog3();
            }
        });

        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                createAlertDialog2();
            }
        });
    }

    void createAlertDialog1() {
        String[] singleChoiceItems = getResources().getStringArray(R.array.SelectEmergencyLevel);
        final AlertDialog.Builder dialog1 = new AlertDialog.Builder(ProfileStatusActivity.this);
        dialog1.setTitle(getString(R.string.emergencyLevel));
        dialog1.setSingleChoiceItems(singleChoiceItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            }
        })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }
    void createAlertDialog2() {
        String[] multiChoiceItems = getResources().getStringArray(R.array.SelectEmergencies);
        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProfileStatusActivity.this);
        boolean[] checkedItems = {false, false, false, false, false};
        dialog2
                .setTitle(getString(R.string.emergencyTypes))
                .setMultiChoiceItems(multiChoiceItems, checkedItems, null)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }
    void createAlertDialog3() {
        String[] multiChoiceItems = getResources().getStringArray(R.array.SelectEmergencyCondition);
        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProfileStatusActivity.this);
        boolean[] checkedItems = {false, false, false, false, false};
        dialog2
                .setTitle(getString(R.string.emergencyCondition))
                .setMultiChoiceItems(multiChoiceItems, checkedItems, null)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }
}
