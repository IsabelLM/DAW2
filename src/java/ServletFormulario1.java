
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletFormulario1 extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
		
		String nom = request.getParameter("nombre");
		String tef = request.getParameter("telefono");
		String lugNaci = request.getParameter("lugar de nacimiento");

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Datos introducidos por Formulario1</title></head");
		out.println("<body>");
		out.println("<h1>DATOS INTRODUCIDOS</h1>");
		out.println("<table border)\"5\">");
		out.println("<tr><td><b>NOMBRE:</b></td><td><i>" + nom + "</i></td></tr>");
		out.println("<tr><td><b>TELÉFONO:</b></td><td><i>" + tef + "</i></td></tr>");
		out.println("<tr><td><b>LUGAR NACIMIENTO:</b></td><td><i>" + lugNaci + "</i></td></tr>");
		out.println("</table><p>");
		out.println("</body>");
		out.println("</html>");
	}
}
