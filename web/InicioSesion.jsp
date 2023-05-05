<%-- 
    Document   : InicioSesion
    Created on : 25 abr 2023, 16:14:51
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "Comunes.Formularios"%>
<%@page import = "controlador.AutenticacionUsuarios"%>
<!DOCTYPE html>
<%
    if (AutenticacionUsuarios.validarSesion(request, response)) response.sendRedirect("index.jsp");
%>
<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="css/formulario.css" type = "text/css">
</head>
<body>
    <%
            String estadoCorreo = (String) request.getAttribute(Formularios.ESTADO_CORREO);
            String estadoClave = (String) request.getAttribute(Formularios.ESTADO_CLAVE);
            
            String iCorreo = (String) request.getAttribute(Formularios.CORREO_ENVIADO);
            String iClave = (String) request.getAttribute(Formularios.CLAVE_ENVIADA);
    %>
    <div class="container">
        <h2>Iniciar Sesión</h2>
        <form method="post" action="InicioSesion" >
                <label for="correo">Dirección de correo:</label>
                <input type="text" name="correo" id="correo" estado=<%=estadoCorreo%> value=<%=iCorreo!=null?iCorreo:""%>><br>
                <label for="password">Contraseña:</label>
                <input type="password" name="clave" id="clave" estado=<%=estadoClave%> value=<%=iClave!=null?iClave:""%>><br>
                <input type="submit" value="Iniciar Sesión">
                <p>¿Aun no tienes una cuenta? <a href="registro.jsp">Registrate</a></p>
        </form>
        <jsp:include page="ListaErrores.jsp" />
    </div>
</body>
</html>

