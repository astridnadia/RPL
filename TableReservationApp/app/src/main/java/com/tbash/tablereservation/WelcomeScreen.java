package com.tbash.tablereservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Class berisi tombol 3 menu utama:menu ke cek reservasi, login karyawan, dan cek status reservasi
 */
public class WelcomeScreen extends AppCompatActivity {

    Button startReservation_btn,startKaryawan_btn, cekStatus_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startReservation_btn=findViewById(R.id.button_startReservation);
        startKaryawan_btn=findViewById(R.id.button_karyawan);
        cekStatus_btn=findViewById(R.id.cekStatus_btn);

        startReservation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateTimeAct();
            }
        });

        startKaryawan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginKarywanAct();
            }
        });

        cekStatus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPassCodeAct();
            }
        });

    }

    /**
     * method untuk membuka tampilan menu awal reservasi yaitu menu pemilihan tanggal dan waktu
     */
    public void openDateTimeAct(){
        Intent intentForDateTime=new Intent(this, DateTimeReservation.class);
        startActivity(intentForDateTime);
    }

    /**
     * method untuk membuka tampilan login karyawan untuk melakukan validasi reservasi
     */
    public void openLoginKarywanAct(){
        Intent intentForKaryawanLog=new Intent(this, LoginKaryawan.class);
        startActivity(intentForKaryawanLog);
    }

    /**
     * method untuk membuka tampilan input pass code untuk mengecek status reservasi
     */
    public void openPassCodeAct(){
        Intent intentForPassCode=new Intent(this, PassCodeLogin.class);
        startActivity(intentForPassCode);
    }



}