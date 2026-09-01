<%@ page import="com.myapp.models.*" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");

    if(userId == null)
    {
        response.sendRedirect("login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");

    int friendId = Integer.parseInt(request.getParameter("friendId"));

    manager.unfriend(userId, friendId);

    response.sendRedirect("dashboard.jsp");
%>
