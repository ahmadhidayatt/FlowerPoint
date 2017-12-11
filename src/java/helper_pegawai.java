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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ahmad
 */
@MultipartConfig
public class helper_pegawai extends HttpServlet {

    public final String retrieve_pegawai = "0";
    public final String insert_pegawai = "1";
    public final String update_pegawai = "2";
    public final String retrieve_foto = "3";
     public final String retrieve_pegawai_id = "4";
    
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
            out.println("<title>Servlet helper_pegawai</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet helper_pegawai at " + request.getContextPath() + "</h1>");
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
            if (code.equals(retrieve_pegawai)) {
                response.setContentType("text/html");

                PrintWriter out = response.getWriter();
 
                String query = "{call retrieve_pegawai()}";
                st = conn.prepareCall(query);
                rs = st.executeQuery();
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String nik = rs.getString("nik");
                    String nama = rs.getString("nama");
                    String status = rs.getString("status");
                    String tanggal = rs.getString("tanggal");
                    String alamat = rs.getString("alamat");
                    String jabatan = rs.getString("jabatan");
                    String no_telp = rs.getString("no_telp");
                    String regional = rs.getString("regional");
                    

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("nik", nik);
                    arrayObj.put("nama", nama);
                    arrayObj.put("status", status);
                    arrayObj.put("tanggal", tanggal);
                    arrayObj.put("alamat", alamat);
                    arrayObj.put("jabatan", jabatan);
                    arrayObj.put("no_telp", no_telp);
                    arrayObj.put("regional", regional);
                   

                    jArray.add(i, arrayObj);
                    i++;

                }
                rs.close();
                hasil = jArray.toString();
                out.print(hasil);
            } else if (code.equals(update_pegawai)) {
                conn.setAutoCommit(false);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                Part foto = request.getPart("foto");
                InputStream fotos = foto.getInputStream();
                String nama = request.getParameter("nama");
                String alamat = request.getParameter("alamat");
                String no_telp = request.getParameter("no_telp");
                String nik = request.getParameter("nik");
                String query = "update tb_pegawai set nama=?,alamat=?,no_telp=?,foto=? where nik =?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, nama);
                statement.setString(2, alamat);
                statement.setString(3, no_telp);
                statement.setBlob(4, fotos);
                statement.setString(5, nik);
                statement.executeUpdate();
                conn.commit();
                hasil = "sukses";
                out.print(hasil);
            } else if (code.equals(retrieve_foto)) {
                String nik = request.getParameter("nik");
                String query = "SELECT nik,foto FROM tb_pegawai where nik = " + nik;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {
                    String fileName = rs.getString("nik");
                    Blob blob = rs.getBlob("foto");
                    byte[] imgData = blob.getBytes(1, (int) blob.length());
                    String encoded = javax.xml.bind.DatatypeConverter
                            .printBase64Binary(imgData);
                    response.setContentType("image/png");
                    response.getOutputStream().print(encoded);

                }
                rs.close();
                stmt.close();
                response.getOutputStream().close();
//                hasil = jArray.toString();
            }else if (code.equals(retrieve_pegawai_id)) {
                response.setContentType("text/html");

                PrintWriter out = response.getWriter();
                String niks = request.getParameter("nik");
                String query = "SELECT nik,nama,alamat,status,jabatan,no_telp,regional FROM tb_pegawai where nik = " + niks;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String nik = rs.getString("nik");
                    String nama = rs.getString("nama");
                    String status = rs.getString("status");
                    String alamat = rs.getString("alamat");
                    String jabatan = rs.getString("jabatan");
                    String no_telp = rs.getString("no_telp");
                    String regional = rs.getString("regional");
                    

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("nik", nik);
                    arrayObj.put("nama", nama);
                    arrayObj.put("status", status);

                    arrayObj.put("alamat", alamat);
                    arrayObj.put("jabatan", jabatan);
                    arrayObj.put("no_telp", no_telp);
                    arrayObj.put("regional", regional);
                   

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
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
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
