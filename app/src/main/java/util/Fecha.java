package util;

import java.text.SimpleDateFormat;

/**
 * Created by Gianno on 12/12/2016.
 */

public class Fecha {
    public     final static String DATE_FORMAT="MM:dd:yyyy:HH:mm:ss";
    public     final static String DIA_FORMAT="MM:dd:yyyy";
    public  static SimpleDateFormat FORMATO_FECHA =new SimpleDateFormat(Fecha.DIA_FORMAT);
}
