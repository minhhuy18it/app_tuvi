package com.example.apptuvi.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptuvi.R;
import com.example.apptuvi.activity.AnsaoActivity;
import com.example.apptuvi.model.Anthaitue;

import java.util.ArrayList;

/**
 *
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    Activity context;
    ArrayList<String> list;

    public TextAdapter(Activity context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thang,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = list.get(position);
        if (text.length() >= 10) holder.txtText.setTextSize(7);
        if (position%2 == 0) holder.txtText.setTextColor(Color.BLUE);
        if (position%2 != 0) holder.txtText.setTextColor(Color.RED);

        holder.txtText.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtText = itemView.findViewById(R.id.txtText);
        }
    }
}
