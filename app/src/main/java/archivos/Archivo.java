package archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Agustín on 24/09/2015.
 */
public class Archivo
{
    String nombre;
    String path;
    File archivo;
    File dir;
    FileOutputStream fOutputStream;
    ZipOutputStream zos;
    ZipEntry ze;
    public Archivo(String path,String nombre )
    {
            this.nombre = nombre;
            this.path = path;
            dir = new File(path);
            dir.mkdirs();
            archivo = new File(dir, nombre+ManangerArchivos.EXTENSION_TXT);
    }

    public void grabar(String s)
    {
        try {
            fOutputStream = new FileOutputStream(archivo,true);
            fOutputStream.write(s.getBytes(Charset.forName("UTF-8")));
            fOutputStream.flush();
            fOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void comprimir()
    {
        try {
            try {
                    byte[] buffer = new byte[1024];
                    File archivoZip = new File(dir, nombre + ManangerArchivos.EXTENSION_ZIP);
                    fOutputStream = new FileOutputStream(archivoZip,true);
                    zos = new ZipOutputStream(fOutputStream);
                    zos.setLevel(Deflater.BEST_COMPRESSION);
                    zos.setMethod(Deflater.DEFLATED);
                    ze= new ZipEntry(nombre+ManangerArchivos.EXTENSION_TXT);
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(archivo);
                    int len;
                    while ((len = in.read(buffer)) > 0)
                        {
                            zos.write(buffer, 0, len);
                        }

                    in.close();
                    zos.closeEntry();

                    //remember close it
                    zos.close();

                } catch (FileNotFoundException e)
                    {
                        Log.LOGGER.severe(e.getMessage());
                    }
        } catch (IOException e)
        {
            Log.LOGGER.severe(e.getMessage());
        }
    }
    public void borrarTxt()
    {
    archivo.delete();
    }
}

