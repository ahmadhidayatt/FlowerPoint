/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
public class helper_news extends HttpServlet {

    public final String retrieve_news = "0";
    public final String insert_news = "1";
    public final String update_news = "2";
    public final String retrieve_id = "3";
    public final String retrieve_status = "4";
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
            out.println("<title>Servlet helper_news</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet helper_news at " + request.getContextPath() + "</h1>");
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

        PrintWriter out = response.getWriter();
        conn = new connection().getConn();
        String hasil = null;
        Statement stmt;
        String Kodeaut = "";
        ResultSet rs;
        Statement st;
        CallableStatement sta;
        String code = request.getParameter("code");

        try {
            if (code.equals(insert_news)) {
//                conn.setAutoCommit(false);
                out.print(code);

//                String id_ticket = "";
//                String queries = "SELECT * from tb_ticket ";
//
//                stmt = conn.createStatement();
//                rs = stmt.executeQuery(queries);
//                int i = 0;
//                JSONArray jArray = new JSONArray();
//                while (rs.next()) {
//                    id_ticket = rs.getString("id_ticket");
//                }
                String deskripsi = request.getParameter("deskripsi");
                String nama = request.getParameter("nama");
                String subject = request.getParameter("subject");
                String kordinator = request.getParameter("kordinator");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String query = "insert into tb_news(deskripsi,nama,subject,id_kordinator,tanggal)values(?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, deskripsi);
                statement.setString(2, nama);
                statement.setString(3, subject);
                statement.setString(4, kordinator);
                statement.setTimestamp(5,new Timestamp(System.currentTimeMillis()));

                statement.executeUpdate();

                hasil = "sukses";
            } else if (code.equals(retrieve_news)) {

                String query = "SELECT a.id_news,a.deskripsi,a.id_kordinator,a.tanggal,b.nama,a.subject FROM tb_news a INNER JOIN tb_pegawai b ON a.id_kordinator = b.nik ORDER BY a.tanggal DESC";
                PreparedStatement statement = conn.prepareStatement(query);
//                st = conn.prepareCall(query);
                rs = statement.executeQuery();
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String id_news = rs.getString("id_news");
                    String deskripsi = rs.getString("deskripsi");
                    String subject = rs.getString("subject");
                    String id_kordinator = rs.getString("id_kordinator");
                    String tanggal = rs.getString("tanggal");
                    String nama = rs.getString("nama");

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("id_news", id_news);
                    arrayObj.put("deskripsi", deskripsi);
                    arrayObj.put("subject", subject);
                    arrayObj.put("id_kordinator", id_kordinator);
                    arrayObj.put("tanggal", tanggal);
                    arrayObj.put("nama", nama);
                    jArray.add(i, arrayObj);
                    i++;
                }
                rs.close();

                hasil = jArray.toString();
//                out.print(hasil);

            } else if (code.equals(update_news)) {
                out.print(code);
                conn.setAutoCommit(false);
                String id_ticket = request.getParameter("id_ticket");
                String id_atm = request.getParameter("id_atm");
                String id_masalah = request.getParameter("id_masalah");

                java.sql.Timestamp start_time = null;
                java.sql.Timestamp end_time = null;

                java.sql.Timestamp start_times = java.sql.Timestamp.valueOf(request.getParameter("start_time"));
                java.sql.Timestamp end_times = java.sql.Timestamp.valueOf(request.getParameter("end_time"));
                if (start_times == null) {
                    String a = "";
                    start_time = Timestamp.valueOf(a);
                } else {
                    start_time = start_times;
                }
                if (end_times == null) {
                    String a = "";
                    end_time = Timestamp.valueOf(a);
                } else {
                    end_time = start_times;
                }

                String custody = request.getParameter("custody");
                String nik = request.getParameter("nik");
                String satwal = request.getParameter("satwal");
                String kartu_tertelan = request.getParameter("kartu_tertelan");
                String deskripsi = request.getParameter("deskripsi");
                String status = request.getParameter("status");

                String query = "update tb_ticket set id_atm=?,id_masalah=?,start_time=STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'),end_time=STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'),nik=?,satwal=?,kartu_tertelan=?,deskripsi=?,status=?,custody=? where id_ticket =?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, id_atm);
                statement.setString(2, id_masalah);
                statement.setTimestamp(3, start_time);
                statement.setTimestamp(4, end_time);
                statement.setString(5, nik);
                statement.setString(6, satwal);
                statement.setString(7, kartu_tertelan);
                statement.setString(8, deskripsi);
                statement.setString(9, status);
                statement.setString(10, custody);
                statement.setString(11, id_ticket);

                String query2 = "insert into tb_report(id_atm,id_masalah,status,custody,nik,start_time,end_time,id_ticket)values(?,?,?,?,?,?,?,?)";
                PreparedStatement statement2 = conn.prepareStatement(query2);

                statement2.setString(1, id_atm);
                statement2.setString(2, id_masalah);
                statement2.setString(3, "open");
                statement2.setString(4, custody);
                statement2.setString(5, nik);
                statement2.setTimestamp(6, start_time);
                statement2.setTimestamp(7, end_time);
                statement2.setString(8, id_ticket);

                statement.executeUpdate();
                statement2.executeUpdate();
                if (statement2.executeUpdate() != 0) {
                    hasil = "sukses";
                    conn.commit();
                } else {
                    hasil = "gagal";
                    conn.rollback();
                }

            } else if (code.equals(retrieve_id)) {
                String id_tickets = request.getParameter("id_ticket");
                String query = "SELECT d.id_ticket,b.id_atm,c.id_masalah,b.nama_atm,c.nama_masalah,d.start_time,d.end_time,d.nik,d.satwal,d.kartu_tertelan,d.deskripsi,d.status,d.custody\n"
                        + "FROM tb_ticket d\n"
                        + "INNER JOIN tb_pegawai a ON d.nik = a.nik\n"
                        + "INNER JOIN tb_atm     b ON  b.id_atm = d.id_atm\n"
                        + "INNER JOIN tb_masalah  c ON c.id_masalah   = d.id_masalah where id_ticket = " + id_tickets;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String id_ticket = rs.getString("id_ticket");
                    String id_atm = rs.getString("id_atm");
                    String id_masalah = rs.getString("id_masalah");
                    String nama_atm = rs.getString("nama_atm");
                    String nama_masalah = rs.getString("nama_masalah");
                    String start_time = rs.getString("start_time");
                    String end_time = rs.getString("end_time");

                    String nik = rs.getString("nik");
                    String satwal = rs.getString("satwal");

                    String kartu_tertelan = rs.getString("kartu_tertelan");
                    String deskripsi = rs.getString("deskripsi");
                    String status = rs.getString("status");
                    String custody = rs.getString("custody");

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("id_ticket", id_ticket);
                    arrayObj.put("id_atm", id_atm);
                    arrayObj.put("id_masalah", id_masalah);
                    arrayObj.put("nama_atm", nama_atm);
                    arrayObj.put("nama_masalah", nama_masalah);
                    arrayObj.put("start_time", start_time);
                    arrayObj.put("end_time", end_time);
                    arrayObj.put("nik", nik);
                    arrayObj.put("satwal", satwal);

                    arrayObj.put("kartu_tertelan", kartu_tertelan);
                    arrayObj.put("deskripsi", deskripsi);
                    arrayObj.put("status", status);
                    arrayObj.put("custody", custody);

                    jArray.add(i, arrayObj);
                    i++;
                }

