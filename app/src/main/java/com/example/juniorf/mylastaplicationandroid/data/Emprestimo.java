package com.example.juniorf.mylastaplicationandroid.data;

import java.sql.Date;

/**
 * Created by juniorf on 22/10/16.
 */

public class Emprestimo {
    private String peca;
    private int id;
    private Date data;
    private String integrante;

    public Emprestimo(String contact, String peca, Date data) {
        this.peca = peca;
        this.data = data;
        this.integrante = contact;
    }

    public String getPeca() {
        return peca;
    }

    public void setPeca(String peca) {
        this.peca = peca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIntegrante() {
        return integrante;
    }

    public void setIntegrante(String integrante) {
        this.integrante = integrante;
    }
}
