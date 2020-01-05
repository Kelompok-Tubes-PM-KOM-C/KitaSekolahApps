package com.example.kita_sekolah;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kita_sekolah.Model.Users;
import com.example.kita_sekolah.Prevalent.Prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import io.paperdb.Paper;


public class login extends AppCompatActivity {

    EditText input_login, password;
    Button btnLogin;
    TextView tvDaftar;
    TextView tvDaftar2;
    TextView tvLewati;
    FirebaseAuth mFirebaseAuth;
    Switch sw_admin = null;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        input_login = findViewById(R.id.input_login);
        password = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        tvDaftar = findViewById(R.id.tv_daftar);
        tvDaftar2 = findViewById(R.id.tv_daftar2);
        tvLewati = findViewById(R.id.tv_lewati2);
        sw_admin = findViewById(R.id.switch_admin);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);


        sw_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    btnLogin.setText("Login Admin");
                    parentDbName = "Admins";
                    sw_admin.setText("Admin");  //To change the text near to switch
                    tvDaftar.setVisibility(View.INVISIBLE);
                    tvLewati.setVisibility(View.INVISIBLE);
                    tvDaftar2.setVisibility(View.INVISIBLE);
                    Log.d("You are :", "Checked");
                }
                
                else {
                    btnLogin.setText("Login");
                    parentDbName = "Users";
                    sw_admin.setText(" ");  //To change the text near to switch
                    tvDaftar.setVisibility(View.VISIBLE);
                    tvLewati.setVisibility(View.VISIBLE);
                    tvDaftar2.setVisibility(View.VISIBLE);
                    Log.d("You are :", " Not Checked");
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(login.this, registrasi.class);
                startActivity(daftar);
            }
        });

        tvLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lewati = new Intent(login.this, start2_activity.class);
                startActivity(lewati);
            }
        });

    }





    private void loginUser() {

        String input = input_login.getText().toString();
        String pwd = password.getText().toString();

        if(input.isEmpty()) {
                    input_login.setError("Masukkan email");
                    input_login.requestFocus();
                }

        else if (pwd.isEmpty()){
                    password.setError("Masukkan password");
                    password.requestFocus();
                }

        else {
            loadingBar.setTitle("Login Akun");
            loadingBar.setMessage("Tunggu Sebentar...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessAccount(input,pwd);
        }

    }

    private void AllowAccessAccount(final String input, final String pwd) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(input).exists()) {

                    Users userData = dataSnapshot.child(parentDbName).child(input).getValue(Users.class);
                    if (userData.getNo_hp().equals(input)) {

                        if (userData.getPassword().equals(pwd)){

                            if (parentDbName.equals("Admins")) {

//                                Paper.book().write(Prevalent.UserPhoneKey, input);
//                                Paper.book().write(Prevalent.UserPasswordKey, pwd);

                                Toast.makeText(login.this, "Login Admin", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent masuk_admin = new Intent(login.this, home_admin_drawer.class);
                                startActivity(masuk_admin);
                            }

                            else {
                                Paper.book().write(Prevalent.UserPhoneKey, input);
                                Paper.book().write(Prevalent.UserPasswordKey, pwd);

                                Toast.makeText(login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent masuk = new Intent(login.this, halaman_utama.class);
                                masuk.putExtra("nama",userData.getUsername());
                                startActivity(masuk);
                            }

                        }

                        else {
                            Toast.makeText(login.this, "Password Salah", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                    }

                }

                else {
                    Toast.makeText(login.this, "Akun tidak tersedia", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    @Overrides
//    protected void onStart() {
//        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//
//    }
}
