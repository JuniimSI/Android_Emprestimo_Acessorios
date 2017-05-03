package com.example.juniorf.mylastaplicationandroid;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.juniorf.mylastaplicationandroid.data.LocationDAO;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by juniorf on 05/11/16.
 */

public class ServiceApp extends Service {

    public List<Worker> threads = new ArrayList<Worker>();
    private NotificationManager mNM;
    public List<Location> locationsMarkers;
    Bundle b;
    Intent notificationIntent;
    Location myLocation;
    Notification notf;

    private LocationListener listener;
    private LocationManager locationManager;
    private GoogleApiClient apiClient;
    private LocationDAO dao;

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        ServiceApp getService() {
            return ServiceApp.this;
        }
    }
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void onCreate() {
        myLocation = new Location("");
        locationsMarkers = new ArrayList<Location>();
        dao = new LocationDAO(this);
        if(locationsMarkers!=null) {
            locationsMarkers = dao.find();
            for (int i = 0; i < locationsMarkers.size(); i++) {
                Log.i("kkkkk", "" + locationsMarkers.get(i).getLatitude() + " + " + locationsMarkers.get(i).getLongitude());
            }
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent is = new Intent("location_update");
                myLocation=location;

                //locationsMarkers.add(location);
                Log.i("kkkLOCATION", "_"+myLocation.toString());

                locationsMarkers = new ArrayList<Location>();
                dao = new LocationDAO(getApplicationContext());
                if(locationsMarkers!=null) {
                    locationsMarkers = dao.find();
                    for (int i = 0; i < locationsMarkers.size(); i++) {
                        if(locationsMarkers.get(i).distanceTo(myLocation) <= 1000) {

                            Intent a = new Intent("RECEIVER");
                            Bundle bundle = new Bundle();
                            bundle.putString("link", "Você está bem perto");
                            Log.i("DISTANCE", "BEM Pertooo");
                            a.putExtras(bundle);
                            sendBroadcast(a);
                        }
                    }
                }

                is.putExtra("coordinates",location.getLatitude()+" "+location.getLongitude());


                sendBroadcast(is);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);*/
        Toast.makeText(this, LocationManager.GPS_PROVIDER, Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, listener);

        super.onCreate();
    }


    public double getDistanceTravelled(double originLatitude,
                                       double originLongitude, double destinationLatitude,
                                       double destinationLongitude) {

        // origin location
        Location originLocation = new Location("");
        originLocation.setLatitude(originLatitude);
        originLocation.setLongitude(originLongitude);

        // destination location
        Location destinationLocation = new Location("");
        destinationLocation.setLatitude(destinationLatitude);
        destinationLocation.setLongitude(destinationLongitude);

        // calculate the distance between the two locations
        float distance = originLocation.distanceTo(destinationLocation);

        // round the float and return
        // TODO might need more here.. test cases will find that out
        //return Math.round(distance);
        return distance;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        locationsMarkers = new ArrayList<Location>();
        dao = new LocationDAO(this);
        if(locationsMarkers!=null) {
            locationsMarkers = dao.find();
            for (int i = 0; i < locationsMarkers.size(); i++) {
                Log.i("kkkkk", "" + locationsMarkers.get(i).getLatitude() + " + " + locationsMarkers.get(i).getLongitude());
                Log.i("DISTANCIA my", locationsMarkers.get(i).distanceTo(myLocation)+"");
                Log.i("CakcularDistancia", ""+getDistanceTravelled(myLocation.getLatitude(), myLocation.getLongitude(), locationsMarkers.get(i).getLatitude(), locationsMarkers.get(i).getLongitude() ));
            }
        }

        String n = "";
        Worker worker = new Worker(startId, n);
        worker.start();
        threads.add(worker);

        return (super.onStartCommand(intent, flags, startId));
    }

    class Worker extends Thread {
        public int count = 0;
        public int startId;
        public boolean ativo = true;
        public String ns="";


        public Worker(int startId, String n) {
            this.ns = n;
            this.startId = startId;
        }

        public void run() {

            while (ativo && count < 1000) {


                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                count++;

                Log.i("script", "COUNT: " + count);
            }
            stopSelf(startId);
        }
    }

    public void onDestroy() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(listener);
        }
        super.onDestroy();
        for(int i = 0, tam = threads.size();i < tam; i++){
            threads.get(i).ativo = false;
        }
    }

    public void showNotification(String title, String content, int id, int tipoNotificacao) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        if (tipoNotificacao == 1) {
            mBuilder.setSmallIcon(R.drawable.saru);

            Intent resultIntent = new Intent(this, MainActivity.class);

            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
        }

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        long[] vibrate = {0,100,200,300};

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setVibrate(vibrate);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
