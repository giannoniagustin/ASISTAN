package interfaz;


/**
 * Created by Agustín on 07/10/2015.
 */
public class DibujableMarcador implements Dibujable {
    private Marcador marcador;

    public DibujableMarcador(Marcador marcador) {
        this.marcador = marcador;
    }

    @Override
    public void Accept(Dibujador dibujador) {

        dibujador.DibujarMarcador(this);

    }

    public Marcador getMarcador() {
        return marcador;
    }
}
