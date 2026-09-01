<%@ page import="com.myapp.models.*" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) return;

    Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
        return;

    int friendId = Integer.parseInt(request.getParameter("friendId"));
    String message = request.getParameter("message");

    manager.sendMessage(userId, friendId, message);
%>
