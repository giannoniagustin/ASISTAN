package core;

import android.app.Application;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import interfaz.ManejadorInterfaz;
import server.ManejadorServidor;

/*import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Util.Iconos;*/

/**
 * Created by Agustín on 09/07/2015.
 */
public class Global extends Application {

    private static Global instance;
/*   private Iconos iconos = new Iconos();
    private Notificaciones notificaciones = new Notificaciones();

    private Validador validador = new Validador();*/
    private ManejadorServidor manejadorServidor;
    private ManejadorInterfaz manejadorInterfaz;

    //private ArchivoCache archivoCache = new ArchivoCache();

    private HttpClient c;


    public Global() {
       this.manejadorServidor = new ManejadorServidor();
        this.manejadorInterfaz = new ManejadorInterfaz();

        this.c = new DefaultHttpClient();
        manejadorServidor.getServer().setClient(c);

    }

   /* public ArchivoCache getArchivoCache() {
        return archivoCache;
    }

    public void setArchivoCache(ArchivoCache archivoCache) {
        this.archivoCache = archivoCache;
    }*/

    public static Global instance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = new Global();


        try {
            archivos.Log.setup();
        } catch (Exception e){
            archivos.Log.LOGGER.severe(e.toString());}

        //      mGoogleApiClient = new ApiClient(this);
    }


  //  public Validador getValidador(){return validador;}

    public ManejadorInterfaz getManejadorInterfaz(){
        return manejadorInterfaz;
    }
    public ManejadorServidor getManejadorServidor(){
        return manejadorServidor;
    }

    public HttpClient getClient (){
        return c;
    }

}
