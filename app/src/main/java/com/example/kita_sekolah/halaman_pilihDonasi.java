package com.example.kita_sekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class halaman_pilihDonasi extends AppCompatActivity {

    private ImageButton mbtn_DonasiBarang;
    private ImageButton mbtn_DonasiUang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_pilih_donasi);

        mbtn_DonasiBarang = findViewById(R.id.btn_donasibarang);
        mbtn_DonasiUang = findViewById(R.id.btn_donasiuang);

        mbtn_DonasiBarang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mbtn_DonasiBarang = new Intent(halaman_pilihDonasi.this, halaman_donasi.class);
                startActivity(mbtn_DonasiBarang);
            }
        });

        mbtn_DonasiUang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mbtn_DonasiUang = new Intent(halaman_pilihDonasi.this, halaman_donasi_uang.class);
                startActivity(mbtn_DonasiUang);
            }
        });


    }
}
