package com.tbash.tablereservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

import static android.widget.Toast.LENGTH_SHORT;

public class PassCodeLogin extends AppCompatActivity {

    Button checkPassCode_btn;
    EditText codepassET;
    String codePass;
    DataBaseHelper mDataBaseHelper;
    boolean checking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code_login);

        mDataBaseHelper=new DataBaseHelper(this);

        checkPassCode_btn=findViewById(R.id.checkPassCode_btn);
        codepassET=findViewById(R.id.checkPass_te);

        checkPassCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codePass= codepassET.getText().toString();
                checkStatusFMServer("http://192.168.43.62/Android/syncReservation.php?mode=cekStatus", codePass);

                //checking=checkPassCode(codePass);
                if(checking==true){
                    //openUserReservStatAct();
                }else {
                    Toast.makeText(getApplicationContext(), "Tunggu Sebentar", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public boolean checkPassCode(String passReserve){
        Cursor data=mDataBaseHelper.getDataUser(passReserve);
        if (data.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public void openUserReservStatAct(){
        Intent intentForUserReservStat=new Intent(this, UserReservationStatus.class);
        intentForUserReservStat.putExtra("passReserve",codePass);
        startActivity(intentForUserReservStat);
    }

    public void checkStatusFMServer(String url, String passButton){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRootObject=new JSONObject(response);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");
//                    JSONObject jsonObject=jsonArray.getJSONObject(0);
//
//                    String id_reservasi=jsonObject.optString("ID_reservasi").trim();
//                    if(id_reservasi!=null){
//                        checking=true;
//                    }else {
//                        checking=false;
//                        Toast.makeText(PassCodeLogin.this,"sorry wrong",Toast.LENGTH_SHORT).show();
//                    }

                    if (jsonArray != null && jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String id_reservasi = jsonObject.optString("ID_reservasi").trim();
                        if (!id_reservasi.equals("null")) {
                            // The id_reservasi is not empty or null, perform your actions here
                            checking=true;
                            openUserReservStatAct();
                        } else {
                            // The id_reservasi is empty or null, handle the case accordingly
                            checking=false;
                            Toast.makeText(PassCodeLogin.this,"Maaf kode salah",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // The jsonArray is empty or null, handle the case accordingly

                        //Toast.makeText(PassCodeLogin.this,"sorry wrong",Toast.LENGTH_SHORT).show();
                    }

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

        VolleySingleton.getInstance(PassCodeLogin.this).addToRequestQueue(stringRequest);

    }
}