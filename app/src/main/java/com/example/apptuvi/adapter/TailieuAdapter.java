package com.example.apptuvi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptuvi.R;
import com.example.apptuvi.model.tailieu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TailieuAdapter extends BaseAdapter {

    List<tailieu> tailieuList;
    Context context;

    public TailieuAdapter(List<tailieu> tailieuList, Context context) {
        this.tailieuList = tailieuList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tailieuList.size();
    }

    @Override
    public Object getItem(int position) {
        return tailieuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public ImageView imgamduong;
        public TextView txtamduong;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dongtailieu,null);
            viewHolder.txtamduong = (TextView) convertView.findViewById(R.id.txtamduong);
            viewHolder.imgamduong = (ImageView) convertView.findViewById(R.id.imgamduong);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        tailieu tailieu =(tailieu) getItem(position);
        viewHolder.txtamduong.setText(tailieu.getTenamduong());
        Picasso.get()
                .load(tailieu.getAnhamduong())
                .error(R.drawable.error)
                .into(viewHolder.imgamduong);


        return convertView;
    }
}
