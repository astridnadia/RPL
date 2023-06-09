package com.tbash.tablereservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FormReservation extends AppCompatActivity {

    EditText namaPemesan_et, kontakPemesan_et, uangMuka_et;
    Button pesan_btn;
    String namaPemesan, kontakPemesan, dateMeja,timeMeja,nomor_meja, passReserve;
    Integer uangMuka;
    DataBaseHelper mDataBaseHelper;
    Boolean insertData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reservation);

        mDataBaseHelper=new DataBaseHelper(this);

        dateMeja=getIntent().getStringExtra("date");
        timeMeja=getIntent().getStringExtra("time");
        nomor_meja=getIntent().getStringExtra("tableNumber");

        namaPemesan_et=findViewById(R.id.editTextName);
        kontakPemesan_et=findViewById(R.id.editTextPhone);
        uangMuka_et=findViewById(R.id.editTextUangMuka);
        pesan_btn=findViewById(R.id.button_Pesan);

        pesan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataPemesan();
                passReserve=getRandomString(5);
                //addDataReserv(nomor_meja,namaPemesan,kontakPemesan,uangMuka,"Pengajuan",dateMeja,timeMeja,passReserve);
                saveDataFmServer("http://192.168.43.62/Android/syncReservation.php?mode=simpan");
                openUserReservStatAct();

            };
        });

    }

    public void getDataPemesan(){
        this.namaPemesan=namaPemesan_et.getText().toString();
        this.kontakPemesan=kontakPemesan_et.getText().toString();
        this.uangMuka=Integer.parseInt(uangMuka_et.getText().toString());
    }

    public void openUserReservStatAct(){
        Intent intentForUserReservStat=new Intent(this, UserReservationStatus.class);
        intentForUserReservStat.putExtra("nomorMeja",nomor_meja);
        intentForUserReservStat.putExtra("namaPemesan",namaPemesan);
        intentForUserReservStat.putExtra("kontakPemesan",kontakPemesan);
        intentForUserReservStat.putExtra("uangMuka",uangMuka);
        intentForUserReservStat.putExtra("date",dateMeja);
        intentForUserReservStat.putExtra("time",timeMeja);
        intentForUserReservStat.putExtra("passReserve",passReserve);
        startActivity(intentForUserReservStat);
    }

    public void addDataReserv(String nomorMeja, String namaReserver, String kontakReserver, Integer dpReserver, String statusReservation, String dateReservation, String timeReservation, String passReserve){
        this.insertData= mDataBaseHelper.addData(nomorMeja,namaReserver,kontakReserver,dpReserver,statusReservation,dateReservation,timeReservation, passReserve);
        if (insertData) {
            Toast.makeText(this, "Data successfullt inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public void saveDataFmServer(String url){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRootObject=new JSONObject(response);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    String status=jsonObject.optString("status").trim();

                   Toast.makeText(FormReservation.this,status,Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("nomor_meja_reservasi",nomor_meja);
                params.put("nama_reserver",namaPemesan);
                params.put("kontak_reserver",kontakPemesan);
                params.put("dp_reserver",uangMuka.toString());
                params.put("status_reserver","Pengajuan");
                params.put("date_reserver", dateMeja);
                params.put("time_reserver",timeMeja);
                params.put("pass_reserver",passReserve);
                return params;
            }
        };

        VolleySingleton.getInstance(FormReservation.this).addToRequestQueue(stringRequest);

    }

}