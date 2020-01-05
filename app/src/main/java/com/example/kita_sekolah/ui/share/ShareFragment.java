package com.example.kita_sekolah.ui.share;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    private ProgressDialog loadingBar;
    private String CategoryName;
    private String nama_brg, desc_brg, jumlah_brg, harga_brg, rating_brg, jenis_brg;
    private String saveCurrentDate, saveCurrentTime;
    private RatingBar ratingBar;
    private ImageView gambar1,gambar2,gambar3;
    private Button btn_input_produk;
    private EditText nama_barang, jenis_barang=null, desc_barang, jumlah_barang, harga_barang;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private Uri ImageUri2;
    private Uri ImageUri3;
    private String productRandomKey, downloadImageURI;
    private StorageReference ProductImageRef;
    private StorageReference ProductImageRef2;
    private StorageReference ProductImageRef3;
    private DatabaseReference ProductsRef;
    float rating;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Bundle arguments = getArguments();
        if (arguments != null) {

            CategoryName = arguments.getString("kategori");
            jenis_barang = root.findViewById(R.id.jenis_barang);
            jenis_barang.setText(CategoryName);

        }


        Toast.makeText(getActivity(), CategoryName, Toast.LENGTH_SHORT).show();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        loadingBar = new ProgressDialog(getActivity());


        gambar1 = root.findViewById(R.id.image1);
        gambar2 = root.findViewById(R.id.image2);
        gambar3 = root.findViewById(R.id.image3);
        btn_input_produk = root.findViewById(R.id.btn_input_produk);
        nama_barang = root.findViewById(R.id.input_namabarang);

        jenis_barang = root.findViewById(R.id.input_jenisbarang);
        jenis_barang.setText(CategoryName);

        desc_barang = root.findViewById(R.id.input_descbarang);
        jumlah_barang = root.findViewById(R.id.input_jumlah);
        harga_barang = root.findViewById(R.id.input_harga);

        ratingBar = root.findViewById(R.id.ratingBar);

        gambar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        gambar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        gambar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        btn_input_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });


        return root;
    }




    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            gambar1.setImageURI(ImageUri);


            ImageUri2 = data.getData();
            gambar2.setImageURI(ImageUri2);


            ImageUri3 = data.getData();
            gambar3.setImageURI(ImageUri3);

        }
    }

    private void ValidateProductData() {

        nama_brg = nama_barang.getText().toString();
        desc_brg = desc_barang.getText().toString();
        jumlah_brg = jumlah_barang.getText().toString();
        harga_brg = harga_barang.getText().toString();
        jenis_brg = jenis_barang.getText().toString();
        rating_brg = String.valueOf(ratingBar.getRating());


        if (ImageUri == null) {
            Toast.makeText(getActivity(), "Product Image is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nama_brg)) {
            Toast.makeText(getActivity(), "Isi nama barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(desc_brg)) {
            Toast.makeText(getActivity(), "Isi deskripsi barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jumlah_brg)){
            Toast.makeText(getActivity(), "Isi jumlah barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(harga_brg)){
            Toast.makeText(getActivity(), "Isi harga barang...", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }


    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


        productRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image berhasil diupload...", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }

                        downloadImageURI = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            downloadImageURI = task.getResult().toString();

                            Toast.makeText(getActivity(), "got product image successfully..", Toast.LENGTH_SHORT).show();

                            SaveProductIntoDatabase();

                        }
                    }
                });
            }
        });


    }




    private void SaveProductIntoDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("image", downloadImageURI);
        productMap.put("name", nama_brg);
        productMap.put("description", desc_brg);
        productMap.put("category", CategoryName);
        productMap.put("price", harga_brg);
        productMap.put("stock", jumlah_brg);
        productMap.put("rating", rating_brg);

        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.nav_host_fragment, new ShareFragment());
                    fr.commit();

                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), "Product Berhasil ditambahkan...", Toast.LENGTH_SHORT).show();
                }

                else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}