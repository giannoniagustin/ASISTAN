package archivos;

import java.io.File;
import java.util.Vector;

/**
 * Created by Gianno on 14/12/2016.
 */

public class Carpeta extends ElmentoCarpeta {
    Vector<ElmentoCarpeta> elmentoCarpetas;

    public Carpeta(Vector<ElmentoCarpeta> elmentoCarpetas) {
        this.elmentoCarpetas = elmentoCarpetas;
    }

    public void agregarElementoCarpeta(ElmentoCarpeta eCarpeta)
    {
        elmentoCarpetas.add(eCarpeta);
    }

    public Vector<ElmentoCarpeta> getElmentoCarpetas()
    {
        return elmentoCarpetas;
    }

    public void setElmentoCarpetas(Vector<ElmentoCarpeta> elmentoCarpetas)
    {
        this.elmentoCarpetas = elmentoCarpetas;
    }

    @Override
    public void comprimir()
    {
        for(int i=0;i < elmentoCarpetas.size();i++)
        {
            elmentoCarpetas.elementAt(i).comprimir();
        }

    }
    public ElmentoCarpeta crearEstructura(String path)
    {
        File dir= new File(path);
        if ( dir.isDirectory())
        {
            String[] listFile= dir.list();
            for(int i=0; i < listFile.length;i++)
            {
                File subDirectorio= new File(listFile[i]);
                if (subDirectorio.isDirectory())
                {
                    Carpeta carpeta=new Carpeta(new Vector<ElmentoCarpeta>());
                    this.agregarElementoCarpeta(carpeta.crearEstructura(listFile[i]));
                }
                else
                {
                    this.agregarElementoCarpeta(new Archivo(listFile[i],subDirectorio.getName(),this.manangerArchivos));
                }

            }


        }
        else
        {
            this.agregarElementoCarpeta(new Archivo(path,dir.getName(),this.manangerArchivos));

        }

        return this;


    }
}
