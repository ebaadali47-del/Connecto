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

    if (userId == null) 
    {
        response.sendRedirect("login.jsp");
        return;
    }

    User user = manager.getUserById(userId);

    PersonalityQuizService quizService = new PersonalityQuizService();

    if(request.getMethod().equalsIgnoreCase("POST"))
    {
        SimpleHashMap<Integer, Integer> answers = new SimpleHashMap<>();

        for(int i=1; i<=10; i++)
        {
            String ansStr = request.getParameter("q" + i);

            if(ansStr != null)
            {
                answers.put(i, Integer.parseInt(ansStr));
            }

        }

        manager.submitQuizAnswers(userId, answers);
        response.sendRedirect("profileSetup.jsp");
        return;
    }

    SimpleArrayList<PersonalityQuestion> questions = quizService.getQuestions();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Personality Quiz</title>
    <link rel="stylesheet" href="quiz.css">
</head>

<body>

    <div class="card">

        <div class="quiz-header">
            <h2>Personality Quiz</h2>

            <span class="progress-text">
                <span id="currentStep">1</span> / <%= questions.size() %>
            </span>

        </div>

        <form method="post" id="quizForm">

            <% 
                for(int i=0; i<questions.size(); i++)
                {
                    PersonalityQuestion q = questions.get(i);
            %>

                    <div class="question <%= (i==0 ? "active" : "") %>">

                        <div class="question-box">

                            <strong><%= (i+1) + ". " + q.getQuestionText() %></strong>

                            <div class="options">

                                <% String[] opts = q.getOptions();

                                for(int j=0; j<opts.length; j++){ %>
                                    <label>
                                        <input type="radio" name="q<%= q.getId() %>" value="<%= (j+1) %>" required>
                                        <span class="circle"><%= (char)('A' + j) %></span>
                                        <span class="opt-text"><%= opts[j] %></span>
                                    </label>
                                <% } %>
                            </div>

                        </div>
                    </div>

                <% } %>

                <div class="button-container">
                    <button type="button" id="nextBtn">Next</button>
                    <button type="submit" id="submitBtn" style="display:none;">Submit Quiz</button>
                </div>

        </form>

    </div>

    <script>

        const questions = document.querySelectorAll(".question");
        const nextBtn = document.getElementById("nextBtn");
        const submitBtn = document.getElementById("submitBtn");
        const stepText = document.getElementById("currentStep");

        let current = 0;

        nextBtn.addEventListener("click", () => {
            const selected = questions[current].querySelector("input:checked");

            if(!selected)
            {
                alert("Please select an option!");
                    return;
            }

            questions[current].classList.remove("active");
            current++;
            stepText.innerText = current + 1;

            if(current < questions.length)
            {
                questions[current].classList.add("active");
            }

            if(current === questions.length - 1)
            {
                nextBtn.style.display = "none";
                submitBtn.style.display = "inline-block";
            }
        });
    </script>

</body>
</html>
