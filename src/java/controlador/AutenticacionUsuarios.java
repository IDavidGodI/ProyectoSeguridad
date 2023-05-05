package controlador;

import Comunes.PropiedadesVerificacion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

public class AutenticacionUsuarios {
    
    public static void crearSesion(HttpServletRequest request, HttpServletResponse response){
            String idSesion = GeneradorCodigos.getUUID();
            String idDobleFactor = GeneradorCodigos.getUUID();
            HttpSession sesion = request.getSession();
            
            Cookie cookieSesion = new Cookie(PropiedadesVerificacion.COOKIE_SESION,idSesion);
            sesion.setAttribute(PropiedadesVerificacion.COOKIE_SESION,idSesion);
            cookieSesion.setMaxAge((int)(PropiedadesVerificacion.DURACION_COOKIES*60*2));
            response.addCookie(cookieSesion);
            if (!validarDobleFactor(request, response)){
                boolean codigoValidado = (boolean) request.getAttribute(PropiedadesVerificacion.COD_VAL);
                if (!codigoValidado) return;
                Cookie cookieVerificacion = new Cookie(PropiedadesVerificacion.COOKIE_VERIF,idDobleFactor);
                sesion.setAttribute(PropiedadesVerificacion.COOKIE_VERIF,idDobleFactor);
                cookieVerificacion.setMaxAge((int)(PropiedadesVerificacion.DURACION_COOKIES*60));
                response.addCookie(cookieVerificacion);
            }
    }
    public static void cerrarSesion(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        HttpSession sesion = request.getSession();
        for (Cookie cookie : cookies){
            if (PropiedadesVerificacion.COOKIE_SESION.equals(cookie.getName())){
                sesion.removeAttribute(PropiedadesVerificacion.COOKIE_SESION);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    private static boolean compararCookie(String atributo,HttpServletRequest request, HttpServletResponse response){
        HttpSession sesion = request.getSession();
        String valorSesion = (String) sesion.getAttribute(atributo);
        Cookie[] cookies = request.getCookies();
        if (valorSesion==null) return false;
        for (Cookie cookie : cookies){
            if (atributo.equals(cookie.getName())){
                String valorCookie = (String)cookie.getValue();
                return valorSesion.equals(valorCookie);
            }
        }
        return false;
    }
    public static boolean validarSesion(HttpServletRequest request, HttpServletResponse response){
        return compararCookie(PropiedadesVerificacion.COOKIE_SESION, request, response);
    }
    
    public static boolean validarDobleFactor(HttpServletRequest request, HttpServletResponse response){
        return compararCookie(PropiedadesVerificacion.COOKIE_VERIF, request, response);
    }
}
