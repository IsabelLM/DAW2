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
public class ServletConsultaTablaModificar1 extends HttpServlet {

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
            String sql = "";
            ResultSet rs = stmt.executeQuery("SELECT * FROM facturamecanica ");
            int contador = rs.getMetaData().getColumnCount();
            sql = "UPDATE facturamecanica SET ";
            
            for (int x = 2; x <= contador; x++) {
                sql += "`" + rs.getMetaData().getColumnName(x) + "` = ";
                if (rs.getMetaData().getColumnTypeName(x).equals("VARCHAR")) {
                    sql += " '" + request.getParameter(rs.getMetaData().getColumnName(x)) + "'";
                } else {
                    sql += request.getParameter(rs.getMetaData().getColumnName(x));
                }
                sql += ",";
            }
            if (sql.lastIndexOf(",") == sql.length() - 1) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += " WHERE id= " + id;
            
            out.println(sql);
            stmt.executeUpdate(sql);
            request.getRequestDispatcher("ServletConsultaTablaEditar").forward(request, response);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletConsultaTablaModificar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletConsultaTablaModificar1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
