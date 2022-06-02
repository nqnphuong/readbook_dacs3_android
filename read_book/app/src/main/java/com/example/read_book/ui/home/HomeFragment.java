package com.example.read_book.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.api.api_book;
import com.example.read_book.api.api_category;
import com.example.read_book.api.api_category_book;
import com.example.read_book.api.api_library;
import com.example.read_book.databinding.FragmentHomeBinding;
import com.example.read_book.loadmore;
import com.example.read_book.model.Book;
import com.example.read_book.model.Category;
import com.example.read_book.model.Category_book;
import com.example.read_book.model.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private bookAdapter bookAdapter;

    private List<Book> mbook;
    private List<Book> items = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //Random data
        getListBook();

        //Init View
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_category_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookAdapter = new bookAdapter(recyclerView, getActivity(), items);
        recyclerView.setAdapter(bookAdapter);

        //Set Load more event
        bookAdapter.setLoadMore(new loadmore() {
            @Override
            public void onLoadMore() {
                if(items.size() <= 20) // Change max size
                {
                    items.add(null);
                    bookAdapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            bookAdapter.notifyItemRemoved(items.size());

//                            Random more data
                            api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
                                @Override
                                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                                    mbook = response.body();
                                    int index = items.size();
                                    int end = index+10;
                                    int i = index;
                                    for (Book bo: mbook){
                                        Book item = new Book(bo.getId_book(),R.drawable.book1,bo.getBookName(),bo.getBookAuthor(),bo.getBookDescription(), 0);
                                        items.add(item);
                                        if(i >= end){
                                            break;
                                        }
                                        i = i + 1;
                                    }
                                    bookAdapter.notifyDataSetChanged();
                                    bookAdapter.setLoaded();
                                }
                                @Override
                                public void onFailure(Call<List<Book>> call, Throwable t) {
                                }
                            });

                        }
                    },2000); // Time to load
                }else{
                    Toast.makeText(getActivity(), "Load data completed !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getListBook() {
        api_book.api_bo.show_all_book().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mbook = response.body();
                int i = 0;
                for (Book bo: mbook){
                    Book item = new Book(bo.getId_book(),R.drawable.book1,bo.getBookName(),bo.getBookAuthor(),bo.getBookDescription(), 0);
                    items.add(item);
                    if(i >= 10){
                        i = 0;
                        break;
                    }
                    i = i + 1;
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}