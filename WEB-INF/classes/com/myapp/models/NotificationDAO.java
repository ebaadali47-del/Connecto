package com.myapp.models;

public class NotificationDAO 
{

    public static void insert(int userId, Notification n) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO notifications VALUES (?,?,?,?,?)");
            ps.setInt(1, n.getId());
            ps.setInt(2, userId);
            ps.setString(3, n.getMessage());
            ps.setBoolean(4, n.isRead());
            ps.setLong(5, System.currentTimeMillis());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void delete(int id)
    {
        try (var c = DBConnection.getConnection())
        {
            var ps = c.prepareStatement(
                "DELETE FROM notifications WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void markRead(int id) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("UPDATE notifications SET isRead=true WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void clearForUser(int userId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM notifications WHERE user_id=?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
