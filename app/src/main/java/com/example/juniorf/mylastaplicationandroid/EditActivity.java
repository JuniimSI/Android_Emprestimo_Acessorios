package com.example.juniorf.mylastaplicationandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;
import com.example.juniorf.mylastaplicationandroid.data.Contact;
import com.example.juniorf.mylastaplicationandroid.data.ContatoDAOSERVER;
import com.example.juniorf.mylastaplicationandroid.data.DBContact;
import com.example.juniorf.mylastaplicationandroid.data.Peca;
import com.example.juniorf.mylastaplicationandroid.data.PecaDAOSERVER;
import com.facebook.login.LoginManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class EditActivity extends AppCompatActivity {

    List<String> lstGrupos = null;
    List<Peca> lstBlusas = null;
    List<Peca> lstCalcas = null;
    List<Peca> lstAcessorios = null;
     String[] listaUsuarios;
    public String no = "";
    int posicao = 0;

    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "nome";
    private static final String TAG_ADD ="endereco";
    private static final String TAG_DATA ="data";
    private static final String TAG_TELEFONE ="telefone";


    JSONArray pecas = null;
    private AlertDialog alerta;

    ArrayList<HashMap<String, String>> pecasList;

    ListView elvCompra;
    private String[] emprestimos;
    private ProgressDialog load;
    public ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String nome = bundle.getString("nome");
        no = nome;TextView txNome = (TextView) findViewById(R.id.editNameTextView);
        txNome.setText(no);

        img = (ImageView) findViewById(R.id.imageButton3);
        new DownloadImage(no).execute();

        pecasList = new ArrayList<HashMap<String,String>>();
        ContatoDAOSERVER c = new ContatoDAOSERVER();
        emprestimos = c.getEmprestimos(this.no);

        //getData();
        setTexts();
        elvCompra = (ListView) findViewById(R.id.elvCompra);

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, emprestimos);
        elvCompra.setAdapter(adapter);

        elvCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String  itemValue = (String) elvCompra.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), itemValue , Toast.LENGTH_LONG).show();
                //Cria o gerador do AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                //define o titulo
                builder.setTitle("Confirmação");
                //define a mensagem
                builder.setMessage("Deseja realmente excluir esse Emprestimo?");
                //define um botão como positivo
                builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Toast.makeText(EditActivity.this, "APAGAR=" + arg1, Toast.LENGTH_SHORT).show();
                       ContatoDAOSERVER c = new ContatoDAOSERVER();

                        if(c.deleteEmprestimo(nome, itemValue) == 1){
                            Toast.makeText(EditActivity.this, "Emprestimo DELETADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(EditActivity.this, "Emprestimo NÃO DELETADO!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(EditActivity.this, "Cancelado=" + arg1, Toast.LENGTH_SHORT).show();
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();

            }
        });

    }



    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;

    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        String name;
        public DownloadImage(String name){
            this.name = name;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            String url="http://"+IP+"/android/pictures/"+name+".JPG";
            try{
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000*30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);

            }catch (Exception a ){
                a.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                img.setImageBitmap(bitmap);
            }
        }
    }

    private void setTexts() {
        String urlPost = "http://"+IP+"/android/buscarContatoPorNome.php";
        ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
        parametrosPost.add(new BasicNameValuePair("nome", this.no));
        int c = 1;

        String respostaRetornada = null;
        try{
            respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, parametrosPost);
            String resposta = respostaRetornada.toString();
            Log.i("RESPOSTAAAAAAAAAAAAAA", "+++"+resposta);

            char separador ='#';
            int contaUsuarios=0;
            for(int i = 0; i<resposta.length(); i++){
                if(separador == resposta.charAt(i))
                    contaUsuarios++;
            }

            listaUsuarios  = new String[contaUsuarios];

            char caracter_lido = resposta.charAt(0);
            String nomes="";
            for(int i=0; caracter_lido != '^';i++){
                caracter_lido = resposta.charAt(i);
                ///Log.i("chars dos peças", "+"+caracter_lido);
                if(caracter_lido != '#'){
                    nomes+=(char) caracter_lido;
                }else{
                    Log.i("nome", " "+nomes);
                    listaUsuarios[posicao] = " "+nomes;

                    if(c==2){
                        TextView txTelefone = (TextView) findViewById(R.id.editTelefoneTextView);
                        txTelefone.setText(nomes);
                    }
                    if(c==3){
                        TextView txTelefone = (TextView) findViewById(R.id.editEnderecoTextView);
                        txTelefone.setText(nomes);
                    }
                    posicao++;
                    nomes="";
                    c++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void cancelar( View view ){
        Intent intentCancelar = new Intent();

        setResult( Codes.RESPONSE_ADD_CANCEL, intentCancelar);
        finish();
    }

    public void devolver(View view){
        TextView tx = (TextView) findViewById(R.id.tvItem);
        String t = tx.getText().toString();

        for(Peca p: lstAcessorios){
            if(p.getNome() == t)
                finish();
        }
        for(Peca p: lstCalcas){
            if(p.getNome() == t)
                finish();
        }
        for(Peca p: lstBlusas){
            if(p.getNome() == t)
                finish();
        }

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, "Peça Devolvida", duration);
        toast.show();
    }
    public void m(View view){
        Toast.makeText(this, this.no, Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_del) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
            //define o titulo
            builder.setTitle("Confirmação de delete");
            //define a mensagem
            builder.setMessage("Deseja realmente deletar esse contato?");
            //define um botão como positivo
            builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(EditActivity.this, "APAGAR=" + arg1, Toast.LENGTH_SHORT).show();
                    ContatoDAOSERVER c = new ContatoDAOSERVER();

                    if(c.deleteContato(no) == "1"){
                        //Intent n = new Intent(EditActivity.this, MainActivity.class);

                        Toast.makeText(EditActivity.this, "CONTATO DELETADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                        finish();
                        //startActivity(n);
                    }
                    else{
                        Toast.makeText(EditActivity.this, "CONTATO NÃO DELETADO!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(EditActivity.this, "Cancelado=" + arg1, Toast.LENGTH_SHORT).show();
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
            return true;

        }


        return super.onOptionsItemSelected(item);
    }



}
