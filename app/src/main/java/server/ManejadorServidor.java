package server;

import android.content.BroadcastReceiver;
import android.content.Context;
import java.util.ArrayDeque;
import archivos.Log;
import server.commands.ServidorCommand;


public class ManejadorServidor {

    private java.util.Queue queueB;
    private Context context;
    private BroadcastReceiver conexionInternet;
    private Server server;

    public ManejadorServidor(){
        queueB = new ArrayDeque();// agregar un COMPARABLE para el tema de la PRIORIDAD PriorityQueue
        server = new Server();
    }

    public void agregarElemento(ServidorCommand elem){
        try
        {
            queueB.add(elem);
            ejecutarTarea();
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }

    public Object sacarElemento(){
        return (queueB.poll()); //retorna null si la cola esta vacia
    }


    public void ejecutarTarea(){    //VER FRECUENCIA despierta a ejecutar cuando llega un elemento nuevo...
        try {
            ServidorCommand c = (ServidorCommand) sacarElemento();
            c.setServer(server);
            c.Ejecutar();
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }



    }
    public int cantidadElementos(){
        return queueB.size();
    }

    public Server getServer(){
        return server;
    }


    
}
