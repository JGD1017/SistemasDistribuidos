<!--Importamos la clase del servidor para poder llamar a los m�todos-->
<%@ page import="es.ubu.lsi.Practica3ChatWeb.ChatServer"%>
<!--Configuramos el bean en el entorno de la aplicaci�n y la clase del servidor-->
<jsp:useBean id="server" class="es.ubu.lsi.Practica3ChatWeb.ChatServer" scope="application" />
<!--Recuperamos todos los par�metros del bean-->
<jsp:setProperty name="server" property="*" />

<%
//Recuperamos la sesi�n del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesi�n el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");

//Obtenemos el mensaje a enviar
String mensaje = request.getParameter("mensaje");

//Si el mensaje no est� vac�o lo mandamos
if (!mensaje.isEmpty()) {
	server.sendMessagge(nickname, mensaje);
} else {
	//Si el mensaje est� vac�o es porque queremos banear o desbanear un usuario
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