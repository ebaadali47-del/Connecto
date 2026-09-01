package com.myapp.models;

public class ChatMessage
{
    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private long timestamp;

    public ChatMessage(int id, int senderId, int receiverId, String content, long timestamp)
    {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId()
    {
        return id;
    }

    public int getSenderId()
    {
        return senderId;
    }

    public int getReceiverId()
    {
        return receiverId;
    }

    public String getContent()
    {
        return content;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
