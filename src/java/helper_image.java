/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ASUS
 */
public class helper_image extends HttpServlet {
 public final String insert_image = "1";
    public final String update_image = "2";
    public final String retrieve_image = "3";
    public final String retrieve_status = "4";
    public final String retrieve_nik = "5";
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
            out.println("<title>Servlet helper_image</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet helper_image at " + request.getContextPath() + "</h1>");
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
       
        String hasil = null;
        Connection con;
        Statement stmt;
        String Kodeaut = "";
        ResultSet rs;
        CallableStatement st;

        String code = request.getParameter("code");
        conn = new connection().getConn();
        try {
             if (code.equals(update_image)) {
                response.setContentType("text/html");

                PrintWriter out = response.getWriter();

                Part foto = request.getPart("foto");
                InputStream fotos = foto.getInputStream();
                String nama = request.getParameter("nama");
                String alamat = request.getParameter("alamat");
                String no_telp = request.getParameter("no_telp");
                String nik = request.getParameter("nik");

                String query = "update tb_pegawai set nama=?,alamat=?,no_telp=?,foto=? where nik = ?";
                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, nama);
                statement.setString(2, alamat);
                statement.setString(3, no_telp);
                statement.setBlob(4, fotos);
                statement.setString(5, nik);

                statement.executeUpdate();

                hasil = "sukses";
                out.print(hasil);
            } else if (code.equals(retrieve_image)) {
                String id = request.getParameter("id");
                String query = "SELECT * FROM tb_produk where id_produk = " + id;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {
                     Blob blob = rs.getBlob("gambar");
                    byte[] imgData = blob.getBytes(1, (int) blob.length());
                    String encoded = javax.xml.bind.DatatypeConverter
                            .printBase64Binary(imgData);
                    response.setContentType("image/png");
                    response.getOutputStream().print(encoded);
                }

//                hasil = jArray.toString();
            }

            conn.close();
        } catch (SQLException sx) {
            hasil = sx.toString();
            out.print(hasil);
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
