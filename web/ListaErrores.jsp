<%-- 
    Document   : ListadoErrores
    Created on : 29 abr 2023, 0:21:25
    Author     : Lenovo
--%>

<%@ page import="java.util.List" %>
<%@ page import="Comunes.Formularios" %>

<ul class="ListaError">
    <% 
        List<String> errores = (List<String>) request.getAttribute(Formularios.LISTA_ERRORES);
        if (errores != null)
            for (String error : errores) { 
    %>
                <li>
                    <p class="TextoError"><%= error %></p>
                </li>
        <% } %>
</ul>

