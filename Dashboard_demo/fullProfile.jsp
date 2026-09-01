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

    int targetId = Integer.parseInt(request.getParameter("id"));

    Profile profile = manager.getFullProfile(userId, targetId);

    if (profile == null) 
    {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    User friend = manager.getUserById(targetId);
    int friendsCount = manager.getFriendsOfUser(targetId).size();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Full Profile</title>
    <link rel="stylesheet" href="fullProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <div class="topbar">
        <div class="logo">Connecto</div>
        <div class="nav-links">
            <a href="dashboard.jsp">Home</a>
            <a href="friends.jsp">Friends</a>
        </div>
    </div>

    <div class="profile-card">

        <a href="javascript:history.back()" class="back-btn">X</a>

        <div class="profile-top">

            <div class="profile-left">
                <img src="<%= profile.getProfilePicturePath() %>" alt="Profile Picture">
            </div>

            <div class="profile-info-column">

                <div class="profile-header">

                    <h2 class="name"><%= friend.getName() %></h2>


                    <div class="f-row">

                        <p class="friend-count"> <i class="fa-solid fa-user-friends"></i><%= friendsCount %> Friends</p>

                    

                        <div class="action-buttons">

                            <form action="unfriend.jsp" method="post">
                                <input type="hidden" name="friendId" value="<%= targetId %>">
                                <button class="btn-danger">Unfriend</button>
                            </form>

                            <form action="chat.jsp" method="get">
                                <input type="hidden" name="friendId" value="<%= targetId %>">
                                <button class="btn-message">Message</button>
                            </form>

                        </div>

                    </div>

                    <p class="about-text">
                        <%= profile.getAbout() != null  ? profile.getAbout()  : "Profile not completed yet." %>
                    </p>

                    

                </div>

                

            </div>

        </div>
        
        <div class="info-block">
                
            <p> <strong>Age:</strong> <%= friend.getAge() %> &nbsp; | &nbsp; <strong>DOB:</strong><%= friend.getDateOfBirth() != null  ? friend.getDateOfBirth() : "Not added" %> </p>
        
        </div>
                
        <div class="info-block">
                    
            <h4>Education</h4>
                    
            <p><%= profile.getEducation() != null  ? profile.getEducation()  : "Not added" %></p>
                
        </div>

        <div class="info-block">

            <h4>Skills</h4>

            <div class="tags">
                <% for(int i=0;i<profile.getSkills().size();i++){ %>
                    <span><%= profile.getSkills().get(i) %></span>
                <% } %>

                <% if(profile.getSkills().size()==0){ %>
                    <span>No skills added</span>
                <% } %>

            </div>

        </div>

        <div class="info-block">

            <h4>Hobbies</h4>

            <div class="tags">
                <% for(int i=0;i<profile.getHobbies().size();i++){ %>
                    <span><%= profile.getHobbies().get(i) %></span>
                <% } %>
                <% if(profile.getHobbies().size()==0){ %>
                    <span>No hobbies added</span>
                <% } %>
            </div>
            
        </div>

    </div>

</body>
</html>
