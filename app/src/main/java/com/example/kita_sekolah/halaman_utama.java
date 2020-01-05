package com.example.kita_sekolah;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class halaman_utama extends AppCompatActivity {


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private FavoriteFragment favoriteFragment;
    private AccountFragment accountFragment;
    private Button mBtnDonasi;

    String getnama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        Intent intent = getIntent();
        getnama = intent.getStringExtra("nama");

        mBtnDonasi = findViewById(R.id.btn_donasi);

        mMainFrame = findViewById(R.id.fl_container);
        mMainNav = findViewById(R.id.bn_main);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();
        accountFragment = new AccountFragment();

        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        setFragment(homeFragment);
                        return true;

                    case R.id.search_menu:
                        setFragment(searchFragment);
                        return true;

                    case R.id.favorite_menu:
                        setFragment(favoriteFragment);
                        return true;

                    case R.id.account_menu:
                        setFragment(accountFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

        mBtnDonasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mBtnDonasi = new Intent(halaman_utama.this, halaman_pilihDonasi.class);
                startActivity(mBtnDonasi);
            }
        });




    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragmentTransaction.commit();
    }

    public String getNama() {
        return getnama;
    }


}