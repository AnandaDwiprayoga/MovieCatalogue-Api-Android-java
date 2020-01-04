package com.example.submission2;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submission2.Data.Movies;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment  {

    private static final String ARG_SECTION_LIST = "section_list";

    public static TabFragment newInstance(ArrayList<ItemData> itemData) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_SECTION_LIST, itemData);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ItemData> itemDatas = Movies.getAllMovies();
        if(getArguments() != null){
            itemDatas = getArguments().getParcelableArrayList(ARG_SECTION_LIST);
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemAdapter itemAdapter = new ItemAdapter(itemDatas);
        itemAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(itemAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this,)

        itemAdapter.setOnItemClickCallback(new ItemAdapter.OnItemClickCallback() {
            @Override
            public voi