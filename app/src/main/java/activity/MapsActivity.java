package activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import archivos.Log;
import giannno.asistan.R;
import google.ubicacion.CapaServicio;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CapaServicio capaServicio = new CapaServicio(this);
    private BroadcastReceiver broadcastReceiver;
    private LatLng latLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            capaServicio.iniciarServicio(this);
            suscribirseUbicacion();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



        } catch (Exception e)
        {
            Log.LOGGER.severe(e.toString());
        }
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
        checkPermissionAndroid();
        // Add a marker in Sydney and move the camera

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void checkPermissionAndroid(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED  &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }




    }
    //Metodo para suscribirse al pedido de ubicacion que se le solicita al servicio. Cuando se consiga
    //un resultado, el broadcast lo recibira
    public void suscribirseUbicacion() {
        try {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    latLng = intent.getParcelableExtra("latLong");
                    setUpMap();
                    desuscribirseUbicacion(broadcastReceiver);
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("resultado"));
        }catch (Exception e)
        {Log.LOGGER.severe(e.toString());}
    }
    //Metodo para avisarle a la capa del servicio, que no queremos recibir mas la informacion de la ubicacion.
    public void desuscribirseUbicacion(BroadcastReceiver b){
        try {
            unregisterReceiver(b);
            capaServicio.desvincularBroadcast(this);
        }catch (Exception e)
        {Log.LOGGER.severe(e.toString());}
    }
    private void setUpMap()
    {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Usted está aquí"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        /*try {


            if (latLng != null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();

                global.getManejadorInterfaz().setContexto(this);
                global.getManejadorInterfaz().setMmap(mMap);
                global.getManejadorInterfaz().DibujarMarcador(new Marcador(latLng,"Usted esta aqui!","IC_MI_UBICACION"));


            }
        }catch (Exception e){Log.LOGGER.severe(e.toString());}*/
    }
}
