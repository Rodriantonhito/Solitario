package seinku;

/**
 *
 * @author Antonio
 */
public class Tablero {

    private final int FILA = 7;
    private final int COLUMNA = 7;

    private char[][] tablero;

    private String[] patron;

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
                tablero[posColumna][posFila]= '.';

            } else {
                tablero[posFinalColum - 1][posFinalFila] = datoFinal;
                tablero[posColumna][posFila]= '.';
            }
        }
        //movimiento de arriba y abajo
        if (posColumna == posFinalColum) {
            if (posFila > posFinalFila) {
                tablero[posColumna][posFila + 1] = datoFinal;
                tablero[posColumna][posFila]= '.';
                
            } else {
                tablero[posFinalColum][posFinalFila - 1] = datoFinal;
                tablero[posColumna][posFila]= '.';
            }
        }
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
