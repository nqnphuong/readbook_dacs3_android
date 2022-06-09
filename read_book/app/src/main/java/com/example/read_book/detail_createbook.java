package com.example.read_book;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
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
import com.bumptech.glide.Glide;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_createbook extends AppCompatActivity {
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

    private static final int MY_REQUEST_CODE = 10;
    public static final String TAG = update_information_user_screen.class.getName();
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result){
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img_book_book.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

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

        mProgressDialog = new ProgressDialog(detail_createbook.this);
        mProgressDialog.setMessage("Please wait ...");

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

        img_book_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickRequestPermission();
            }
        });


        btn_update_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String bookName = txt_title_book_detailcreatebook.getText().toString();
                String bookDes = txt_description_book_detailcreatebook.getText().toString();
                RequestBody requestBodytitle = RequestBody.create(MediaType.parse("multipart/form-data"),bookName);
                RequestBody requestBodydes = RequestBody.create(MediaType.parse("multipart/form-data"),bookDes);

                String strRealPath = RealPathUtil.getRealPath(detail_createbook.this,mUri);
                Log.e("Trang thai duong dan", strRealPath);
                File file = new File(strRealPath);
                RequestBody imgava = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imgMultipartBody = MultipartBody.Part.createFormData("bookImage",file.getName(),imgava);

                api_book.api_bo.book_update(id_book,requestBodytitle, imgMultipartBody, requestBodydes)
                        .enqueue(new Callback<List<Book>>() {
                            @Override
                            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                                Toast.makeText(getApplicationContext(),"Update book successfully" , Toast.LENGTH_SHORT).show();
                                mProgressDialog.cancel();
                                showInforBook(id_book);
                            }

                            @Override
                            public void onFailure(Call<List<Book>> call, Throwable t) {
                                System.out.println(t.toString());
                                mProgressDialog.cancel();
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

    private void onCLickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void showInforBook(Integer id_book){
        api_book.api_bo.read_by_id(id_book).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                for (Book bo : response.body()){
                    txt_title_book_detailcreatebook.setText(bo.getBookName());
                    txt_description_book_detailcreatebook.setText(bo.getBookDescription());
                    Glide.with(detail_createbook.this).load(bo.getBookImage()).into(img_book_book);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                System.out.println(t.toString());
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

