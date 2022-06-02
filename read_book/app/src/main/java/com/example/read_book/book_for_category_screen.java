package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_category_book;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category;
import com.example.read_book.model.Category_book;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class book_for_category_screen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private bookAdapter2 bookAdapter;
    private List<Book> mbook;
    private List<Category_book> mcategory_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_category_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
//        Category category = (Category) bundle.get("name_category");
        TextView txt_name_category_book_for_category = findViewById(R.id.txt_name_category_book_for_category);
        txt_name_category_book_for_category.setText((String) bundle.get("name_category"));
        Integer id_category= (Integer) bundle.get("id_category");
//        System.out.println(id_category);



        api_category_book.api_cate_bo.show_all_category_book().enqueue(new Callback<List<Category_book>>() {
            @Override
            public void onResponse(Call<List<Category_book>> call, Response<List<Category_book>> response) {
                mcategory_book = response.body();

                api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        mbook = response.body();
                        recyclerView = findViewById(R.id.recycleview_book_category);
                        bookAdapter = new bookAdapter2( book_for_category_screen.this, getListBook(id_category));
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(book_for_category_screen.this,3,GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(bookAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {

                    }
                });

            }
            @Override
            public void onFailure(Call<List<Category_book>> call, Throwable t) {

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
                Intent intent = new Intent(book_for_category_screen.this, update_information_user_screen.class);
                startActivity(intent);
            case R.id.create_book:
            case R.id.logout:
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private List<Book> getListBook(Integer id_category) {
        List<Book> book = new ArrayList<>();
        List<Integer> id_book = new ArrayList<>();


        for (Category_book cate_bo : mcategory_book){
            if (cate_bo.getId_category() == id_category){
                id_book.add(cate_bo.getId_book());
            }
        }

        for (Integer i : id_book){
            for (Book bo : mbook){
                if (i == bo.getId_book()){
                    book.add(new Book(bo.getId_book(), R.drawable.book1, bo.getBookName(), bo.getBookAuthor(), bo.getBookDescription(), i));
                    break;
                }
            }
        }

        return book;
    }
}