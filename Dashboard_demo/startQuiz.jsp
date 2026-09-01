<%@ page import="com.myapp.models.*" %>
<%
    Manager manager = (Manager)application.getAttribute("manager");

    if(manager == null)
    {
        manager = new Manager();
        DataLoader.loadAll(manager);
        application.setAttribute("manager", manager);
    }

    Integer userId = (Integer) session.getAttribute("userId");

    if(userId == null)
    {
        response.sendRedirect("login.jsp");
        return;
    }

    User user = manager.getUserById(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Get Ready | Personality Quiz</title>
    <link rel="stylesheet" href="startQuiz.css">
</head>

<body>

    <div class="card">

        <h2>Hey <%= user.getUsername() %>!</h2>

        <p>You're about to take the <strong>Personality Quiz</strong>.<br>
        This will help us understand your personality and give personalized friend suggestions based on compatibility.</p>

        <p class="small">It only takes a few minutes. Be honest with your answers to get the best results!</p>

        <form method="get" action="quiz.jsp">
            <button type="submit">Start Quiz</button>
        </form>

    </div>

</body>
</html>
