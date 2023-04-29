package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.StringWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "envioCorreos", urlPatterns = {"/envioCorreos"})
public class envioCorreos extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        response.sendRedirect("registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoVerificacion = GeneradorCodigos.generarCodigoVerificacion();
        String emailDestino =(String) request.getAttribute("destinatario");
        
        request.setAttribute("codVerificacion", codigoVerificacion);
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
            CodigoVerificacion limiteCodVerif = new CodigoVerificacion(1.5f);
            sesion.setAttribute("limiteCodVerif", limiteCodVerif);
            sesion.setAttribute("codigoVerificacion", codigoVerificacion);
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
