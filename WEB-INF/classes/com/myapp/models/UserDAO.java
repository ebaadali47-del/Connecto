package com.myapp.models;

public class UserDAO 
{

    public static void insert(User u) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("INSERT INTO users " +"(id, username, password, name, age, gender, dob, quizCompleted, intro, ambi, extro) " +"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, u.getId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getName());
            ps.setInt(5, u.getAge());
            ps.setString(6, u.getGender());
            ps.setString(7, u.getDateOfBirth());
            ps.setBoolean(8, u.isQuizCompleted());
            ps.setDouble(9, 0.0);
            ps.setDouble(10, 0.0);
            ps.setDouble(11, 0.0);
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void updateBasicInfo(User u) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var ps = c.prepareStatement("UPDATE users SET name=?, age=?, gender=?, dob=? WHERE id=?" );
            ps.setString(1, u.getName());
            ps.setInt(2, u.getAge());
            ps.setString(3, u.getGender());
            ps.setString(4, u.getDateOfBirth());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public static void updatePersonality(User u) 
    {
        try (var c = DBConnection.getConnection()) 
        {
            var p = u.getPersonalityProfile();
            var ps = c.prepareStatement("UPDATE users SET intro=?, ambi=?, extro=?, quizCompleted=true WHERE id=?");
            ps.setDouble(1, p.getIntrovertConfidence());
            ps.setDouble(2, p.getAmbivertConfidence());
            ps.setDouble(3, p.getExtrovertConfidence());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
