package com.example.kita_sekolah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment {
    private ViewPager mSlideViewPager;
    private SliderHeaderAdapter sliderHeaderAdapter;
    private View myFragment;
    private Context thiscontext;
    private Button mbtn_buku;

    private TextView hallo_nama;
    String nama;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        halaman_utama activity = (halaman_utama) getActivity();
        nama = activity.getNama();

        thiscontext = container.getContext();

        mSlideViewPager = myFragment.findViewById(R.id.slideViewPager);
        mbtn_buku = myFragment.findViewById(R.id.btn_buku);
        hallo_nama = myFragment.findViewById(R.id.hallo_nama);
        hallo_nama.setText("Hallo "+nama);


//        mbtn_buku.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent mbtn_buku = new Intent(thiscontext, ListBuku.class);
//                startActivity(mbtn_buku);
//            }
//        });



        sliderHeaderAdapter = new SliderHeaderAdapter(thiscontext);
        mSlideViewPager.setAdapter(sliderHeaderAdapter);

        return myFragment;
    }
    }


