package archivos;

import java.util.Vector;

/**
 * Created by Gianno on 14/12/2016.
 */

public class ConjuntoArchivos extends ElmentoCarpeta {
    Vector<ElmentoCarpeta> elmentoCarpetas;

    public ConjuntoArchivos(Vector<ElmentoCarpeta> elmentoCarpetas) {
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
}
