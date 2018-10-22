package com.example.renato.recicleview.classes;

public class Bin {
    private String tipo, bandeira, pais, estado, moeda;
    public Bin(String tipo, String bandeira, String pais, String estado, String moeda){
        this.tipo = tipo;
        this.bandeira = bandeira;
        this.pais = pais;
        this.estado = estado;

    }

    public String getTipo(){return this.tipo;}
    public String getBandeira(){return this.bandeira;}
    public String getPais(){return this.pais;}
    public String getEstado(){return this.estado;}
    public String getMoeda(){return this.moeda;}

}
