package com.myapp.models;

public class ChatDAO 
{

    public static void create(ChatThread t, String key) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO chats VALUES (?,?,?,?)");
            ps.setInt(1, t.getId());
            ps.setInt(2, t.getUser1Id());
            ps.setInt(3, t.getUser2Id());
            ps.setString(4, key);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void saveMessage(ChatMessage m, int chatId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO messages VALUES (?,?,?,?,?,?)");
            ps.setInt(1, m.getId());
            ps.setInt(2, chatId);
            ps.setInt(3, m.getSenderId());
            ps.setInt(4, m.getReceiverId());
            ps.setString(5, m.getContent());
            ps.setLong(6, m.getTimestamp());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
