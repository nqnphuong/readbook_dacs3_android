package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.api.api_book;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category_book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mybook_screen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private com.example.read_book.adapter.bookAdapter2 bookAdapter2;
    private List<Book> mbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);


        Integer id_user = login_screen.id_user;
        System.out.println(id_user);

        api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mbook = response.body();
                recyclerView = (RecyclerView) findViewById(R.id.recycleview_book_mybook);
                bookAdapter2 = new bookAdapter2( mybook_screen.this, getListBook(id_user));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mybook_screen.this,3,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(bookAdapter2);
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    private List<Book> getListBook(Integer id_user) {
        List<Book> book = new ArrayList<>();

        for (Book bo : mbook){
            if (bo.getId_user() == id_user){
                book.add(new Book(bo.getId_book(), R.drawable.book1, bo.getBookName(), bo.getBookAuthor(), bo.getBookDescription(), 0));
            }
        }

        return book;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}