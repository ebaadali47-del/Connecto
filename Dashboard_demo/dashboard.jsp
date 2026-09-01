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


    FriendAction lastAction = null;

    if (!user.getUndoStack().isEmpty()) 
    {
        lastAction = user.getUndoStack().peek();
    }

    int friendsCount = manager.getFriendsOfUser(userId).size();
    int pendingCount = manager.getPendingRequestsForUser(userId).size();
    int unreadMessages = 0;

    SimpleArrayList<User> friends = manager.getFriendsOfUser(userId);

    for (int i = 0; i < friends.size(); i++) 
    {
        unreadMessages += manager.getUnreadMessageCount(userId, friends.get(i).getId());
    }

    SimpleArrayList<FriendSuggestionEntry> suggestions = manager.getFriendSuggestions(userId);

    SimpleLinkedList<Notification> unreadNotifs = manager.getUnreadNotifications(userId);

    int notificationCount = manager.getUnreadNotifications(userId).size;

%>
<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Dashboard</title>
    <link rel="stylesheet" href="dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>

    <script>

        let reloadInterval;

        function startReload() 
        {
            reloadInterval = setInterval(function() { window.location.reload(); }, 10000); 
        }

        function stopReload() 
        {
            clearInterval(reloadInterval);
        }

        startReload();

    </script>

<body>

    <div class="topbar">

        <div class="top-left">

            <div class="logo">Connecto</div>

                <form action="search.jsp" method="get">

                    <input class="search" name="keyword"  placeholder="Search for friends..." onfocus="stopReload()" onblur="startReload()" required>

                </form>

            </div>

        <div class="top-right">
           <% 
                if (lastAction != null) 
                {
                    User otherUser = manager.getUserById(lastAction.getOtherUserId());
                    String otherName = (otherUser != null)  ? (otherUser.getName() != null ? otherUser.getName() : otherUser.getUsername()): "Unknown User";

                    String actionText = "";
    
                    actionText = "Sent friend request to " + otherName;
            %>

            <div class="undo-box">

                <span><%= actionText %></span>

                <form action="undo.jsp" method="post" style="display:inline;">

                    <button class="undo-btn">Unsend</button>

                </form>

            </div>

             <% } %>

            <a href="notifications.jsp" class="chat-icon">
                
                <i style="color: rgba(254, 225, 10, 0.767);" class="fa-solid fa-bell"></i>

                <% if (notificationCount > 0) { %>

                    <span class="badge"><%= notificationCount %></span>

                <% } %>
            
            </a>
        
            <a href="chat.jsp" class="chat-icon">

                <i class="fa-solid fa-comments"></i> 

                <% if (unreadMessages > 0) { %>
                    <span class="badge"><%= unreadMessages %></span>
                <% } %>
            </a>



            <div class="user-mini">

                <img src="<%= user.getProfile().getProfilePicturePath() %>">
           
                <span><%= user.getUsername() %></span>

            </div>
    
        </div>
    </div>

    <div class="layout">

        <div class="sidebar">

            <a class="active">Home</a>
            <a href="suggestions.jsp">Suggestions</a>
            <a href="friends.jsp"> Friends (<%= friendsCount %>)</a>
            <a href="requests.jsp">Requests (<%= pendingCount %>)</a>
            <a href="chat.jsp">Messages</a>
            <a href="history.jsp">History</a>
            <a href="logout.jsp"> Logout</a>

        </div>

        <div class="main">

            <h2>People You Might Connect With</h2>

            <div class="suggestions">

                <% for (int i = 0; i < suggestions.size() && i<4; i++) 
                {
                    FriendSuggestionEntry e = suggestions.get(i);
                    PublicProfileSummary s = e.getSummary();
                %>

                <div class="suggest-card">

                    <span class="match"> <%= String.format("%.0f", e.getSimilarityPercent()) %>% Match  </span>

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

            <div class="profile-card">

                <a href="editProfile.jsp" class="edit-profile-btn">
                    
                    <i class="fa-solid fa-pen"></i> Edit

                </a>

                <div class="profile-top">

                    <div class="profile-left">
                        <img src="<%= user.getProfile().getProfilePicturePath() %>" alt="Profile Picture">
                    </div>

                    <div class="profile-header">
                        <h2 class="name"><%= user.getName() != null ? user.getName() : user.getUsername() %></h2>
                        <p class="friend-count"><i class="fa-solid fa-user-friends"></i> <%= friendsCount %> Friends</p>
                    </div>

                </div>

                <div class="info-block">
                    <p><strong>Age:</strong> <%= user.getAge() %> &nbsp; | &nbsp; <strong>DOB:</strong> <%= user.getDateOfBirth() != null ? user.getDateOfBirth() : "Not added" %></p>
                </div>

                <div class="info-block">
                    <h4>About</h4>
                    <p><%= user.getProfile().getAbout() != null ? user.getProfile().getAbout() : "Profile not completed yet." %></p>
                </div>

                <div class="info-block">
                    <h4>Education</h4>
                    <p><%= user.getProfile().getEducation() != null ? user.getProfile().getEducation() : "Not added" %></p>
                </div>

                <div class="info-block">

                    <h4>Skills</h4>

                    <div class="tags">

                        <% for(int i=0; i<user.getProfile().getSkills().size(); i++){ %>
                            <span><%= user.getProfile().getSkills().get(i) %></span>
                        <% } %>

                        <% if(user.getProfile().getSkills().size()==0){ %>
                            <span>No skills added</span>
                        <% } %>
                    </div>

                </div>

                <div class="info-block">

                    <h4>Hobbies</h4>

                    <div class="tags">
                        <% for(int i=0; i<user.getProfile().getHobbies().size(); i++){ %>
                            <span><%= user.getProfile().getHobbies().get(i) %></span>
                        <% } %>
                        <% if(user.getProfile().getHobbies().size()==0){ %>
                            <span>No hobbies added</span>
                        <% } %>
                    </div>

                </div>

            </div>

        </div>
    </div>

</body>
</html>
