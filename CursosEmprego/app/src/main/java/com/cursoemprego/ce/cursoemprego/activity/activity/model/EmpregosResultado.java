package com.cursoemprego.ce.cursoemprego.activity.activity.model;

import java.io.Serializable;
import java.net.URL;

public class EmpregosResultado implements Serializable {
    private String text;
    private int imagem;

    public EmpregosResultado() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
