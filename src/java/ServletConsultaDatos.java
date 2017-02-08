/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class ServletConsultaDatos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String consulta = request.getParameter("consulta");

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tallermecanico";
        Connection con = null;
        /*String conductor, marca;
        int importe;*/
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");

            Statement stmt;
            stmt = con.createStatement();
            int nFilas = stmt.executeUpdate(consulta);
            out.println("Filas afectadas: " + nFilas + "<br>");

            String sentenciaSQL = "SELECT * FROM information_schema.columns WHERE table_name = 'facturamecanica'";
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            String nombreColumna, tipo, nombreTabla;

            out.println("<table border=\"5\">");

            if (rs.next()) {
                nombreTabla = rs.getString("TABLE_SCHEMA");
                out.println("<tr><td colspan=\"2\">" + nombreTabla + "</td></tr>");
            }

            out.println("<tr><td><b>Nombre Columna</b></td><td><b>Tipo</b></td></tr>");

            while (rs.next()) {
                nombreColumna = rs.getString("COLUMN_NAME");
                tipo = rs.getString("COLUMN_TYPE");
                out.println("<tr><td>" + nombreColumna + "</td><td>" + tipo + "</td></tr>");
            }
            
            out.println("</table>");

        } catch (ClassNotFoundException e) {
            System.out.println("Controlador JDBC no encontrado: " + e.toString());
        } catch (SQLException e) {
            System.out.println("Excepcion capturada de SQL: " + e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("No se puede cerrar la conexion: " + e.toString());
            }
        }
        out.println("<br><a href=\"./\">Inicio</a>");
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
