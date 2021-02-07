package com.example.apptuvi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apptuvi.R;
import com.example.apptuvi.model.ThongTin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private Spinner spinner,spinnergioi,spinnergio,spinnerphut,spinnerthang;
    private Button btnansao,btnLuuSo,btnDanhSach;
    private Spinner txtNgay;
    private EditText txtNam;
    public static ArrayList<ThongTin> listThongTin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();
        Listyear();
        ListDay();
        Listgioitinh();
        Listgio();
        Listphut();
        Listthang();
        Eventbtnansao();
    }

    private void ListDay() {
        ArrayList<String> listngay= new ArrayList<String>();
        for (int i = 1; i <= 31; i++ ){
            listngay.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listngay);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtNgay.setAdapter(arrayAdapter);
    }

    private void Eventbtnansao() {

        btnansao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = txtNgay.getSelectedItem().toString();
                String month = spinnerthang.getSelectedItem().toString();
                String year = txtNam.getText().toString().trim();
                String hour = spinnergio.getSelectedItem().toString();
                String minute = spinnerphut.getSelectedItem().toString();
                String gender = spinnergioi.getSelectedItem().toString();

                if (date.isEmpty() || year.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Ngày hoặc tháng còn trống",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (!isValidDate(date,month,year)) return;
                    ThongTin thongTin = new ThongTin("",date,month,year,hour,minute,gender);
                    Intent intent = new Intent(MainActivity.this,AnsaoActivity.class);
                    intent.putExtra("ThongTin",thongTin);
                    startActivity(intent);
                }
            }
        });
    }

    private void Listthang() {
        ArrayList<String> listthang= new ArrayList<String>();
        for (int i = 1; i <= 12; i++ ){
            listthang.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listthang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerthang.setAdapter(arrayAdapter);
    }

    private void Listphut() {
        ArrayList<String> listgio= new ArrayList<String>();
        for (int i = 0; i <= 23; i++ ){
            listgio.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listgio);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergio.setAdapter(arrayAdapter);

    }

    private void Listgio() {
        ArrayList<String> listphut = new ArrayList<String>();
        for (int i = 0; i <= 59; i++ ){
            listphut.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listphut);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerphut.setAdapter(arrayAdapter);

    }

    private void Listgioitinh() {
        ArrayList<String> listgioitinh = new ArrayList<String>();
        listgioitinh.add("Nam");
        listgioitinh.add("Nữ");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listgioitinh);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergioi.setAdapter(arrayAdapter);
    }

    private void Listyear() {
        ArrayList<String> listyear = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 2000; i <= 2079; i++ ){
            listyear.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listyear);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(listyear.indexOf(""+calendar.get(Calendar.YEAR)));
    }

    private void Anhxa() {
        spinner = findViewById(R.id.spinner);
        spinnergioi = findViewById(R.id.spinnergioi);
        spinnerthang = findViewById(R.id.spinnerthang);
        spinnergio = findViewById(R.id.spinnergio);
        spinnerphut = findViewById(R.id.spinnerphut);
        btnansao = findViewById(R.id.btnansao);
        btnLuuSo = findViewById(R.id.btnLuuSo);
        txtNgay = findViewById(R.id.txtNgay);
        txtNam = findViewById(R.id.txtNam);
        btnDanhSach = findViewById(R.id.btnDanhSach);

        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DanhSachActivity.class);
                startActivity(intent);
            }
        });

        btnLuuSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = txtNgay.getSelectedItem().toString();
                String month = spinnerthang.getSelectedItem().toString();
                String year = txtNam.getText().toString().trim();
                String hour = spinnergio.getSelectedItem().toString();
                String minute = spinnerphut.getSelectedItem().toString();
                String gender = spinnergioi.getSelectedItem().toString();

                if (date.isEmpty() || year.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Ngày hoặc tháng còn trống",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (!isValidDate(date,month,year)) return;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Nhập tên");
                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ten = input.getText().toString();
                            if (ten.isEmpty()) {
                                Toast.makeText(MainActivity.this,"Bạn chưa nhập tên",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ThongTin thongTin = new ThongTin(ten,date,month,year,hour,minute,gender);
                            listThongTin.add(thongTin);
                            Toast.makeText(MainActivity.this,"Lưu thành công.",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                }
            }
        });
    }

    private boolean isValidDate(String date, String month, String year) {
        String formatString = "MM/dd/yyyy";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(month+"/"+date+"/"+year);
        } catch (ParseException e) {
            Toast.makeText(MainActivity.this,"Ngày không tồn tại",Toast.LENGTH_LONG).show();
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString("thongtin",null);
            if (json != null) {
                listThongTin = gson.fromJson(json, new TypeToken<ArrayList<ThongTin>>(){}.getType());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(listThongTin);
        editor.putString("thongtin",json);
        editor.commit();
    }
}