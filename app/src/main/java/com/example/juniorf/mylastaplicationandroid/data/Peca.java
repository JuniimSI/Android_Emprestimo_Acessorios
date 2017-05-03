package com.example.juniorf.mylastaplicationandroid.data;

import java.sql.Date;

/**
 * Created by juniorf on 20/10/16.
 */

public class Peca {
    private String nome;
    private String detalhes;
    private Date data;



    public Peca(){

    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Peca(String nome, String detalhes) {
        this.nome = nome;
        this.detalhes = detalhes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String toString() {
        return "- " + nome +  ", " + detalhes ;
    }
}
