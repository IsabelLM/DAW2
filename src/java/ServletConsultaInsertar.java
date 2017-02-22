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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class ServletConsultaInsertar extends HttpServlet {

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

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tallermecanico";
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt;
            stmt = con.createStatement();
            insertarRegistro(con, stmt, out, request);
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
        out.println("<br><a href=\"./FormularioConsulta_1.html\">Volver</a>");

    }

    public void insertarRegistro(Connection con, Statement stmt, PrintWriter out, HttpServletRequest request) throws SQLException {
        int cont = 0;
        String arrayCampos[];
        String sentenciaSQL2 = "SELECT * FROM information_schema.columns WHERE table_name = 'facturamecanica'";
        ResultSet rs = stmt.executeQuery(sentenciaSQL2);

        //Bucle para sacar el nombre de las columnas que hay actualmente en la tabla
        out.println("<form>");
        out.println("<table style=\"text-align:center;\" border=\"5\">");
        rs.first();

        while (rs.next()) {
            cont++;
        }
        
        arrayCampos = new String[cont];

        rs.first();

        while (rs.next()) {
            int i = 0;
            String nombreColumna = rs.getString("COLUMN_NAME");
            out.print("<tr><td><b>" + nombreColumna + "</b></td></tr>");
            out.println("<td><input type=\"text\" name=\"campo" + cont + "\"><br></td>");
            arrayCampos[i] = nombreColumna;
            i++;
        }

        out.println("<input type=\"submit\">");
        out.println("</table>");
        out.println("</form>");

        //("INSERT INTO `facturamecanica`  VALUES (NULL, '" + nombre + "', '" + marca + "', '" + importe + "')");
        for (int i = 0; i < cont; i++) {
            String nombreCampo[] = new String[cont];
            nombreCampo[i] = request.getParameter("campo" + cont);
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
