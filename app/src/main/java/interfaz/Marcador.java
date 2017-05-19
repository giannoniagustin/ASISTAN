package interfaz;


/**
 * Created by Agustín on 06/10/2015.
 */
public class Marcador {
    private com.google.android.gms.maps.model.LatLng LatLng;
    private String descripcion;
    private String nombreIcono;

    public Marcador(com.google.android.gms.maps.model.LatLng LatLng, String descripcion, String nombreIcono) {
        this.LatLng = LatLng;
        this.descripcion = descripcion;
        this.nombreIcono = nombreIcono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLatLng(com.google.android.gms.maps.model.LatLng latLng) {
        this.LatLng = latLng;
    }

    public void setNombreIcono(String nombreIcono) {
        this.nombreIcono = nombreIcono;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return LatLng;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreIcono() {
        return nombreIcono;
    }
}
