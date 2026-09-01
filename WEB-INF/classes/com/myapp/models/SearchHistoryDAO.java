package com.myapp.models;

public class SearchHistoryDAO 
{

    public static void insert(int userId, SearchHistoryEntry e) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO search_history VALUES (?,?,?)");
            ps.setInt(1, userId);
            ps.setString(2, e.getQuery());
            ps.setLong(3, e.getTimestamp());
            ps.executeUpdate();
        } 
        catch (Exception ex) 
        { 
            ex.printStackTrace(); 
        }
    }

    public static void clear(int userId) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("DELETE FROM search_history WHERE user_id=?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } 
        catch (Exception ex) 
        { 
            ex.printStackTrace(); 
        }
    }

     public static void deleteAtIndex(int userId, int index) 
     {
        try (var c = DBConnection.getConnection()) 
        {
            
            var selectPs = c.prepareStatement("SELECT timestamp FROM search_history WHERE user_id=? ORDER BY timestamp LIMIT 1 OFFSET ?" );
            selectPs.setInt(1, userId);
            selectPs.setInt(2, index);
            var rs = selectPs.executeQuery();

            if (rs.next()) 
            {

                long ts = rs.getLong("timestamp");
                var deletePs = c.prepareStatement("DELETE FROM search_history WHERE user_id=? AND timestamp=?");

                deletePs.setInt(1, userId);
                deletePs.setLong(2, ts);
                deletePs.executeUpdate();
            } 
            else 
            {
                System.out.println("No search entry found at this index.");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
