package com.myapp.models;

public class ProfileDAO 
{

    public static void upsert(User u) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("REPLACE INTO profiles VALUES (?,?,?,?)");
            ps.setInt(1, u.getId());
            ps.setString(2, u.getProfile().getAbout());
            ps.setString(3, u.getProfile().getEducation());
            ps.setString(4, u.getProfile().getProfilePicturePath());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void update(User u) 
    {
        upsert(u);
    }

    public static void addSkill(int uid, String skill) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO skills VALUES (?,?)" );
            ps.setInt(1, uid);
            ps.setString(2, skill);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void removeSkill(int uid, String skill) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM skills WHERE user_id=? AND skill=?" );
            ps.setInt(1, uid);
            ps.setString(2, skill);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void addHobby(int uid, String hobby) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement( "INSERT INTO hobbies VALUES (?,?)");
            ps.setInt(1, uid);
            ps.setString(2, hobby);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 

        }
    }

    public static void removeHobby(int uid, String hobby) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM hobbies WHERE user_id=? AND hobby=?");
            ps.setInt(1, uid);
            ps.setString(2, hobby);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
