<%@ page import="com.myapp.models.*" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    Manager manager = (Manager) application.getAttribute("manager");
    User user = manager.getUserById(userId);

    SimpleArrayList<User> friends = manager.getFriendsOfUser(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Friends</title>
    <link rel="stylesheet" href="dashboard.css">
</head>
<body>

    <div class="topbar">
        <div class="logo">Connecto</div>

        <div class="chat-nav">
            <a href="dashboard.jsp">Home</a>
            <a href="chat.jsp" class="active">Freinds</a>
        </div>

    </div>

    <div class="Jsp/chat-container">

        <div class="main">

            <h2>Your Friends</h2>

            <% 
                if (friends.size() == 0) 
                { 
            %>
                <p>You have no friends yet.</p>

            <%  }  %>

        <div class="suggestions">

            <% 
                for (int i = 0; i < friends.size(); i++) 
                {
                    User f = friends.get(i);
            %>

                    <div class="suggest-card">
                        <img src="<%= f.getProfile().getProfilePicturePath() %>">

                        <h3 style="margin-bottom: 7px;"><%= f.getName() != null ? f.getName() : f.getUsername() %></h3>

                        <form action="fullProfile.jsp" method="get">

                            <input type="hidden" name="id" value="<%= f.getId() %>">
                            <button class="btn">View Profile</button>
                            
                        </form>

                    </div>

            <%  } %>

        </div>
    </div>

</body>
</html>
