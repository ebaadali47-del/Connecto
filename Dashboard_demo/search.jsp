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

    String keyword = request.getParameter("keyword");
    SimpleArrayList<PublicProfileSummary> results =  manager.searchUsers(keyword, userId);

    User currentUser = manager.getUserById(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" href="search.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <div class="topbar">

        <div class="top-left">

            <div class="logo">Connecto</div>

            <form action="search.jsp" method="get">
                <input class="search" name="keyword" placeholder="Search for friends...">
            </form>

        </div>

        <div class="top-right">

            <div class="nav-links">
                <a href="dashboard.jsp">Home</a>
            </div>

        </div>

    </div>


    <div class="search-container">

        <h2>Search Results for "<%= keyword %>"</h2>

        <% if(results.size() == 0){ %>
            <p>No users found.</p>
        <% } else {

            for(int i=0;i<results.size();i++)
            {
                PublicProfileSummary s = results.get(i);
                boolean isFriend = currentUser.isFriendWith(s.getUserId());
        %>

                <div class="result-card">

                    <div class="left-section">
                        <img src="<%= s.getProfilePicturePath() %>" class="profile-img">

                        <div class="user-info">

                            <h3><%= s.getName() %></h3>
                            <p class="friend-count"><i class="fa-solid fa-user-friends"></i> <%=  s.getFriendsCount() %> Friends</p>
                            <p class="about"><%= s.getAbout() %></p>

                        </div>
                    </div>

                    <div class="right-section">

                        <% if(isFriend){ %>

                            <a href="fullProfile.jsp?id=<%= s.getUserId() %>" class="add-btn">
                                View Profile
                            </a>

                        <% } else { %>

                            <a href="restrictedProfile.jsp?id=<%= s.getUserId() %>" class="add-btn">
                                View Profile
                            </a>
                            
                        <% } %>
                    </div>

                </div>


        <%  }
        } %>

    </div>

</body>
</html>
