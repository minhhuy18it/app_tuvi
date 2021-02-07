package com.example.apptuvi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptuvi.R;
import com.example.apptuvi.model.Anthaitue;

import java.util.List;

public class ThaitueAdapter extends RecyclerView.Adapter<ThaitueAdapter.ItemHolder>{
    List<Anthaitue> anthaitues;
    Context context;

    public ThaitueAdapter(List<Anthaitue> anthaitues, Context context) {
        this.anthaitues = anthaitues;
        this.context = context;
    }

    @NonNull
    @Override
    public ThaitueAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thang,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThaitueAdapter.ItemHolder holder, int position) {
        Anthaitue anthaitue = anthaitues.get(position);
        holder.txttext.setText(anthaitue.getSao());

        if (anthaitue.getSao().length() >= 10) holder.txttext.setTextSize(7);
        if (position%2 == 0) holder.txttext.setTextColor(Color.RED);
        if (position%2 != 0) holder.txttext.setTextColor(Color.BLUE);

    }

    @Override
    public int getItemCount() {
        return anthaitues.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView txttext;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            txttext = itemView.findViewById(R.id.txtText);
        }
    }
}
