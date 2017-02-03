import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletEnlace extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        response.setContentType("text/html");

        PrintWriter out=response.getWriter();

        out.println("<html>");
        out.println("<head><title>ServletEnlace</title></head>");
        out.println("<body bgcolor=\"yellow\">");
        out.println("<h2>Vienes de pulsar el enlace Púlsame\"</h2>");
        out.println("<img src=\"./tomcat.gif\">");
        out.println("<br><br><a href=\"./\">Inicio</a>");
        out.println("</body>");
        out.println("</html>");
    }
}