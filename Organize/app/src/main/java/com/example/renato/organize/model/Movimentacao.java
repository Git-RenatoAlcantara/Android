package com.example.renato.organize.model;

import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.example.renato.organize.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {
   private String data;
   private String categoria;
   private String descricao;
   private String tipo;
   private double valor;
   private String dataFormat;
   private String key;

    public Movimentacao() {
    }

    public void salvar(){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getfireBaseAutenticacao();
        String idUsuario = Base64Custom.base64Encode(autenticacao.getCurrentUser().getEmail());
        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();

        firebase.child("movimentacao")
                .child(idUsuario)
                .child(dataFormat)
                .push()
                .setValue(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        String explode[] = data.split("/");
        this.dataFormat = explode[1] +""+ explode[2];
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
