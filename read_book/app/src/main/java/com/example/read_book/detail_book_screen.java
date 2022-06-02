package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_library;
import com.example.read_book.model.Book;
import com.example.read_book.model.Library;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_book_screen extends AppCompatActivity {


    private Button btn_readbook_detailbook, btn_addbook_detailbook;
    private TextView txt_description_detailbook, txt_author_book, txt_name_book;
    private ImageView img_book_book;
    private LinearLayout layout_choose_chapter_detailbook;
    public static Integer id_book;
    private List<Book> mbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book_screen);

        btn_readbook_detailbook = (Button) findViewById(R.id.btn_readbook_detailbook);
        btn_readbook_detailbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_book_screen.this, read_book_chapter_screen.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        txt_description_detailbook = (TextView) findViewById(R.id.txt_description_detailbook);
        txt_author_book = (TextView) findViewById(R.id.txt_author_book);
        txt_name_book = (TextView) findViewById(R.id.txt_name_book);
        img_book_book = (ImageView) findViewById(R.id.img_book_book);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        Integer id_user = login_screen.id_user;
        Integer bookImage= (Integer) bundle.get("bookImage");
        String bookName= (String) bundle.get("bookName");
        String bookAuthor= (String) bundle.get("bookAuthor");
        id_book = (Integer) bundle.get("id_book");
        String bookDescription= (String) bundle.get("bookDescription");
        txt_author_book.setText(bookAuthor);
        txt_description_detailbook.setText(bookDescription);
        txt_name_book.setText(bookName);
        System.out.println("id_book "+id_book);
        System.out.println("bookDescription "+bookDescription);

        layout_choose_chapter_detailbook = (LinearLayout) findViewById(R.id.layout_choose_chapter_detailbook);
        layout_choose_chapter_detailbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_book_screen.this, choose_chapter_screen.class);
                startActivity(intent);
            }
        });

        btn_addbook_detailbook = (Button) findViewById(R.id.btn_addbook_detailbook);
        btn_addbook_detailbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_book.api_bo.read_your_library(id_user).enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        mbook = response.body();
                        for (Book bo : mbook){
                            if (bo.getId_book() == id_book){
                                Toast.makeText(detail_book_screen.this, "Your library already has this book", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        api_library.api_li.create_library(id_user, id_book).enqueue(new Callback<Library>() {
                            @Override
                            public void onResponse(Call<Library> call, Response<Library> response) {
                                response.body();
                                Toast.makeText(getApplicationContext(),"Add book successful" , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Library> call, Throwable t) {
                                Toast.makeText(detail_book_screen.this, "Didn't add book", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {

                    }
                });
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
                Intent intent = new Intent(detail_book_screen.this, update_information_user_screen.class);
                startActivity(intent);
            case R.id.create_book:
            case R.id.logout:
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}