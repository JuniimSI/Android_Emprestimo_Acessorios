package com.example.juniorf.mylastaplicationandroid.data;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by juniorf on 15/11/16.
 */

public class ContatoDAOSERVER {
    public String[] getAllContats( ){

        String [] listaUsuario = null;
        int posicao = 0;

        Log.i("ENTROU NO EVENTO", "ENTROU");
        String url = "http://"+IP+"/android/listarContatos.php";
        String respostaRetornada = null;
        Log.i("TRY", "VAI ENTRAR");
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpGet(url);
            String resposta = respostaRetornada.toString();
            Log.i("Usuarios", ""+resposta);

            char separador ='#';
            int contaUsuarios=0;
            for(int i = 0; i<resposta.length(); i++){
                if(separador == resposta.charAt(i))
                    contaUsuarios++;
            }
            Log.i("CONTADOR", "+"+contaUsuarios);
            listaUsuario  = new String[contaUsuarios];

            char caracter_lido = resposta.charAt(0);
            String nome="";
            for(int i=0; caracter_lido != '^';i++){
                caracter_lido = resposta.charAt(i);
                Log.i("chars dos usuarios", "+"+caracter_lido);
                if(caracter_lido != '#'){
                    nome+=(char) caracter_lido;
                }else{
                    Log.i("nome", ""+nome);
                    listaUsuario[posicao] = ""+nome;
                    posicao++;
                    nome="";
                }

            }
        }catch (Exception erro){
            Log.i("erro", "="+erro);
        }
        return listaUsuario;

    }

    public String deleteContato(String nome) {
        String urlPost = "http://"+IP+"/android/excluirContato.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        parametrosPost.add(new BasicNameValuePair("nome", nome));

        String respostaRetornada = null;
        try {
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            Log.i("RESPOTA ", "+"+resposta);
            resposta = resposta.replaceAll("\\s", "");
            Log.i("RESPOTA ", "+"+resposta);
            if (resposta.equals("1")) {
                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public int insertContato(Contact c){
        String urlPost = "http://"+IP+"/android/inserirContato.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        parametrosPost.add(new BasicNameValuePair("nome", c.getNome()));
        parametrosPost.add(new BasicNameValuePair("telefone", c.getTelefone()));
        parametrosPost.add(new BasicNameValuePair("endereco", c.getEndereco()));
        parametrosPost.add(new BasicNameValuePair("imagem", c.getImagem()));


        String respostaRetornada = null;
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            resposta = resposta.replaceAll("\\s", "");
            if(resposta.equals("1")){
                return 1;
            }else{
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertEmprestimo(Emprestimo e){
        String urlPost = "http://"+IP+"/android/inserirEmprestimo.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        parametrosPost.add(new BasicNameValuePair("integrante", e.getIntegrante()));
        parametrosPost.add(new BasicNameValuePair("peca", e.getPeca()));
        parametrosPost.add(new BasicNameValuePair("data", e.getData().toString()));


        String respostaRetornada = null;
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            resposta = resposta.replaceAll("\\s", "");
            if(resposta.equals("1")){
                return 1;
            }else{
                return 0;
            }

        } catch (Exception es) {
            es.printStackTrace();
        }
        return 0;
    }

    public String[] getEmprestimos(String nomes ){

        String [] listaUsuario = null;
        int posicao = 0;

        String url = "http://"+IP+"/android/listarEmprestimosS.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        Log.i("NOMES", "+"+nomes);
        parametrosPost.add(new BasicNameValuePair("integrante", nomes));

        Log.i("ENTROU NO EVENTO", "ENTROU");
        String respostaRetornada = null;
        Log.i("TRY", "VAI ENTRAR");
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpPost(url, parametrosPost);
            String resposta = respostaRetornada.toString();


            Log.i("Usuarios", ""+resposta);

            char separador ='#';
            int contaUsuarios=0;
            for(int i = 0; i<resposta.length(); i++){
                if(separador == resposta.charAt(i))
                    contaUsuarios++;
            }
           /// Log.i("CONTADOR", "+"+contaUsuarios);
            listaUsuario  = new String[contaUsuarios];

            char caracter_lido = resposta.charAt(0);
            String nome="";
            for(int i=0; caracter_lido != '^';i++){
                caracter_lido = resposta.charAt(i);
               // Log.i("chars dos usuarios", "+"+caracter_lido);
                if(caracter_lido != '#'){
                    nome+=(char) caracter_lido;
                }else{
                    Log.i("nome", ""+nome);
                    listaUsuario[posicao] = ""+nome;
                    posicao++;
                    nome="";
                }

            }
        }catch (Exception erro){
            Log.i("erro", "="+erro);
        }
        return listaUsuario;

    }

    public int deleteEmprestimo(String nome, String peca){
        String urlPost = "http://"+IP+"/android/excluirEmprestimo.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
//        String agora = pegarONome(nome);
        String agora = nome;
        parametrosPost.add(new BasicNameValuePair("integrante", agora));
        parametrosPost.add(new BasicNameValuePair("peca",peca));
        Log.i("NO DELETE EMPRESTIMO", "NOME"+agora);
        Log.i("NO DELETE EMPRESTIMO", "NOME"+peca);

        String respostaRetornada = null;
        try {
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            Log.i("RESPOTA ", "+"+resposta);
            resposta = resposta.replaceAll("\\s", "");
            Log.i("RESPOTA ", "+"+resposta);
            if (resposta.equals("1")) {
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public String pegarONome(String frase){
        char caracter_lido=' ';
        String nome="";
        for(int i = 0; caracter_lido != '}'; i++) {
            caracter_lido = frase.charAt(i);
            if(caracter_lido == 'e' && frase.charAt(i+1)=='=')
                for(int j=i+2; caracter_lido != '}';j++){
                    caracter_lido = frase.charAt(j);
                    if(caracter_lido != '}'){
                        nome+=(char) caracter_lido;
                    }else{
                        Log.i("nome", ""+nome);
                        return nome;
                    }
                }

        }
        return nome;
    }
}
