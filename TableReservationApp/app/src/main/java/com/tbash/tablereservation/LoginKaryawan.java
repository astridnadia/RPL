package com.tbash.tablereservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginKaryawan extends AppCompatActivity {

    EditText passKaryawan_et;
    String passKaryawan;
    Button logKaryawan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_karyawan);

        passKaryawan_et=findViewById(R.id.editText_pwd);
        logKaryawan_btn=findViewById(R.id.button_login);

        logKaryawan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passKaryawan=passKaryawan_et.getText().toString();
                if(passKaryawan.equals("makan")){
                    openDataReservAct();
                }
            }
        });

    }

    public void openDataReservAct(){
        Intent intentForDataReserv=new Intent(this, ReservationList.class);
        startActivity(intentForDataReserv);
        finish();
    }
}