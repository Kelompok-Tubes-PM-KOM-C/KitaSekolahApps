package com.example.kita_sekolah;


import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import com.google.android.material.tabs.TabLayout;

public class halaman_donasi extends AppCompatActivity {


    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_donasi);

        tabLayout = findViewById(R.id.donasi_tablayout);
        viewPager = findViewById(R.id.donasi_viewpager);
        appBarLayout = findViewById(R.id.appbar);

        DonasiAdapter adapter = new DonasiAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailFragment(), "Detail");
        adapter.addFragment(new AlamatFragment(),"Alamat");
        adapter.addFragment(new KonfirmasiFragment(),"Konfirmasi");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
