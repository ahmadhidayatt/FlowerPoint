/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: stephen
 * Date: 13-6-8
 * Time: PM9:28
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "ShoutoutServlet", urlPatterns = {"../ShoutoutServlet"}, asyncSupported = true)
public class ShoutoutServlet extends HttpServlet {
    private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AsyncContext> aContexts = new ArrayList<AsyncContext>(this.contexts);
        this.contexts.clear();
        String name = request.getParameter("nama");
        String msg = request.getParameter("msg");
         String date = request.getParameter("t");
        String currentMessages = (String) request.getServletContext().getAttribute("message");
        String htmlMsg = "<li style='width:100%;'>" +
                "<div class='msj-rta macro'>" +
                "<div class='text text-r'>" +
                "<p>" + msg + "</p>" +
                "<p><small>" + date + "</small></p>" +
                "</div>" +
                "<div class='avatar' style='padding:0px 0px 0px 10px !important'><img class='img-circle' style='width:100%;' src='https://a11.t26.net/taringa/avatares/9/1/2/F/7/8/Demon_King1/48x48_5C5.jpg' /></div>" +
                "</li>";

        if(currentMessages != null) {
            currentMessages = htmlMsg + currentMessages;
        } else{
            currentMessages = htmlMsg;
        }

        request.getServletContext().setAttribute("message", currentMessages);
//        request.getRequestDispatcher("/index.jsp").forward(request,response);
        for(AsyncContext context : aContexts){
            try(PrintWriter writer = context.getResponse().getWriter()){
                writer.println(htmlMsg);
                writer.flush();
                context.complete();
            }catch (Exception ex){

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10*60*1000);
        contexts.add(asyncContext);
    }
}