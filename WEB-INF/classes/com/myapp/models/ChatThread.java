package com.myapp.models;

public class ChatThread
{
    private int id;
    private int user1Id;
    private int user2Id;
    private SimpleLinkedList<ChatMessage> messages;

    public ChatThread(int id, int user1Id, int user2Id)
    {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.messages = new SimpleLinkedList<>();
    }

    public int getId()
    {
        return id;
    }

    public int getUser1Id()
    {
        return user1Id;
    }

    public int getUser2Id()
    {
        return user2Id;
    }

    public SimpleLinkedList<ChatMessage> getMessages()
    {
        return messages;
    }

    public boolean involvesUser(int userId)
    {
        return user1Id == userId || user2Id == userId;
    }
}
