package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.read_book.api.api_user;
import com.example.read_book.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_screen extends AppCompatActivity {

    private Button btn_login_signup, btn_signup_signup;
    private EditText txt_email_signup, txt_password_singup, txt_passwordconfirm_singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        btn_login_signup = (Button) findViewById(R.id.btn_login_signup);
        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup_screen.this, login_screen.class);
                startActivity(intent);
            }
        });


        txt_email_signup = (EditText) findViewById(R.id.txt_email_signup);
        txt_password_singup = (EditText) findViewById(R.id.txt_password_singup);
        txt_passwordconfirm_singup = (EditText) findViewById(R.id.txt_passwordconfirm_singup);

        btn_signup_signup = (Button) findViewById(R.id.btn_signup_signup);
        btn_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singup();
            }
        });
    }

    private void singup(){
        String email = txt_email_signup.getText().toString();
        String password = txt_password_singup.getText().toString();
        String password_confirm = txt_passwordconfirm_singup.getText().toString();
        if (password.equals(password_confirm)){
            api_user.api_us.create_user(email, password, "", "", 0,
                    "", "", "", "", "", "").enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    response.body();
                    Toast.makeText(getApplicationContext(),"Create account successful" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup_screen.this, login_screen.class);
                    startActivity(intent);
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(signup_screen.this, "Cannot create account", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(signup_screen.this, "Passwords are not the same", Toast.LENGTH_SHORT).show();
        }

    }
}