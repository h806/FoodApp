package com.example.foodapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtPhone,edtName,edtPass;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName =findViewById(R.id.edtName);
        edtPhone =findViewById(R.id.edtPhone);
        edtPass =findViewById(R.id.edtPassword);

        btnSignUp =findViewById(R.id.signup);

        //init firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("صبر کنید...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {


                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "این کاربر از قبل موجود است", Toast.LENGTH_SHORT).show();

                        }else {
                            progressDialog.dismiss();
                            User user = new User(edtName.getText().toString(),edtPass.getText().toString(),edtPhone.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "شماره تلفن شما ثبت شد", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