                hasil = jArray.toString();

            } else if (code.equals(retrieve_status)) {
                String statuss = request.getParameter("statuss");
                String query = "SELECT d.id_ticket,b.nama_atm,c.nama_masalah,d.start_time,d.end_time,d.nik,d.satwal,d.kartu_tertelan,d.deskripsi,d.status,d.custody\n"
                        + "FROM tb_ticket d\n"
                        + "INNER JOIN tb_pegawai a ON d.nik = a.nik\n"
                        + "INNER JOIN tb_atm     b ON  b.id_atm = d.id_atm\n"
                        + "INNER JOIN tb_masalah  c ON c.id_masalah   = d.id_masalah where d.status = '" + statuss + "'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int i = 0;
                JSONArray jArray = new JSONArray();
                while (rs.next()) {

                    String id_ticket = rs.getString("id_ticket");
                    String nama_atm = rs.getString("nama_atm");
                    String nama_masalah = rs.getString("nama_masalah");
                    String start_time = rs.getString("start_time");
                    String end_time = rs.getString("end_time");

                    String nik = rs.getString("nik");
                    String satwal = rs.getString("satwal");

                    String kartu_tertelan = rs.getString("kartu_tertelan");
                    String deskripsi = rs.getString("deskripsi");
                    String status = rs.getString("status");
                    String custody = rs.getString("custody");

                    JSONObject arrayObj = new JSONObject();

                    arrayObj.put("id_ticket", id_ticket);

                    arrayObj.put("nama_atm", nama_atm);
                    arrayObj.put("nama_masalah", nama_masalah);
                    arrayObj.put("start_time", start_time);
                    arrayObj.put("end_time", end_time);
                    arrayObj.put("nik", nik);
                    arrayObj.put("satwal", satwal);

                    arrayObj.put("kartu_tertelan", kartu_tertelan);
                    arrayObj.put("deskripsi", deskripsi);
                    arrayObj.put("status", status);
                    arrayObj.put("custody", custody);

                    jArray.add(i, arrayObj);
                    i++;
                }

                hasil = jArray.toString();

            }

            conn.close();
        } catch (SQLException sx) {
            hasil = sx.toString();
        }
        out.print(hasil);
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
