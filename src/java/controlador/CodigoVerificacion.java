/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import java.util.Date;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSession;

public class CodigoVerificacion implements HttpSessionBindingListener {
    private Date fechaCreacion;
    private float tiempoLimite;
    
    public CodigoVerificacion(float tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
        
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        
        this.fechaCreacion = new Date();
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        // Se compara la fecha y hora actual con la fecha y hora de creación del atributo
        Date fechaActual = new Date();
        long tiempoTranscurrido = fechaActual.getTime() - fechaCreacion.getTime();
        if (tiempoTranscurrido > (tiempoLimite * 60 * 1000)) {
            
            HttpSession session = event.getSession();
            session.removeAttribute("codigoVerificacion");
//            session.removeAttribute("codigoVerificacion");
        }
    }
}

