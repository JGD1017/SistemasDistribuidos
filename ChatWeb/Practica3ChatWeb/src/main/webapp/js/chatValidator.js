//Refrescamos elchat cada segundo
setInterval(refrescarChat, 1000);

//Funcion que actualiza el contenido del iframe que contiene los mensajes
function refrescarChat() {
	frames[0].location.reload(true);
}

//Comprobamos que los campos son válidos ya que no podemos usar required al haber
//dos posibilidades de acciones en el formulario: mensajes o baneos
function validarFormulario() {
	let mensaje = document.forms["formChat"]["mensaje"].value
	let usuario = document.forms["formChat"]["usuario"].value
	if ((!mensaje || mensaje == '') && (!usuario || usuario == '')) {
		alert("Debe escribirse un mensaje para enviar o un usuario para ban/uban")
		return false;
	}
	return true;
}
