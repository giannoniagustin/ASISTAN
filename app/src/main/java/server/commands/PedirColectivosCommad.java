package server.commands;

import java.io.InputStream;
import java.util.ArrayList;


import archivos.Log;
import interfaz.ManejadorInterfaz;
import parser.ManejadorParserSUMO;
import sumo.Cole;



/**
 * Created by soled_000 on 13/10/2015.
 */
public class PedirColectivosCommad extends ServidorCommand{


   private ManejadorInterfaz manejadorInterfaz;
    String linea= null;

//Deberia ir el codigo del colectivo
    public PedirColectivosCommad(String lin) {
       linea = lin;
    }


    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }


    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParserSUMO manejadorParserSumo = new ManejadorParserSUMO();
        try {


            ArrayList<Cole> arreglo =  manejadorParserSumo.LeerColes(s, linea);
            Log.LOGGER.severe(arreglo.toString());
            manejadorInterfaz.DibujarColes(arreglo);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        try {


        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirColectivos(this, linea);
        } catch (Exception e)
        {
            Log.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void Deshacer() {

    }
}
