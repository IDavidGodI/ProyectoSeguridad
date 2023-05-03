<%-- 
    Document   : registro
    Created on : 25 abr 2023, 16:48:25
    Author     : Lenovo
--%>
<%@page import = "java.util.List"%>
<%@page import = "Comunes.Formularios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrarse</title>
    <link rel="stylesheet" href="formulario.css">
</head>
<body>
    <div class="container">
        
        <%
            
            String estadoCorreo = (String) request.getAttribute(Formularios.ESTADO_CORREO);
            String estadoClave = (String) request.getAttribute(Formularios.ESTADO_CLAVE);
            String estadoConfirmClave = (String) request.getAttribute(Formularios.ESTADO_CONFIRM_CLAVE);
            
            String iCorreo = (String) request.getAttribute(Formularios.CORREO_ENVIADO);
            String iClave = (String) request.getAttribute(Formularios.CLAVE_ENVIADA);
            
            

        %>
            
        <h2>Registrarse</h2>
        <!--<p>En Perros salchicha voladores gatunos cats pio pio muuu</p>-->
        
        <form method="post" action="registro">
                <label for="correo">Dirección de correo:</label>
                <input type="text" name="correo" id="correo" estado=<%=estadoCorreo%> value=<%=iCorreo!=null?iCorreo:""%>><br>
                <label for="password">Contraseña:</label>
                <input type="password" name="clave" id="clave" estado=<%=estadoClave%> value=<%=iClave!=null?iClave:""%>><br>
                <label for="confirm_password">Confirmar Contraseña:</label>
                <input type="password" name="confirm_clave" id="confirm_clave" estado=<%=estadoConfirmClave%>><br><br>
                <input type="submit" value="Registrarse">
                <p>¿Ya tienes una cuenta? <a href="InicioSesion.jsp"> Inicia sesion</a></p>
        </form>
        <jsp:include page="ListaErrores.jsp" />

    </div>

</body>
</html>

