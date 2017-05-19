package interfaz;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.Hashtable;



/**
 * Created by Agustín on 07/10/2015.
 */
public class Dibujador {
    private GoogleMap mMap;
    private Polyline line;
    private Hashtable<Integer, Integer> colores = new Hashtable<>();

    public Dibujador() {
        setColores();

    }
    public void setColores(){
        colores.put(0, Color.GREEN);
        colores.put(1, Color.RED);
        colores.put(2, Color.BLUE);
        colores.put(3, Color.BLACK);
        colores.put(4, Color.CYAN);
        colores.put(5, Color.DKGRAY);
        colores.put(6, Color.GRAY);
        colores.put(7, Color.LTGRAY);
        colores.put(8, Color.YELLOW);
        colores.put(9, Color.MAGENTA);
    }

    public int getRandomColores(){
        int range = ((colores.size() - 1) - 0) + 1;
        return (int) (Math.random() * range) + 0;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

 /*   public void DibujarCorte(DibujableCorte dibujableCorte) {

        try {


            MarkerOptions markerOptions = new MarkerOptions();
            LatLng a = dibujableCorte.getCorte().getLatLng();
            markerOptions.position(dibujableCorte.getCorte().getLatLng());
            markerOptions.title(dibujableCorte.getCorte().getDescripcion());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(Iconos.getIcono(dibujableCorte.getCorte().getNombreIcono())));

            mMap.addMarker(markerOptions);
        } catch (Exception e) {

            Log.LOGGER.severe(e.toString() + "Parametros: MapaGenral" + mMap.toString());
        }


    }*/

    public void DibujarMarcador(DibujableMarcador dibujableMarcador) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(dibujableMarcador.getMarcador().getLatLng());
        markerOptions.title(dibujableMarcador.getMarcador().getDescripcion());
        markerOptions.icon(BitmapDescriptorFactory.fromResource( Iconos.getIcono(dibujableMarcador.getMarcador().getNombreIcono())));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dibujableMarcador.getMarcador().getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        mMap.addMarker(markerOptions);


    }




}


