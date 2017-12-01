/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Follow;
import business.Twit;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jordan
 */
public class FollowDB {
    
    public static int insert(Follow follow) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO Follow (userid, followeduserid, followdate) "
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(follow.getUserID()));
            ps.setString(2, Integer.toString(follow.getFollowedUserID()));
            ps.setString(3, follow.getDate());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    // returns who YOU'RE following if follow = true
    // returns whose following YOU if follow = false
    public static ArrayList<User> getFollowerIDs(int userID, boolean follow)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "";
        if (follow){
            query = "SELECT * FROM Follow "
                + " WHERE userid = ? ";
        }else{
            query = "SELECT * FROM Follow "
            + " WHERE followeduserid = ? ";
        }
        try {
                        
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(userID));

            rs = ps.executeQuery();
            ArrayList<User> followers = new ArrayList<User>();
            
            while (rs.next()) {
                int followedUserID = Integer.parseInt(rs.getString("followeduserid"));
                User user = UserDB.searchByID(followedUserID);
                // add every follower to the list
                followers.add(user);
            }
            
            // reverse sort followerIDs (so newest ones are first)
            Collections.reverse(followers);

            return followers;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int delete(int userID, int followID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM Follow "
                + "WHERE userid = ?"
                + "AND followeduserid = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(userID));
            ps.setString(2, Integer.toString(followID));
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<User> getNotifications(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Follow "
                + " WHERE followeduserid = ? "
                + " AND followdate >= ?";
        try {
                        
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(user.getUserID()));
            ps.setString(2, user.getLastLogin());

            rs = ps.executeQuery();
            ArrayList<User> followers = new ArrayList<User>();
            
            while (rs.next()) {
                int followedUserID = Integer.parseInt(rs.getString("userid"));
                User followUser = UserDB.searchByID(followedUserID);
                // add every follower to the list
                followers.add(followUser);
            }
            
            // reverse sort followerIDs (so newest ones are first)
            Collections.reverse(followers);

            return followers;
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
