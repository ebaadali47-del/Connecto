package com.myapp.models;

public class Profile 
{
    private String about;
    private String education;
    private SimpleArrayList<String> skills;
    private SimpleArrayList<String> hobbies;
    private String profilePicturePath;

    public Profile() 
    {
        this.skills = new SimpleArrayList<>();
        this.hobbies = new SimpleArrayList<>();
    }

    public String getAbout() 
    {
        return about;
    }

    public void setAbout(String about) 
    {
        this.about = about;
    }

    public String getEducation() 
    {
        return education;
    }

    public void setEducation(String education) 
    {
        this.education = education;
    }

    public SimpleArrayList<String> getSkills() 
    {
        return skills;
    }

    public SimpleArrayList<String> getHobbies() 
    {
        return hobbies;
    }

    public String getProfilePicturePath() 
    {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) 
    {
        this.profilePicturePath = profilePicturePath;
    }
}
