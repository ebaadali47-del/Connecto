package com.myapp.models;

public class MessageStateDAO 
{

    public static void markDeleted(int userId, int chatId, int msgId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO deleted_messages VALUES (?,?,?)");
            ps.setInt(1, userId);
            ps.setInt(2, chatId);
            ps.setInt(3, msgId);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void updateRead(int userId, int chatId, int msgId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("REPLACE INTO read_receipts VALUES (?,?,?)");
            ps.setInt(1, userId);
            ps.setInt(2, chatId);
            ps.setInt(3, msgId);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
