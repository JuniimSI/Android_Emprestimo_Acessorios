package com.example.juniorf.mylastaplicationandroid.data;

import android.os.AsyncTask;
import android.util.Log;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

/**
 * Created by juniorf on 15/11/16.
 */

public class PecaDAOSERVER {
    public String[] getAllPeca() {

        int posicao = 0;
        String[] listaUsuarios = null;
        Log.i("ENTROU NO EVENTO", "ENTROU");
        String url = "http://"+IP+"/android/listarPecasSpinner.php";
        String respostaRetornada = null;
        Log.i("TRY", "VAI ENTRAR");
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpGet(url);
            String resposta = respostaRetornada.toString();
            Log.i("Pecas", ""+resposta);

            char separador ='#';
            int contaUsuarios=0;
            for(int i = 0; i<resposta.length(); i++){
                if(separador == resposta.charAt(i))
                    contaUsuarios++;
            }

            listaUsuarios  = new String[contaUsuarios];

            char caracter_lido = resposta.charAt(0);
            String nome="";
            for(int i=0; caracter_lido != '^';i++){
                caracter_lido = resposta.charAt(i);
                Log.i("chars dos peças", "+"+caracter_lido);
                if(caracter_lido != '#'){
                    nome+=(char) caracter_lido;
                }else{
                    Log.i("nome", ""+nome);
                    listaUsuarios[posicao] = ""+nome;
                    posicao++;
                    nome="";
                }
            }
        }catch (Exception erro){
            Log.i("erro", "="+erro);
        }
        return listaUsuarios;
    }

    public int insertPeca(Peca p){

        String urlPost = "http://"+IP+"/android/inserirPeca.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        parametrosPost.add(new BasicNameValuePair("nome", p.getNome()));
        parametrosPost.add(new BasicNameValuePair("detalhes", p.getDetalhes()));

        String respostaRetornada = null;
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            resposta = resposta.replaceAll("\\s", "");
            Log.i("RESPOSTAAAAAAAAAA", resposta+"+++");
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


    public int deletePeca(String nome){
        String urlPost = "http://"+IP+"/android/deletePeca.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        String agora = pegarONome(nome);

        parametrosPost.add(new BasicNameValuePair("nome", agora));
        Log.i("NO DELETE PECA", "NOME"+agora);

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
