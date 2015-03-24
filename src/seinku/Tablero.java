package seinku;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
        Movimientos movimiento = new Movimientos(posColumna, posFila, posFinalColum, posFinalFila);
        listaMovimientos.add(movimiento);
    }

    public void deshacer() {
        //El listaMovimientos guarda los 4 int y el get ultimo nos da la posicion que sea
        //y la guarda en la variable oportuna.
        int posColumna = listaMovimientos.get(listaMovimientos.size() - 1).getPosColumna();
        int posFila = listaMovimientos.get(listaMovimientos.size() - 1).getPosFila();
        int posFinalColum = listaMovimientos.get(listaMovimientos.size() - 1).getPosFinalColum();
        int posFinalFila = listaMovimientos.get(listaMovimientos.size() - 1).getPosFinalFila();

        //movimiento de izquierda a derecha.
        if (posFila == posFinalFila) {
            if (posColumna > posFinalColum) {
                tablero[posColumna + 1][posFila] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila] = '.';

            } else {
                tablero[posFinalColum - 1][posFinalFila] = '#';
                tablero[posColumna][posFila] = '#';
                tablero[posFinalColum][posFinalFila] = '.';
            }
        }

        //movimiento hacia arriba y hacia abajo.
        if (posColumna == posFinalColum) {
            if (posFila > posFinalFila) {
                tablero[posColumna][posFila + 1] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila] = '.';

            } else {
                tablero[posFinalColum][posFinalFila - 1] = '#';
                tablero[posColumna][posFila] = '.';
                tablero[posFinalColum][posFinalFila] = '.';
            }
        }
        listaMovimientos.remove(listaMovimientos.size() - 1);
    }

    public void crearXML() {
        try {
            DocumentBuilderFactory creadorDocumento = DocumentBuilderFactory.newInstance();
            DocumentBuilder crearDocumento = creadorDocumento.newDocumentBuilder();
            //Crear un nuevo documento XML
            Document documento = crearDocumento.newDocument();

            //Crear el nodo raíz y colgarlo del documento
            Element elementoRaiz = documento.createElement("MOVIMIENTOS");
            documento.appendChild(elementoRaiz);

            for (int i = 0; i < listaMovimientos.size(); i++) {
                //Crear un elemento MOVMIMIENTO colgando de MOVIMIENTOS
                Element elementoMovimiento = documento.createElement("MOVIMIENTO");
                elementoRaiz.appendChild(elementoMovimiento);

                //Crear un elemento NUM_MOVIMIENTO colgando de MOVIMIENTO
                Element elementoNumMovimiento = documento.createElement("NUM_MOVIMIENTO");
                elementoMovimiento.appendChild(elementoNumMovimiento);

                //Crear un elemento Columna_ORIGEN colgando de MOVIMIENTO
                Element elementoFilaOrigen = documento.createElement("POS_COLUMNA");
                elementoMovimiento.appendChild(elementoFilaOrigen);

                //Crear un elemento FILA_ORIGEN colgando de MOVIMIENTO
                Element elementoFilaDestino = documento.createElement("POS_FILA");
                elementoMovimiento.appendChild(elementoFilaDestino);

                //Crear un elemento COLUM_FINAL colgando de MOVIMIENTO
                Element elementoColumOrigen = documento.createElement("POS_FINALCOLUMNA");
                elementoMovimiento.appendChild(elementoColumOrigen);

                //Crear un elemento FILA_FINAL colgando de MOVIMIENTO
                Element elementoColumDestino = documento.createElement("POS_FINALFILA");
                elementoMovimiento.appendChild(elementoColumDestino);

                //Obtener los numero de movimiento y colgarlos de NUM_MOVIMIENTO
                Text textoNumMovimiento = documento.createTextNode(String.valueOf(i + 1));
                elementoNumMovimiento.appendChild(textoNumMovimiento);

                //Obtener la fila de origen y colgarlos de FILA_ORIGEN
                Text textoFilaOrigen = documento.createTextNode(String.valueOf(listaMovimientos.get(i).getPosColumna()));
                elementoFilaOrigen.appendChild(textoFilaOrigen);

                //Obtener la fila de destino y colgarlos de FILA_DESTINO
                Text textoFilaDestino = documento.createTextNode(String.valueOf(listaMovimientos.get(i).getPosFila()));
                elementoFilaDestino.appendChild(textoFilaDestino);

                //Obtener la columna de origen y colgarlos de COLUM_ORIGEN
                Text textoColumOrigen = documento.createTextNode(String.valueOf(listaMovimientos.get(i).getPosFinalColum()));
                elementoColumOrigen.appendChild(textoColumOrigen);

                //Obtener la columna de destino y colgarlos de COLUM_DESTINO
                Text textoColumDestino = documento.createTextNode(String.valueOf(listaMovimientos.get(i).getPosFinalFila()));
                elementoColumDestino.appendChild(textoColumDestino);
            }
            //Generar el tranformador para obtener el documento XML en un fichero
            TransformerFactory fábricaTransformador = TransformerFactory.newInstance();
            Transformer transformador = fábricaTransformador.newTransformer();
            //Insertar saltos de línea al final de cada línea
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            //Añadir 3 espacios delante, en función del nivel de cada nodo
            transformador.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "3");
            Source origen = new DOMSource(documento);
            Result destino = new StreamResult("movimientos.xml");
            transformador.transform(origen, destino);

        } catch (ParserConfigurationException ex) {
            System.out.println("ERROR: No se ha creado el documento XML\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (TransformerException ex) {
            System.out.println("ERROR: No se ha creado la salida del documento XML\n" + ex.getMessage());
            ex.printStackTrace();
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

    public void leerDocumento() throws FileNotFoundException, IOException {
        String niveles = "nivel1.txt";
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
            //Crear un objeto BufferedReader al que se le pasa 
            //   un objeto FileReader con el nombre del fichero
            br = new BufferedReader(new FileReader(niveles));
            //Leer la primera línea, guardando en un String
            String texto = br.readLine();
            //Repetir mientras no se llegue al final del fichero
            while (texto != null) {
                //Hacer lo que sea con la línea leída
                System.out.println(texto);
                //Leer la siguiente línea
                texto = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }

        }

    }
}
