package es.ubu.lsi.formulario;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Muestra información recibida del formulario
 */
@WebServlet("/ProcesarFormulario")
public class ProcesarFormulario extends HttpServlet{

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 13L;

	/**
	 *
	 * Hacer operación post
	 *
	 * @param request petición  
	 * @param response respuesta
	 */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Fijamos el tipo de respuesta añadiendo compatibilidad con acentos y eñes
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
    	// Abrimos el canal
    	PrintWriter out = response.getWriter();
		
		// Recuperamos los datos del formulario
    	String nombre = request.getParameter("nombre");
    	String email = request.getParameter("email");
    	String telefono = request.getParameter("telefono");
    	
    	String message = null;
	    
    	// Usamos el calendario para saber si es de día o de noche y personalizar la respuesta
    	GregorianCalendar calendar = new GregorianCalendar();
    	if(calendar.get(Calendar.AM_PM) == Calendar.AM) {
      		message = "Buenos días";
    	} else {
      		message = "Buenas noches";
    	}
	    	
    	// Generamos el html de la respuesta
	    out.println("<html>");
	    // Cabecera
	    out.println("<head>");
	    out.println("<title>Datos del formulario</title>");
	    out.println("</head>");
	    // Cuerpo del mensaje
	    out.println("<body>");
	    out.println("<h2>Datos recibidos desde el formulario:</h2>");
	    out.println("<p>" + message + ", " + nombre + "</p>");
		// Datos del formulario
	    out.println("<p>  Gracias por registrar tu email (" + email + ") con nosotros.</p>");	    
	    out.println("<p>  Gracias por registrar tu teléfono (" + telefono + ") con nosotros.</p>");	    
	    // Fin del cuerpo
	    out.println("</body>");
	    out.println("</html>");
		    
		// Cerramos el canal
    	out.close();
  	}
	
	// Inicializacion del servlet
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
	}
	
	
}
