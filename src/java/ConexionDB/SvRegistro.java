package ConexionDB;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
public class SvRegistro extends HttpServlet {
    Pattern regexCorreo= Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
    Pattern regexClave= Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$");
    Matcher comprobar;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String confirm_clave = request.getParameter("confirm_clave");
        comprobar = regexCorreo.matcher(correo);
        boolean registroCompleto = true;
        ArrayList<String> errores = new ArrayList<>();
        sesion.removeAttribute("estadoCorreo");
        sesion.removeAttribute("estadoClave");
        sesion.removeAttribute("estadoConfirmClave");
        sesion.removeAttribute("ListaErrores");
        if (!comprobar.find()){
            sesion.setAttribute("estadoCorreo", "error");
            errores.add("El correo ingresado no es valido");
            registroCompleto=false;
        }
        comprobar = regexClave.matcher(clave);
        if (!comprobar.find()){
            sesion.setAttribute("estadoClave", "error");
            errores.add("Ingrese una contraseña mas segura (Al menos una mayusula, una minuscula, al menos un caracter especial y un caracter numerico)");
            registroCompleto = false;
        }
        if (!clave.equals(confirm_clave)){
            sesion.setAttribute("estadoConfirmClave", "error");
            errores.add("Las contraseñas no coinciden");
            registroCompleto=false;
        }
        sesion.setAttribute("ListaErrores", errores);
        if (registroCompleto)response.sendRedirect("index.jsp");
        else response.sendRedirect("registro.jsp");
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
