package com.itskshitizsh.bhukhad_orders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity {

    private Button SignInButton;
    private EditText editStaffId;
    private EditText editStaffPassword;
    private CheckBox checkBox;
    private DatabaseReference userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editStaffId = findViewById(R.id.editUniqueID);
        editStaffPassword = findViewById(R.id.editPassword);
        checkBox = findViewById(R.id.rememberMeCheckBox);

        Paper.init(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userTable = database.getReference("Staff");

        SignInButton = findViewById(R.id.SignInButton);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save Staff and Password
                if (checkBox.isChecked()) {
                    Paper.book().write("name", editStaffId.getText().toString());
                    Paper.book().write("password", editStaffPassword.getText().toString());
                }

                signInStaff(editStaffId.getText().toString(), editStaffPassword.getText().toString());
            }
        });
    }

    private void signInStaff(final String staffId, final String staffPassword) {
        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Working...\nPlease Wait !");
        mDialog.show();

        userTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Staff exists or not?
                if (dataSnapshot.child(staffId).exists()) {
                    // Get user information
                    mDialog.dismiss();
                    Staff currentStaff = dataSnapshot.child(staffId).getValue(Staff.class);
                    if (currentStaff.getPassword().equals(staffPassword)) {
                        Toast.makeText(SignInActivity.this, "Sign In Successfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, HomeActivity.class).putExtra("name", currentStaff.getName()));
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, " Sign In Failed !!\nCheck Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Staff not exist inside Database\nPlease Login with Staff Account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
