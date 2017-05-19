package sumo;


public class Cole extends Punto  {
    private String idCole;
    private String Descripcion;

    private String nombreIcono;

    public Cole(String id, double latitud, double longitud, String fechaCreacion, String descripcion, String nombreIcono) {
        super(latitud,longitud,fechaCreacion);
        this.idCole = id;
         this.Descripcion = descripcion;

        this.nombreIcono = nombreIcono;
    }


    public String getIdCole() {
        return idCole;
    }

    public void setIdCole(String id) {
        this.idCole = id;
    }


    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

/*
    @Override
    public void dibujar(GoogleMap mGeneral) { //no va mas


        try {


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(super.latLng);
            markerOptions.title(Descripcion);
            //   String tipoIcono =
            markerOptions.icon(BitmapDescriptorFactory.fromResource(Iconos.getIcono(nombreIcono)));

            mGeneral.addMarker(markerOptions);
        }catch (Exception e){

            Log.LOGGER.severe(e.toString()+"Parametros: MapaGenral"+mGeneral.toString()+"Iconmno :"+(Iconos.getIcono(nombreIcono)));
        }




    }

    @Override
    public void borrar(GoogleMap mGeneral) {
        //implementar
    }

*/

    public String getNombreIcono() {
        return nombreIcono;
    }

    public void setNombreIcono(String nombreIcono) {
        this.nombreIcono = nombreIcono;
    }
}
