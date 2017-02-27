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
public class ServletConsultaTablaInsertar1 extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
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
            String sql, col = "", valores = "";
            ResultSet rs = stmt.executeQuery("SELECT * FROM facturamecanica");
            int contador = rs.getMetaData().getColumnCount();
            for (int i = 2; i <= contador; i++) {
                if (rs.getMetaData().getColumnTypeName(i).equals("VARCHAR")) {
                    valores += "'" + request.getParameter(rs.getMetaData().getColumnName(i)) + "'";
                } else {
                    valores += request.getParameter(rs.getMetaData().getColumnName(i));
                }
                valores += ",";
            }
            if (valores.lastIndexOf(",") == valores.length() - 1) {
                valores = valores.substring(0, valores.length() - 1);
            }
            for (int x = 2; x <= contador; x++) {
                col += rs.getMetaData().getColumnName(x) + ",";
            }
            if (col.lastIndexOf(",") == col.length() - 1) {
                col = col.substring(0, col.length() - 1);
            }
            sql = "INSERT INTO facturamecanica (" + col + ") VALUES (" + valores + ")";
            stmt.executeUpdate(sql);
            request.getRequestDispatcher("ServletConsultaTablaEditar").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletConsultaTablaInsertar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletConsultaTablaInsertar1.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletConsultaTablaInsertar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletConsultaTablaInsertar1.class.getName()).log(Level.SEVERE, null, ex);
        }
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
