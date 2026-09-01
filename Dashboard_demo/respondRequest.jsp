<%@ page import="com.myapp.models.*" %>
<%
    Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
    {
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    User user = manager.getUserById(userId);

    if (user == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    String reqIdParam = request.getParameter("requestId");
    String action = request.getParameter("action");

    if (reqIdParam != null && action != null) 
    {
        int requestId = Integer.parseInt(reqIdParam);

        if ("accept".equals(action)) 
        {
            manager.acceptFriendRequest(requestId, userId);
        } 
        else if ("reject".equals(action)) 
        {
            manager.rejectFriendRequest(requestId, userId);
        }
    }

    response.sendRedirect("requests.jsp");
%>
