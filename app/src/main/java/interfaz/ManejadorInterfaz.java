package interfaz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import java.util.ArrayList;
import archivos.Log;
import sumo.Cole;


/**
 * Created by Agustín on 06/10/2015.
 */
public class ManejadorInterfaz {
    private Context context;
    private ArrayList<Dibujable> elementosDibujar;
    private GoogleMap mmap;
    private Dibujador dibujador;
    private ProgressDialog dialog;
 //   private Hashtable<Integer, Integer> colores = new Hashtable<>();

    public ManejadorInterfaz() {
        elementosDibujar = new ArrayList();
        dibujador = new Dibujador();
 //       setColores();
    }

    public void setContexto(Context context) {
        this.context = context;
    }

    public void setMmap(GoogleMap mmap) {
        this.mmap = mmap;
        dibujador.setmMap(mmap);
    }

    public ArrayList getElementosDibujar() {
        return elementosDibujar;
    }

    public void AgregarDibujable(ArrayList<Dibujable> objeto) {
        try {
            elementosDibujar.clear();
            elementosDibujar.addAll(objeto);


            this.Dibujar();
        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());
        }
    }

    public void DibujarPosicionActual() {

    }

    // se puede abstraer con anteriores?
    public void DibujarColes(ArrayList<Cole> coles) {
        try {

            ArrayList<Dibujable> dibujable = new ArrayList<>();
            int i = 0;
            while (i < coles.size()) {

                dibujable.add(new DibujableMarcador(new Marcador(coles.get(i).getLatLng(), coles.get(i).getDescripcion(), coles.get(i).getNombreIcono())));
                i++;
            }
          //  dibujable.add(new DibujableMarcador(new Marcador(CapaServicio.retornarUltimaUbicacionConocida(), "", "IC_MI_UBICACION")));//Agrega posicion actual para centrar el mapa
            AgregarDibujable(dibujable);


        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    public void DibujarMarcador(Marcador marcador) {
        try {

            ArrayList<Dibujable> dibujable = new ArrayList<>();


            dibujable.add(new DibujableMarcador(marcador));


            AgregarDibujable(dibujable);
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    public void Dibujar() {
        mmap.clear();
        int i = 0;
        while (i < elementosDibujar.size()) {

            elementosDibujar.get(i).Accept(dibujador);
            i++;
        }

    }

    public void EliminarDibujable(Object objeto) {

        elementosDibujar.remove(objeto);

    }

    public void mostrarDialogoEspera(String texto) {

        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setMessage(texto);
        dialog.show();

    }


    public void cerrarDialogoEspera() {
        dialog.dismiss();
    }






    public Context getContext() {
        return context;
    }




    public void limpiarMapa() {
        mmap.clear();
    }






}
