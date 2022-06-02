package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.adapter.chooseChapterAdapter;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_category_book;
import com.example.read_book.api.api_chapter;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category_book;
import com.example.read_book.model.Chapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class choose_chapter_screen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private com.example.read_book.adapter.chooseChapterAdapter chooseChapterAdapter;
    private List<Chapter> mchapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chapter_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        recyclerView = findViewById(R.id.recycleview_choose_chapter_choosechapter);
        chooseChapterAdapter = new chooseChapterAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle == null){
//            return;
//        }
        Integer id_book= detail_book_screen.id_book;
        api_chapter.api_ch.show_all_chapter().enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                mchapter = response.body();
                chooseChapterAdapter.setData(choose_chapter_screen.this, getListChapter(id_book));
                recyclerView.setAdapter(chooseChapterAdapter);
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });
//
//        chooseChapterAdapter.setData(choose_chapter_screen.this, getListChapter(id_book));
//        recyclerView.setAdapter(chooseChapterAdapter);

    }

    private List<Chapter> getListChapter(Integer id_book) {
        List<Chapter> chapter = new ArrayList<>();
        mchapter.sort(Comparator.comparing(a -> a.getChapterTitle()));
        for (Chapter ch : mchapter){
            if (ch.getId_book() == id_book){
//                chapter.add(new Chapter(ch.getId_chapter(), id_book, ch.getChapterTitle()));
                chapter.add(new Chapter(ch.getId_chapter(), ch.getId_book(), ch.getChapterNumberLove(), ch.getChapterTitle(), ch.getChapterContent()));
            }
        }

        return chapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.update_infor:
                Intent intent = new Intent(choose_chapter_screen.this, update_information_user_screen.class);
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