package com.example.kita_sekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.kita_sekolah.Model.Users;
import com.example.kita_sekolah.Prevalent.Prevalent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private static int Splash_Time_Out = 3000;
    private ProgressDialog loadingBar;
    private String firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        final String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);



        if (UserPhoneKey != "" && UserPasswordKey != ""){
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                if (UserPasswordKey.equals("12345")) {
                    firebaseDB = "Admins";
                }
                else {
                    firebaseDB = "Users";
                }

                AllowAccess(UserPhoneKey,UserPasswordKey);

                loadingBar.setTitle("Login Akun");
                loadingBar.setMessage("Tunggu Sebentar...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }

            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent page1Intent = new Intent(MainActivity.this, start1_activity.class);
                        startActivity(page1Intent);
                        finish();

                    }
                },Splash_Time_Out);
            }
        }



    }




    private void AllowAccess(final String input, final String pwd) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseDB).child(input).exists()) {

                    Users userData = dataSnapshot.child(firebaseDB).child(input).getValue(Users.class);
                    if (userData.getNo_hp().equals(input)) {

                        if (userData.getPassword().equals(pwd)){

                            if (firebaseDB.equals("Admins")) {
                                Toast.makeText(MainActivity.this, "Login admin", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent masuk = new Intent(MainActivity.this, home_admin_drawer.class);
                                startActivity(masuk);
                            }

                            else {
                                Toast.makeText(MainActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent masuk = new Intent(MainActivity.this, halaman_utama.class);
                                startActivity(masuk);
                            }
                        }

                        else {
                            Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                    }

                }

                else {
                    Toast.makeText(MainActivity.this, "Akun tidak tersedia", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
