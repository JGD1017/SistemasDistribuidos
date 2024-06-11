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

//Obtenemos el mensaje a enviar
String mensaje = request.getParameter("mensaje");

//Si el mensaje no está vacío lo mandamos
if (!mensaje.isEmpty()) {
	server.sendMessagge(nickname, mensaje);
} else {
	//Si el mensaje está vacío es porque queremos banear o desbanear un usuario
	String usuarioElegido = request.getParameter("usuario");

	//Comprobamos si vamos a banear o desvanear
	String accion = request.getParameter("control");
	if (accion.equals("bloquear")) {
		//Baneamos al usuario
		server.banUnban(nickname, usuarioElegido, true);
	} else {
		//Desbaneamos el usuario
		server.banUnban(nickname, usuarioElegido, false);
	}
}

//Regresamos al chat
response.sendRedirect("chat.jsp");
%>