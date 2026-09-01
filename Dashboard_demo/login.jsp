<%@ page import="com.myapp.models.*" %>
<%
   Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
    {
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    String message = "";

    if(request.getMethod().equalsIgnoreCase("POST"))
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = manager.signIn(username, password);

        if (user == null) 
        {
            message = "Invalid username or password";
        } 
        else 
        {
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            response.sendRedirect("dashboard.jsp");
            return;
        }

    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

    <div class="card">

        <div class="title">Connecto </div>
        <div class="subtitle">Find people who match your interest and vibe</div>

        <form method="post">

            <input class="input" type="text" name="username" placeholder="Username" required>
            <input class="input" type="password" name="password" placeholder="Password" required> 
            <button>Log In </button>

        </form>

        <% if(!message.equals("")){ %>
            <div class="msg"><%= message %></div>
        <% } %>

        <div class="link">

            New here? <a href="signup.jsp">Create a new account-></a>
            
        </div>
    </div>

</body>
</html>
