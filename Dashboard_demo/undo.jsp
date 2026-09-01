<%@ page import="com.myapp.models.*" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    manager.undoLastAction(userId);

    response.sendRedirect("dashboard.jsp");
%>
