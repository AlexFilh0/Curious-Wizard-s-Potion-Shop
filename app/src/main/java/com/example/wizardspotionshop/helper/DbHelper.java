package com.example.wizardspotionshop.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DB_NAME = "DB_LOCAL";
    public static String TABELA_USUARIOS = "USUARIO";
    public static String TABELA_USUARIO_LOG = "USUARIO_LOGADO";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIOS +
                            " (usuario VARCHAR(15) PRIMARY KEY, " +
                            "  senha VARCHAR(20) NOT NULL, " +
                            "  logado BOOLEAN DEFAULT 'F');";

        String sqlUsuarioLog = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO_LOG +
                               " (usuario VARCHAR(15) PRIMARY KEY);";

        try{
            sqLiteDatabase.execSQL(sqlUsuario);
            sqLiteDatabase.execSQL(sqlUsuarioLog);
        }catch (Exception e){
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
