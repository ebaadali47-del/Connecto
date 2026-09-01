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

    SimpleArrayList<FriendSuggestionEntry> suggestions = manager.getFriendSuggestions(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Suggestions</title>
    <link rel="stylesheet" href="suggestions.css">
</head>

<body>

    <div class="topbar">

        <div class="logo">Connecto</div>

        <div class="nav-links">
            <a href="dashboard.jsp">Home</a>
            <a href="chat.jsp" class="active">Suggestions</a>
        </div>

    </div>

    <div class="main">

        <h2>All Suggestions</h2>

        <div class="suggestions">

                <% for (int i = 0; i < suggestions.size() && i<4; i++) 
                {
                    FriendSuggestionEntry e = suggestions.get(i);
                    PublicProfileSummary s = e.getSummary();
                %>

                <div class="suggest-card">

                    <span class="match">
                        <%= String.format("%.0f", e.getSimilarityPercent()) %>% Match
                    </span>

                    <img src="<%= s.getProfilePicturePath() %>">

                    <h3><%= s.getName() %></h3>
                    <p><%= s.getAbout() %></p>

                    <form action="restrictedProfile.jsp" method="get">

                        <input type="hidden" name="id" value="<%= s.getUserId() %>">
                        <button class="btn">View Profile</button>
                        
                    </form>

                </div>


                <% } %>
        </div>

    </div>

</body>
</html>
