package controlador;


import Comunes.PropiedadesEnvios;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CodigoVerificacion {
    
    private static float TIEMPO_EXPIRACION;
    
    public static void setCodigoVerificacion(HttpServletRequest request, String codigo, float tiempo) {
        HttpSession sesion = request.getSession();
        sesion.setAttribute(PropiedadesEnvios.COD_VERIFICACION, codigo);
        
        sesion.setAttribute(PropiedadesEnvios.TS_VERIFICACION, new Date());
        TIEMPO_EXPIRACION = tiempo;
        System.out.println(TIEMPO_EXPIRACION);
    }
    
    public static boolean codigoExpirado(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        String codigoGuardado = (String) sesion.getAttribute(PropiedadesEnvios.COD_VERIFICACION);
        if (codigoGuardado != null) {
            Date tiempoCreacion = (Date) sesion.getAttribute(PropiedadesEnvios.TS_VERIFICACION);
            long expiracion = tiempoCreacion.getTime() + ((long)TIEMPO_EXPIRACION  * 60 * 1000);
            return System.currentTimeMillis() > expiracion;
        }
        return true;
    }
    
}


