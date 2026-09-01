package com.myapp.models;

public class ActionHistoryDAO 
{

    public static void insert(int userId, FriendAction a) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO friend_actions VALUES (?,?,?,?,?)");
            ps.setInt(1, userId);
            ps.setString(2, a.getType().name());
            ps.setInt(3, a.getOtherUserId());
            ps.setInt(4, a.getFriendRequestId());
            ps.setLong(5, a.getTimestamp());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void clear(int userId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM friend_actions WHERE user_id=?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void deleteAtIndex(int userId, int index) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var selectPs = c.prepareStatement("SELECT timestamp FROM friend_actions WHERE user_id=? ORDER BY timestamp LIMIT 1 OFFSET ?");
            selectPs.setInt(1, userId);
            selectPs.setInt(2, index);
            var rs = selectPs.executeQuery();

            if (rs.next()) 
            {
                long ts = rs.getLong("timestamp");
                var deletePs = c.prepareStatement("DELETE FROM friend_actions WHERE user_id=? AND timestamp=?");
                deletePs.setInt(1, userId);
                deletePs.setLong(2, ts);
                deletePs.executeUpdate();
            } 
            else 
            {
                System.out.println("No action found at this index.");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
