
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletFormulario1InformacionCliente extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
		
		String nom = request.getParameter("nombre");
		String tef = request.getParameter("telefono");
		String lugNaci = request.getParameter("lugar de nacimiento");
                String accept = request.getHeader("Accept");
                String referer = request.getHeader("Referer");
                String acceptLanguage = request.getHeader("Accept-Language");
                String contentType = request.getHeader("Content-Type");
                String acceptEncoding = request.getHeader("Accept-Encoding");
                String userAgent = request.getHeader("User-Agent");
                String host = request.getHeader("Host");
                String contentLength = request.getHeader("Content-Length");
                String connection = request.getHeader("Conection");
                String cachecontrol = request.getHeader("Cache-Control");
                
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Datos introducidos por Formulario1</title></head");
		out.println("<body>");
		out.println("<h1>DATOS INTRODUCIDOS</h1>");
		out.println("<table border=\"5\">");
		out.println("<tr><td><b>NOMBRE:</b></td><td><i>" + nom + "</i></td></tr>");
		out.println("<tr><td><b>TELÉFONO:</b></td><td><i>" + tef + "</i></td></tr>");
		out.println("<tr><td><b>LUGAR NACIMIENTO:</b></td><td><i>" + lugNaci + "</i></td></tr>");
		out.println("</table><p>");
                
                out.println("<h3>Información sobre la petición</h3>");
                out.println("<br><b>Protocolo de la peticion: </b>" + request.getProtocol());
                out.println("<br><b>Nombre del cliente: </b>" + request.getRemoteHost());      
                out.println("<br><b>Direccion ip del cliente: </b>" + request.getRemoteAddr());                
                out.println("<br><b>Clave del usuario que realiza la petición: </b>" + request.getRemoteUser());                
                out.println("<br><b>Tipo de petición: </b>" + request.getMethod() );                
                out.println("<br><b>Tipo MIME usado por el cliente: </b>" + request.getContentType());                
                out.println("<br><b>Cadena de parámetros de la petición: </b>" + request.getQueryString());                
                out.println("<br><b>URI de la petición: </b>" + request.getRequestURI());                
                out.println("<br><b>URL  de la petición: </b>" + request.getRequestURL());                
                out.println("<br><b>Ruta URI del servlet: </b>" + request.getServletPath());                
                out.println("<br><b>Nombre del servidor Web que recibe la petición: </b>" + request.getServerName());                
                out.println("<br><b>Numero del puerto por el que el servidor acepta la conexión del cliente: </b>" + request.getRemotePort());                
               
                out.println("<br> <br><b>Encabezados o headers asociados a la petición: </b>");                
                out.println("<table border=\"5\">");
		out.println("<tr><td><b>accept: </b></td><td><i>" + accept + "</i></td></tr>");
		out.println("<tr><td><b>referer: </b></td><td><i>" + referer + "</i></td></tr>");
		out.println("<tr><td><b>accept-language: </b></td><td><i>" + acceptLanguage + "</i></td></tr>");
                out.println("<tr><td><b>content-type: </b></td><td><i>" + contentType + "</i></td></tr>");
                out.println("<tr><td><b>accept-encoding: </b></td><td><i>" + acceptEncoding + "</i></td></tr>");
                out.println("<tr><td><b>user-agent: </b></td><td><i>" + userAgent + "</i></td></tr>");
                out.println("<tr><td><b>host: </b></td><td><i>" + host + "</i></td></tr>");
                out.println("<tr><td><b>content-length: </b></td><td><i>" + contentLength + "</i></td></tr>");
                out.println("<tr><td><b>connection: </b></td><td><i>" + connection + "</i></td></tr>");
                out.println("<tr><td><b>cache-control: </b></td><td><i>" + cachecontrol + "</i></td></tr>");
		out.println("</table><p>");
                out.println("<br><a href=\"./\">Inicio</a>");
		out.println("</body>");
		out.println("</html>");
	}
}
