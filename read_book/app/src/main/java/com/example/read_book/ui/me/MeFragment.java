package com.example.read_book.ui.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_library;
import com.example.read_book.api.api_user;
import com.example.read_book.databinding.FragmentMeBinding;
import com.example.read_book.login_screen;
import com.example.read_book.model.Book;
import com.example.read_book.model.Library;
import com.example.read_book.model.User;
import com.example.read_book.mybook_screen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeFragment extends Fragment {

    private FragmentMeBinding binding;
    private RecyclerView recyclerView;
    private bookAdapter2 bookAdapter;
    private List<Book> mbook;
    private TextView txt_description_me, txt_email_me, txt_name_me;
    private LinearLayout layout_mybook_me;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MeViewModel meViewModel =
                new ViewModelProvider(this).get(MeViewModel.class);

        binding = FragmentMeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Integer id_user = login_screen.id_user;
        System.out.println(id_user);

        txt_description_me = (TextView) view.findViewById(R.id.txt_description_me);
        txt_email_me = (TextView) view.findViewById(R.id.txt_email_me);
        txt_name_me = (TextView) view.findViewById(R.id.txt_name_me);
        api_user.api_us.show_all_user().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> user = response.body();
                for (User us: user){
                    if (us.getId_user() == id_user){
                        txt_description_me.setText(us.getUserDescription());
                        txt_email_me.setText(us.getUserEmail());
                        txt_name_me.setText(us.getUserFirstname());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mbook = response.body();
                recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_book_me);
                bookAdapter = new bookAdapter2( getActivity(), getListBook(id_user));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(bookAdapter);
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });

        layout_mybook_me = (LinearLayout) view.findViewById(R.id.layout_mybook_me);
        layout_mybook_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), mybook_screen.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private List<Book> getListBook(Integer id_user) {
        Integer i=0;
        List<Book> book = new ArrayList<>();
        for (Book bo : mbook){
            if (bo.getId_user() == id_user){
                i=i+1;
                book.add(new Book(bo.getId_book(), R.drawable.book1, bo.getBookName(), bo.getBookAuthor(), bo.getBookDescription(), i));
            }
            if (i>=5) break;
        }


        return book;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}