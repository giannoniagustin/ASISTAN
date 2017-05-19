package parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import archivos.Log;
import sumo.Cole;

/**
 * Created by Agustín on 13/10/2015.
 */
public class ManejadorParserSUMO {

    static HashMap<String, String> lineas = new HashMap<>();
    static HashMap<String, String> iconos = new HashMap<>();
    static boolean inicializada = false;

    private static void inicializar(){
        lineas.put("Linea 500", "3_500");
        lineas.put("Linea 501", "4_501");
        lineas.put("Linea 502", "5_502");
        lineas.put("Linea 503", "6_503");
        lineas.put("Linea 504", "7_504");
        lineas.put("Linea 505", "8_505");

        iconos.put("Linea 500", "IC_BUS_AMARILLO");
        iconos.put("Linea 501", "IC_BUS_ROJO");
        iconos.put("Linea 502", "IC_BUS_BLANCO");
        iconos.put("Linea 503", "IC_BUS_AZUL");
        iconos.put("Linea 504", "IC_BUS_VERDE");
        iconos.put("Linea 505", "IC_BUS_MARRON");
        inicializada=true;

    }





    public ManejadorParserSUMO() {

        if (!inicializada)
             inicializar();

    }


    public static String getLinea(String linea){

        if (!inicializada){
            inicializar();
        }
        return lineas.get(linea);
    }

    public static String getIcono(String linea){

        if (!inicializada){
            inicializar();
        }
        return iconos.get(linea);
    }
    public ArrayList<Cole> LeerColes(InputStream in, String linea) {

        try {
            ArrayList<Cole> coles =  new ArrayList<Cole>();
            String contenido = this.parsearRespuestaString(in);
            if (contenido.contains("L.marker")){
                String[] colectivos = contenido.split("L.marker");
                String recorridos = colectivos[0];
                String[] puntosrecorridos = recorridos.split("L.latLng");
                for (int i = 1; i < colectivos.length; i++){
                    try{
                        String coordenadas = colectivos[i].split("\\]")[0];
                        coordenadas = coordenadas.replace("([", "");
                        coordenadas = coordenadas.replace(" ","");
                        String datos = colectivos[i].split("title: \\'\\[")[1];
                        String ident = datos.split("]")[0];
                        String time = datos.split(" ")[1];
                        String vel = datos.split(" ")[2];
                        Cole c1 = new Cole(ident,
                                Double.parseDouble(coordenadas.split(",")[0]),
                                Double.parseDouble(coordenadas.split(",")[1]),
                                time,
                                "Movil " + ident + " hora " + time + " Vel. " + vel,   // CAMBIAR
                                getIcono(linea)          //Dependera del colectivoa  ver
                                );

                        coles.add(c1);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                //System.out.println("--------------------------------------------------------------");
            }



            return (ArrayList)coles;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }




    public String parsearRespuestaString(InputStream in){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    Log.LOGGER.severe(e.toString());
                }
            }
        }

        String response = sb.toString();
        return response;
    }

    public String LeerSaldo(InputStream in) {

        try {
            String saldo =  this.parsearRespuestaString(in);
            return saldo;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }
    
    public Integer parsearRespuesta(InputStream in){

        return(Integer.parseInt(parsearRespuestaString(in).trim()));
    }




}
