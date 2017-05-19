package interfaz;


import java.util.HashMap;


import giannno.asistan.R;

public  class Iconos {

    static HashMap<String,Integer> iconos;

    public Iconos() {


        iconos = new HashMap<>();

        iconos.put("Protesta",R.mipmap.ic_protesta ); //TIPO 1
        iconos.put("Choque", R.mipmap.ic_choque_transp);// TIPO 2
        // agregar icono parquimetro
        iconos.put("Parquimetro",R.mipmap.ic_park);// TIPO 9
        iconos.put("PuntodeCarga",R.mipmap.ic_punto_carga); //TIPO 10


        iconos.put("IC_CHOQUE_GREEN",R.mipmap.ic_choque_green);
        iconos.put("IC_PROTESTA_GREEN",R.mipmap.ic_protesta_green);

        iconos.put("IC_TRACKER", R.mipmap.ic_tracker);
        iconos.put("IC_OTRA_UBICACION",R.mipmap.ic_otra_ubicacion);

        iconos.put("IC_TRACKER_GREEN", R.mipmap.ic_tracker_green);

        iconos.put("IC_OTRA_UBICACION_GREEN",R.mipmap.ic_otra_ubicacion_green);
        iconos.put("IC_MI_UBICACION",R.mipmap.mi_ubicacion);

        iconos.put("IC_BUS_AZUL",R.mipmap.bus_azul);
        iconos.put("IC_BUS_BLANCO",R.mipmap.bus_blanco);
        iconos.put("IC_BUS_ROJO",R.mipmap.bus_rojo);
        iconos.put("IC_BUS_AMARILLO",R.mipmap.bus_amarillo);
        iconos.put("IC_BUS_VERDE",R.mipmap.bus_verde);
        iconos.put("IC_BUS_MARRON",R.mipmap.bus_marron);

        iconos.put("IC_RUTA", R.mipmap.ic_ruta);
        iconos.put("IC_PUNTO_ESTADIA",R.mipmap.ic_punto_estadia);




    }

    public  static Integer getIcono(String clave) {
        return iconos.get(clave);
    }

    public HashMap<String, Integer> getIconos() {
        return iconos;
    }

}
