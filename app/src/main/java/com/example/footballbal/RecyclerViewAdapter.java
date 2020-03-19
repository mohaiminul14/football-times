package com.example.footballbal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    Context context;
    List<ChanelList> channelList;

    public RecyclerViewAdapter(Context context, List<ChanelList> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_channel,parent,false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.textView.setText(channelList.get(position).getName());
        holder.link.setText(channelList.get(position).getLink());

    }

    @Override
    public int getItemCount() {
        return channelList.size() ;
    }

    public static  class MyHolder extends  RecyclerView.ViewHolder{

        private TextView textView;
        private TextView link;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.chText);
            link=itemView.findViewById(R.id.link);
        }
    }

}
