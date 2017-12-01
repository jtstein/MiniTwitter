/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Hashtag;
import business.Twit;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jordan
 */
public class HashtagDB {
    public static int insert(Hashtag hashtag) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO Hashtag (hashtagtext, hashtagcount) "
                + "VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, hashtag.getHashtagText());
            ps.setString(2, Integer.toString(hashtag.getHashtagCount()));
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    // checks if the hashtag is already in the table
    public static boolean inHashtagTable(String hashtagText){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        System.out.println("in inHashtagTable hashtagtext is "+hashtagText);

        String query = "SELECT hashtagtext FROM Hashtag "
                + "WHERE hashtagtext = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, hashtagText);
            rs = ps.executeQuery();
            System.out.println("rs.next() is: " +rs.next());
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }   
    }
    
    public static Hashtag search(String hashtagText) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Hashtag "
                + "WHERE hashtagtext = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, hashtagText);
            rs = ps.executeQuery();
            Hashtag hashtag = null;
            if (rs.next()) {
                hashtag = new Hashtag();
                hashtag.setHashtagID(Integer.parseInt(rs.getString("hashtagid")));
                hashtag.setHashtagText(rs.getString("hashtagtext"));
                hashtag.setHashtagCount(Integer.parseInt(rs.getString("hashtagcount")));
            }
            return hashtag;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int updateHashtagCount(Hashtag hashtag) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE Hashtag SET "
                + "hashtagcount = ?"
                + " WHERE hashtagtext = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(hashtag.getHashtagCount()));
            ps.setString(2, hashtag.getHashtagText());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int delete(Hashtag hashtag) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM Hashtag "
                + "WHERE hashtagid = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(hashtag.getHashtagID()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
        
    public static int getHashtagID(Hashtag hashtag){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT hashtagid FROM Hashtag "
                + " WHERE hashtagText = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, hashtag.getHashtagText());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                return Integer.parseInt(rs.getString("hashtagid"));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return -1;
    }
    
    public static ArrayList<Hashtag> getTrendyHashtags()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Hashtag "
                     + " ORDER BY hashtagcount desc "
                     + " limit 10";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Hashtag> hashtagList = new ArrayList<Hashtag>();
            
            Hashtag hashtag = null;
            while (rs.next()) {
                hashtag = new Hashtag();
                hashtag.setHashtagID(Integer.parseInt(rs.getString("hashtagid")));
                String hashtagText = rs.getString("hashtagtext");
                hashtag.setHashtagCount(Integer.parseInt(rs.getString("hashtagcount")));
                
                // format hashtagText to be a link
                hashtagText = "<span style = 'color: blue'>"
                        + "<a href=\"hashtag.jsp?hashtag=" 
                        + hashtagText.substring(1)
                        + "\">"
                        + hashtagText
                        + "</a></span>";
                hashtag.setHashtagText(hashtagText);
                hashtagList.add(hashtag);
            }

            return hashtagList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
}
