package com.example.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login_screen extends AppCompatActivity {

    private Button btn_signup_login, btn_login_login;
    private TextView txt_email_login, txt_password_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btn_signup_login = (Button) findViewById(R.id.btn_signup_login);
        btn_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, signup_screen.class);
                startActivity(intent);
            }
        });

        txt_email_login = (TextView) findViewById(R.id.txt_email_login);
        txt_password_login = (TextView) findViewById(R.id.txt_password_login);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, home_screen.class);
                startActivity(intent);
            }
        });
    }
}