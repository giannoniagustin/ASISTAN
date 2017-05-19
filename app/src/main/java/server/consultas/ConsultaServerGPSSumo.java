package server.consultas;

import android.os.AsyncTask;
import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import parser.ManejadorParserSUMO;
import server.Server;
import server.commands.ServidorCommand;



public  class ConsultaServerGPSSumo extends AsyncTask<HashMap<String, Object>, Void, InputStream> {

    private HashMap<String, Object> parametros;
    private InputStream salida;
    private ServidorCommand serverCommand;



    public ConsultaServerGPSSumo(ServidorCommand serverCommand)
    {

        this.serverCommand = serverCommand;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected InputStream doInBackground(HashMap<String, Object>... params) {
        salida = null;
        parametros = params[0];
        try {

            String busId = ManejadorParserSUMO.getLinea((String)parametros.get("linea"));
            Long time = new Date().getTime();
            //Nueva forma de pedido a sumo
            String uri = "http://gpssumo.com/ajax/ebus_dev/get/{time}/0".replace("{time}", Server.HASH_ID_PEDIDOCOLECTIVO);

            Log.d("Coneccion", "leyendo el " + busId);
            String urlParameters  = "r={id}&t=0".replace("{id}", busId);
            byte[] postData       = urlParameters.getBytes("UTF-8");
            int    postDataLength = postData.length;
            URL url            = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);

            conn.setDoOutput(true);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setRequestProperty("charset", "utf-8");

            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));

            conn.setRequestProperty("Host", "gpssumo.com");

            conn.setRequestProperty("Origin", "http://gpssumo.com");

            conn.setRequestProperty("Referer", "http://gpssumo.com");


            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();


            conn.connect();


            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(conn.getInputStream(), Map.class);
// reads response


           StringBuilder sb = new StringBuilder();
           BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           String line = "";
           while ((line = rd.readLine()) != null) {
           	sb.append(line);
           }


            salida = new ByteArrayInputStream(sb.toString().getBytes());

            String cadena;
            cadena=sb.toString();

           // salida = conn.getInputStream();
            Log.d("Coneccion", "EL largo fue " + conn.getContentLength());


        } catch (Exception e) {
            e.printStackTrace();
            salida = new ByteArrayInputStream(e.toString().getBytes());//new StringBufferInputStream(e.toString());
        }
        return salida;
    }






    protected void onPostExecute(InputStream s){
        serverCommand.procesarRespuesta(s);
    }

 /*   public AsyncRespuesta getValor() {
        return valor;
    }

    public void setValor(AsyncRespuesta valor) {
        this.valor = valor;
    }
    public ProgressDialog getDialog() {
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    */
}
