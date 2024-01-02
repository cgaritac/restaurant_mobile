package com.example.carlosgarita_proyectofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //Esta clase SQLiteOpenHelper recibe 4 parámetros
    public AdminSQLiteOpenHelper (Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){

        //Invocamos la clase super o principal y pasámos por parámetro: context, nombre, factory, version
        super(context, nombre, factory, version);
    }
    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("create table productos (codigo integer primary key, orden integer, articulo text, tipo text, cantidad integer, precio real)");
        db.execSQL("create table ordenes (orden integer primary key, cantPer integer, mesa text, total real, observaciones text)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int version1, int version2){
        db.execSQL("drop table if exists productos");
        db.execSQL("create table productos (codigo integer primary key, orden integer, articulo text, tipo text, cantidad integer, precio real)");
        db.execSQL("drop table if exists ordenes");
        db.execSQL("create table ordenes (orden integer primary key, cantPer integer, mesa text, total real, observaciones text)");
    }
}
