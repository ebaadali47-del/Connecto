package com.myapp.models;

public class FriendRequestDAO 
{

    public static void insert(FriendRequest fr) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO friend_requests VALUES (?,?,?,?)");
            ps.setInt(1, fr.getId());
            ps.setInt(2, fr.getFromUserId());
            ps.setInt(3, fr.getToUserId());
            ps.setString(4, fr.getStatus().name());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void updateStatus(int id, FriendRequestStatus status) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("UPDATE friend_requests SET status=? WHERE id=?");
            ps.setString(1, status.name());
            ps.setInt(2, id);
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
            var ps = c.prepareStatement("DELETE FROM friend_requests WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
