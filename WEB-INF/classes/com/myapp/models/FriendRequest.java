package com.myapp.models;

public class FriendRequest 
{
    private int id;
    private int fromUserId;
    private int toUserId;
    private FriendRequestStatus status;


    public FriendRequest(int id,int fromUserId, int toUserId) 
    {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = FriendRequestStatus.PENDING;
    }

    public int getId() 
    {
        return id;
    }

    public int getFromUserId() 
    {
        return fromUserId;
    }

    public int getToUserId() 
    {
        return toUserId;
    }

    public FriendRequestStatus getStatus() 
    {
        return status;
    }

    public void setStatus(FriendRequestStatus status) 

    {
        this.status = status;
    }
}
