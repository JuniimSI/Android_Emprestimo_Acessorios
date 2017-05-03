package com.example.juniorf.mylastaplicationandroid.data;

/**
 * Created by marcioefmaia on 10/6/16.
 */
public class Contact {

    static int counter;
    int id;
    String nome;
    String telefone;
    String endereco;
    String imagem;


    public Contact(String nome, String telefone, String endereco, String imagem) {
        this.nome = nome;
        this.telefone = telefone;

        this.imagem = imagem;
        this.endereco = endereco;
        this.id = counter++;
    }

    public Contact() {

    }

    public String getNome() {
        return nome;

    }

    public int getContactId() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
