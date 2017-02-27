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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Isabel
 */
public class ServletConsultaTablaModificar extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/tallermecanico";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();
            metadatos = con.getMetaData();
            String id = request.getParameter("id");
            ResultSet rs = stmt.executeQuery("SELECT * FROM facturamecanica WHERE Id=" + id);

            out.println("<form action='ServletConsultaTablaModificar1' method='post'>");
            out.println("<table style=\"text-align:center;\" border=\"0\"><tr>");
            for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                out.println("<td><b>" + rs.getMetaData().getColumnName(i) + "<b></td>");
            }
            out.println("</tr><tr>");
            while (rs.next()) {
                for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                    out.println("<td><input type='text' name='" + rs.getMetaData().getColumnName(i) + "' value='" + rs.getString(i) + "'/></th>");
                }
            }
            out.println("</tr></table><input type='hidden' name='id' value='" + id + "'/>");
            out.println("<input type='hidden' value='facturamecanica' name='tabla'>");
            out.println("<input type='submit' value='Actualizar' name='actualizar'/>");
            out.println("</form>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletConsultaTablaModificar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletConsultaTablaModificar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServletConsultaTablaModificar.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.close();
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
