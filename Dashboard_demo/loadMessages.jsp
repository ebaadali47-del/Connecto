<%@ page import="com.myapp.models.*" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) return;

    Manager manager = (Manager) application.getAttribute("manager");

    if (manager == null) 
        return;

    int friendId = Integer.parseInt(request.getParameter("friendId"));

    User friend = manager.getUserById(friendId);

    if (friend == null) 
        return;

    SimpleLinkedList<ChatMessage> messages = manager.getChatHistory(userId, friendId);

    manager.markChatAsRead(userId, friendId);

    boolean editMode = "true".equals(request.getParameter("edit"));
%>

    <% if (messages == null || messages.size == 0) { %>

        <div class="chat-empty">No messages yet</div>

    <% } 
    else 
    {

        SimpleLinkedList.Node<ChatMessage> node = messages.head;

        while (node != null) 
        {

            ChatMessage msg = node.value;
            boolean mine = msg.getSenderId() == userId;
    %>

        <div class="message-row <%= mine ? "me-row" : "them-row" %>">

            <% if (mine && editMode) { %>
                <input type="checkbox" name="msgIds" value="<%= msg.getId() %>" class="msg-check">
            <% } %>

            <div class="bubble <%= mine ? "me" : "them" %>">

                <%= msg.getContent() %>
                
            </div>

        </div>

    <%
            node = node.next;
        }
    }
%>

