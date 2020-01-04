package com.example.kita_sekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class halaman_utama_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_admin);

        Toast.makeText(halaman_utama_admin.this, "Selamat Datang Admin", Toast.LENGTH_SHORT).show();
    }
}
