package com.example.kita_sekolah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

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

public class input_produk_admin extends AppCompatActivity {

    private ProgressDialog loadingBar;
    private String CategoryName;
    private String nama_brg, desc_brg, jumlah_brg, harga_brg, rating_brg, jenis_brg;
    private String saveCurrentDate, saveCurrentTime;
    private RatingBar ratingBar;
    private ImageView gambar1,gambar2,gambar3;
    private Button btn_input_produk;
    private EditText nama_barang, jenis_barang, desc_barang, jumlah_barang, harga_barang;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_produk_admin);

        Toast.makeText(input_produk_admin.this, CategoryName, Toast.LENGTH_SHORT).show();

        CategoryName = getIntent().getExtras().get("kategori").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        loadingBar = new ProgressDialog(this);


        gambar1 = findViewById(R.id.image1);
        gambar2 = findViewById(R.id.image2);
        gambar3 = findViewById(R.id.image3);
        btn_input_produk = findViewById(R.id.btn_input_produk);
        nama_barang = findViewById(R.id.input_namabarang);

        jenis_barang = findViewById(R.id.input_jenisbarang);
        jenis_barang.setText(CategoryName);

        desc_barang = findViewById(R.id.input_descbarang);
        jumlah_barang = findViewById(R.id.input_jumlah);
        harga_barang = findViewById(R.id.input_harga);

        ratingBar = findViewById(R.id.ratingBar);
//        rating = ratingBar.getRating();

//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Float ratingVal = rating;
//                Float ratingvalue = ratingBar.getRating();
//            }
//        });




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



    }





    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            Toast.makeText(this, "Product Image is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nama_brg)) {
            Toast.makeText(this, "Isi nama barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(desc_brg)) {
            Toast.makeText(this, "Isi deskripsi barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jumlah_brg)){
            Toast.makeText(this, "Isi jumlah barang...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(harga_brg)){
            Toast.makeText(this, "Isi harga barang...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(input_produk_admin.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(input_produk_admin.this, "Image berhasil diupload...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(input_produk_admin.this, "got product image successfully..", Toast.LENGTH_SHORT).show();

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

                    Intent intent = new Intent(input_produk_admin.this, halaman_utama_admin.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(input_produk_admin.this, "Product Berhasil ditambahkan...", Toast.LENGTH_SHORT).show();
                }

                else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(input_produk_admin.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
