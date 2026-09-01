<%@ page import="com.myapp.models.*" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    
    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");

    int friendId = Integer.parseInt(request.getParameter("friendId"));
    String type = request.getParameter("type");

    if ("all".equals(type)) 
    {

        manager.clearChatHistory(userId, friendId);

    } 
    else if ("selected".equals(type)) 
    {

        String[] ids = request.getParameterValues("msgIds");

        if (ids != null) 
        {

            SimpleArrayList<Integer> list = new SimpleArrayList<>();

            for (String id : ids) 
            {
                list.add(Integer.parseInt(id));
            }

            manager.deleteMessagesForMe(userId, friendId, list);
        }
    }

    response.sendRedirect("chat.jsp?friendId=" + friendId);
%>
