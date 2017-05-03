package com.example.juniorf.mylastaplicationandroid;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.conexao.ConexaoHttpClient;
import com.example.juniorf.mylastaplicationandroid.constants.Codes;
import com.example.juniorf.mylastaplicationandroid.constants.Keys;
import com.example.juniorf.mylastaplicationandroid.data.Contact;
import com.example.juniorf.mylastaplicationandroid.data.ContactDAO;
import com.example.juniorf.mylastaplicationandroid.data.ContatoDAOSERVER;
import com.example.juniorf.mylastaplicationandroid.data.DBContact;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.juniorf.mylastaplicationandroid.constants.Codes.IP;

public class NovoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextEndereco;
    DBContact dbContact;
    ContactDAO db;
    ImageView img;
    private ImageView foto;
    Bitmap bitmap;
    private SQLiteDatabase database;
    private CursorAdapter dataSource;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADRESS = IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = (ImageView) findViewById(R.id.imageView6);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                 abrirCamera();

            }
        });
        db = new ContactDAO(this);

        dbContact = DBContact.getInstance();


    }

    public void abrirCamera(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    public void girarFoto(View v){

        img.setRotation(90);
    }

    public void voltarFoto(View v){
        img.setRotation(0);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if(requestCode == 0 && resultCode == RESULT_OK){
            try{
                if(bitmap!=null){
                    bitmap.recycle();
                }
                stream=getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                img.setImageBitmap(resizeImage(this, bitmap, 700, 600));
                Uri selectedImage = data.getData();
                Log.i("URI", ""+selectedImage);
                img.setImageURI(selectedImage);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }finally {
                if(stream != null){
                    try{
                        stream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Bitmap resizeImage(Context context,Bitmap original, float newWidth, float newHeight){
        Bitmap novoBmp = null;
        int w = original.getWidth();
        int h = original.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;

        float scalaW = novoW / w;
        float scalaH = novoH / h;

        Matrix matrix = new Matrix();

        matrix.postScale(scalaW, scalaH);

        novoBmp = Bitmap.createBitmap(original, 0, 0, w, h, matrix, true);
        return novoBmp;
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

    private class UploadImage extends AsyncTask<Void, Void, Void>{

        Bitmap image;
        String name;
        public UploadImage(Bitmap image, String name){
            this.image = image;
            this.name= name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String urlPost = "http://"+IP+"/android/savePicture.php";
            String respostaRetornada = null;


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            Log.i("KAAAAAAAAAAAA", "AAAAAAAAAAA  "+ encodedImage.toString()+"  kkkk");
            Log.i("NAME", ""+name);
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));

            try{
                respostaRetornada = ConexaoHttpClient.executeHttpPost(urlPost, dataToSend);
                String resposta = respostaRetornada.toString();
                Log.i("RESPOSTAAAAAAAAAA", resposta+"+++");

            }catch (Exception e){
                e.printStackTrace();
            }
/*

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost("http://"+IP+"/android/savePicture.php");


            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();
            }
*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;

    }

    public void cadastrarC(View view){

        this.editTextNome = (EditText) findViewById(R.id.editTextNome);
        String nome = editTextNome.getText().toString();
        this.editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        String telefone = editTextTelefone.getText().toString();
        this.editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        String endereco = editTextEndereco.getText().toString();
        this.foto = (ImageView)findViewById(R.id.imageView6);
        String imagem = foto.getDrawable().toString();

        Bitmap imagew = ((BitmapDrawable) img.getDrawable()).getBitmap();
        new UploadImage(imagew, nome).execute();

        Contact contact = new Contact( nome, telefone, endereco , imagem);
        ContatoDAOSERVER contatoDAOSERVER = new ContatoDAOSERVER();
        if(contatoDAOSERVER.insertContato(contact) == 1){
            Toast.makeText(this, "SUCESSO AO INSERIR", Toast.LENGTH_SHORT);
            Intent intentSalvar = new Intent();
            intentSalvar.putExtra( Keys.RESPONSE_SAVE_NOME, nome );
            setResult( Codes.RESPONSE_ADD_OK, intentSalvar );
            finish();
        } else{
            Toast.makeText(this, "ERRO AO INSERIR", Toast.LENGTH_SHORT);
        }
    }



    public void cancelar(View view){
        Intent intentSalvar = new Intent();

        setResult( Codes.RESPONSE_ADD_CANCEL, intentSalvar );
        finish();
    }

    public  void tirarFoto(){

    }

}