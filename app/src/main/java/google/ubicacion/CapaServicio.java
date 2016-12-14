package google.ubicacion;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Vector;

import archivos.Log;

//import transporte.appbase.Archivos.Log;


/**
 * Created by soled_000 on 07/07/2015.
 */
public class CapaServicio {

    private Intent i;
    private boolean mBound= false;
    private static ServicioTracker mService = null;
    private Context context;
    private ServicioTracker.LocalBinder mBinder;

    public CapaServicio(Context context) {
        try {
            this.context = context;
        } catch (Exception e){

            Log.LOGGER.severe(e.toString());


        }
    }

    public void iniciarServicio(Context context) {
        try {


            i = new Intent(context, ServicioTracker.class);
            if (!(mBound)) {
                context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
            }
            Toast.makeText(context, "Se esta guardando la ubicacion...", Toast.LENGTH_LONG).show();
            context.startService(i);
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }


    public void detenerServicio(Context context) {
        i = new Intent(context, ServicioTracker.class);
        context.stopService(i);
        if (mBound) {
            context.unbindService(mConnection);
            mBound = false;
        }
    }

    public void desvincularBroadcast(Context context){
        try {
            if (mBound) {
                context.unbindService(mConnection);
                mBound = false;
            }
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            ServicioTracker.LocalBinder binder = (ServicioTracker.LocalBinder) service;

            mBinder = binder;
            mService = binder.getService();

            mBound = true;
            mService.registerReceiver(broadcastReceiver, new IntentFilter("receive_location"));
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Double lat = intent.getDoubleExtra("latitud", 0);
                Double longi = intent.getDoubleExtra("longitud", 0);
                char[] actividad=intent.getCharArrayExtra("actividad");
                LatLng latLng = new LatLng(lat, longi);


                Intent resultado = new Intent("resultado");
                resultado.putExtra("latLong",latLng);
                context.sendBroadcast(resultado);
                mService.unregisterReceiver(broadcastReceiver);

            }

        };
    //metodo para solicitarle al tracker su ultima ubicacion conocida. OJO! puede ser null si se solcita muy al comienzo del inicio del servicio
    public static LatLng retornarUltimaUbicacionConocida(){
        try {


            if (mService != null) {
                Location l = mService.ultimaUbicacionConocida();
                LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
                return latLng;
            }
            return null;//Devolver la ultima ubicacion conocida del archivo de preferencias
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        return null;}
    }
    public static Vector<LatLng> retornarUltimasUbicaciones(){
        try {


            if (mService != null) {
                Vector<LatLng> l = mService.ultimasUbicaciones();

                return l;
            }
            return null;//Devolver la ultima ubicacion conocida del archivo de preferencias
        }catch (Exception e)
        {
            Log.LOGGER.severe(e.toString());
            return null;}
    }




    public String retornarUltimaActividadConocida(){
        if (mService!=null) {
           return mService.ultimaActividadConocida();


        }
        return null;//Devolver la ultima actividad conocida del archivo de preferencias
    }

}



