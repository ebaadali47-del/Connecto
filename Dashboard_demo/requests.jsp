<%@ page import="com.myapp.models.*" %>
<%
    Manager manager = (Manager) application.getAttribute("manager");
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    User user = manager.getUserById(userId);

    SimpleArrayList<FriendRequest> pending = manager.getPendingRequestsForUser(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Friend Requests</title>
    <link rel="stylesheet" href="dashboard.css">
    <link rel="stylesheet" href="requests.css">
</head>

<body>

    <div class="topbar">

        <div class="top-left">
            <div class="logo">Connecto</div>
        </div>

        <div class="top-right">

            <div class="user-mini">

                <img src="<%= user.getProfile().getProfilePicturePath() %>">
                <span><%= user.getUsername() %></span>

            </div>

        </div>

    </div>

    <div class="layout">

        <div class="sidebar">
            <a href="dashboard.jsp"> Home</a>
            <a class="active">Requests</a>
            <a href="logout.jsp"> Logout</a>
        </div>

        <div class="main">

            <h2 class="section-title">Friend Requests</h2>

            <% if (pending.size() == 0) { %>
                <div class="empty-state">No pending requests</div>
            <% } %>

            <div class="requests-grid">

                <% for (int i = 0; i < pending.size(); i++) 
                {
                    FriendRequest fr = pending.get(i);
                    User from = manager.getUserById(fr.getFromUserId());
                %>

                <div class="request-card">

                    <img src="<%= from.getProfile().getProfilePicturePath() %>">

                    <div class="request-info">
                        <h3><%= from.getName() %></h3>
                        <p>wants to connect with you</p>
                    </div>

                    <form action="respondRequest.jsp" method="post" class="request-actions">
                        <input type="hidden" name="requestId" value="<%= fr.getId() %>">
                        <button name="action" value="accept" class="btn accept">Accept</button>
                        <button name="action" value="reject" class="btn reject">Reject</button>
                    </form>
                </div>

                <% } %>

            </div>
        </div>
    </div>

</body>
</html>
