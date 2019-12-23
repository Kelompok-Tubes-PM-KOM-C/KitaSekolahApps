package com.example.kita_sekolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class KonfirmasiFragment extends Fragment {

        View myFragment;

        private Button mBtn_Konfirmasi;

        public KonfirmasiFragment() {

        }


        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){

            myFragment = inflater.inflate(R.layout.konfirmasi_fragment, container, false);

            mBtn_Konfirmasi = myFragment.findViewById(R.id.btn_konfirmasi);

            return myFragment;
    }
}