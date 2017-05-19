package server.commands;



import java.io.InputStream;

import server.Server;


/**
 * Created by Agustín on 05/10/2015.
 */
public abstract class ServidorCommand implements Command {

    protected  int tiempoEspera;
    protected int reintento;
    protected int frecuencia;
    protected Server server;

    public void setServer(Server s){
        this.server = s;
    }

    public abstract void procesarRespuesta(InputStream s);

}
