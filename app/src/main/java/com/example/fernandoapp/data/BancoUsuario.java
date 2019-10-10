package com.example.fernandoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BancoUsuario extends SQLiteOpenHelper {
    public static final String TABELA = "usuarios";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String TELEFONE = "telefone";
    public static final String DISCIPLINA = "disciplina";
    public static final String TURMA = "turma";
    public static final String SENHA = "senha";
    private static final String NOME_BANCO = "FernandoApp.db";
    public static final String PERCURSO = "dadosGPS";
    private static final int VERSAO = 9;

    public BancoUsuario(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + NOME + " text, "
                + EMAIL + " text  NOT NULL UNIQUE, "
                + TELEFONE + " text, "
                + DISCIPLINA + " text,"
                + TURMA + " integer,"
                + SENHA + " text ,"
                + PERCURSO + " text "
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
