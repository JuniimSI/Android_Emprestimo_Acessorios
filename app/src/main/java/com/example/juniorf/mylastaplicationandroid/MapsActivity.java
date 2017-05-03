package com.example.juniorf.mylastaplicationandroid;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorf.mylastaplicationandroid.data.LocationDAO;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private TextView texView;
    private GoogleApiClient apiClient;
    private LocationManager service;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver ==null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    texView.setText("\n"+intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.i("TAAAAAAAAAAAAAG", ""+"ANTESDO SET MARKERS NO ON CREATE");
        setMarkers();
        Log.i("TAAAAAAAAAAAAAG", ""+"DEPOIS DO SET MARKERS NO ON CREATE");
        texView  = (TextView) findViewById(R.id.myLocation);
        initMap();
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        apiClient = builder.build();
       // getLocation();
    }

    public void setMarkers(){
        Log.i("TAAAAAAAAAAAAAG", ""+"INICIO DO SETMARKS");
          List<Location>  locationsMarkers = new ArrayList<Location>();
          LocationDAO locationDAO = new LocationDAO(getApplicationContext());

        locationsMarkers = locationDAO.find();
        Log.i("LOCATIONMARKERS", locationsMarkers+"");
        for(int i = 0; i < locationsMarkers.size(); i++){
            //MapsActivity.this.setMarker("Devolução", locationsMarkers.get(i).getLatitude(), locationsMarkers.get(i).getLongitude());
            Log.i("SET MARKERS", "MAPS, NO FOR" + locationsMarkers.get(i).getLatitude() + " + " + locationsMarkers.get(i).getLongitude());

        }

    }

    protected void onStart() {
        super.onStart();
        Log.i("TAAAAAAAAAAAAAG", "SETANDO MARKERS NO START");
        setMarkers();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        apiClient.disconnect();
        super.onStop();
    }

    public void doConnect(View view) {
        apiClient.connect();
    }

    public void doDisconnect(View view) {
        apiClient.disconnect();
    }

    public void getLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        String text = "Location = <" + location.getLatitude() + "," + location.getLongitude() + ">";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void initMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        if(mMap != null){

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    List<Location>  locationsMarkers = new ArrayList<Location>();
                    LocationDAO locationDAO = new LocationDAO(getApplicationContext());

                    locationsMarkers = locationDAO.find();
                    Log.i("LOCATIONMARKERS", locationsMarkers+"");
                    for(int i = 0; i < locationsMarkers.size(); i++){
                        MapsActivity.this.setMarker("Devolução", locationsMarkers.get(i).getLatitude(), locationsMarkers.get(i).getLongitude());
                        Log.i("SET MARKERS", "MAPS, NO FOR" + locationsMarkers.get(i).getLatitude() + " + " + locationsMarkers.get(i).getLongitude());

                    }
                    getLocation();
                }
            });


            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(final LatLng latLng) {
                    final String[] n = {""};

                    LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
                    View pront = layoutInflater.inflate(R.layout.ip, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
                    alertDialogBuilder.setView(pront);

                    final EditText editText = (EditText) pront.findViewById(R.id.ip);
                    alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            n[0] = editText.getText().toString();

                            String nzero = n[0];
                            Location location = new Location("s");
                            LocationDAO locationDAO = new LocationDAO(getApplicationContext());
                            location.setLatitude(latLng.latitude);
                            location.setLongitude(latLng.longitude);
                            locationDAO.insert(location);




                            MapsActivity.this.setMarker(nzero, latLng.latitude ,latLng.longitude);


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            return;
                        }
                    });

                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                                    }
            });


            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc = new Geocoder(MapsActivity.this);
                    LatLng ll = marker.getPosition();
                    List<Address> list = null;
                    try {
                       list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();

                }
            });

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView txLocality = (TextView) view.findViewById(R.id.tvLocality);
                    TextView txLat = (TextView) view.findViewById(R.id.tvLat);
                    TextView txLng = (TextView) view.findViewById(R.id.tvLng);
                    TextView txSnippet = (TextView) view.findViewById(R.id.tvSnippet);

                    LatLng ll = marker.getPosition();
                    txLocality.setText(marker.getTitle());
                    txLat.setText("Latitude : "+ ll.latitude);
                    txLng.setText("Longitude : "+ ll.longitude);
                    txSnippet.setText(marker.getSnippet());
                    return view;
                }
            });
        }

        /*apiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        apiClient.connect();*/
        // Add a marker in Sydney and move the camera
        goToLocationZoom(-5.19812, -39.2962, 10);

       /* mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    public String showInput(){
        final String[] n = {""};

        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
        View pront = layoutInflater.inflate(R.layout.ip, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
        alertDialogBuilder.setView(pront);

        final EditText editText = (EditText) pront.findViewById(R.id.ip);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                n[0] = editText.getText().toString();
                Log.i("FLAH_TAG", "++"+n[0]);
                Log.i("FLAH_TAG", "++"+editText.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                return;
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return n[0].toString();
    }

    public void goToLocation(double lat, double lng){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);

    }

    public void goToLocationZoom(double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }



    Circle circle;



    public void setMarker(String locality, double lat, double lng){
        if(locality == null || locality == "") return;
        MarkerOptions options = new MarkerOptions().title(locality).draggable(false)
                .snippet("Seu lugar de devolução")
                .position(new LatLng(lat, lng));
        mMap.addMarker(options);


        circle = drawCircle(new LatLng(lat, lng));
    }




    private Circle drawCircle(LatLng latLng) {
        CircleOptions options = new CircleOptions().center(latLng)
                .radius(100).fillColor(0x33FF0000)
                .strokeColor(Color.BLUE)
                .strokeWidth(3);
        return mMap.addCircle(options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        /*mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, mLocationRequest, this);*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null){

            Toast.makeText(this, "Cant get current Location", Toast.LENGTH_SHORT).show();
        }else{
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 18);
            mMap.animateCamera(update);
        }
    }

    /*public void geoLocate(View view) throws IOException {

        EditText et = (EditText) findViewById(R.id.editTextLocal);
        String location = et.getText().toString();
        if(location == null)
            return;

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat, lng, 25);

        setMarker(locality, lat, lng);

    }*/

}
