package com.example.foodapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp,btnSignIn;
    TextView txtSlogon , txtLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.signUp);
        btnSignIn = findViewById(R.id.signin);
        txtSlogon = findViewById(R.id.txtSlogon);
        txtLogo   = findViewById(R.id.txtlogo);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/IranNastaliq_1.ttf");
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");

        txtLogo.setTypeface(face);
        txtSlogon.setTypeface(typeface);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SigninActivity.class);
                startActivity(i);
            }
        });
    }


}
