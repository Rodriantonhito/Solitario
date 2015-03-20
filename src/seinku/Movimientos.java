package seinku;

/**
 *
 * @author Antonio
 */
public class Movimientos {

    int posColumna;
    int posFila;
    int posFinalColum;
    int posFinalFila;

    public Movimientos(int posColumna, int posFila, int posFinalColum, int posFinalFila) {
        this.posColumna = posColumna;
        this.posFila = posFila;
        this.posFinalColum = posFinalColum;
        this.posFinalFila = posFinalFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public int getPosFila() {
        return posFila;
    }

    public int getPosFinalColum() {
        return posFinalColum;
    }

    public int getPosFinalFila() {
        return posFinalFila;
    }

}
