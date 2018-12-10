package com.ifsp.cadastropessoa.modal;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
   private String nome;
   private String cpf;
   private String idade;
   private String telefone;
   private String email;
   private String id;
   List<String> dadosPessoa = new ArrayList<>();

    public List<String> getDadosPessoa() {
        dadosPessoa.add(getId());
        dadosPessoa.add(getNome());
        dadosPessoa.add(getCpf());
        dadosPessoa.add(getIdade());
        dadosPessoa.add(getTelefone());
        dadosPessoa.add(getEmail());
        return dadosPessoa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
