package core;

/**
 * Created by Gianno on 12/12/2016.
 */
public class Actividad {
    String nombre;
    int probabilidad;

    public Actividad(String nombre, int probabilidad) {
        this.nombre = nombre;
        this.probabilidad = probabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "nombre='" + nombre + '\'' +
                ", probabilidad=" + probabilidad +
                '}';
    }
}
