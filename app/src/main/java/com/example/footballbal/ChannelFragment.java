package com.example.footballbal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ChannelFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<ChanelList> lists;


    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_channel, container, false);

        recyclerView= v.findViewById(R.id.recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lists = new ArrayList<>();
        lists.add(new ChanelList("shanto","hkk.j"));
        lists.add(new ChanelList("shanto1","hkk.j"));
        lists.add(new ChanelList("shanto2","hkk.j"));
        lists.add(new ChanelList("shanto3","hkk.j"));
        lists.add(new ChanelList("shanto4","hkk.j"));
        lists.add(new ChanelList("shanto5","hkk.j"));
        lists.add(new ChanelList("shanto6","hkk.j"));
        lists.add(new ChanelList("shanto7","hkk.j"));
        lists.add(new ChanelList("shanto8","hkk.j"));
        lists.add(new ChanelList("shanto9","hkk.j"));
        lists.add(new ChanelList("shanto10","hkk.j"));
        lists.add(new ChanelList("shanto11","hkk.j"));
        lists.add(new ChanelList("shanto12","hkk.j"));

    }
}
