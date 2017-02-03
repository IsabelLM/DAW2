/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.DateFormat;

/**
 *
 * @author bernardo
 */
public class PrimerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() throws ServletException {
        Date ahora = new Date();

//Escribe en tomcat_home\logs\localhost_log.xxxx-xx-xx.txt
//Cada día, se crea un fichero nuevo con la fecha correspondiente
        log("PrimerServlet inicializado el " + ahora);

//Escribe en tomcat_home\logs\stdout.txt
//Este fichero es como la consola del DOS en aplicaciones cliente
        System.out.println("Mensaje lanzado desde el init");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
//Captura de parámetro mediante el método String getParameter(String name)
//de javax.servlet.ServletRequest, pasándole el name correspondiente.
//Si dicho name no existe, devuelve null.

//Asignación a la respuesta html que va a generarse, del tipo MIME
//Generación de la respuesta html
            out.println("<html>");
            out.println("<head><title>Primer servlet</title></head>");
            out.println("<body>");
            out.println("<h1>Hola, " + nombre + "</h1>");
            out.println("<h1>" + getServletName() + " funcionando</h1>");
            DateFormat dfEspañol = DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.LONG,
                    Locale.getDefault());
            out.println("<h3>Fecha: " + dfEspañol.format(new Date()) + "</h3>");
            out.println("Este es un servlet muy sencillo");
            out.println("<br><br><a href=\"./\">Inicio</a>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    public void destroy() {
        Date d = new Date();
        log("PrimerServlet fuera de servicio el " + d);
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
