package com.example.kita_sekolah;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class DetailFragment extends Fragment {
    private static final int REQUEST_TAKE_PHOTO = 1;
    View view;
        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;
        private String mPathImage1;
        private int imageChoose;

        public DetailFragment() {

        }


        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            view = inflater.inflate(R.layout.detail_fragment,container,false);

            imageChoose = 0;
            imageView1 = view.findViewById(R.id.image1);
            imageView2 = view.findViewById(R.id.image2);
            imageView3 = view.findViewById(R.id.image3);

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChoose = 1;
                    dispatchTakePictureIntent();
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChoose = 2;
                    dispatchTakePictureIntent();
                }
            });

            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChoose =3;
                    dispatchTakePictureIntent();
                }
            });

            return view;
        }

        //TODO : Take image with camera
         @TargetApi(Build.VERSION_CODES.KITKAT)
         @RequiresApi(api = Build.VERSION_CODES.KITKAT)
         private void dispatchTakePictureIntent() {
             Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                  File photoFile = null;
                  try{
                      photoFile = createImageFile();
                  }catch (IOException ex){
                      Log.d("error image " , String.valueOf(ex));
                  }
                  if (photoFile != null) {
                      Uri photoURI = FileProvider.getUriForFile(getActivity(),
                              "com.example.android.fileprovider",
                              photoFile);
                      takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                      startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                  }
             }
         }

        //TODO : jika selesai mengambil kamera
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            try {
                if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                    Thread mThread = null;
                    try{
                        mThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Get the dimensions of the View
                                Bitmap bitmap = null;

                                bitmap = scaleImage(mPathImage1);

                                final Bitmap finalBitmap = bitmap;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (finalBitmap != null && imageChoose == 1){
                                            imageView1.setImageBitmap(finalBitmap);
                                        }if (finalBitmap != null && imageChoose == 2){
                                            imageView2.setImageBitmap(finalBitmap);
                                        }if (finalBitmap != null && imageChoose == 3){
                                            imageView3.setImageBitmap(finalBitmap);
                                        }
                                    }
                                });
                            }
                        });
                        mThread.start();
                    }finally {
                        if (mThread != null){
                            mThread.interrupt();
                        }
                    }
                }
            }catch (Exception error){
                error.printStackTrace();
            }
        }

    //TODO : mencreate file untuk proses penyimpanan
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = null;

        imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = null;
        storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        assert imageFileName != null;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

//         Save a file: path for use with ACTION_VIEW intents
//        if (jenisFoto == 1){
            mPathImage1 = image.getAbsolutePath();
            //  Log.d("mPathFotoRumah","foto rumah path : "+mPathImgRumah);
//        }else if(jenisFoto == 2){
//            mPathImgMeter_temp = image.getAbsolutePath();
//            //     Log.d("mPathFotoMeter","foto Meter path : "+mPathImgMeter);
//        }
        return image;
    }

    protected Bitmap scaleImage(String pathImage){
        int targetW = 500;
        int targetH = 500;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(pathImage, bmOptions);
    }
}