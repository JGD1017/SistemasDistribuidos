//Validacion del formulario
function validarFormulario() {
	// Valores de los campos
    var nombre = document.forms["formulario"]["nombre"].value;
    var email = document.forms["formulario"]["email"].value;
    var telefono = document.forms["formulario"]["telefono"].value;

	// Expresiones regulares
	var nombreRegEx = /^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$/;
    var emailRegEx = /^\w+([.-_+]?\w+)*@\w+([.-]?\w+)*(\.\w{2,10})+$/;
    var telefonoRegEx = /^\d{9}$/;  // Asumiendo que el teléfono tiene 9 dígitos

	//Valida si el campo tiene un valor
    if (nombre == null || nombre === "") {
        alert("El campo nombre debe tener un valor");
        return false;
    //Valida que sea un nombre valido
    } else if (!nombre.match(nombreRegEx)) {
        alert("El nombre solo debe contener letras y espacios");
        return false;
    }

	//Valida que el campo tenga un valor
    if (email == null || email === "") {
        alert("El campo email debe tener un valor");
        return false;
    //Valida que el email sea valido
    } else if (!email.match(emailRegEx)) {
        alert("Por favor, introduce un email válido");
        return false;
    }

	//Valida que el campo tenga un valor
    if (telefono == null || telefono === "") {
        alert("El campo teléfono debe tener un valor");
        return false;
    //Valida que el teléfono sea válido
    } else if (!telefono.match(telefonoRegEx)) {
        alert("El teléfono debe contener exactamente 9 dígitos");
        return false;
    }

    return true;
}