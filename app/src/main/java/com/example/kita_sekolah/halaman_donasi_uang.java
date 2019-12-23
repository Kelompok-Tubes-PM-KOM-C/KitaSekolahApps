package com.example.kita_sekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class halaman_donasi_uang extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_donasi_uang);


        tabLayout = findViewById(R.id.donasiuang_tablayout);
        viewPager = findViewById(R.id.donasiuang_viewpager);
        appBarLayout = findViewById(R.id.appbar);

        DonasiUangAdapter adapter = new DonasiUangAdapter(getSupportFragmentManager());
        adapter.addFragment(new JumlahFragment_DU(), "Jumlah");
        adapter.addFragment(new TempatFragment_DU(),"Tempat Donasi");
        adapter.addFragment(new KonfirmasiFragment_DU(),"Konfirmasi");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
