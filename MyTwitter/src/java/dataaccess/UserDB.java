package dataaccess;

import java.sql.*;
import java.util.ArrayList;

import business.User;
import java.util.List;

public class UserDB {

    public static int insert(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO User (email, fullname, birthdate, password, securityquestion, securityanswer, numtwits, lastlogin, salt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getDoB());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getSecurityQuestion());
            ps.setString(6, user.getSecurityAnswer());
            ps.setString(7, user.getNumTwits());
            ps.setString(8, user.getLastLogin());
            ps.setString(9, user.getSalt());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static User search(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM User "
                + "WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserID(Integer.parseInt(rs.getString("userid")));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setDoB(rs.getString("birthdate"));
                user.setSecurityQuestion(rs.getString("securityquestion"));
                user.setSecurityAnswer(rs.getString("securityanswer"));
                user.setNumTwits(rs.getString("numtwits"));
                user.setLastLogin(rs.getString("lastlogin"));
                user.setSalt(rs.getString("salt"));
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static User searchByID(int userID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM User "
                + "WHERE userid = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(userID));
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserID(Integer.parseInt(rs.getString("userid")));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setDoB(rs.getString("birthdate"));
                user.setSecurityQuestion(rs.getString("securityquestion"));
                user.setSecurityAnswer(rs.getString("securityanswer"));
                user.setNumTwits(rs.getString("numtwits"));
                user.setLastLogin(rs.getString("lastlogin"));
                user.setSalt(rs.getString("salt"));
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int update(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE User SET "
                + "fullname = ?,"
                + "birthdate = ?,"
                + "password = ?,"
                + "securityquestion = ?,"
                + "securityanswer = ?,"
                + "numtwits = ?,"
                + "lastlogin = ?,"
                + "salt = ?"
                + " WHERE Email = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getDoB());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getSecurityQuestion());
            ps.setString(5, user.getSecurityAnswer());
            ps.setString(6, user.getNumTwits());
            ps.setString(7, user.getLastLogin());
            ps.setString(8, user.getSalt());
            ps.setString(9, user.getEmail());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<User> selectAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM User ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<User> userList = new ArrayList<User>();
            
            User user = null;
            
            while (rs.next()) {
                user = new User();
                user.setUserID(Integer.parseInt(rs.getString("userid")));
                user.setFullName(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setDoB(rs.getString("birthdate"));
                user.setSecurityQuestion(rs.getString("securityquestion"));
                user.setSecurityAnswer(rs.getString("securityanswer"));
                user.setNumTwits(rs.getString("numtwits"));
                user.setLastLogin(rs.getString("lastlogin"));
                user.setSalt(rs.getString("salt"));
                                
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int delete(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM User "
                + "WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean emailExists(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Email FROM User "
                + "WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
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
    
    public static int getUserID(String fullname){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Userid FROM User "
                + "WHERE fullname = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, fullname);
            rs = ps.executeQuery();
            int userid = -1;
            if (rs.next()) {
                userid = Integer.parseInt(rs.getString("userid"));
            }
            return userid;
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static String getFullName(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT fullname FROM User "
                + "WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            String fullname = "";
            if (rs.next()) {
                fullname = rs.getString("fullname");
            }
            return fullname;
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