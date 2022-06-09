package com.example.read_book.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.adapter.bookAdapter2;
import com.example.read_book.adapter.categoryfindAdapter;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_category_book;
import com.example.read_book.api.api_library;
import com.example.read_book.book_for_category_screen;
import com.example.read_book.databinding.FragmentLibraryBinding;
import com.example.read_book.login_screen;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category;
import com.example.read_book.model.Category_book;
import com.example.read_book.model.Library;
import com.example.read_book.mybook_screen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private RecyclerView recyclerView;
    private bookAdapter2 bookAdapter;
    private List<Book> mbook;
    private List<Library> mlibrary;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        View view = inflater.inflate(R.layout.fragment_library, container, false);
        Integer id_user = login_screen.id_user;
        System.out.println("id_user "+ id_user);

        api_book.api_bo.read_your_library(id_user).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mbook = response.body();
                recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_book_library);
                bookAdapter = new bookAdapter2(getActivity(), getListBook());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(bookAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });


        return view;
    }

    private List<Book> getListBook() {
        List<Book> book = new ArrayList<>();
        for (Book bo : mbook){
            book.add(new Book(bo.getId_book(), bo.getBookImage(), bo.getBookName(), bo.getBookAuthor(), bo.getBookDescription(), 0));
        }
        return book;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}