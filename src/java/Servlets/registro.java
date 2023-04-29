package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import Comunes.Formularios;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "SvRegistro", urlPatterns = {"/registro"})
public class registro extends HttpServlet {
    String regexCorreo= "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    String regexClave= "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get");
        response.sendRedirect("registro.jsp");
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String confirm_clave = request.getParameter("confirm_clave");
        boolean registroCompleto = true;
        ArrayList<String> errores = new ArrayList<>();

        if (!correo.matches(regexCorreo)){
            request.setAttribute(Formularios.ESTADO_CORREO, "error");
            errores.add("El correo ingresado no es válido");
            registroCompleto = false;
        }
        if (!clave.matches(regexClave)){
            request.setAttribute(Formularios.ESTADO_CLAVE, "error");
            errores.add("Ingrese una contraseña más segura (al menos una mayúscula, una minúscula, al menos un caracter especial y un caracter numérico)");
            registroCompleto = false;
        }
        if (!clave.equals(confirm_clave) || confirm_clave.isEmpty()){
            request.setAttribute(Formularios.ESTADO_CONFIRM_CLAVE, "error");
            errores.add("Las contraseñas no coinciden");
            registroCompleto=false;
        }

        if (registroCompleto) {
            request.setAttribute(Formularios.ESTADO_CORREO, "");
            request.setAttribute(Formularios.ESTADO_CLAVE, "");
            request.setAttribute(Formularios.ESTADO_CONFIRM_CLAVE, "");
            System.out.print("Datos verificados");
            request.setAttribute("destinatario", correo);
            request.getRequestDispatcher("envioCorreos").forward(request, response);
        } else {
            request.setAttribute(Formularios.CORREO_ENVIADO, correo);
            request.setAttribute(Formularios.CLAVE_ENVIADA, clave);
            request.setAttribute(Formularios.LISTA_ERRORES, errores);
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
