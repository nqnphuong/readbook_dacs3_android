package com.example.read_book.ui.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.adapter.categoryfindAdapter;
import com.example.read_book.api.api_category;
import com.example.read_book.databinding.FragmentFindBinding;
import com.example.read_book.model.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindFragment extends Fragment {

    private FragmentFindBinding binding;
    private RecyclerView recyclerView;
    private categoryfindAdapter categoryAdapter;
    private List<Category> mcategory;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        FindViewModel findViewModel =
                new ViewModelProvider(this).get(FindViewModel.class);
        binding = FragmentFindBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_find, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_category_find);
        categoryAdapter = new categoryfindAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        api_category.api_ca.show_all_category().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mcategory = response.body();
                System.out.println("Category " + mcategory.size());
                categoryAdapter.setData(getListCategory());
                recyclerView.setAdapter(categoryAdapter);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        return view;
    }

    private List<Category> getListCategory() {
        List<Category> category = new ArrayList<>();
        if (mcategory == null || mcategory.isEmpty()){
            return category;
        }
        for (Category cate : mcategory) {
            System.out.println(cate);
            category.add(new Category(cate.getCategoryName(), cate.getId_category()));
        }
        return category;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}