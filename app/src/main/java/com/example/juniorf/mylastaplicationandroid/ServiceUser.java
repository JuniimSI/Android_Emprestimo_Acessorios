package com.example.juniorf.mylastaplicationandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ServiceUser extends Service {
    public List<Workers> threads = new ArrayList<Workers>();
    private NotificationManager mNM;
    Bundle b;
    Intent notificationIntent;
    Notification notf;


    public ServiceUser() {
    }

    public void onCreate(){
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Workers worker = new Workers(startId);
        worker.start();
        threads.add(worker);

        return (super.onStartCommand(intent, flags, startId));
    }

    class Workers extends Thread{
        public int count = 0;
        public int startId;
        public boolean ativo = true;

        public Workers(int startId){

            this.startId = startId;
        }

        public void run(){
            while(ativo && count<50){
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } count++;
                if(count == 50){
                    Intent i = new Intent("RECEIVER2");
                    Bundle bundle = new Bundle();
                    bundle.putString("link","O usuario X está com mais de cinco roupas Alocada" );
                    i.putExtras( bundle );

                    sendBroadcast(i);

                }


                Log.i("script", "COUNT: " + count);
            }stopSelf(startId);
        }

    }

    public void onDestroy(){
        super.onDestroy();
        for(int i = 0, tam = threads.size();i < tam; i++){
            threads.get(i).ativo = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
