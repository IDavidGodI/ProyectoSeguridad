
package Servlets;

import java.util.Random;

public class GeneradorCodigos {
    public static String generarCodigoVerificacion(){
        Random rand = new Random();
        int codigo = rand.nextInt(900000) + 100000;
        return Integer.toString(codigo);
    }
}
