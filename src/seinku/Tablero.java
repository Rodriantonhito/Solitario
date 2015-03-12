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
                            tablero[i][j] = ' ';
                            break;
                        case 1:
                            tablero[i][j] = ' ';
                            break;
                        case 2:
                            tablero[i][j] = '#';
                            break;
                        case 3:
                            tablero[i][j] = '#';
                            break;
                        case 4:
                            tablero[i][j] = '#';
                            break;
                        case 5:
                            tablero[i][j] = ' ';
                            break;
                        case 6:
                            tablero[i][j] = ' ';
                            break;
                    }
                } else {
                    if (i == 2 || i == 4) {
                        tablero[i][j] = '#';
                    } else {
                        if (j == 3) {
                            tablero[i][j] = '.';
                        } else {
                            tablero[i][j] = '#';
                        }
                    }
                }
            }
        }
    }
    
    public void dameMovimiento(int posColumna, int posFila, int posFinalColum, int posFinalFila ){
       char datoOrigen = tablero[posColumna][posFila];
       char datoFinal= tablero[posFinalColum][posFinalFila];       
       tablero [posColumna][posFila] = datoOrigen;
       tablero[posFinalColum][posFinalFila]=datoFinal;
       
       if(posColumna>posFinalColum){
           tablero[posColumna+1][posFila]=datoFinal;
       }else{
           tablero[posFinalColum-1][posFinalFila]=datoFinal;
       }
    
    }

    public String dibujarTablero() {
        String texto = "";
        for (int i = 0; i < FILA; i++) {
            for (int j = 0; j < COLUMNA; j++) {
                texto += tablero[i][j];
            }
            texto += "\n";
        }
        return texto;
    }
}
