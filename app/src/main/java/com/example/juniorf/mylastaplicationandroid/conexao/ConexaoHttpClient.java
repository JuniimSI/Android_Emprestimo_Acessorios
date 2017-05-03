package com.example.juniorf.mylastaplicationandroid.conexao;

/**
 * Created by juniorf on 14/11/16.
 */

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.*;
import java.util.ArrayList;
import java.net.URI;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;



public class ConexaoHttpClient {
    public static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient httpClient;

    private static HttpClient getHttpClient(){
        if(httpClient == null){
            httpClient = new DefaultHttpClient();
            final HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
        }return httpClient;
    }


    public static String executeHttpPost(String url, ArrayList<NameValuePair> parametrosPost) throws  Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BufferedReader bufferedReader = null;

        try{
            HttpClient Client = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parametrosPost);
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = Client.execute(httpPost);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LS = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line+LS);
            }
            bufferedReader.close();
            String resultado = stringBuffer.toString();
            return resultado;

        }finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }

    public static String executeHttpGet(String url) throws  Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BufferedReader bufferedReader = null;

        try{
            HttpClient Client = getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = Client.execute(httpGet);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LS = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line+LS);
            }
            bufferedReader.close();
            String resultado = stringBuffer.toString();
            return resultado;

        }finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }

}
