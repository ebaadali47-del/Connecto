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

    String action = request.getParameter("action");

    if (action != null) 
    {

        if (action.equals("editName")) 
        {
            String name = request.getParameter("name");

            if (name != null && !name.trim().isEmpty()) 
            {
                manager.editName(userId, name.trim());
            }

        }
        else if (action.equals("editAge")) 
        {
            try 
            {
                int age = Integer.parseInt(request.getParameter("age"));
                manager.editAge(userId, age);
            } 
            catch (Exception e) {}
        }
        else if (action.equals("editEducation")) 
        {
            String education = request.getParameter("education");
            manager.editEducation(userId, education);
        }
        else if (action.equals("editAbout")) 
        {
            String about = request.getParameter("about");
            manager.editAbout(userId, about);
        }
        else if (action.equals("uploadPicture")) 
        {
            String fileName = request.getParameter("profilePicName");

            if (fileName != null && !fileName.trim().isEmpty()) 
            {
                manager.editProfilePicturePath(userId, fileName.trim());
            }
        }
        else if (action.equals("addSkill")) 
        {
            String skill = request.getParameter("skill");

            if (skill != null && !skill.trim().isEmpty()) 
            {
                manager.addSkill(userId, skill.trim());
            }

        }
        else if (action.equals("removeSkill")) 
        {
            manager.removeSkill(userId, request.getParameter("skill"));
        }
        else if (action.equals("addHobby")) 
        {
            String hobby = request.getParameter("hobby");

            if (hobby != null && !hobby.trim().isEmpty()) 
            {
                manager.addHobby(userId, hobby.trim());
            }

        }
        else if (action.equals("removeHobby")) 
        {
            manager.removeHobby(userId, request.getParameter("hobby"));
        }

        response.sendRedirect("editProfile.jsp");
        return;
    }

    int friendsCount = manager.getFriendsOfUser(userId).size();
    int pendingCount = manager.getPendingRequestsForUser(userId).size();

    int unreadMessages = 0;

    SimpleArrayList<User> friends = manager.getFriendsOfUser(userId);

    for (int i = 0; i < friends.size(); i++) 
    {
        unreadMessages += manager.getUnreadMessageCount(userId, friends.get(i).getId());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Edit Profile</title>
    <link rel="stylesheet" href="editProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

    <div class="topbar">

        <div class="top-left">

            <div class="logo">Connecto</div>
            <input class="search" placeholder="Search for friends...">

        </div>

        <div class="top-right">

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
            <a href="dashboard.jsp">Home</a>
            <a href="friends.jsp">Friends (<%= friendsCount %>)</a>
            <a href="requests.jsp">Requests (<%= pendingCount %>)</a>
            <a href="chat.jsp">Messages</a>
            <a href="logout.jsp">Logout</a>
        </div>

        <div class="main">

            <div class="profile-card">

                <h2>Edit Profile</h2>

                    <div class="section">

                        <h3>Profile Picture</h3>

                        <img class="profile-pic" src="<%= user.getProfile().getProfilePicturePath()%>">

                        <form method="post">

                            <input type="file" id="profilePicInput" accept="image/*" required>
                            <input type="hidden" name="profilePicName" id="profilePicName">

                            <div class="actions">
                                <button class="mini-btn" name="action" value="uploadPicture">Upload</button>
                            </div>

                        </form>
                    </div>

                    <div class="section">

                        <h3>Basic Information</h3>

                        <form method="post">
                            <label>Name</label>
                            <input class="input-field" type="text" name="name" value="<%= user.getName() != null ? user.getName() : "" %>">

                            <div class="actions">
                                <button class="mini-btn" name="action" value="editName">Save</button>
                            </div>

                        </form>

                        <form method="post">

                            <label>Age</label>
                            <input class="input-field" type="number" name="age" value="<%= user.getAge() %>">
                            <div class="actions">
                                <button class="mini-btn" name="action" value="editAge">Save</button>
                            </div>

                        </form>

                        <form method="post">

                            <label>Education</label>
                            <input class="input-field" type="text" name="education" value="<%= user.getProfile().getEducation() != null ? user.getProfile().getEducation() : "" %>">
                            <div class="actions">
                                <button class="mini-btn" name="action" value="editEducation">Save</button>
                            </div>

                        </form>

                    </div>

                    <div class="section">
                        <h3>About You</h3>

                        <form method="post">
                            <label>About</label>
                            <textarea class="input-field" name="about" rows="4"><%=  user.getProfile().getAbout() != null ? user.getProfile().getAbout() : ""  %></textarea>
                            <div class="actions">
                                <button class="mini-btn" name="action" value="editAbout">Save</button>
                            </div>
                        </form>

                    </div>

                    <div class="section">

                        <h3>Skills</h3>

                        <form method="post" style="display:flex; gap:10px;">

                        <input class="input-field" type="text" name="skill" placeholder="Add a skill...">
                        <button class="mini-btn" name="action" value="addSkill">+</button>

                        </form>


                        <ul class="tag-list">
                            <% for (int i = 0; i < user.getProfile().getSkills().size(); i++) { %>
                                <li>
                                    <%= user.getProfile().getSkills().get(i) %>

                                    <form method="post" class="inline">
                                        <input type="hidden" name="skill" value="<%= user.getProfile().getSkills().get(i) %>">
                                        <button class="mini-btn danger" name="action" value="removeSkill">x</button>
                                    </form>

                                </li>

                            <% } %>

                        </ul>
                    </div>

                    <div class="section">
                        <h3>Hobbies</h3>

                        <form method="post" style="display:flex; gap:10px;">
                            <input class="input-field" type="text" name="hobby" placeholder="Add a hobby...">
                            <button class="mini-btn" name="action" value="addHobby">+</button>
                        </form>


                        <ul class="tag-list">
                            <% for (int i = 0; i < user.getProfile().getHobbies().size(); i++) { %>
                                <li>
                                    <%= user.getProfile().getHobbies().get(i) %>
                                    <form method="post" class="inline">
                                        <input type="hidden" name="hobby" value="<%= user.getProfile().getHobbies().get(i) %>">
                                        <button class="mini-btn danger" name="action" value="removeHobby">x</button>
                                    </form>

                                </li>

                            <% } %>

                        </ul>

                    </div>

            </div>

        </div>

    </div>

    <script>

        document.getElementById("profilePicInput").addEventListener("change", function () {

            if (this.files && this.files.length > 0) 
            {
                document.getElementById("profilePicName").value = this.files[0].name;
            }

        });

    </script>


</body>
</html>
