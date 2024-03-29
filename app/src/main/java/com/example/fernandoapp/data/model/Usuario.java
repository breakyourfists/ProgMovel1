package com.example.fernandoapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario  implements Parcelable {
    String nome,email,telefone,disciplina, senha;
    int turma;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(turma);
        out.writeString(nome);
        out.writeString(email);
        out.writeString(telefone);
        out.writeString(disciplina);
        out.writeString(senha);
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    private Usuario(Parcel in) {
        turma = in.readInt();
        nome = in.readString();
        email = in.readString();
        telefone = in.readString();
        disciplina= in.readString();
        senha = in.readString();
    }

    public Usuario(){
    };

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
