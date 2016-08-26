/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminhelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 339250
 */
public class AdminHelper 
{
    public AdminHelper()
    {
        
    }
    
    public String displayTable() throws ClassNotFoundException, InstantiationException, NamingException, SQLException, IllegalAccessException
    {
        String tableDisplay = "<table border = 1><tr><th>Username</th><th>Password</th><th>Admin</th><th>Delete</th></tr>";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("jdbc/Assignment3Pool");
        Connection conn=ds.getConnection();
        
        CallableStatement cstmt = conn.prepareCall("{call getAllUserData()}");
        ResultSet rs = cstmt.executeQuery();
        
        while(rs.next())
        {
            tableDisplay += "<tr><td>" + rs.getString(1) +
                            "</td><td><a href='UserServices?passwordChange=" + rs.getString(1) +"'>" +rs.getString(2);
            if(rs.getString(3).equals("0"))
            {
                tableDisplay += "</td><td><a href='UserServices?adminChange=" + rs.getString(1) + "'>False</a>";
            }
            else
            {
                tableDisplay += "</td><td><a href='UserServices?adminChange=" + rs.getString(1) +"'>True</a>";
            }
            
            tableDisplay += "</td><td><a href='UserServices?deleteUser=" + rs.getString(1) +"'>Delete</a></td></tr>";
        }
        tableDisplay += "</table>";
        rs.close();
        cstmt.close();
        conn.close();
        return tableDisplay;
    }
}
