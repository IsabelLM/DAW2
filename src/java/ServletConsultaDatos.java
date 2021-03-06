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
        String columnaEliminar = request.getParameter("nombreColumnaEliminar");
        String columnaAnadir = request.getParameter("nombreColumnaAnadir");

        String baseDatos = request.getParameter("nombreBaseDatos");
        String nombreTabla = request.getParameter("nombreTabla");
        String mostrarRegistrosTabla = request.getParameter("nombreTablaRegistros");

        String nombre = request.getParameter("nombre");
        String importe = request.getParameter("importe");
        String marca = request.getParameter("marca");

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tallermecanico";
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt;
            stmt = con.createStatement();

            //Ifs para saber qué opción ha escogido el usuario
            if (columnaEliminar != null) {
                String tabla = request.getParameter("nombreTablaEliminar");
                int nFilas = stmt.executeUpdate("Alter table " + tabla + " drop " + columnaEliminar);
                out.println("Filas afectadas: " + nFilas + "<br>");
                crearTablaEstructuraNombre(con, stmt, out, tabla);
            } else if (columnaAnadir != null) {
                String tipo = request.getParameter("tipo");
                String tabla = request.getParameter("nombreTablaAnadir");
                int nFilas = stmt.executeUpdate("Alter table " + tabla + " add " + columnaAnadir + " " + tipo);
                out.println("Filas afectadas: " + nFilas + "<br>");
                crearTablaEstructuraNombre(con, stmt, out, tabla);
            } else if (baseDatos != null) {
                mostrarBD(con, stmt, out, baseDatos);
            } else if (nombreTabla != null) {
                crearTablaEstructuraNombre(con, stmt, out, nombreTabla);
            } else if (mostrarRegistrosTabla != null) {
                mostrarContenidoTabla(con, stmt, out, mostrarRegistrosTabla);
            } else if (nombre != null && importe != null && marca != null) {
                int nFilas = stmt.executeUpdate("INSERT INTO `facturamecanica` (`Id`, `Nombre`, `Marca`, `Importe`) VALUES (NULL, '" + nombre + "', '" + marca + "', '" + importe + "')");
                out.println("Filas afectadas: " + nFilas + "<br>");
                mostrarContenidoTabla(con, stmt, out, "facturamecanica");

            }

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

    public void crearTablaEstructuraNombre(Connection con, Statement stmt, PrintWriter out, String nombreTabla) throws SQLException {
        String sentenciaSQL = "SELECT * FROM information_schema.columns WHERE table_name = '" + nombreTabla + "'";
        ResultSet rs = stmt.executeQuery(sentenciaSQL);
        String nombreColumna, tipo, pk;

        out.println("<table style=\"text-align:center;\" border=\"5\">");

        if (rs.next()) {
            String nombreBase;
            nombreBase = rs.getString("TABLE_SCHEMA");
            out.println("<tr><td colspan=\"3\">" + "<b>Nombre Base de Datos</b>" + "</td></tr>");
            out.println("<tr><td colspan=\"3\">" + nombreBase + "</td></tr>");
            nombreTabla = rs.getString("TABLE_NAME");
            out.println("<tr><td colspan=\"3\">" + "<b>Nombre de tabla</b>" + "</td></tr>");
            out.println("<tr><td colspan=\"3\">" + nombreTabla + "</td></tr>");

        }

        rs = stmt.executeQuery(sentenciaSQL);
        out.println("<tr><td><b>Nombre Columna</b></td><td><b>Tipo</b></td><td><b>Primary Key</b></td></tr>");

        while (rs.next()) {
            nombreColumna = rs.getString("COLUMN_NAME");
            tipo = rs.getString("COLUMN_TYPE");
            pk = rs.getString("COLUMN_KEY");
            out.println("<tr><td>" + nombreColumna + "</td><td>" + tipo + "</td><td>" + pk + "</td></tr>");
        }

        out.println("</table>");
    }

    public void mostrarBD(Connection con, Statement stmt, PrintWriter out, String baseDatos) throws SQLException, ClassNotFoundException {
        DatabaseMetaData metadatos;
        String url = "jdbc:mysql://127.0.0.1:3306/" + baseDatos;
        con = DriverManager.getConnection(url, "root", "");
        metadatos = con.getMetaData();
        String aux = null;

        ResultSet tablas = metadatos.getTables(null, null, null, null);
        ArrayList arrayTablas = new ArrayList();
        while (tablas.next()) {
            arrayTablas.add(tablas.getObject(3).toString());
        }
        tablas.close();

        out.println("<body>");
        out.println("<h3>" + baseDatos + "</h3>");
        for (int i = 0; i < arrayTablas.size(); i++) {
            aux = arrayTablas.get(i).toString();
            //Sacar informacion del nombre de la tabla
            out.println("<table style=\"text-align:center;\" border=\"5\">");
            out.println("<td><b>" + aux + "</b></th>");
            out.println("<tr>");
            //Sacar los nombres de las columnas
            ResultSet cols = metadatos.getColumns(null, null, aux, null);
            while (cols.next()) {
                out.println("<td>" + cols.getString(4) + "</td>");
            }
            cols.close();
            out.println("</tr>");
            out.println("</table>");
            out.println("<br>");
        }
        out.println("</body>");
    }

    public void mostrarContenidoTabla(Connection con, Statement stmt, PrintWriter out, String nombreTabla) throws SQLException {
        String sentenciaSQL = "SELECT * FROM facturamecanica";
        String sentenciaSQL2 = "SELECT * FROM information_schema.columns WHERE table_name = '" + nombreTabla + "'";
        ResultSet rs = stmt.executeQuery(sentenciaSQL);
        int contador = 0;

        out.println("<table style=\"text-align:center;\" border=\"5\">");

        if (rs.next()) {
            out.println("<tr><td colspan=\"5\">" + "<b>Nombre</b>" + "</td></tr>");
            out.println("<tr><td colspan=\"5\">" + nombreTabla + "</td></tr>");
        }

        rs = stmt.executeQuery(sentenciaSQL2);
        while (rs.next()) {
            contador++;
        }
        rs.beforeFirst();

        //Bucle para sacar el nombre de las columnas que hay actualmente en la tabla
        while (rs.next()) {
            String nombreColumna = rs.getString("COLUMN_NAME");
            out.println("<td><b>" + nombreColumna + "</b></td>");
        }
        rs = stmt.executeQuery(sentenciaSQL);

        rs.beforeFirst();

        out.println("<tr>");
        //Bucle para sacar los registros
        for (int i = 1; rs.next(); i++) {
            for (int j = 1; j <= contador; j++) {
                String parametro;
                parametro = rs.getString(j);
                out.println("<td>" + parametro + "</td>");
                if (j == contador) {
                    out.println("</tr>");
                }
            }
        }
        out.println("</table>");
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
