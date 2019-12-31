package com.example.kita_sekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrasi extends AppCompatActivity {

    EditText emailId, password;
    Button btnDaftar;
    TextView tvLogin;
    TextView tvLewati;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.input_email_reg);
        password = findViewById(R.id.input_password_reg);
        btnDaftar = findViewById(R.id.btn_daftar);
        tvLogin = findViewById(R.id.tv_login);
        tvLewati = findViewById(R.id.tv_lewati);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Masukkan email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Masukkan password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(registrasi.this, "Email & Password kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(registrasi.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(registrasi.this, "Registrasi Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(registrasi.this, login.class));
                            }
                        }
                    });
                }

                else{
                    Toast.makeText(registrasi.this, "Error", Toast.LENGTH_SHORT).show();

                }
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
}
