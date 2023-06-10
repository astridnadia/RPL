package com.tbash.tablereservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

/**
 * class untuk mengetahui status reservasi yang telah diajukan
 */
public class UserReservationStatus extends AppCompatActivity {

    TextView userStatus_tv, userTable_tv, userDate_tv,userTime_tv, userName_tv, userKontak_tv, userUangMuka_tv, passCodeTV;
    String namaPemesan, kontakPemesan, dateMeja,timeMeja,nomor_meja, passReserve;
    Integer uangMuka;
    DataBaseHelper mDataBaseHelper;
    ImageView accepted_img,rejected_img,pengajuan_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_status);
        mDataBaseHelper=new DataBaseHelper(this);


        userStatus_tv=findViewById(R.id.userReservStat_TV);
        userTable_tv=findViewById(R.id.userTable_TV);
        userDate_tv=findViewById(R.id.userDate_TV);
        userTime_tv=findViewById(R.id.userTime_TV);
        userName_tv=findViewById(R.id.userNama_TV);
        userKontak_tv=findViewById(R.id.userPhone_TV);
        userUangMuka_tv=findViewById(R.id.userUangMuka_TV);
        passCodeTV=findViewById(R.id.passCodeTV);
        accepted_img=findViewById(R.id.imageView4);
        rejected_img=findViewById(R.id.imageView4_rejected);
        pengajuan_img=findViewById(R.id.imageView4_pengajuan);

        accepted_img.setVisibility(View.INVISIBLE);
        rejected_img.setVisibility(View.INVISIBLE);
        pengajuan_img.setVisibility(View.INVISIBLE);

        this.passReserve=getIntent().getStringExtra("passReserve");
        //generateDataReserver();
        generateStatusFMServer("http://192.168.43.62/Android/syncReservation.php?mode=cekStatus", passReserve);


    }

    /**
     * method untuk mendapatkan data status reservasi dari database lokal, tidak digunakan pada versi 2
     */
    public void generateDataReserver(){
        Cursor data=mDataBaseHelper.getDataUser(passReserve);
        if (data.getCount()>0){
            data.moveToFirst();
            userTable_tv.setText(data.getString(1));
            userName_tv.setText(data.getString(2));
            userKontak_tv.setText(data.getString(3));
            userUangMuka_tv.setText(data.getString(4));
            if(data.getString(5).equals("ACCEPTED")){
                userStatus_tv.setTextColor(Color.parseColor("#046C42"));
                accepted_img.setVisibility(View.VISIBLE);
            }else if(data.getString(5).equals("REJECTED")){
                userStatus_tv.setTextColor(Color.RED);
                rejected_img.setVisibility(View.VISIBLE);
            }else if(data.getString(5).equals("Pengajuan")){
                pengajuan_img.setVisibility(View.VISIBLE);
            }
            userStatus_tv.setText(data.getString(5));
            userDate_tv.setText(data.getString(6));
            userTime_tv.setText(data.getString(7));
            passCodeTV.setText(data.getString(8));
        }
        data.close();

    }

    /**
     * digunakan untuk mendapatkan data status reservasi dari database server
     * @param url
     * @param passButton
     */
    public void generateStatusFMServer(String url, String passButton){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRootObject=new JSONObject(response);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    userTable_tv.setText(jsonObject.optString("nomor_meja_reservasi").trim());
                    userName_tv.setText(jsonObject.optString("nama_reserver").trim());
                    userKontak_tv.setText(jsonObject.optString("kontak_reserver").trim());
                    userUangMuka_tv.setText(jsonObject.optString("dp_reserver").trim());
                    if(jsonObject.optString("status_reserver").trim().equals("ACCEPTED")){
                        userStatus_tv.setTextColor(Color.parseColor("#046C42"));
                        accepted_img.setVisibility(View.VISIBLE);
                    }else if(jsonObject.optString("status_reserver").trim().equals("REJECTED")){
                        userStatus_tv.setTextColor(Color.RED);
                        rejected_img.setVisibility(View.VISIBLE);
                    }else if(jsonObject.optString("status_reserver").trim().equals("Pengajuan")){
                        pengajuan_img.setVisibility(View.VISIBLE);
                    }
                    userStatus_tv.setText(jsonObject.optString("status_reserver").trim());
                    userDate_tv.setText(jsonObject.optString("date_reserver").trim());
                    userTime_tv.setText(jsonObject.optString("time_reserver").trim());
                    passCodeTV.setText(jsonObject.optString("pass_reserver").trim());

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
                params.put("pass_reserver",passButton);
                return params;
            }
        };

        VolleySingleton.getInstance(UserReservationStatus.this).addToRequestQueue(stringRequest);

    }


}