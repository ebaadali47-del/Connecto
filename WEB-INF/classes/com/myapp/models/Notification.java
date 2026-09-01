package com.myapp.models;

public class Notification
{
    private int id;            
    private String message;
    private boolean isRead;
    private long timestamp;

    public Notification(int id, String message)
    {
        this.id = id;
        this.message = message;
        this.isRead = false;
        this.timestamp = System.currentTimeMillis();
    }

    public int getId()
    {
        return id;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void markRead()
    {
        isRead = true;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
