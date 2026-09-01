package com.myapp.models;

public class FriendAction 
{
    private ActionType type;
    private int actorUserId;
    private int otherUserId;
    private int friendRequestId;
    private long timestamp;

    public FriendAction(ActionType type, int actorUserId, int otherUserId, int friendRequestId, long timestamp)
    {
        this.type = type;
        this.actorUserId = actorUserId;
        this.otherUserId = otherUserId;
        this.friendRequestId = friendRequestId;
        this.timestamp = timestamp;
    }

    public ActionType getType() 
    {
        return type;
    }

    public int getActorUserId() 
    {
        return actorUserId;
    }

    public int getOtherUserId() 
    {
        return otherUserId;
    }

    public int getFriendRequestId() 
    {
        return friendRequestId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
