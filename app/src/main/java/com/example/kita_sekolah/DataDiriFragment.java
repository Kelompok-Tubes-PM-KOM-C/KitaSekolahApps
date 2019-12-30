package com.example.kita_sekolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class DataDiriFragment extends Fragment{
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
//
//    @Override
//    public void onClick(View v) {
//    FirebaseAuth.getInstance().signOut();
//        Intent logout = new Intent(getActivity(), registrasi.class);
//        view.getContext().startActivity(logout);
//
//    }

}

