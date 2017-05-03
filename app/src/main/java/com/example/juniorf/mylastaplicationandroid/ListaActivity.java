package com.example.juniorf.mylastaplicationandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.data.ContatoDAOSERVER;
import com.example.juniorf.mylastaplicationandroid.data.Peca;
import com.example.juniorf.mylastaplicationandroid.data.PecaDAOSERVER;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class ListaActivity extends AppCompatActivity {

    List<String> lstGrupos = null;
    List<Peca> lstBlusas = null;
    List<Peca> lstCalcas = null;
    List<Peca> lstAcessorios = null;
    ListView listView;
    private ArrayAdapter<String> adapter;
    private ListView listV;
    TextView estado, cidade, noItem;
    private AlertDialog alerta;

    int posicao = 0;
    String [] listaUsuarios;

    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD ="detalhes";

    JSONArray pecas = null;

    ArrayList<HashMap<String, String>> pecasList;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.elvpecas);

       /* PecaDAOSERVER p = new PecaDAOSERVER();
        listaUsuarios = p.getAllPeca();
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listaUsuarios);
        listView.setAdapter(adapter);
                */

        pecasList = new ArrayList<HashMap<String, String>>();
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String nome = (String) listView.getItemAtPosition(position).toString();

                Log.i("KKKKKKK", "+" + nome);

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
                //define o titulo
                builder.setTitle("Confirmação de delete");
                //define a mensagem
                builder.setMessage("Deseja realmente deletar essa Peça?");
                //define um botão como positivo
                builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(ListaActivity.this, "APAGAR=" + arg1, Toast.LENGTH_SHORT).show();
                        PecaDAOSERVER c = new PecaDAOSERVER();

                        if (c.deletePeca(nome) == 1) {
/**/                        Toast.makeText(ListaActivity.this, "PECA DELETADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ListaActivity.this, "PECA NÃO DELETADA!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(ListaActivity.this, "Cancelado=" + arg1, Toast.LENGTH_SHORT).show();
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
            }
        });

    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://"+IP+"/android/listarPecas.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            pecas = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<pecas.length();i++){
                JSONObject c = pecas.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);

                pecasList.add(persons);
            }

            if(pecasList!=null) {
                ListAdapter adapter = new SimpleAdapter(
                        ListaActivity.this, pecasList, R.layout.list_item,
                        new String[]{ TAG_NAME, TAG_ADD},
                        new int[]{ R.id.name, R.id.address}
                );

                listView.setAdapter(adapter);
            }else{

                String[] listaUsuarioss = {"SEM CONEXÂO"};
                final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaUsuarioss);
                listView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
