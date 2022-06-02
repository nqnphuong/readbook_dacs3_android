package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.read_book.api.api_user;
import com.example.read_book.model.User;

import org.w3c.dom.Text;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_information_user_screen extends AppCompatActivity {
    private EditText txt_pass_infor_update, txt_name_infor_update, txt_dob_infor_update, txt_phonenum_infor_update, txt_description_infor_update;
    private TextView txt_email_infor_update;
    private Button btn_update_update_infor_us;
    private de.hdodenhof.circleimageview.CircleImageView img_avatar_infor_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_user_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);


        txt_email_infor_update = (TextView) findViewById(R.id.txt_email_infor_update);
        txt_pass_infor_update = (EditText) findViewById(R.id.txt_pass_infor_update);
        txt_name_infor_update = (EditText) findViewById(R.id.txt_name_infor_update);
        txt_dob_infor_update = (EditText) findViewById(R.id.txt_dob_infor_update);
        txt_phonenum_infor_update = (EditText) findViewById(R.id.txt_phonenum_infor_update);
        txt_description_infor_update = (EditText) findViewById(R.id.txt_description_infor_update);
        txt_description_infor_update.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        img_avatar_infor_update = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.img_avatar_infor_update);

        Integer id_user = login_screen.id_user;
        System.out.println(id_user);
        show_infor_user(id_user);

        btn_update_update_infor_us = (Button) findViewById(R.id.btn_update_update_infor_us);
        btn_update_update_infor_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test_dob = txt_dob_infor_update.getText().toString();
                char c[] = test_dob.toCharArray();
                if (c.length !=10 ){
                    Toast.makeText(getApplicationContext(),"Wrong date of birth format" , Toast.LENGTH_SHORT).show();
                }
                if (c[2] != '/' || c[5] != '/'){
                    Toast.makeText(getApplicationContext(),"Wrong date of birth format" , Toast.LENGTH_SHORT).show();
                } else {
                    String email = txt_email_infor_update.getText().toString();
                    String pass = txt_pass_infor_update.getText().toString();
                    String name = txt_name_infor_update.getText().toString();
                    String dob = txt_dob_infor_update.getText().toString();
                    String phonenum = txt_phonenum_infor_update.getText().toString();
                    String description = txt_description_infor_update.getText().toString();
                    api_user.api_us.update_user(id_user, email, pass, name, "", 0, dob, "","", "",phonenum, description)
                            .enqueue(new Callback<List<User>>() {
                                @Override
                                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                    Toast.makeText(getApplicationContext(),"Update information successfully" , Toast.LENGTH_SHORT).show();
                                    show_infor_user(id_user);
                                }

                                @Override
                                public void onFailure(Call<List<User>> call, Throwable t) {
                                    System.out.println(t.toString());
                                }
                            });

                }

            }
        });


    }

    public void show_infor_user(Integer id_user){

        api_user.api_us.user_readByID(id_user).enqueue(new Callback<List<User>>() {
        @Override
        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            for (User user : response.body()){
                System.out.println(user);
                txt_email_infor_update.setText(user.getUserEmail());
                txt_pass_infor_update.setText(user.getUserPassword());
                txt_name_infor_update.setText(user.getUserFirstname());
                txt_dob_infor_update.setText(user.getUserDayofbirth());
                txt_phonenum_infor_update.setText(user.getUserPhone());
                txt_description_infor_update.setText(user.getUserDescription());
            }
        }

        @Override
        public void onFailure(Call<List<User>> call, Throwable t) {
            System.out.println(t.toString());
        }
    });
}



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.update_infor:
                Intent intent = new Intent(this, update_information_user_screen.class);
                startActivity(intent);
            case R.id.create_book:
            case R.id.logout:
        }

        return super.onOptionsItemSelected(item);
    }
}