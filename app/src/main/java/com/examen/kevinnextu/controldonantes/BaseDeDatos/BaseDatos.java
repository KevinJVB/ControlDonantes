package com.examen.kevinnextu.controldonantes.BaseDeDatos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 26/03/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final String tipo = " TEXT";
    private static final String Coma = ",";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CONTROL_DONANTES.sqlite";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Estructura.EstructuraBase.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_2 = "DROP TABLE IF EXISTS " + Estructura.EstructuraBase.TABLE_NAME_DONANTES;

    private static final String Sentencia =
            "CREATE TABLE " + Estructura.EstructuraBase.TABLE_NAME + " ("
                    + Estructura.EstructuraBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Estructura.EstructuraBase.USER + tipo + Coma +
                    Estructura.EstructuraBase.PASS + tipo + " )";

    private static final String Sentencia_2 =
            "CREATE TABLE " + Estructura.EstructuraBase.TABLE_NAME_DONANTES + " ("
                    + Estructura.EstructuraBase.IDENTIFICACION + " INTEGER PRIMARY KEY, "
                    + Estructura.EstructuraBase.NOMBRE + tipo + Coma
                    + Estructura.EstructuraBase.APELLIDOS + tipo + Coma
                    + Estructura.EstructuraBase.EDAD + tipo + Coma
                    + Estructura.EstructuraBase.SANGRE + tipo + Coma
                    + Estructura.EstructuraBase.RH + tipo + Coma
                    + Estructura.EstructuraBase.PESO + tipo + Coma +
                    Estructura.EstructuraBase.ESTATURA + tipo + " )";

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Sentencia);
        db.execSQL(Sentencia_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_2);
        onCreate(db);
    }
}
