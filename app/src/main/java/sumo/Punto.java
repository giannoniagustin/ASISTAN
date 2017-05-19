package sumo;

import android.os.Parcel;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Soledad on 30/04/2015.
 */
public class Punto implements ElementoRuta{

    protected LatLng latLng;
    private String fechaCreacion;
    private Double latitude;
    private Double longitude;

    public Punto(double latitude, double longitude, String fechaCreacion) {

       this.latLng = new LatLng(latitude, longitude);
        this.fechaCreacion = fechaCreacion;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Punto(Parcel in){
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        fechaCreacion = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
    public double getLatitude() {
        return latLng.latitude;
    }

    public void setLatLng(LatLng l) {
        this.latLng = l;
    }

    public LatLng getLatLng() {
        return (new LatLng(latitude, longitude));
    }

    public double getLongitude() {
        return latLng.longitude;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

/*
    @Override
    public void dibujar(GoogleMap mGeneral){}

    @Override
    public  void borrar(GoogleMap mGeneral){}
*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeString(fechaCreacion);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
