package google.ubicacion;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Vector;

import archivos.ManangerArchivos;
import core.Actividad;
import core.Evento;
import giannno.asistan.R;
import google.actividad.ActivityRecognitionReceiverIntentService;


public class ServicioTracker extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //ApiClient
    private GoogleApiClient mGoogleApiClient;
    //Ubicacion
    private LocationRequest mLocationRequest;
    private Location ubicacionActual = null;
    private Vector<LatLng> ultimasUbicaciones;
    //Archivo

    ManangerArchivos manangerArchivos;
    //Actividad
    private int tipoActividad = DetectedActivity.UNKNOWN;
    private int probabilidad = 0;
    private String nombreActividad;


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    private PendingIntent mActivityRecognitionPendingIntent;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try
            {
            final int tipoActividad = intent.getIntExtra("activity_type", 0);
            int probabilidadActividad = intent.getIntExtra("confidence", 0);

            ServicioTracker.this.tipoActividad = tipoActividad;
            probabilidad = probabilidadActividad;
            perfilPedidoUbicacion(tipoActividad);
                suscribirseLocation(mLocationRequest);
            } catch (Exception e) {
                archivos.Log.LOGGER.severe(e.toString());
            }
        }
    };

    // Cambia el perfil de pedido de ubicacion dependiendo la activida que esta realizando
    private void perfilPedidoUbicacion(int tipoActividad)

    {
        try {

            switch (tipoActividad) {

                case DetectedActivity.IN_VEHICLE: {//valor 0
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(5 * 1000)        //5 segundos, en milisegundos
                            .setFastestInterval(5 * 1000)
                            .setSmallestDisplacement(10);
                    nombreActividad = getString(R.string.actividad_auto);

                    break;
                }
                case DetectedActivity.ON_BICYCLE: { //valor 1
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10 * 1000)        //10 segundos, en milisegundos
                            .setFastestInterval(10 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_bicileta);
                    break;

                }
                case DetectedActivity.RUNNING: { //valor 8
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(15 * 1000)        //15 segundos, en milisegundos
                            .setFastestInterval(15 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_corriendo);
                    break;

                }
                case DetectedActivity.ON_FOOT: { //valor 2
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setInterval(15 * 1000)        //15 segundos, en milisegundos
                            .setFastestInterval(15 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_caminando_rapido);
                    break;


                }
                case DetectedActivity.WALKING: { //valor 7
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setInterval(15 * 1000)        //15 segundos, en milisegundos
                            .setFastestInterval(15 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_caminando);
                    break;
                }

                case DetectedActivity.STILL: { //3
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER)
                            .setInterval(3000 * 1000)        //3000 segundos, en milisegundos
                            .setFastestInterval(3000 * 1000)
                      /*      .setSmallestDisplacement(500)*/;
                    nombreActividad = getString(R.string.actividad_detenido);
                    break;
                }
                case DetectedActivity.TILTING: { //valor 5
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setInterval(15 * 1000)        //15 segundos, en milisegundos
                            .setFastestInterval(15 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_tomando_dispositivo);
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setInterval(30 * 1000)        //30 segundos, en milisegundos
                            .setFastestInterval(30 * 1000)
                            .setSmallestDisplacement(25);
                    nombreActividad = getString(R.string.actividad_desconocida);
                    break;

                }
            }
            suscribirseLocation(mLocationRequest);
        }catch (Exception e)
        {
            archivos.Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void onCreate() {

        try {
            manangerArchivos= new ManangerArchivos();
            manangerArchivos.crearArchivoTrack();
            manangerArchivos.crearEstructura();
            nombreActividad= getString(R.string.actividad_desconocida);
            ultimasUbicaciones = new Vector<>();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(ActivityRecognition.API)
                    //  .enableAutoManage((FragmentActivity) Map, 0, this)
                    .build();

            mLocationRequest= new LocationRequest();
            //Location generico para los casos de actividad desconocida

            perfilPedidoUbicacion(DetectedActivity.UNKNOWN);
            mGoogleApiClient.connect();
            //   track = new TrackUbicacionHistoria(this);
            registerReceiver(broadcastReceiver, new IntentFilter("receive_recognition"));

        } catch (Exception e) {
            archivos.Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Toast.makeText(this, "Se detuvo correctamente el servicio", Toast.LENGTH_SHORT).show();
    }

    public void enviarCambioUbicacion(Location location) {
        try {


            Intent notifyIntent = new Intent("receive_location");
            notifyIntent.putExtra("latitud", location.getLatitude());
            notifyIntent.putExtra("longitud", location.getLongitude());
            notifyIntent.putExtra("actividad", nombreActividad);
            Toast.makeText(this,location.getLatitude()+","+location.getLongitude()+"Act"+nombreActividad, Toast.LENGTH_SHORT).show();
            sendBroadcast(notifyIntent);
        } catch (Exception e) {
            //     transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            ubicacionActual = location;
            ultimasUbicaciones.add(new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude()));
            enviarCambioUbicacion(location);

            Date fecha = new Date();
            Actividad actividad=new Actividad(nombreActividad,probabilidad);
            Evento evento=new Evento(fecha,actividad,new LatLng(ubicacionActual.getLatitude(),ubicacionActual.getLongitude()));

            manangerArchivos.grabarArchivoTrack(evento);
     /*       track.escribir(" Tipo: " + tipoActividad + " " + "Actividad: " + nombreActividad + " Prob: " + probabilidad + " " + Double.toString(ubicacionActual.getLatitude()) +
                    ":" +
                    Double.toString(ubicacionActual.getLongitude()) + ":" + DateFormat.format("MM:dd:yyyy:HH:mm:ss", fecha.getTime())
            );*/
        } catch (Exception e) {
                archivos.Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            ubicacionActual = location;
            Intent intent = new Intent(this, ActivityRecognitionReceiverIntentService.class);
            mActivityRecognitionPendingIntent = PendingIntent.getService(this, 10000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, 10000, mActivityRecognitionPendingIntent);


        } catch (Exception e) {

             archivos.Log.LOGGER.severe(e.toString());
        }

    }

    public void suscribirseLocation(LocationRequest mLocationRequest) {
        try {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch (Exception e){
        //    transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Suspendido", "ConnectionSupend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Suspendido","ConnectionSupend");
    }

    // Binder
    private final IBinder mBinder = new LocalBinder();



    public class LocalBinder extends Binder {
        ServicioTracker getService() {
            try {


                // Returna a instancia do SeuService
                return ServicioTracker.this;
            }catch (Exception e){
           //     transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
                return  null;}
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //metodo para acceder desde la CapaServicio que retorna la ultima location conocida
    public Location ultimaUbicacionConocida(){
        return ubicacionActual;
    }
    public Vector<LatLng> ultimasUbicaciones(){
        return ultimasUbicaciones;
    }
    public String ultimaActividadConocida() {
        return nombreActividad;
    }





}
