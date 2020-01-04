package com.example.submission3fix;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView         = view.findViewById(R.id.rv_tv);
        final ProgressBar progressBar     = view.findViewById(R.id.progressTvList);

        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final ItemsAdapter itemsAdapter = new ItemsAdapter();
        itemsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(itemsAdapter);

        Log.d("BAHASA" , Locale.getDefault().getLanguage());

        itemsAdapter.setOnItemClickCallback(new ItemsAdapter.onItemClickCallback() {
            @Override
            public void onItemClicked(int id) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_ID, id);
                intent.putExtra(DetailActivity.ARG_RESOURCES, "tv");
                startActivity(intent);
            }
        });

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        String currentLanguage = Locale.getDefault().getLanguage();

        if (currentLanguage.equals("en")){
            mainViewModel.setData("tv","en-US");
        }else if (currentLanguage.equals("in")){
            mainViewModel.setData("tv","id-ID");
        }

        mainViewModel.getData().observe(this, new Observer<ArrayList<ItemsData>>() {
            @Override
            public void onChanged(ArrayList<ItemsData> itemsData) {
                if (itemsData != null){
                    itemsAdapter.setData(itemsData);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
