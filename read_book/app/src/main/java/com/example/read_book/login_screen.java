package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.read_book.api.api_user;
import com.example.read_book.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_screen extends AppCompatActivity {

    private Button btn_signup_login, btn_login_login;
    private EditText txt_email_login, txt_password_login;
    private List<User> mList;
    public static Integer id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        btn_signup_login = (Button) findViewById(R.id.btn_signup_login);
        btn_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, signup_screen.class);
                startActivity(intent);
            }
        });

        txt_email_login = (EditText) findViewById(R.id.txt_email_login);
        txt_password_login = (EditText) findViewById(R.id.txt_password_login);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListUser();
                login();
            }
        });
    }
    private void login(){
        String userEmail = txt_email_login.getText().toString().trim();
        String userPassword = txt_password_login.getText().toString().trim();
        if (mList == null || mList.isEmpty()){
            return;
        }
        boolean ishaveuser = false;
        for (User user : mList) {
            if  (userEmail.equals(user.getUserEmail()) && userPassword.equals(user.getUserPassword())) {
                id_user = user.getId_user();
                ishaveuser = true; // dung tk
                break;
            }
        }
        if (ishaveuser == true){
            Toast.makeText(login_screen.this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(login_screen.this, home_screen.class);
            startActivity(intent);
        } else {
            Toast.makeText(login_screen.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUser(){
        api_user.api_us.show_all_user().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mList = response.body();
                System.out.println(mList.size());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(login_screen.this, "Didn't get database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}