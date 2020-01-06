package com.example.kita_sekolah.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kita_sekolah.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ImageView gambar_produk;
    private TextView tv_namaproduk, tv_descproduk, tv_hargaproduk;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        gambar_produk = root.findViewById(R.id.gambar_produk);
        tv_namaproduk = root.findViewById(R.id.tv_namaproduk);
        tv_descproduk = root.findViewById(R.id.tv_descproduk);
        tv_hargaproduk = root.findViewById(R.id.tv_hargaproduk);

        return root;
    }
}