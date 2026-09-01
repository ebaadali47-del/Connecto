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

    User user = manager.getUserById(userId);

    if ("POST".equalsIgnoreCase(request.getMethod())) 
    {

        Profile temp = new Profile();

        user.setName(request.getParameter("name"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        user.setGender(request.getParameter("gender"));
        user.setDateOfBirth(request.getParameter("dob"));

        temp.setAbout(request.getParameter("about"));
        temp.setEducation(request.getParameter("education"));
        temp.setProfilePicturePath(request.getParameter("profilePic"));

        String[] skills = request.getParameter("skills").split(",");

        for (String s : skills) 
        {
            if (!s.trim().isEmpty())
                temp.getSkills().add(s.trim());
        }

        String[] hobbies = request.getParameter("hobbies").split(",");

        for (String h : hobbies) 
        {
            if (!h.trim().isEmpty())
                temp.getHobbies().add(h.trim());
        }

        manager.CompleteProfile(userId, temp);

        response.sendRedirect("dashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Complete Profile | Connecto</title>
    <link rel="stylesheet" href="profileStyle.css">
</head>

<body>
    <div class="layout">

        <div class="main">

            <div class="profile-card">

                <h2>Complete Your Profile</h2>

                <form method="post">

                    <div class="section">

                        <h3>Basic Information</h3>

                        <input type="text" name="name" class="input-field" placeholder="Full Name" required>

                        <input type="number" name="age" class="input-field" placeholder="Age" min="13" required>

                        <select name="gender" class="input-field" required>
                            
                            <option value="">Select Gender</option>
                            <option>Male</option>
                            <option>Female</option>
                            <option>Other</option>

                        </select>

                        <input type="date" name="dob" class="input-field">

                    </div>

                    <div class="section">

                        <h3>About You</h3>

                        <textarea name="about" rows="3" class="input-field" placeholder="A short intro about yourself"></textarea>

                        <input type="text" name="education" class="input-field" placeholder="Education">

                    </div>

                    <div class="section">

                        <h3>Profile Picture</h3>
                        <input type="file" name="profilePic">

                    </div>

                    <div class="section">

                        <h3>Skills</h3>

                        <div style="display:flex; gap:8px;">
                            <input type="text" id="skillInput" class="input-field" placeholder="Enter skill">
                            <button type="button" class="mini-btn" onclick="addSkill()">+</button>
                        </div>

                        <ul id="skillList" class="tag-list"></ul>

                        <input type="hidden" name="skills" id="skillsHidden">

                    </div>
                    
                    <div class="section">

                        <h3>Hobbies</h3>

                        <div style="display:flex; gap:8px;">
                            <input type="text" id="hobbyInput" class="input-field" placeholder="Enter hobby">
                            <button type="button" class="mini-btn" onclick="addHobby()">+</button>
                        </div>

                        <ul id="hobbyList" class="tag-list"></ul>

                        <input type="hidden" name="hobbies" id="hobbiesHidden">

                    </div>

                    <div class="actions">
                        <button type="submit" class="mini-btn"> Finish Setup </button>
                    </div>

                </form>

            </div>
        </div>
    </div>

    <script>
        function addSkill() 
        {
            var input = document.getElementById("skillInput");
            var hidden = document.getElementById("skillsHidden");
            var list = document.getElementById("skillList");

            var value = input.value.trim();

            if (value !== "") 
            {
                if (hidden.value === "") 
                {
                    hidden.value = value;
                } 
                else 
                {
                    hidden.value += "," + value;
                }

                list.innerHTML += "<li>" + value + "</li>";

                input.value = "";
            }
        }

        function addHobby() 
        {
            var input = document.getElementById("hobbyInput");
            var hidden = document.getElementById("hobbiesHidden");
            var list = document.getElementById("hobbyList");

            var value = input.value.trim();

            if (value !== "") 
            {

                if (hidden.value === "") 
                {
                    hidden.value = value;
                } 
                else 
                {
                    hidden.value += "," + value;
                }

                list.innerHTML += "<li>" + value + "</li>";

                input.value = "";
            }
        }
        
    </script>



</body>
</html>
