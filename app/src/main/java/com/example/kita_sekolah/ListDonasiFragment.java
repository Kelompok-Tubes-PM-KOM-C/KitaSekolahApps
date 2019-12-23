package com.example.kita_sekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListDonasiFragment extends Fragment {
        View view;

        public ListDonasiFragment() {

        }


        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){

            view = inflater.inflate(R.layout.list_donasi_fragment,container,false);
            return view;
    }
}