package com.tbash.tablereservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableResevationStatus extends AppCompatActivity {

    Button table1Reserv_btn,table2Reserv_btn ;

    TextView table1Status_tv, table2Status_tv;

    ImageView checklistMeja1, checklistMeja2;

    Boolean table1Status,table2Status;

    String tableChoose,date,time;

    DataBaseHelper mDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_resevation_status);

        mDataBaseHelper=new DataBaseHelper(this);

        date=getIntent().getStringExtra("date");
        time=getIntent().getStringExtra("time");

        table1Reserv_btn=findViewById(R.id.button_Meja1);
        table1Status_tv=findViewById(R.id.TV_statusMeja1);
        checklistMeja1=findViewById(R.id.imageView4_penuh);
        checklistMeja1.setVisibility(View.INVISIBLE);

        table2Reserv_btn=findViewById(R.id.button_Meja2);
        table2Status_tv=findViewById(R.id.TV_statusMeja2);
        checklistMeja2=findViewById(R.id.imageView4_penuh_2);
        checklistMeja2.setVisibility(View.INVISIBLE);


        checkStatusTBServer("http://192.168.43.62/Android/syncReservation.php?mode=cekMeja",date,time);
        //cekStatus();

        table1Reserv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableChoose="table 1";
                openFormReservStatAct();
            }
        });

        table2Reserv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableChoose="table 2";
                openFormReservStatAct();
            }
        });
    }

    public void cekStatus(){
//        table1Status=statusMejaB("table 1", date,time);
//        table2Status=statusMejaB("table 2", date,time);

        if(table1Status==false){
            table1Reserv_btn.setEnabled(false);
            checklistMeja1.setVisibility(View.VISIBLE);
            table1Status_tv.setText("Status: Tidak Tersedia");
        }else if(table2Status==false){
            table2Reserv_btn.setEnabled(false);
            table2Status_tv.setText("Status: Tidak Tersedia");
            checklistMeja2.setVisibility(View.VISIBLE);
        }

    }

    public void openFormReservStatAct(){
        Intent intentForFormReservStat=new Intent(this, FormReservation.class);
        intentForFormReservStat.putExtra("tableNumber",tableChoose);
        intentForFormReservStat.putExtra("date",date);
        intentForFormReservStat.putExtra("time",time);
        startActivity(intentForFormReservStat);
        finish();
    }

    public boolean statusMejaB(String nomorMeja, String date, String time){
        Cursor data=mDataBaseHelper.getTableStatus(nomorMeja, date, time);
        if (data.getCount()>0){
            return false;
        }else{
            return true;
        }
    }

    public void checkStatusTBServer(String url, String date, String time){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRootObject=new JSONObject(response);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");

                    ArrayList<String> listNomor = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        listNomor.add(jsonObject.optString("nomor_meja_reservasi").trim());
                    }
                    if (listNomor.contains("table 1")){
                        //table1Status=false;
                        table1Reserv_btn.setEnabled(false);
                        checklistMeja1.setVisibility(View.VISIBLE);
                        table1Status_tv.setText("Status: Tidak Tersedia");
                    }else {
                        table1Status=true;
                    }
                    if (listNomor.contains("table 2")){
                        //table2Status=false;
                        table2Reserv_btn.setEnabled(false);
                        table2Status_tv.setText("Status: Tidak Tersedia");
                        checklistMeja2.setVisibility(View.VISIBLE);
                    }else {
                        table2Status=true;
                    }



//                    if(id_reservasi!=null){
//                        if(nomorMeja.equals("table 1")){
//                            table1Status=false;
//                        }
//                        if(nomorMeja.equals("table 2")){
//                            table2Status=false;
//                        }
//                    }else {
//                        if(nomorMeja.equals("table 1")){
//                            table1Status=true;
//                        }
//                        if(nomorMeja.equals("table 2")){
//                            table2Status=true;
//                        }
//                    }

                    //Toast.makeText(PassCodeLogin.this,status,Toast.LENGTH_SHORT).show();

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
                params.put("date_reserver",date);
                params.put("time_reserver",time);
                return params;
            }
        };

        VolleySingleton.getInstance(TableResevationStatus.this).addToRequestQueue(stringRequest);

    }



  

}