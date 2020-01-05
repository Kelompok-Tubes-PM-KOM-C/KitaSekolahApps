package com.example.kita_sekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class halaman_utama_admin extends AppCompatActivity {

    private Button btn_sepatu, btn_baju, btn_tas, btn_buku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_admin);

        Toast.makeText(halaman_utama_admin.this, "Selamat Datang Admin", Toast.LENGTH_SHORT).show();

        btn_baju = findViewById(R.id.btn_baju);
        btn_buku = findViewById(R.id.btn_buku);
        btn_sepatu = findViewById(R.id.btn_sepatu);
        btn_tas = findViewById(R.id.btn_tas);

        btn_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_utama_admin.this,input_produk_admin.class);
                intent.putExtra("kategori","buku");
                startActivity(intent);
            }
        });

        btn_baju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_utama_admin.this,input_produk_admin.class);
                intent.putExtra("kategori","baju");
                startActivity(intent);
            }
        });

        btn_sepatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_utama_admin.this,input_produk_admin.class);
                intent.putExtra("kategori","sepatu");
                startActivity(intent);
            }
        });

        btn_tas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_utama_admin.this,input_produk_admin.class);
                intent.putExtra("kategori","tas");
                startActivity(intent);
            }
        });
    }
}
