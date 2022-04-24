package com.example.webservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {
    EditText edtManv,edtTennv,edtSdt;
    Button btnThem,btnThoat;
    private String urlInsert  = "http://lenhutran1807-001-site1.htempurl.com/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        edtManv = findViewById(R.id.editTextManv);
        edtTennv = findViewById(R.id.editTextTennv);
        edtSdt = findViewById(R.id.editTextSdt);
        btnThem = findViewById(R.id.buttonThem);
        btnThoat = findViewById(R.id.buttonThoat);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themNhanVien(urlInsert);
            }
        });

    }
    private void themNhanVien(String url) {
        RequestQueue  requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("insert data successful")) {
                    Toast.makeText(InsertActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(InsertActivity.this, MainActivity.class));
                } else
                    Toast.makeText(InsertActivity.this, "Thêm không thành công", Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InsertActivity.this, "Xảy ra lỗi", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }
    )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MaNV",edtManv.getText().toString());
                params.put("TenNV",edtTennv.getText().toString());
                params.put("SDT",edtSdt.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}