package archivos;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by Agustín on 17/09/2015.
 */
public class Log {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    static public void setup() throws IOException {

        try {

            File dir = new File (ManangerArchivos.PATH_LOG_USER_TXT);
            dir.mkdirs();
            LOGGER.setLevel(Level.ALL);
            fileTxt = new FileHandler(ManangerArchivos.PATH_LOG_USER_TXT+"/"+ManangerArchivos.NOMBRE_LOG_USER_TXT,true);
            fileHTML = new FileHandler(ManangerArchivos.LOG_USER_HTML,true);
            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            formatterHTML = new MyHtmlFormatter();
            fileHTML.setFormatter(formatterHTML);
            LOGGER.addHandler(fileTxt);
            LOGGER.addHandler(fileHTML);
            LOGGER.info("Se Crearon los archivos de log");


        } catch (Exception e)
        {
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("No se crearon los archivos Excepcion");

        }
    }

}
