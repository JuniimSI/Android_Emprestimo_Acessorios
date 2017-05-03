package com.example.juniorf.mylastaplicationandroid;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.MapsActivity;
import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;
import com.example.juniorf.mylastaplicationandroid.data.Contact;
import com.example.juniorf.mylastaplicationandroid.data.ContactDAO;
import com.example.juniorf.mylastaplicationandroid.data.ContatoDAOSERVER;
import com.example.juniorf.mylastaplicationandroid.data.DBContact;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
/*import com.facebook.AccessToken;
import com.facebook.login.LoginManager;*/
/*
import com.example.juniorf.mylastaplicationandroid.LoginsActivity;
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private CursorAdapter dataSource;
    private LoginManager loginManager;
    private CallbackManager callbackManager;


    //Atenção: é necessário inserir o PK (chave primária _id) como último campo
    private static final String campos[] = {"nome", "endereco", "telefone", "_id"};

    ContactDAO helper;
    DBContact dbContact;
    ListView listView;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    AccessToken accessToken;
    String [] listaUsuarios;
    int posicao = 0;
    public AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        helper = new ContactDAO(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*if(AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }*/
        //startService();


        listView = ( ListView )findViewById( R.id.listView );

        ContatoDAOSERVER c = new ContatoDAOSERVER();
        listaUsuarios = c.getAllContats();

        if(listaUsuarios!=null){
            final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaUsuarios);
            listView.setAdapter(adapter);
        }else{

            String[] listaUsuarioss = {"SEM CONEXÂO"};
            final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaUsuarioss);
            listView.setAdapter(adapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nome = ( String )MainActivity.this.listView.getItemAtPosition( position );
                Intent intent = new Intent( MainActivity.this, EditActivity.class) ;

                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                intent.putExtras( bundle );
                startActivity( intent);
            }
        });

        String newString;
        String imageUrl="";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= "extra null";
            } else {
                newString= extras.getString("name")+ " " + extras.getString("surname");
                imageUrl = extras.getString("imageUrl");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("name");
        }

        TextView g = (TextView) findViewById(R.id.userName);
        g.setText(newString);
        new DownloadImage((ImageView)findViewById(R.id.userProfilePicture)).execute(imageUrl);
    }

    private void goLoginScreen() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void startService2(){
        Intent in = new Intent("SERVICO2");
        startService(in);
    }

    private void startService(View view){
        Intent in = new Intent(getApplicationContext(), ServiceApp.class);
        startService(in);

    }
    private void startService(){
        Intent in = new Intent(getApplicationContext(), ServiceApp.class);
        startService(in);

    }

    private void stopService(View view){
        Intent in = new Intent("SERVICO");
        stopService(in);
    }

    private void stopService2(View view){
        Intent in = new Intent("SERVICO2");
        stopService(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void CarregarMapa(View view){
        startService(view);
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LoginManager.getInstance().logOut();
            goLoginScreen();
            return true;
        }
        if(id==R.id.action_new){
            Intent intent = new Intent(this, NovoActivity.class);
            startActivityForResult( intent, Codes.REQUEST_ADD );
            return true;
        }
        if(id==R.id.emprestimo){
            Intent intent = new Intent(this, EmprestimoActivity.class);
            startActivity( intent);
            return true;
        }
        if(id==R.id.list_pecas){
            Intent intent = new Intent(this, ListaActivity.class);
            startActivity( intent);
            return true;
        }
        if(id==R.id.newpeca){
            Intent intent = new Intent(this, NovaActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_servidor){
            Intent intent = new Intent(this, ServidorActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {

        super.onStart();
        AppEventsLogger.activateApp(this);

      /*  ArrayList<Contact> list = DBContact.getContactList();*/
        //refreshContactList(  );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode,data);

        //Se a tela chamada foi a de adicionar
        if( requestCode == Codes.REQUEST_ADD ){

            //Se o resultado da tela de adicionar foi ok
            if( resultCode == Codes.RESPONSE_ADD_OK ){

                String nome = data.getExtras().getString( Keys.RESPONSE_SAVE_NOME );

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, nome, duration);
                toast.show();


            } else if( resultCode == Codes.RESPONSE_ADD_CANCEL ){
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Operação cancelada", duration);
                toast.show();
            }
            //Implementar a volta da tela de edição
        } else if( requestCode == Codes.REQUEST_EDIT ){


        }
    }
    private String[] refreshContactList( ){

        String [] listaUsuario = null;


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

    protected void onResume() {
        super.onResume();
        //Facebook login
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }


}
