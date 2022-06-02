package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.read_book.api.api_book;
import com.example.read_book.model.Book;
import com.example.read_book.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_createbook_screeen extends AppCompatActivity {
    private Button btn_create_book;
    private EditText txt_description_book_detailcreatebook, txt_title_book_detailcreatebook;
    private LinearLayout layout_bookcover_createbook;
    private ImageView img_book_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_createbook_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        layout_bookcover_createbook = (LinearLayout) findViewById(R.id.layout_bookcover_createbook);
        img_book_book = (ImageView) findViewById(R.id.img_book_book);
        txt_title_book_detailcreatebook = (EditText) findViewById(R.id.txt_title_book_detailcreatebook);
        txt_description_book_detailcreatebook = (EditText) findViewById(R.id.txt_description_book_detailcreatebook);
        txt_description_book_detailcreatebook.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        btn_create_book = (Button) findViewById(R.id.btn_create_book);

//
        btn_create_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txt_title_book_detailcreatebook.getText().toString();
                String des = txt_description_book_detailcreatebook.getText().toString();
                Integer id_user = login_screen.id_user;
                api_book.api_bo.create_book(title, "123", id_user, des, "","")
                        .enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        Toast.makeText(getApplicationContext(),"Create book successfully" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(detail_createbook_screeen.this, create_book.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, create_book.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}