package com.tbash.tablereservation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "reservation_table";
    private static final String TAG = "DatabaseHelper";
    private static final String COL1 = "ID_reservasi";
    private static final String COL2 = "nomor_meja_reservasi";
    private static final String COL3 = "nama_reserver";
    private static final String COL4 = "kontak_reserver";
    private static final String COL5 = "dp_reserver";
    private static final String COL6 = "status_reserver";
    private static final String COL7 = "date_reserver";
    private static final String COL8 = "time_reserver";
    private static final String COL9 = "pass_reserver";

    public DataBaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID_reservasi INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT," +
                COL3+" TEXT," +
                COL4+" TEXT,"+
                COL5+" INTEGER,"+
                COL6+" TEXT,"+
                COL7+" DATE,"+
                COL8+" TIME,"+
                COL9+" TEXT, UNIQUE(pass_reserver))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String nomorMeja, String namaReserver, String kontakReserver, Integer dpReserver, String statusReservation, String dateReservation, String timeReservation, String passReserve){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, nomorMeja);
        contentValues.put(COL3, namaReserver);
        contentValues.put(COL4, kontakReserver);
        contentValues.put(COL5, dpReserver);
        contentValues.put(COL6, statusReservation);
        contentValues.put(COL7, dateReservation);
        contentValues.put(COL8, timeReservation);
        contentValues.put(COL9, passReserve);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+TABLE_NAME;
        Cursor data= db.rawQuery(query,null);
        return data;
    }

    public Cursor getDataUser(String passReserve){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COL9+" = '"+passReserve+"'";
        Cursor data= db.rawQuery(query,null);
        return data;
    }

    public void changeStatusAccept(Integer idReservation){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "UPDATE "+TABLE_NAME+" SET "+COL6+" = 'ACCEPTED' WHERE "+COL1+" = '"+idReservation+"'";
        db.execSQL(query);
    }
    public void changeStatusReject(Integer idReservation){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "UPDATE "+TABLE_NAME+" SET "+COL6+" = 'REJECTED' WHERE "+COL1+" = '"+idReservation+"'";
        db.execSQL(query);
    }

    public Cursor getTableStatus(String nomorMeja, String date, String time){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+" = '"+nomorMeja+"' AND "+COL7+"= '"+date+"' AND "+COL8+"='"+time+"' AND "+COL6+"= '"+"ACCEPTED"+"'";
        Cursor data= db.rawQuery(query,null);
        return data;
    }




}
