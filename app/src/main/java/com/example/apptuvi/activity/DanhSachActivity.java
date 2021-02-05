package com.example.apptuvi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptuvi.R;
import com.example.apptuvi.model.ThongTin;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DanhSachActivity extends AppCompatActivity {
    ListView listThongTin;
    Button btnAnSao;
    Button btnSua;
    Button btnXoa;
    Button btnQuayLai;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    public static int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != -1) {
                    list.remove(position);
                    MainActivity.listThongTin.remove(position);
                    position = 0;
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnAnSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachActivity.this,AnsaoActivity.class);
                intent.putExtra("ThongTin",MainActivity.listThongTin.get(position));
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        listThongTin = findViewById(R.id.listThongTin);
        btnAnSao = findViewById(R.id.btnAnSao);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        list = new ArrayList<>();
        for (ThongTin thongTin : MainActivity.listThongTin) {
            list.add(thongTin.toString());
        }

        adapter = new ArrayAdapter<String>(
                DanhSachActivity.this,
                android.R.layout.simple_list_item_single_choice,
                list
        );
        listThongTin.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(MainActivity.listThongTin);
        editor.putString("thongtin",json);
        editor.commit();
    }


}