package com.example.kita_sekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class start2_activity extends AppCompatActivity {
    private Button mulai;
    private EditText inputnama;

    String nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2_activity);

        inputnama = findViewById(R.id.input_nama);
        mulai = findViewById(R.id.btn_mulai);



        mulai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mulai = new Intent(start2_activity.this, halaman_utama.class);

                mulai.putExtra("nama",inputnama.getText().toString());
                startActivity(mulai);
            }
        });
    }
}
