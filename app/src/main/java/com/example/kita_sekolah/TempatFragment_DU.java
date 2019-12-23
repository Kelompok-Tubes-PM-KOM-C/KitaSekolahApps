package com.example.kita_sekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TempatFragment_DU extends Fragment {
        View view;

        public TempatFragment_DU() {

        }


        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){

            view = inflater.inflate(R.layout.fragment_tempat_du,container,false);
            return view;
    }
}