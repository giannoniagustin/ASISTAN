package archivos;

import android.text.format.DateFormat;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import baseDatos.BdHelper;
import core.Evento;
import global.Global;
import util.Fecha;

/**
 * Created by Gianno on 08/12/2016.
 */

public class ManangerArchivos
{
    Archivo archivo;
    public     final static String TARJETA_MEMORIA=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    public     final static String RAIZ_APP=TARJETA_MEMORIA+"/"+ Global.NOMBRE_APP;
    public     final static String DATA_USER=RAIZ_APP+"/DataUser";
    public     final static String PATH_LOG_USER_TXT=RAIZ_APP+"/Log";
    public     final static String NOMBRE_LOG_USER_TXT="Logging.txt";
    public     final static String NOMBRE_LOG_USER_HTML="Logging.txt";
    public     final static String LOG_USER_HTML=RAIZ_APP+"/Log"+"/Logging.html";
    public     final static String BD=RAIZ_APP+"/BD/"+ BdHelper.DB_NAME;
    public     final static String PATH_ARCH_TRACK =RAIZ_APP+"/ArchTrack";
    public     final static String NOMBRE_ARCH_TRACK="AppCortes_track_historia_";
    public final static String EXTENSION_ZIP=".zip";
    public final static String EXTENSION_TXT =".txt" ;
    public final static String SALTO_LINEA="\r\n";

    public File crearArchivo(String path)
    {
        return new File(path);
    }
    public void listarArchivosCarpeta(String paht)
    {
        File dir= new File(paht);

        dir.list();

    }

    public  Archivo crearArchivo(String path, String nombre)
    {
            return new Archivo(path,nombre,this);
    }
    public void crearArchivoTrack()
    {
        try
        {
            Date fechaHoy = Calendar.getInstance().getTime();
            String nombreArchTrack = NOMBRE_ARCH_TRACK + DateFormat.format("MM_dd_yyyy", fechaHoy.getTime());
            archivo=this.crearArchivo(PATH_ARCH_TRACK,nombreArchTrack);
        }
        catch (Exception e)
        {
            Log.LOGGER.severe(e.toString());
        }
    }
    public  void grabarArchivoTrack(Evento evento)
    {
     int resultado=  Fecha.FORMATO_FECHA.format(evento.getFecha()).compareTo(Fecha.FORMATO_FECHA.format(Calendar.getInstance().getTime()));
        if (resultado==0)
        {
            archivo.grabar(evento.toString() + ManangerArchivos.SALTO_LINEA);
        }
        else
        {
            archivo.comprimir();
            archivo.borrarTxt();
            this.crearArchivoTrack();
            this.grabarArchivoTrack(evento);
        }
    }

}
