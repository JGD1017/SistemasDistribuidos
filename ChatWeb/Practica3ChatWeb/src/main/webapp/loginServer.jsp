<!--Importamos la clase del servidor para poder llamar a los m�todos-->
<%@ page import="es.ubu.lsi.Practica3ChatWeb.ChatServer"%>
<!--Configuramos el bean en el entorno de la aplicaci�n y la clase del servidor-->
<jsp:useBean id="server" class="es.ubu.lsi.Practica3ChatWeb.ChatServer" scope="application" />
<!--Recuperamos todos los par�metros del bean-->
<jsp:setProperty name="server" property="*" />
<%
//Realizamos el login en el servidor
server.loginUser(request.getParameter("nickname"));
//Recuperamos la sesi�n del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//A�adimos en el par�metro nickname el nombre del usuario que inicia sesi�n
sesion.setAttribute("nickname", request.getParameter("nickname"));
//Saltamos a la p�gina del chat
response.sendRedirect("chat.jsp");
%>