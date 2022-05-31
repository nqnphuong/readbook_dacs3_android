package com.example.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signup_screen extends AppCompatActivity {

    private Button btn_login_signup, btn_signup_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        btn_login_signup = (Button) findViewById(R.id.btn_login_signup);
        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup_screen.this, login_screen.class);
                startActivity(intent);
            }
        });

        btn_signup_signup = (Button) findViewById(R.id.btn_signup_signup);
        btn_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup_screen.this, login_screen.class);
                startActivity(intent);
            }
        });
    }
}