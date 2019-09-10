package com.example.fernandoapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.example.fernandoapp.data.BancoUsuario;
import com.example.fernandoapp.data.model.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase db;
    private BancoUsuario banco;

    public UsuarioDAO(Context context) {
        banco = new BancoUsuario(context);
    }

    public Usuario inserirUsuario(String nome, String email, String telefone, int turma, String senha, String disciplina) {
        ContentValues valores;
        int id = getProxUsuarioID();
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(BancoUsuario.NOME, nome);
        valores.put(BancoUsuario.EMAIL, email);
        valores.put(BancoUsuario.TELEFONE, telefone);
        valores.put(BancoUsuario.TURMA, turma);
        valores.put(BancoUsuario.SENHA, senha);
        valores.put(BancoUsuario.DISCIPLINA, disciplina);
        valores.put(BancoUsuario.ID, id);

        long resultado = db.insert(BancoUsuario.TABELA, null, valores);
        db.close();

        if (resultado == -1) {
            return null;
        } else {
            return new Usuario(id, nome, email, senha, disciplina, telefone, turma);
        }
    }

    public int getProxUsuarioID() {

        db = banco.getReadableDatabase();
        String[] campos = {BancoUsuario.ID};
        Cursor cursor = db.query(BancoUsuario.TABELA, campos, null, null, null, null, BancoUsuario.ID + " DESC", null);
        cursor.moveToFirst();

        int id;
        try {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(BancoUsuario.ID));
        } catch (CursorIndexOutOfBoundsException e) {
            id = 0;
        }
        db.close();
        if (id == 0)
            return 1;
        else
            return id + 1;
    }

    public Usuario getUsuario(String emailP, String senhaP) {
        db = banco.getReadableDatabase();
        Usuario usuario = null;
        String[] campos = {BancoUsuario.EMAIL,
                BancoUsuario.SENHA, BancoUsuario.DISCIPLINA, BancoUsuario.NOME, BancoUsuario.TURMA, BancoUsuario.TELEFONE, BancoUsuario.ID};
        String where = BancoUsuario.EMAIL + "='" + emailP + "'";
        where += " and " + BancoUsuario.SENHA + "=" + senhaP;

        Cursor cursor = db.query(BancoUsuario.TABELA, campos, where, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(BancoUsuario.NOME));
            int turma = cursor.getInt(cursor.getColumnIndexOrThrow(BancoUsuario.TURMA));
            String telefone = cursor.getString(cursor.getColumnIndexOrThrow(BancoUsuario.TELEFONE));
            String disciplina = cursor.getString(cursor.getColumnIndexOrThrow(BancoUsuario.DISCIPLINA));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow(BancoUsuario.SENHA));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(BancoUsuario.EMAIL));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BancoUsuario.ID));
            usuario = new Usuario(id, nome, email, senha, disciplina, telefone, turma);
        }
        db.close();
        return usuario;
    }
}