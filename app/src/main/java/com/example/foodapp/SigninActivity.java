package com.example.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.Model.User;
import com.example.foodapp.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SigninActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnOk);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User");

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);
                progressDialog.setMessage("صبر کنید...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        //check if user not exist in database
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                            progressDialog.dismiss();
                            //user get information
                            User user;
                            user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString());
                            assert user != null;
                            if (user.getPassword().equals(edtPassword.getText().toString())) {

                                Intent intent=new Intent(SigninActivity.this,Home.class);
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();
                            } else {

                                Toast.makeText(SigninActivity.this, "خطا در ورود", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(SigninActivity.this,"کاربر موجود نیست", Toast.LENGTH_SHORT).show();

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
