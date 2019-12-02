package com.example.fernandoapp.data.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Piado {
    private int piadoId;
    private int autorId;
    private String mensagem;
    private String url;
    private Date dataCriacao;
    private boolean isAtivo;
    private String usuarioNome = "";

    public Piado(JSONObject piadoJson) throws JSONException, ParseException {
        this.piadoId = piadoJson.getInt("piado_id");
        this.autorId = piadoJson.getInt("usuario_id");
        this.mensagem = piadoJson.getString("mensagem");
        this.url = piadoJson.getString("url");
        this.usuarioNome = piadoJson.getString("usuario_nome");
        this.dataCriacao = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(piadoJson.getString("data_criacao"));
        this.isAtivo = piadoJson.getInt("isAtivo") == 1;
    }

    public Piado(int piadoId, int autorId, String mensagem, String url, Date dataCriacao, boolean isAtivo) {
        this.piadoId = piadoId;
        this.autorId = autorId;
        this.mensagem = mensagem;
        this.url = url;
        this.dataCriacao = dataCriacao;
        this.isAtivo = isAtivo;
    }

    public Piado(int autorId, int piadoId, String mensagem) {
        this.piadoId = piadoId;
        this.autorId = autorId;
        this.mensagem = mensagem;
    }

    public Piado() {
        mensagem = "Nova";
        autorId = -1;
    }

    public Piado(Usuario usuario, String mensagem) {
        this.mensagem = mensagem;
        this.autorId = usuario.getId();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Piado ID: %d \n Autor: %s \n Mensagem: %s \n", piadoId, usuarioNome, mensagem);
    }

    public int getPiadoId() {
        return piadoId;
    }

    public void setPiadoId(int piadoId) {
        this.piadoId = piadoId;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public String getAutorNome() {
        return usuarioNome;
    }
}
