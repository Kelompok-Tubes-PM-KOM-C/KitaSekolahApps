package com.example.kita_sekolah.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kita_sekolah.R;
import com.example.kita_sekolah.halaman_utama_admin;
import com.example.kita_sekolah.input_produk_admin;
import com.example.kita_sekolah.ui.share.ShareFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn_sepatu, btn_baju, btn_tas, btn_buku;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.hallo_nama);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


//        Toast.makeText(HomeFragment.this, "Selamat Datang Admin", Toast.LENGTH_SHORT).show();

        btn_baju = root.findViewById(R.id.btn_baju);
        btn_buku = root.findViewById(R.id.btn_buku);
        btn_sepatu = root.findViewById(R.id.btn_sepatu);
        btn_tas = root.findViewById(R.id.btn_tas);

        btn_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fr = getFragmentManager().beginTransaction();
//                fr.replace(R.id.nav_host_fragment, new ShareFragment());
//                fr.commit();

                Fragment fragment = new Fragment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                bundle.putString("kategori","buku"); // use as per your need
                fragment.setArguments(bundle);
                fr.addToBackStack(null);
                fr.replace(R.id.nav_host_fragment, new ShareFragment());
                fr.commit();

//                Intent intent = new Intent(HomeFragment.this, ShareFragment.class);
//                intent.putExtra("kategori","buku");
//                startActivity(intent);
            }
        });

        btn_baju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                bundle.putString("kategori","baju"); // use as per your need
                fragment.setArguments(bundle);
                fr.addToBackStack(null);
                fr.replace(R.id.nav_host_fragment, new ShareFragment());
                fr.commit();

            }
        });

        btn_sepatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                bundle.putString("kategori","sepatu"); // use as per your need
                fragment.setArguments(bundle);
                fr.addToBackStack(null);
                fr.replace(R.id.nav_host_fragment, new ShareFragment());
                fr.commit();

            }
        });

        btn_tas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                bundle.putString("kategori","tas"); // use as per your need
                fragment.setArguments(bundle);
                fr.addToBackStack(null);
                fr.replace(R.id.nav_host_fragment, new ShareFragment());
                fr.commit();

            }
        });

        return root;
    }
}