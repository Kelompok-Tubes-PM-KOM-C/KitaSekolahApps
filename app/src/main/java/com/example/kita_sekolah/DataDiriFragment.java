package com.example.kita_sekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataDiriFragment extends Fragment {
    View view;
    public DataDiriFragment() {

    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.data_diri_fragment,container,false);
        return view;
    }
}
