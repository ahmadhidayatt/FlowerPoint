/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ahmad
 */
public class helper_masalah extends HttpServlet {

    public final String retrieve_masalah = "0";
    public final String insert_masalah = "1";
    public final String update_masalah = "2";
    private Connection conn;

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet masalah_helper</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet masalah_helper at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setContentType("text/html");
        conn = new connection().getConn();
        PrintWriter out = response.getWriter();
        String hasil = null;
        Connection con;
        Statement stmt;
        String Kodeaut = "";
        ResultSet rs;
        CallableStatement st;

        String code = request.getParameter("code");

        try {
            if (code.equals(retrieve_masalah)) {
                String query = "{call retrieve_masalah()}";
                st = conn.prepareCall(query);
                rs = st.executeQuery();
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String id_masalah = rs.getString("id_masalah");
                    String nama_masalah = rs.getString("nama_masalah");
                    String deskripsi = rs.getString("deskripsi");

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("id_masalah", id_masalah);
                    arrayObj.put("nama_masalah", nama_masalah);
                    arrayObj.put("deskripsi", deskripsi);

                    jArray.add(i, arrayObj);
                    i++;
                }
                rs.close();

                hasil = jArray.toString();
                out.print(hasil);
            }
            conn.close();
        } catch (SQLException sx) {
            hasil = sx.toString();
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
