package com.myapp.models;

import java.sql.*;
public class DataLoader 
{

    public static void loadAll(Manager manager) 
    {

        loadUsers(manager);
        loadProfiles(manager);
        loadSkills(manager);
        loadHobbies(manager);

        loadFriendRequests(manager);
        loadFriends(manager);

        loadChats(manager);
        loadMessages(manager);

        loadDeletedMessages(manager);
        loadReadReceipts(manager);

        loadNotifications(manager);
        loadSearchHistory(manager);
        loadFriendActions(manager);

        fixCounters(manager);
    }

    private static void loadUsers(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM users").executeQuery();

            while (rs.next()) 
            {
                User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));

                u.setName(rs.getString("name"));
                u.setAge(rs.getInt("age"));
                u.setGender(rs.getString("gender"));
                u.setDateOfBirth(rs.getString("dob"));

                if (rs.getBoolean("quizCompleted")) 
                {
                    u.setPersonalityProfile(new PersonalityProfile(rs.getDouble("intro"),rs.getDouble("ambi"),rs.getDouble("extro")));
                }

                m._addUser(u);
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void loadProfiles(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM profiles").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));
                if (u == null) continue;

                Profile p = u.getProfile();
                p.setAbout(rs.getString("about"));
                p.setEducation(rs.getString("education"));
                p.setProfilePicturePath(rs.getString("picture"));
            }

        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadSkills(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM skills").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u != null)
                    u.getProfile().getSkills().add(rs.getString("skill"));
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadHobbies(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM hobbies").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u != null)
                    u.getProfile().getHobbies().add(rs.getString("hobby"));
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void loadFriendRequests(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM friend_requests").executeQuery();

            while (rs.next()) 
            {
                FriendRequest fr = new FriendRequest(rs.getInt("id"), rs.getInt("from_user"), rs.getInt("to_user"));

                fr.setStatus(FriendRequestStatus.valueOf(rs.getString("status")));
                m._addFriendRequest(fr);
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void loadFriends(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM friends").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u != null)
                    u.getFriendIds().add(rs.getInt("friend_id"));
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void loadChats(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM chats").executeQuery();

            while (rs.next()) 
            {
                ChatThread t = new ChatThread(rs.getInt("id"),rs.getInt("user1"),rs.getInt("user2"));

                m._addChat(t, rs.getString("chat_key"));

            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadMessages(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM messages ORDER BY timestamp").executeQuery();

            while (rs.next()) 
            {
                ChatThread t = m._getChat(rs.getInt("chat_id"));
                if (t == null) continue;

                ChatMessage msg = new ChatMessage(rs.getInt("id"), rs.getInt("sender"), rs.getInt("receiver"), rs.getString("content"), rs.getLong("timestamp"));

                t.getMessages().addLast(msg);
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void loadDeletedMessages(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM deleted_messages").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u == null) continue;

                int chatId = rs.getInt("chat_id");
                int msgId = rs.getInt("message_id");

                SimpleArrayList<Integer> list = u.getDeletedMessagesByChat().get(chatId);

                if (list == null) 
                {
                    list = new SimpleArrayList<>();
                    u.getDeletedMessagesByChat().put(chatId, list);
                }

                list.add(msgId);
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadReadReceipts(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM read_receipts").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u != null)
                    u.getLastReadMessageByChat().put(rs.getInt("chat_id"), rs.getInt("last_read_message"));
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadNotifications(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM notifications").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u == null) 
                    continue;

                Notification n = new Notification(rs.getInt("id"), rs.getString("message"));

                if (rs.getBoolean("isRead"))
                    n.markRead();

                u.getNotifications().addLast(n);
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    private static void loadSearchHistory(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM search_history").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u != null)
                    u.getSearchHistory().addLast(new SearchHistoryEntry(rs.getString("query"),rs.getLong("timestamp")));
            }
        } 
        catch (Exception e) 
        { 

            e.printStackTrace(); 
        }
    }

    private static void loadFriendActions(Manager m) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT * FROM friend_actions").executeQuery();

            while (rs.next()) 
            {
                User u = m.getUserById(rs.getInt("user_id"));

                if (u == null) 
                    continue;

                u.getActionHistory().addLast(new FriendAction(ActionType.valueOf(rs.getString("type")), rs.getInt("user_id"),rs.getInt("other_user"),rs.getInt("request_id"),rs.getLong("timestamp")));
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }


    private static void fixCounters(Manager m) 
    {
        m._setCounters(maxId("users", "id") + 1, maxId("friend_requests", "id") + 1, maxId("chats", "id") + 1, maxId("messages", "id") + 1, maxId("notifications", "id") + 1);
    }

    private static int maxId(String table, String col) 
    {
        try (Connection c = DBConnection.getConnection()) 
        {
            ResultSet rs = c.prepareStatement("SELECT MAX(" + col + ") FROM " + table).executeQuery();

            if (rs.next()) 
                return rs.getInt(1);
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
        
        return 0;
    }
}
