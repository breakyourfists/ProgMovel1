package com.example.fernandoapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
    String nome, email, telefone, disciplina, senha;
    int turma, id;

    public Usuario(int id, String nome, String email, String senha, String disciplina, String telefone, int turma) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.disciplina = disciplina;
        this.telefone = telefone;
        this.turma = turma;
    }

    private Usuario(Parcel in) {
        turma = in.readInt();
        nome = in.readString();
        email = in.readString();
        telefone = in.readString();
        disciplina = in.readString();
        senha = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(turma);
        out.writeString(nome);
        out.writeString(email);
        out.writeString(telefone);
        out.writeString(disciplina);
        out.writeString(senha);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTurma() {
        return turma;
    }

    public void setTurma(int turma) {
        this.turma = turma;
    }

    public void setTurma(String turma) {
        this.turma = Integer.parseInt(turma);
    }
}
