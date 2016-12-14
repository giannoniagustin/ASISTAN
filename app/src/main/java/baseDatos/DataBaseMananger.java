package baseDatos;

/**
 * Created by Agustín on 26/08/2015.
 */
public class DataBaseMananger {
    public static final String TABLE_NAME="archivos";

    public static  final String CN_ID="_id";
    public static  final String CN_NAME="nombre";
    public static  final String CN_FECHA="fecha";
    public static  final String CN_ENVIADO="enviado";
    public static  final String CN_INTENTO="intento";

    public static  final String CREATE_TABLE="create table "+TABLE_NAME+" ("
            +CN_ID+" integer primary key autoincrement, "
            +CN_NAME+" text not null,"
            +CN_FECHA+" text,"
            +CN_ENVIADO+" text,"
            +CN_INTENTO+" integer );";



}
