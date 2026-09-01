<%@ page import="com.myapp.models.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("Jsp/login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
    {
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    String type = request.getParameter("type");
    String action = request.getParameter("action");

    if (type != null && action != null) 
    {

        if (type.equals("search")) 
        {
            if (action.equals("clear")) 
            {
                manager.clearSearchHistory(userId);
            } 
            else if (action.equals("delete")) 
            {
                int index = Integer.parseInt(request.getParameter("index"));
                manager.removeSearchHistoryEntry(userId, index);
            }

        } else if (type.equals("friend")) 
        {
            if (action.equals("clear")) 
            {
                manager.clearActionHistory(userId);
            } 
            else if (action.equals("delete")) 
            {
                int index = Integer.parseInt(request.getParameter("index"));
                manager.removeActionHistory(userId, index);
            }
        }

        response.sendRedirect("history.jsp");
        return;
    }

    SimpleLinkedList<SearchHistoryEntry> searchHistory = manager.getSearchHistory(userId);

    SimpleLinkedList<FriendAction> actionHistory = manager.getFriendActionHistory(userId);

    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | History</title>
    <link rel="stylesheet" href="history.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <div class="topbar">

        <div class="logo">Connecto</div>

        <div class="nav">
            <a href="dashboard.jsp">Home</a>
            <a href="chat.jsp">Messages</a>
            <a href="history.jsp" class="active">History</a>
        </div>

    </div>

    <div class="container">

        <div class="card">
            <h2>Search History</h2>

            <% if (searchHistory == null || searchHistory.size == 0) { %>
                <p>No search history.</p>
            <% } else { %>

                <form method="post">
                    <input type="hidden" name="type" value="search">
                    <button class="clear-btn" name="action" value="clear"> Clear All </button>
                </form>

                <ul>

                <%
                    SimpleLinkedList.Node<SearchHistoryEntry> node = searchHistory.head;
                    int index = 0;

                    while (node != null) 
                    {
                        SearchHistoryEntry entry = node.value;
                        String formattedDate = sdf.format(new Date(entry.getTimestamp()));
                %>
                    <li>
                        <div>
                            <p>Searched for <strong><%= entry.getQuery() %></strong></p>
                            <span class="time"><%= formattedDate %></span>
                        </div>

                        <form method="post">
                            <input type="hidden" name="type" value="search">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="index" value="<%= index %>">
                            <button class="delete-btn"> <i class="fa-solid fa-trash"></i> </button>
                        </form>

                    </li>

                    <%
                        node = node.next;
                        index++;
                    }
                    %>
                    
                </ul>

            <% } %>
        </div>

        <div class="card">

            <h2>Friend Action History</h2>

            <% if (actionHistory == null || actionHistory.size == 0) { %>
                <p>No friend actions.</p>
            <% } else { %>

                <form method="post">
                    <input type="hidden" name="type" value="friend">
                    <button class="clear-btn" name="action" value="clear"> Clear All </button>
                </form>

                <ul>
                <%
                    SimpleLinkedList.Node<FriendAction> node2 = actionHistory.head;

                    int index2 = 0;

                    while (node2 != null) 
                    {
                        FriendAction a = node2.value;
                        String formattedDate = sdf.format(new Date(a.getTimestamp()));

                        User actor = manager.getUserById(a.getActorUserId());
                        User target = manager.getUserById(a.getOtherUserId());
                        String description = "";

                        if (a.getType() == ActionType.SEND_REQUEST) 
                        {
                            description =  "You sent a friend request to " + target.getName();
                        } 
                        else if (a.getType() == ActionType.ACCEPT_REQUEST) 
                        {
                            description = target.getName() + " accepted your" + " friend request";
                        } 
                        else if (a.getType() == ActionType.REJECT_REQUEST) 
                        {
                            description = target.getName() + " rejected your" + " friend request";
                        } 
                        else 
                        {
                            description = a.getType().toString();
                        }
                    %>

                    <li>
                        <div>
                            <p><%= description %></p>
                            <span class="time"><%= formattedDate %></span>
                        </div>

                        <form method="post">
                            <input type="hidden" name="type" value="friend">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="index" value="<%= index2 %>">
                            <button class="delete-btn"><i class="fa-solid fa-trash"></i></button>
                        </form>
                    </li>

                    <%
                        node2 = node2.next;
                        index2++;
                    }
                    %>

                </ul>

            <% } %>
        </div>

    </div>

</body>
</html>
