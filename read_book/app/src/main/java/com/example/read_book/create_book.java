package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.adapter.bookAdapter_createbook;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_chapter;
import com.example.read_book.model.Book;
import com.example.read_book.model.Chapter;
import com.example.read_book.ui.home.HomeFragment;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_book extends AppCompatActivity {
    private RecyclerView recyclerView;
    private bookAdapter_createbook bookAdapter_createbook;
    private List<Book> mbook;
    private List<Chapter> mchapter;
    private LinearLayout layout_create_new_book_createbook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        layout_create_new_book_createbook = (LinearLayout) findViewById(R.id.layout_create_new_book_createbook);
        layout_create_new_book_createbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(create_book.this, detail_createbook_screeen.class);
                startActivity(intent);
            }
        });



        Integer id_user = login_screen.id_user;
        System.out.println(id_user);

        api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mbook = response.body();
                api_chapter.api_ch.show_all_chapter().enqueue(new Callback<List<Chapter>>() {
                    @Override
                    public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                        mchapter = response.body();
                        recyclerView = findViewById(R.id.recycleview_book_createbook);
                        bookAdapter_createbook = new bookAdapter_createbook( create_book.this, getListBook(id_user));
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(create_book.this,1,GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(bookAdapter_createbook);
                    }

                    @Override
                    public void onFailure(Call<List<Chapter>> call, Throwable t) {

                    }
                });

            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(create_book.this, home_screen.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private List<Book> getListBook(Integer id_user) {
        List<Book> book = new ArrayList<>();
        int num = 0;
        for (Book bo : mbook){
            if (bo.getId_user() == id_user){
//                for (Chapter ch : mchapter){
//                    if (ch.getId_book() == bo.getId_book()){
//                        num = num + 1;
//                        System.out.println(num);
//                    }
//
//                }
                book.add(new Book(bo.getId_book(), bo.getBookName(), bo.getBookImage(),  bo.getBookAuthor(), bo.getBookDescription(),"", num));
                num = 0;
            }
        }
        return book;
    }
}