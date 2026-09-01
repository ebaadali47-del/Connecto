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

    SimpleArrayList<User> friends = manager.getFriendsOfUser(userId);


    String fidParam = request.getParameter("friendId");
    int friendId = -1;
    User friend = null;

    if (fidParam != null) 
    {
        try 
        {
            friendId = Integer.parseInt(fidParam);
            friend = manager.getUserById(friendId);
        } 
        catch (Exception e) 
        {
            friend = null;
        }

    }

    SimpleLinkedList<ChatMessage> messages = null;

    if (friend != null) 
    {
        messages = manager.getChatHistory(userId, friendId);
        manager.markChatAsRead(userId, friendId);
    }

%>

<!DOCTYPE html>
<html>
<head>
    <title>Connecto | Chat</title>
    <link rel="stylesheet" href="chat.css">
</head>

<body>
    <div class="chat-topbar">
        <div class="logo">Connecto</div>

        <div class="chat-nav">
            <a href="dashboard.jsp">Home</a>
            <a href="chat.jsp" class="active">Messages</a>
        </div>

    </div>

    <div class="chat-container">

        <div class="chat-sidebar">

            <div class="sidebar-header">Chats</div>

            <% if (friends.size() == 0) { %>
                <div class="empty">No friends yet</div>
            <% } %>

            <% for (int i = 0; i < friends.size(); i++) {
                User f = friends.get(i);
            %>
                
            <% int unread = manager.getUnreadMessageCount(userId, f.getId()); %>

            <a class="friend-item <%= (f.getId() == friendId ? "active" : "") %>"

            href="chat.jsp?friendId=<%= f.getId() %>">

            <img src="<%= f.getProfile().getProfilePicturePath() %>">

            <span><%= f.getUsername() %></span>

            <% if(unread > 0){ %>
                <span class="friend-badge"><%= unread %></span>
            <% } %>

            </a>

            <% } %>

        </div>

        <div class="chat-main">

        <% if (friend == null) { %>

            <div class="chat-empty">
                Select a friend to start chatting
            </div>

        <% } else { %>
        
        <div class="chat-header">
            <img src="<%= friend.getProfile().getProfilePicturePath() %>">

            <span><%= friend.getUsername() %></span>

            <div class="chat-actions">

                <% boolean editMode = "true".equals(request.getParameter("edit")); %>

                <% if (!editMode) { %>

                    <a href="chat.jsp?friendId=<%= friendId %>&edit=true" class="edit-btn">DELETE</a>

                <% } else { %>

                    
                    <form method="post" action="deleteMessage.jsp">

                        <input type="hidden" name="friendId" value="<%= friendId %>">

                        <button type="submit" name="type" value="selected" class="delete-btn"> Delete Selected </button>

                        <button type="submit" name="type" value="all" class="delete-all-btn"> Delete All</button>
                <% } %>

            </div>

        </div>

        <div class="chat-messages">

            <% if (messages == null || messages.size == 0) { %>

                <div class="chat-empty">No messages yet</div>

            <% } else {

                SimpleLinkedList.Node<ChatMessage> node = messages.head;

                while (node != null) 
                {

                    ChatMessage msg = node.value;
                    boolean mine = msg.getSenderId() == userId;
            %>

                <div class="message-row <%= mine ? "me-row" : "them-row" %>">

                    <% if (editMode) { %>

                        <input type="checkbox" name="msgIds" value="<%= msg.getId() %>" class="msg-check">

                    <% } %>

                    <div class="bubble <%= mine ? "me" : "them" %>">

                        <%= msg.getContent() %>

                    </div>

                </div>

            <%
                    node = node.next;
                }

            } %>

        </div>

        <% if (editMode) { %>
            </form>  
        <% } %>


        <form onsubmit="sendMessage(); return false;" class="chat-input">

            <input type="hidden" id="friendId" value="<%= friendId %>">
            <input type="text" id="messageInput" placeholder="Type a message..." required>
            <button type="submit">Send</button>

        </form>

    <% } %>

    </div>
    </div>

    <script>

        function sendMessage() 
        {
            const text = document.getElementById("messageInput").value.trim();
            const friendId = document.getElementById("friendId").value;

            if (!text) return;

            fetch("sendMessage.jsp", { method: "POST", headers: { "Content-Type": "application/x-www-form-urlencoded" }, body: "friendId=" + friendId + "&message=" + encodeURIComponent(text) });

            document.getElementById("messageInput").value = "";
        }

        setInterval(() => {

            const friendId = document.getElementById("friendId")?.value;

            if (!friendId) 
                return;

            const urlParams = new URLSearchParams(window.location.search);
            const edit = urlParams.get("edit");

            if(edit === "true")
            {
                return;
            }

            fetch("loadMessages.jsp?friendId=" + friendId)
                .then(res => res.text())
                .then(html => {
                    document.querySelector(".chat-messages").innerHTML = html;
                });

        }, 2000);


    </script>

</body>
</html>
