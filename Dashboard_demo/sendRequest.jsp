<%@ page import="com.myapp.models.*" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");

    if ("POST".equalsIgnoreCase(request.getMethod())) 
    {

        int toUserId = Integer.parseInt(request.getParameter("toUserId"));

        manager.sendFriendRequest(userId, toUserId);

        response.sendRedirect("dashboard.jsp");
        return;
    }
%>
