package com.myapp.models;

public class PublicProfileSummary 
{
    private int userId;
    private String name;
    private String about;
    private String profilePicturePath;
    private int friendsCount;

    public PublicProfileSummary(int userId, String name, String about, String profilePicturePath, int friendsCount) 
    {
        this.userId = userId;
        this.name = name;
        this.about = about;
        this.profilePicturePath = profilePicturePath;
        this.friendsCount = friendsCount;
    }

    public int getUserId() 
    {
        return userId;
    }

    public String getName() 
    {
        return name;
    }

    public String getAbout() 
    {
        return about;
    }

    public String getProfilePicturePath() 
    {
        return profilePicturePath;
    }

    public int getFriendsCount() 

    {
        return friendsCount;
    }
}
