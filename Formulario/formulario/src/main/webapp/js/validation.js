function validarFormulario() {
    var nombre = document.forms["formulario"]["nombre"].value;
    var email = document.forms["formulario"]["email"].value;
    var telefono = document.forms["formulario"]["telefono"].value;

    if (nombre == null || nombre === "" || email == null || email === "" || telefono == null || telefono === "") {
        alert("Todos los campos deben ser rellenados");
        return false;
    }
    return true;
}