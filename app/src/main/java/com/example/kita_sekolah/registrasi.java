package com.example.kita_sekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class registrasi extends AppCompatActivity {

    EditText input_nama,
            input_username,
            nohp,
            emailId,
            password;
    Button btnDaftar;
    TextView tvLogin;
    TextView tvLewati;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mFirebaseAuth = FirebaseAuth.getInstance();
        input_nama = findViewById(R.id.input_nama);
        input_username = findViewById(R.id.input_username);
        nohp = findViewById(R.id.input_nohp_reg);
        emailId = findViewById(R.id.input_email_reg);
        password = findViewById(R.id.input_password_reg);
        btnDaftar = findViewById(R.id.btn_daftar);
        tvLogin = findViewById(R.id.tv_login);
        tvLewati = findViewById(R.id.tv_lewati);
        loadingBar = new ProgressDialog(this);


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(registrasi.this, login.class);
                startActivity(login);
            }
        });

        tvLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lewati = new Intent(registrasi.this, start2_activity.class);
                startActivity(lewati);
            }
        });

    }

    private void CreateAccount() {

        String nama = input_nama.getText().toString();
        String username = input_username.getText().toString();
        String no_hp = nohp.getText().toString();
        String email = emailId.getText().toString();
        String pwd = password.getText().toString();

        if (nama.isEmpty()) {
            input_nama.setError("Masukkan Nama Depan");
            input_nama.requestFocus();
        }
        else if (username.isEmpty()) {
            input_username.setError("Masukkan Nama Belakang");
            input_username.requestFocus();
        }
        else if (no_hp.isEmpty()) {
            nohp.setError("Masukkan No HP");
            nohp.requestFocus();
        }
        else if(email.isEmpty()) {
            emailId.setError("Masukkan email");
            emailId.requestFocus();
        }
        else if (pwd.isEmpty()){
            password.setError("Masukkan password");
            password.requestFocus();
        }
//        else if (email.isEmpty() && pwd.isEmpty()){
//            Toast.makeText(registrasi.this, "Email & Password kosong", Toast.LENGTH_SHORT).show();
//        }
//        else if (!(email.isEmpty() && pwd.isEmpty())) {
//            mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(registrasi.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(!task.isSuccessful()) {
//                        Toast.makeText(registrasi.this, "Registrasi Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        startActivity(new Intent(registrasi.this, login.class));
//                    }
//                }
//            });
//        }

        else{
            loadingBar.setTitle("Daftar Akun");
            loadingBar.setMessage("Tunggu Sebentar...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

//          FirebaseAuthEmail(nama_depan,no_hp,pwd,nama_belakang,email);
            ValidatephoneNumber(nama,no_hp,pwd,username,email);
        }

    }

//    private void FirebaseAuthEmail(final String nama_depan, final String no_hp, final String pwd, final String nama_belakang, final String email) {
//        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(registrasi.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(!task.isSuccessful()) {
//                        Toast.makeText(registrasi.this, "Registrasi Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        ValidatephoneNumber(nama_depan,no_hp,pwd,nama_belakang,email);
//                    }
//                }
//            });
//
//    }

    private void ValidatephoneNumber(final String nama, final String no_hp, final String pwd, final String username, final String email) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(no_hp).exists())) {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("nama", nama);
                    userdataMap.put("username", username);
                    userdataMap.put("no_hp", no_hp);
                    userdataMap.put("email", email);
                    userdataMap.put("password", pwd);

                    RootRef.child("Users").child(no_hp).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registrasi.this, "Akun berhasil terdaftar", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent login = new Intent(registrasi.this, login.class);
                                        startActivity(login);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(registrasi.this, "Network Error : Coba lagi..", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(registrasi.this, "This " + no_hp + "Sudah terdaftar", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(registrasi.this, "Coba lagi gunakan no yang berbeda", Toast.LENGTH_SHORT).show();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
