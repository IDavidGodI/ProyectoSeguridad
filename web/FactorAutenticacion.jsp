<%-- 
    Document   : FactorAutenticacion
    Created on : 25 abr 2023, 17:09:30
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.List"%>
<%@page import = "Comunes.Formularios"%>
<!DOCTYPE html>
<html>
<head>
    <title>Autenticación de código</title>
    <link rel="stylesheet" href="formulario.css">
</head>
<body>
    <%
        String estadoCodigo = (String) request.getAttribute(Formularios.ESTADO_COD_VERIF);
    %>
    <div class="container">
        <h2>Autenticación de código</h2>
        <p>A tu correo fue enviado un codigo de autenticación de 6 dígitos, escribe dicho código:</p>
        <form method="post" action="FactorAutenticacion">
                <label for="code">Código:</label>
                <input type="text" name="codigoVerificacion" estado=<%=estadoCodigo%> id="codigoVerificacion"><br><br>
                <input type="submit" value="Autenticar">
        </form>
        <jsp:include page="ListaErrores.jsp" />
        
    </div>
</body>
</html>

