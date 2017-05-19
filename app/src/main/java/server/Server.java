package server;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

import archivos.Log;
import server.commands.PedirColectivosCommad;
import server.consultas.ConsultaServerGPSSumo;


/**
 * Created by Agustín on 06/10/2015.
 */
public class Server {


    private static final String URL_PEDIDO_COLECTIVOS = "http://gpssumo.com/ajax/ebus_dev/get/";
    public static final String HASH_ID_PEDIDOCOLECTIVO = "faa8f91f9b9fbc077ac44ca18aaa7b97";
    private HttpClient client ;

    public HttpClient getClientUsuario(){
        return client;
    }
    public void setClient(HttpClient client){
        this.client = client;
    }




    public void pedirColectivos(PedirColectivosCommad pedirColectivosCommand, String linea){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametros.put("parametrosConsulta", parametrosPost);
            String dir = URL_PEDIDO_COLECTIVOS + "faa8f91f9b9fbc077ac44ca18aaa7b97" + "/0";
            parametros.put("url", dir);
            //MEJORAR MANEJOS DE CONSTANTES EN CODIGO
            parametros.put("linea", linea);

            ConsultaServerGPSSumo consultaServerGenerica = new ConsultaServerGPSSumo(pedirColectivosCommand);
            consultaServerGenerica.execute(parametros, null,null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

}



