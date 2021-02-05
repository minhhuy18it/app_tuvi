package com.example.apptuvi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apptuvi.R;
import com.example.apptuvi.Services;
import com.example.apptuvi.ServicesApi;
import com.example.apptuvi.model.Code;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinhGiaiActivity extends AppCompatActivity {
    Button btnnhapcode;
    TextView txtbinhgiai,txtbinhgiaicode;

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
        ServicesApi servicesApi = new Services().getService();
        servicesApi.nhapcode(code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
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
}