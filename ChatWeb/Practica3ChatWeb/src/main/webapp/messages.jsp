<!--Importamos la clase del servidor para poder llamar a los métodos-->
<%@ page import="es.ubu.lsi.Practica3ChatWeb.ChatServer"%>
<!--Configuramos el bean en el entorno de la aplicación y la clase del servidor-->
<jsp:useBean id="server" class="es.ubu.lsi.Practica3ChatWeb.ChatServer" scope="application" />
<!--Recuperamos todos los parámetros del bean-->
<jsp:setProperty name="server" property="*" />

<%
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesión el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");

//Recuperamos los mensajes del servidor para el usuario activo
String mensajes = server.getMessagesFromNickname(nickname);
%>

<html>
	<body style="font-family: Arial, sans-serif;">
		<%=mensajes%>
	</body>
</html>