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
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    String idParam = request.getParameter("id");

    if (idParam == null) 
    {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    int targetId = Integer.parseInt(idParam);

    PublicProfileSummary summary = manager.getPublicProfileSummary(targetId);

    if (summary == null) 
    {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    User currentUser = manager.getUserById(userId);
    boolean alreadyFriend = currentUser.isFriendWith(targetId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Profile</title>
    <link rel="stylesheet" href="restrictedProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

    <div class="topbar">
        <div class="logo">Connecto</div>

        <div class="nav-links">
            <a href="dashboard.jsp">Home</a>
        
        </div>
    </div>

    <div class="main-container">

        <div class="profile-card">
            
            <a href="javascript:history.back()" class="back-btn">X</a>


        <img src="<%= summary.getProfilePicturePath() %>">

        <div class="profile-info">

            <h2><%= summary.getName() != null ? summary.getName() : "User" %></h2>

            <p class="friend-count"><i class="fa-solid fa-user-friends"></i> <%=  summary.getFriendsCount() %> Friends</p>
            <p class="about"><%= summary.getAbout() != null ? summary.getAbout()  : "No description available." %></p>

            <div class="btn-container">

                <% if (!alreadyFriend) { %>

                    <form action="sendRequest.jsp" method="post">

                        <input type="hidden" name="toUserId" value="<%= targetId %>">
                        <button type="submit" class="btn">Send Request</button>

                    </form>

                <% } else if (alreadyFriend) { %>
                    <a href="fullProfile.jsp?id=<%= targetId %>" class="btn"> View Full Profile </a>
                <% } %>
            
            </div>
        </div>

    </div>


    </div>

</body>
</html>
