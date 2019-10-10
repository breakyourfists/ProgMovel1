package com.example.fernandoapp.data.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

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

    public ArrayList<LatLng> getPercurso() {
        return percurso;
    }
    public void addLocation(Location loc) {
        percurso.add( new LatLng(loc.getLatitude(),loc.getLongitude()));
    }

    public void setPercurso(ArrayList<LatLng> percurso) {
        this.percurso = percurso;
    }

    ArrayList<LatLng> percurso;
    LatLng lat;


    public Usuario(int id, String nome, String email, String senha, String disciplina, String telefone, int turma) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.disciplina = disciplina;
        this.telefone = telefone;
        this.turma = turma;
        this.percurso = new ArrayList<LatLng>();
    }

    private Usuario(Parcel in) {
        turma = in.readInt();
        nome = in.readString();
        email = in.readString();
        telefone = in.readString();
        disciplina = in.readString();
        senha = in.readString();
        percurso = new ArrayList<LatLng>();

    }

    public Usuario(int id, String nome, String email, String senha, String disciplina, String telefone, int turma, String percurso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.disciplina = disciplina;
        this.telefone = telefone;
        this.turma = turma;
        this.percurso = new ArrayList<LatLng>();
        try {
            String[] percursoSplit = percurso.split("//|");

            Double d1, d2;
            String[] celula;
            for (String splitado : percursoSplit) {
                celula = splitado.split(",");
                d1 = Double.parseDouble(celula[0]);
                d2 = Double.parseDouble(celula[1]);
                this.percurso.add(new LatLng(d1,d2));
            }
        } catch (java.lang.NumberFormatException e) {
            Log.e("DAO","valor do percurso é >"+percurso+"<",e);
        }
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(turma);
        out.writeString(nome);
        out.writeString(email);
        out.writeString(telefone);
        out.writeString(disciplina);
        out.writeString(senha);
        out.writeParcelable(this.lat, flags);
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
