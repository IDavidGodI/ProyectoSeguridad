package controlador;

import Comunes.Formularios;
import Comunes.PropiedadesEnvios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.StringWriter;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
@WebServlet(name = "envioCorreos", urlPatterns = {"/envioCorreos"})
public class envioCorreos extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoVerificacion = GeneradorCodigos.generarCodigoVerificacion();
        String emailDestino =(String) request.getAttribute("destinatario");
        request.setAttribute(PropiedadesEnvios.COD_VERIFICACION, codigoVerificacion);
        request.setAttribute("imagenCorreo", "https://media.tenor.com/_4v3Nx_hzjwAAAAM/peepo.gif");
        final StringWriter writer = new StringWriter();
        getServletContext().getRequestDispatcher("/correoVerificacion.jsp").include(request, new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(writer);
            }
        });
                String contenidoCorreo = writer.toString();
        System.out.println(contenidoCorreo);

        if (ControladorCorreo.enviarEmail(emailDestino, "Codigo de verificacion", contenidoCorreo))
        {
            HttpSession sesion = request.getSession();
            System.out.println("Codigo generado: "+codigoVerificacion);
            CodigoVerificacion.setCodigoVerificacion(request, codigoVerificacion, 1);
            sesion.setAttribute(Formularios.CORREO_ENVIADO,request.getAttribute(Formularios.CORREO_ENVIADO));
            sesion.setAttribute(Formularios.CLAVE_ENVIADA,request.getAttribute(Formularios.CLAVE_ENVIADA));
            sesion.setAttribute(PropiedadesEnvios.USUARIO_REGISTRADO,request.getAttribute(PropiedadesEnvios.USUARIO_REGISTRADO));
            response.sendRedirect("FactorAutenticacion.jsp");
            return;
        }
        request.removeAttribute("codVerificacion");
        request.removeAttribute("imagenCorreo");
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
