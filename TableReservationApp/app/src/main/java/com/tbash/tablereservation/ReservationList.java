package com.tbash.tablereservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class ReservationList extends AppCompatActivity {

    TableLayout tableReservation;
    DataBaseHelper mDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        mDataBaseHelper=new DataBaseHelper(this);

        tableReservation=findViewById(R.id.listReservationTable);
        //generateRow();


        ambilDataFmServer("http://192.168.43.62/Android/syncReservation.php?mode=baca");
    }

    public void generateRow(){
        Cursor data = mDataBaseHelper.getAllData();

        ArrayList<Integer> listDataID = new ArrayList<>();
        ArrayList<String> listNomor = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();
        ArrayList<String> listKontak = new ArrayList<>();
        ArrayList<String> listDP = new ArrayList<>();
        ArrayList<String> listStatus = new ArrayList<>();
        ArrayList<String> listDate = new ArrayList<>();
        ArrayList<String> listTime = new ArrayList<>();
        ArrayList<String> listPassReserve = new ArrayList<>();

        while(data.moveToNext()){
            listDataID.add(data.getInt(0));
            listNomor.add(data.getString(1));
            listNama.add(data.getString(2));
            listKontak.add(data.getString(3));
            listDP.add(data.getString(4));
            listStatus.add(data.getString(5));
            listDate.add(data.getString(6));
            listTime.add(data.getString(7));
            listPassReserve.add(data.getString(8));
        }

        for (int i=0; i<listDataID.size();i++){
            TableRow tbrow=new TableRow(this);

//            TextView tv_ID= new TextView(this);
//            tv_ID.setText(listDataID.get(i).toString());
//            tbrow.addView(tv_ID);

            TextView tv_NomorMeja= new TextView(this);
            tv_NomorMeja.setText(listNomor.get(i));
            tv_NomorMeja.setGravity(Gravity.CENTER);
            tbrow.addView(tv_NomorMeja);

            TextView tv_Nama= new TextView(this);
            tv_Nama.setText(listNama.get(i));
            tv_Nama.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Nama);

            TextView tv_Kontak= new TextView(this);
            tv_Kontak.setText(listKontak.get(i));
            tv_Kontak.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Kontak);

            TextView tv_Date= new TextView(this);
            tv_Date.setText(listDate.get(i));
            tv_Date.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Date);

            TextView tv_Time= new TextView(this);
            tv_Time.setText(listTime.get(i));
            tv_Time.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Time);

            TextView tv_Pass= new TextView(this);
            tv_Pass.setText(listPassReserve.get(i));
            tv_Pass.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Pass);

            TextView tv_Status= new TextView(this);
            tv_Status.setText(listStatus.get(i));
            tv_Status.setGravity(Gravity.CENTER);
            tbrow.addView(tv_Status);

            Button btnAccept=new Button(this);
            btnAccept.setText("ACCEPT");
            btnAccept.setId(listDataID.get(i));

            Button btnReject=new Button(this);
            btnReject.setText("REJECT");
            btnReject.setId(listDataID.get(i));


            if((listStatus.get(i).equals("Pengajuan"))){
                tbrow.addView(btnAccept);
                tbrow.addView(btnReject);
            }

            int finalI = i;
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataBaseHelper.changeStatusAccept(listDataID.get(finalI));
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });

            int finalI1 = i;
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataBaseHelper.changeStatusReject(listDataID.get(finalI1));
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });

            tableReservation.addView(tbrow);
        }

    }





    public void ambilDataFmServer(String url){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonRootObject=new JSONObject(response);
                        JSONArray jsonArray=jsonRootObject.optJSONArray("result");

                        ArrayList<Integer> listDataID = new ArrayList<>();
                        ArrayList<String> listNomor = new ArrayList<>();
                        ArrayList<String> listNama = new ArrayList<>();
                        ArrayList<String> listKontak = new ArrayList<>();
                        ArrayList<String> listDP = new ArrayList<>();
                        ArrayList<String> listStatus = new ArrayList<>();
                        ArrayList<String> listDate = new ArrayList<>();
                        ArrayList<String> listTime = new ArrayList<>();
                        ArrayList<String> listPassReserve = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                                listDataID.add(Integer.parseInt(jsonObject.optString("ID_reservasi").trim()));
                                listNomor.add(jsonObject.optString("nomor_meja_reservasi").trim());
                                listNama.add(jsonObject.optString("nama_reserver").trim());
                                listKontak.add(jsonObject.optString("kontak_reserver").trim());
                                listDP.add(jsonObject.optString("dp_reserver").trim());
                                listStatus.add(jsonObject.optString("status_reserver").trim());
                                listDate.add(jsonObject.optString("date_reserver").trim());
                                listTime.add(jsonObject.optString("time_reserver").trim());
                                listPassReserve.add(jsonObject.optString("pass_reserver").trim());
                        }

                        for (int i=0; i<listDataID.size();i++){
                            TableRow tbrow=new TableRow(getApplicationContext());

//            TextView tv_ID= new TextView(this);
//            tv_ID.setText(listDataID.get(i).toString());
//            tbrow.addView(tv_ID);

                            TextView tv_NomorMeja= new TextView(getApplicationContext());
                            tv_NomorMeja.setText(listNomor.get(i));
                            tv_NomorMeja.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_NomorMeja);

                            TextView tv_Nama= new TextView(getApplicationContext());
                            tv_Nama.setText(listNama.get(i));
                            tv_Nama.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Nama);

                            TextView tv_Kontak= new TextView(getApplicationContext());
                            tv_Kontak.setText(listKontak.get(i));
                            tv_Kontak.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Kontak);

                            TextView tv_Date= new TextView(getApplicationContext());
                            tv_Date.setText(listDate.get(i));
                            tv_Date.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Date);

                            TextView tv_Time= new TextView(getApplicationContext());
                            tv_Time.setText(listTime.get(i));
                            tv_Time.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Time);

                            TextView tv_Pass= new TextView(getApplicationContext());
                            tv_Pass.setText(listPassReserve.get(i));
                            tv_Pass.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Pass);

                            TextView tv_Status= new TextView(getApplicationContext());
                            tv_Status.setText(listStatus.get(i));
                            tv_Status.setGravity(Gravity.CENTER);
                            tbrow.addView(tv_Status);

                            Button btnAccept=new Button(getApplicationContext());
                            btnAccept.setText("ACCEPT");
                            btnAccept.setId(listDataID.get(i));

                            Button btnReject=new Button(getApplicationContext());
                            btnReject.setText("REJECT");
                            btnReject.setId(listDataID.get(i));


                            if((listStatus.get(i).equals("Pengajuan"))){
                                tbrow.addView(btnAccept);
                                tbrow.addView(btnReject);
                            }

                            int finalI = i;
                            btnAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //mDataBaseHelper.changeStatusAccept(listDataID.get(finalI));
                                    changeDataStatusServer("http://192.168.43.62/Android/syncReservation.php?mode=gantiAccepted",listPassReserve.get(finalI));

                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            });

                            btnReject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //mDataBaseHelper.changeStatusReject(listDataID.get(finalI1));
                                    changeDataStatusServer("http://192.168.43.62/Android/syncReservation.php?mode=gantiRejected",listPassReserve.get(finalI));
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            });

                            tableReservation.addView(tbrow);
                        }

                       // JSONObject jsonObject=jsonArray.getJSONObject(0);

                        //String a=jsonObject.optString("nomor_meja_reservasi").trim();

                        //namaMejaTV.setText(a);

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
                    params.clear();

                    return params;
                }
            };

            VolleySingleton.getInstance(ReservationList.this).addToRequestQueue(stringRequest);

    }

    public void changeDataStatusServer(String url, String passButton){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRootObject=new JSONObject(response);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    String status=jsonObject.optString("status").trim();

                    Toast.makeText(ReservationList.this,status,Toast.LENGTH_SHORT).show();

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

        VolleySingleton.getInstance(ReservationList.this).addToRequestQueue(stringRequest);

    }



}