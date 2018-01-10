<%-- 
    Document   : index
    Created on : Jan 1, 2018, 3:02:12 AM
    Author     : Vaio
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Shoutout</title>
  </head>
  <body>
        <form>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" id="name"/></td>
                </tr>
                <tr>
                    <td>Message:</td>
                    <td><input type="text" name="msg" id="msg"/></td>
                </tr>
            </table>
            <input type="submit" value="Shout" onclick="postMessage();"/>
        </form>
        <div id="message">
            <% if(application.getAttribute("message")!=null){%>
            <%=application.getAttribute("message")%>
            <%}%>
        </div>
          <!--   Core JS Files   -->
    <script src="../assets/js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../js/index.js"></script>
  </body>
</html>