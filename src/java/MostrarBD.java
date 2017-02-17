/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class MostrarBD extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    static DatabaseMetaData metadatos;
    static ResultSetMetaData resultDatos;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/micms";
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();
            metadatos = con.getMetaData();
            //String colText="";
            String aux=null;
            
            ResultSet tablas=metadatos.getTables(null, null, null, null);
            ArrayList aTablas= new ArrayList();
            int countt=0;
            while (tablas.next()) {               
                aTablas.add(tablas.getObject(3).toString());
            }
            tablas.close();            
            
            out.println("<body>");
            out.println("<h3>Tablas de Tallermecanico</h3>");
            for (int i = 0; i < aTablas.size(); i++) {
                aux=aTablas.get(i).toString();
                out.println("<table style=\"text-align:center;\" border=\"5\">");
                out.println("<th colspan='20' >" + aux + "</th>");
                out.println("<tr>");
                
                ResultSet cols= metadatos.getColumns(null, null, aux, null);
                while(cols.next()){
                    out.println("<td>" + cols.getString(4) + "</td>");
                }       
                cols.close();
                out.println("</tr>");
                out.println("</table>");
                out.println("<br>");
            }
            out.println("</body>");
        } catch (ClassNotFoundException e) {
            out.println("Controlador JDBC no encontrado: " + e.toString());
        } catch (SQLException e) {
            out.println("Excepcion capturada de SQL: " + e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                out.println("No se puede cerrar la conexion: " + e.toString());
            }
        }
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
