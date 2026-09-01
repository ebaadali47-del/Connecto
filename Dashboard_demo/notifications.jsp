<%@ page import="com.myapp.models.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

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
    SimpleLinkedList<Notification> unread = manager.getUnreadNotifications(userId);
    SimpleLinkedList.Node<Notification> node = unread.head;

    while (node != null) 
    {
        Notification n = node.value;
        manager.markNotificationAsRead(userId, n.getId());
        node = node.next;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM yyyy");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Notifications</title>
    <link rel="stylesheet" href="notifications.css">
</head>

<body>

    <div class="topbar">
        <div class="logo">Connecto</div>
        <div class="nav-links">
            <a href="dashboard.jsp">Home</a>
            <a href="requests.jsp">Requests</a>
            <a href="notifications.jsp" class="active">Notifications</a>
        </div>
    </div>

    <div class="container">

        <h2>Notifications</h2>

        <% if (unread.size == 0) { %>
            <p>No new notifications</p>
        <% } else { %>

            <div class="notification-list">

            <%
                SimpleLinkedList.Node<Notification> showNode = unread.head;

                while (showNode != null) 
                {
                    Notification n = showNode.value;
                    String formattedDate = sdf.format(new Date(n.getTimestamp()));
            %>

                <div class="notification-card">
                    <%= n.getMessage() %>
                    <span class="time"><%= formattedDate %></span>
                </div>

            <%
                    showNode = showNode.next;
                }
            %>

            </div>

        <% } %>

    </div>

</body>
</html>
