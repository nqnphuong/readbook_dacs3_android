package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.adapter.bookAdapter_createbook;
import com.example.read_book.adapter.categoryAdapter_detail_createchapter;
import com.example.read_book.adapter.categoryfindAdapter;
import com.example.read_book.adapter.chapterAdapter_detail_createchapter;
import com.example.read_book.adapter.chooseChapterAdapter;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_category;
import com.example.read_book.api.api_category_book;
import com.example.read_book.api.api_chapter;
import com.example.read_book.api.api_user;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category;
import com.example.read_book.model.Category_book;
import com.example.read_book.model.Chapter;
import com.example.read_book.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_createbook extends AppCompatActivity {
    private LinearLayout layout_bookcover_createbook;
    private ImageView img_book_book;
    private EditText txt_title_book_detailcreatebook, txt_description_book_detailcreatebook;
    private RecyclerView recycleview_category, recycleview_chapter;
    private Button btn_update_book, btn_create_chapter, btn_delete_book;
    private List<Chapter> mchapter;
    private chapterAdapter_detail_createchapter chapterAdapter_detail_createchapter;
    private categoryAdapter_detail_createchapter categoryAdapter_detail_createchapter;
    public static Integer id_book;
    private List<Category> mcategory;
    private List<Category_book> mcategory_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_createbook);

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
        recycleview_category = (RecyclerView) findViewById(R.id.recycleview_category);
        recycleview_chapter = (RecyclerView) findViewById(R.id.recycleview_chapter);
        btn_update_book = (Button) findViewById(R.id.btn_update_book);
        btn_create_chapter = (Button) findViewById(R.id.btn_create_chapter);
        btn_delete_book = (Button) findViewById(R.id.btn_delete_book);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            id_book = create_chapter_screen.id_book;
        } else {
            id_book = (Integer) bundle.get("id_book");
        }

        showInforBook(id_book);
        System.out.println("id_book "+ id_book);
        api_chapter.api_ch.read_by_id_book(id_book).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                mchapter = response.body();
                System.out.println("chapter "+mchapter);
                chapterAdapter_detail_createchapter = new chapterAdapter_detail_createchapter();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(detail_createbook.this, recycleview_chapter.VERTICAL, false);
                recycleview_chapter.setLayoutManager(linearLayoutManager);
                chapterAdapter_detail_createchapter.setData(detail_createbook.this, getListChapter());
                recycleview_chapter.setAdapter(chapterAdapter_detail_createchapter);
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

        btn_update_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = txt_title_book_detailcreatebook.getText().toString();
                String bookDes = txt_description_book_detailcreatebook.getText().toString();
                api_book.api_bo.book_update(id_book, bookName, "123", bookDes)
                        .enqueue(new Callback<List<Book>>() {
                            @Override
                            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                                Toast.makeText(getApplicationContext(),"Update book successfully" , Toast.LENGTH_SHORT).show();
                                showInforBook(id_book);
                            }

                            @Override
                            public void onFailure(Call<List<Book>> call, Throwable t) {
                                System.out.println(t.toString());
                            }
                        });
            }
        });

        btn_create_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_createbook.this, create_chapter_screen.class);
                startActivity(intent);
            }
        });


    }

    private void showInforBook(Integer id_book){
        api_book.api_bo.read_by_id(id_book).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                for (Book bo : response.body()){
                    txt_title_book_detailcreatebook.setText(bo.getBookName());
                    txt_description_book_detailcreatebook.setText(bo.getBookDescription());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

        // category
        recycleview_category = (RecyclerView) findViewById(R.id.recycleview_category);
        categoryAdapter_detail_createchapter = new categoryAdapter_detail_createchapter(detail_createbook.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recycleview_category.setLayoutManager(linearLayoutManager);

        api_category.api_ca.show_all_category().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mcategory = response.body();

                api_category_book.api_cate_bo.category_book_readByID_book(id_book).enqueue(new Callback<List<Category_book>>() {
                    @Override
                    public void onResponse(Call<List<Category_book>> call, Response<List<Category_book>> response) {
                        mcategory_books = response.body();
                        categoryAdapter_detail_createchapter.setData(getListCategory());
                        recycleview_category.setAdapter(categoryAdapter_detail_createchapter);
                    }
                    @Override
                    public void onFailure(Call<List<Category_book>> call, Throwable t) {

                    }
                });

            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

        btn_delete_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_book.api_bo.delete_book(id_book).enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        Toast.makeText(getApplicationContext(),"Delete book successfully" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(detail_createbook.this, create_book.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        });
    }

    private List<Category> getListCategory() {
        List<Category> categories = new ArrayList<>();

        for (Category ca : mcategory){
            int num = 0;
            for (Category_book cabo : mcategory_books){
                if (ca.getId_category() == cabo.getId_category()){
                    num = 1;
                    break;
                }
            }
            categories.add(new Category(ca.getCategoryName(), ca.getId_category(), id_book, num));
        }
        return categories;
    }


    private List<Chapter> getListChapter(){
        List<Chapter> chapter = new ArrayList<>();
        mchapter.sort(Comparator.comparing(a -> a.getChapterTitle()));
        for (Chapter ch : mchapter){
            chapter.add(new Chapter(ch.getId_chapter(), ch.getChapterTitle()));
        }
        return chapter;
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

