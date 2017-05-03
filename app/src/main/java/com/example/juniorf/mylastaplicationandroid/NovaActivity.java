package com.example.juniorf.mylastaplicationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;
import com.example.juniorf.mylastaplicationandroid.data.DBManager;
import com.example.juniorf.mylastaplicationandroid.data.Peca;
import com.example.juniorf.mylastaplicationandroid.data.PecaDAOSERVER;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class NovaActivity extends AppCompatActivity {

    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_nova);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager = new DBManager(this);

    }

    public void Cancelar(View view){

        finish();
    }

    public void Cadastrar(View view){


        EditText ednome = (EditText) findViewById(R.id.editTextNome);
        EditText eddetalhes = (EditText) findViewById(R.id.editTextDetalhes);
        String nome = ednome.getText().toString();
        String detalhes = eddetalhes.getText().toString();


        PecaDAOSERVER pdao = new PecaDAOSERVER();
        if(pdao.insertPeca(new Peca(nome, detalhes)) == 1){
            Toast.makeText(this, "SUCESSO AO INSERIR", Toast.LENGTH_SHORT);
            finish();
        }else{
            Toast.makeText(this, "ERRO AO INSERIR", Toast.LENGTH_SHORT);
        }
       /* Intent intentSalvar = new Intent(this, com.example.juniorf.mylastaplicationandroid.MainActivity.class);
        startActivity(intentSalvar);
        finish();*/

    }
}
