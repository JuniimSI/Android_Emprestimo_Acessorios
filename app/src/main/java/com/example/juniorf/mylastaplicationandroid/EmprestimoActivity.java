package com.example.juniorf.mylastaplicationandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;
import com.example.juniorf.mylastaplicationandroid.data.ContatoDAOSERVER;
import com.example.juniorf.mylastaplicationandroid.data.DBManager;
import com.example.juniorf.mylastaplicationandroid.data.Emprestimo;
import com.example.juniorf.mylastaplicationandroid.data.PecaDAOSERVER;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class EmprestimoActivity extends AppCompatActivity {

    private Spinner spBusinessType;
    private Spinner spBusinessType2;
    private ArrayAdapter<String> adapterBusinessType;
    private DBManager dbManager;
    String[] businessType;
    String[] businessType2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprestimo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ContatoDAOSERVER contatoDAOSERVER = new ContatoDAOSERVER();
        businessType = contatoDAOSERVER.getAllContats();

        if(businessType!=null){
            spBusinessType = (Spinner) findViewById(R.id.spinnerNomes);
            adapterBusinessType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, businessType);

            adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBusinessType.setAdapter(adapterBusinessType);
        }else{

            Toast.makeText(this, "Sem conexão com servidor", Toast.LENGTH_SHORT).show();
        }





        PecaDAOSERVER pecaDAOSERVER =new PecaDAOSERVER();
        businessType2 = pecaDAOSERVER.getAllPeca();
        if(businessType2!=null) {

            spBusinessType2 = (Spinner) findViewById(R.id.spinnerPecas);
            adapterBusinessType = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, businessType2);

            adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBusinessType2.setAdapter(adapterBusinessType);
        }else{

            Toast.makeText(this, "Sem conexão com servidor", Toast.LENGTH_SHORT).show();
        }



    }




    public void insertEmprestimo(View view){
        String a = String.valueOf(spBusinessType.getSelectedItem());
        String b = String.valueOf(spBusinessType2.getSelectedItem());
        Toast.makeText(this, a+"   "+b, Toast.LENGTH_SHORT).show();
        ContatoDAOSERVER c = new ContatoDAOSERVER();
        java.util.Date data = new java.util.Date();
        java.sql.Date d = new java.sql.Date(data.getTime());

        Emprestimo e = new Emprestimo(a, b, d);
        if(c.insertEmprestimo(e)==1){
            Toast.makeText(this, "SUCESSO AO INSERIR", Toast.LENGTH_SHORT);
            Intent intentSalvar = new Intent();
            intentSalvar.putExtra( Keys.RESPONSE_SAVE_NOME, a+" - "+b );
            setResult( Codes.RESPONSE_ADD_OK, intentSalvar );
            finish();
        }else{
            Toast.makeText(this, "FALHA AO INSERIR", Toast.LENGTH_SHORT).show();
        }


    }

    public void Cancelar(View view){
        finish();
    }

}
