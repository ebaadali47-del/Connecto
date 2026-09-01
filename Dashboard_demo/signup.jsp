<%@ page import="com.myapp.models.*" %>
<%
    Manager manager = (Manager)application.getAttribute("manager");
         
    if (manager == null) 
    {
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    String message = "";

    if (request.getMethod().equalsIgnoreCase("POST")) 
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (manager.isUsernameTaken(username)) 
        {
            message = "Username already taken";
        }
        else if (!manager.isValidPassword(password)) 
        {
            message = "Password must contain letters and numbers";
        }
        else 
        {
           User newUser = manager.signUp(username, password);

            session.setAttribute("userId", newUser.getId());
            response.sendRedirect("startQuiz.jsp");
            return;
        }
    }
%>



<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

    <div class="card">
        <div class="title">Join Connecto</div>
        <div class="subtitle">Make friends, not stress</div>

        <form method="post">

            <input class="input" type="text" name="username" placeholder="Choose username" required>
            <input class="input" type="password" name="password" placeholder="Choose password" required>

            <button >Connect Sweetly :)</button>
        </form>

        <% if(!message.equals("")){ %>
            <div class="msg"><%= message %></div>
        <% } %>

        <div class="link">
            Already have an account? <a href="login.jsp">Login-></a>
        </div>
        
    </div>

</body>
</html>
