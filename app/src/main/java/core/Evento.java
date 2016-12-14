package core;

import android.text.format.DateFormat;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import util.Fecha;

/**
 * Created by Gianno on 12/12/2016.
 */

public class Evento {

    Date fecha;
    Actividad actividad;
    LatLng punto;

    public Evento(Date fecha, Actividad actividad, LatLng punto) {
        this.fecha = fecha;
        this.actividad = actividad;
        this.punto = punto;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "fecha=" + DateFormat.format(Fecha.DATE_FORMAT, fecha.getTime()) +
                ", actividad=" + actividad +
                ", punto=" + punto.latitude+","+punto.longitude +
                '}';
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public LatLng getPunto() {
        return punto;
    }

    public void setPunto(LatLng punto) {
        this.punto = punto;
    }
}
