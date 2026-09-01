package com.myapp.models;

public class FriendDAO 
{

    public static void add(int a, int b) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO friends VALUES (?,?)");
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void remove(int a, int b) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM friends WHERE user_id=? AND friend_id=?");
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
