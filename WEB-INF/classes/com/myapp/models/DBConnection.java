package com.myapp.models;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection 
{

    private static final String URL = "jdbc:mysql://localhost:3306/socialconnector";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    private static boolean connectedOnce = false;


    public static Connection getConnection() 
    {
        Connection conn = null;

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            connectedOnce = true;
        } 
        catch (Exception e) 
        {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }

        return conn;
    }
}
