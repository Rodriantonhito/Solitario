package seinku;

import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class Tablero {

    private final int FILA = 7;
    private final int COLUMNA = 7;

    private final char[][] tablero;

    private String[] patron;
    private final ArrayList<Movimientos> listaMovimientos = new ArrayList();

    public Tablero() {
        tablero = new char[FILA][COLUMNA];

    }

    public void rellenarTablero() {
        for (int i = 0; i < FILA; i++) {
            for (int j = 0; j < COLUMNA; j++) {
                if (i < 2 || i > 4) {
                    switch (j) {
                        case 0:
                        case 1:
                            tablero[j][i] = ' ';
                            break;
                        case 2:
                        case 3:
                        case 4:
                            tablero[j][i] = '#';
                            break;
                        case 5:
                        case 6:
                            tablero[j][i] = ' ';
                            break;
                    }
                } else {
                    if (i == 2 || i == 4) {
                        tablero[j][i] = '#';
                    } else {
                        if (j == 3) {
                            tablero[j][i] = '.';
                        } else {
                            tablero[j][i] = '#';
                        }
                    }
                }
            }
        }
    }

    public void dameMovimiento(int posColumna, int posFila, int posFinalColum, int posFinalFila) {
        char datoOrigen = tablero[posColumna][posFila];
        char datoFinal = tablero[posFinalColum][posFinalFila];
        tablero[posColumna][posFila] = '#';
        tablero[posFinalColum][posFinalFila] = '#';

        //movimiento horizontal
        if (posFila == posFinalFila) {
            if (posColumna > posFinalColum) {
                tablero[posColumna + 1][posFila] = datoFinal;
                tablero[posColumna][posFila] = '.';

            } else {
                tablero[posFinalColum - 1][posFinalFila] = datoFinal;
                tablero[posColumna][posFila] = '.';
            }
        }
        //movimiento de arriba y abajo
        if (posColumna == posFinalColum) {
            if (posFila > posFinalFila) {
                tablero[posColumna][posFila + 1] = datoFinal;
                tablero[posColumna][posFila] = '.';

            } else {
                tablero[posFinalColum][posFinalFila - 1] = datoFinal;
                tablero[posColumna][posFila] = '.';
            }
        }
         Movimientos movimiento = new Movimientos( posColumna, posFila,  posFinalColum, posFinalFila);
         listaMovimientos.add(movimiento);
    }
    public void deshacer(){
        //El listaMovimientos guarda los 4 int y el get ultimo nos da la posicion que sea
        //y la guarda en la variable oportuna.
        int posColumna = listaMovimientos.get(listaMovimientos.size()-1).getPosColumna();
        int posFila = listaMovimientos.get(listaMovimientos.size()-1).getPosFila();
        int posFinalColum = listaMovimientos.get(listaMovimientos.size()-1).getPosFinalColum();
        int posFinalFila = listaMovimientos.get(listaMovimientos.size()-1).getPosFinalFila();
        
        //movimiento de izquierda a derecha.
        if (posFila == posFinalFila) {
            if (posColumna > posFinalColum) {
                tablero[posColumna + 1][posFila] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila]='.';

            } else {
                tablero[posFinalColum - 1][posFinalFila] = '#';
                tablero[posColumna][posFila] = '#';
                tablero[posFinalColum][posFinalFila]='.';
            }
        }
        
        //movimiento hacia arriba y hacia abajo.
        if (posColumna == posFinalColum) {
            if (posFila > posFinalFila) {
                tablero[posColumna][posFila + 1] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila]='.';

            } else {
                tablero[posFinalColum][posFinalFila - 1] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila]='.';
            }
        }
        listaMovimientos.remove(listaMovimientos.size()-1);
    }

    public String dibujarTablero() {
        String texto = "";
        for (int i = 0; i < FILA; i++) {
            for (int j = 0; j < COLUMNA; j++) {
                texto += tablero[j][i];
            }
            texto += "\n";
        }
        return texto;
       
    }   

}
