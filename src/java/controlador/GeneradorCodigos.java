
package controlador;

import java.util.Random;
import java.util.UUID;

public class GeneradorCodigos {
    public static String generarCodigoVerificacion(){
        Random rand = new Random();
        int codigo = rand.nextInt(900000) + 100000;
        return Integer.toString(codigo);
    }
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
