package com.example.apptuvi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptuvi.R;
import com.example.apptuvi.Services;
import com.example.apptuvi.ServicesApi;
import com.example.apptuvi.model.Code;
import com.example.apptuvi.model.ThongTin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinhGiaiActivity extends AppCompatActivity {
    Button btnnhapcode;
    TextView txtbinhgiai,txtbinhgiaicode;
    Code code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh_giai);

        btnnhapcode = findViewById(R.id.btnnhapcode);
        txtbinhgiai = findViewById(R.id.txtbinhgiai);
        txtbinhgiaicode = findViewById(R.id.txtbinhgiaicode);

        btnnhapcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               xuLyNhapCode();
            }
        });
    }

    private void xuLyNhapCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập code");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = input.getText().toString();
                if (!code.isEmpty()) {
                    callAPI(code);
                }
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

    private void callAPI(String code) {
        ProgressDialog dialog = new ProgressDialog(BinhGiaiActivity.this);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        ServicesApi servicesApi = new Services().getService();
        servicesApi.nhapcode(code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.has("trangthai")) {
                            if (jsonObject.getString("trangthai").equals("Kích Hoạt")) {
                                btnnhapcode.setVisibility(View.GONE);
                                txtbinhgiaicode.setVisibility(View.VISIBLE);
                                SharedPreferences preferences = getSharedPreferences("Code", Context.MODE_PRIVATE);
                                if (preferences != null) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("code",code);
                                    editor.commit();
                                    Toast.makeText(BinhGiaiActivity.this,"Nhập Code Thành Công ",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(BinhGiaiActivity.this,"Code "+jsonObject.getString("trangthai"),Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(BinhGiaiActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        Log.d("JSON", jsonObject.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("LOI", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("Code", Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString("code",null);
            if (json != null) {
                callAPI(json);
            }
        }

    }
}