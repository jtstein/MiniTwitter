package dataaccess;

import business.Twit;
import business.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwitDB {

    public static int insert(Twit twit) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO Twit (fullname, useremail, text, twitdate, mentionedUserID, userid) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twit.getFullName());
            ps.setString(2, twit.getEmailAddress());
            ps.setString(3, twit.getText());
            ps.setString(4, twit.getDate());
            ps.setString(5, Integer.toString(twit.getMentionedUserID()));
            ps.setString(6, Integer.toString(twit.getUserID()));

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Twit> search(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Twit "
                + " WHERE useremail = ? "
                + " or mentionedUserID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, Integer.toString(user.getUserID()));

            rs = ps.executeQuery();
            ArrayList<Twit> twitList = new ArrayList<Twit>();
            
            Twit twit = null;
            while (rs.next()) {
                twit = new Twit();
                                
                twit.setTwitID(Integer.parseInt(rs.getString("twitid")));
                twit.setFullName(rs.getString("fullname"));
                twit.setEmailAddress(rs.getString("useremail"));
                String text = rs.getString("text");
                String[] splt = text.split(" ");
                // make any hashtag or @ tag blue.
                for(int i=0; i < splt.length; i++){
                    if (splt[i].charAt(0) == '#'){
                        splt[i] = "<span style = 'color: blue'>" 
                                + "<a href=\"hashtag.jsp?hashtag=" + splt[i].substring(1) + "\">"
                                + splt[i] + 
                                "</a>"
                                + "</span>";
                    }else if(splt[i].charAt(0) == '@'){
                        if (i < splt.length){
                            if (Integer.parseInt(rs.getString("mentionedUserID")) != -1){
                                splt[i] = "<span style = 'color: blue'>" + splt[i] + "</span>";
                                splt[i+1] = "<span style = 'color: blue'>" + splt[i+1] + "</span>";
                            }
                        }
                    }
                }
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < splt.length; i++) {
                   strBuilder.append(splt[i]+ " ");
                }
                text = strBuilder.toString();
                
                twit.setText(text);
                twit.setDate(rs.getString("twitdate"));
                twit.setMentionedUserID(Integer.parseInt(rs.getString("mentionedUserID"))); 
                twit.setUserID(Integer.parseInt(rs.getString("userid")));
                twitList.add(twit);
            }
            
            // reverse sort twitList (so newest ones are first)
            Collections.reverse(twitList);

            return twitList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Twit> getAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Twit ";
        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();
            ArrayList<Twit> twitList = new ArrayList<Twit>();
            
            Twit twit = null;
            while (rs.next()) {
                twit = new Twit();
                                
                twit.setTwitID(Integer.parseInt(rs.getString("twitid")));
                twit.setFullName(rs.getString("fullname"));
                twit.setEmailAddress(rs.getString("useremail"));
                String text = rs.getString("text");
                String[] splt = text.split(" ");
                // make any hashtag or @ tag blue.
                for(int i=0; i < splt.length; i++){
                    if (splt[i].charAt(0) == '#'){
                        splt[i] = "<span style = 'color: blue'>" 
                                + "<a href=\"hashtag.jsp?hashtag=" + splt[i].substring(1) + "\">"
                                + splt[i] + 
                                "</a>"
                                + "</span>";
                    }else if(splt[i].charAt(0) == '@'){
                        if (i < splt.length){
                            if (Integer.parseInt(rs.getString("mentionedUserID")) != -1){
                                splt[i] = "<span style = 'color: blue'>" + splt[i] + "</span>";
                                splt[i+1] = "<span style = 'color: blue'>" + splt[i+1] + "</span>";
                            }
                        }
                    }
                }
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < splt.length; i++) {
                   strBuilder.append(splt[i]+ " ");
                }
                text = strBuilder.toString();
                
                twit.setText(text);
                twit.setDate(rs.getString("twitdate"));
                twit.setMentionedUserID(Integer.parseInt(rs.getString("mentionedUserID")));
                twit.setUserID(Integer.parseInt(rs.getString("userid")));
                twitList.add(twit);
            }
            
            // reverse sort twitList (so newest ones are first)
            Collections.reverse(twitList);

            return twitList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static List<Twit> getFollowingTwits(List<User> following)
    {
        List<Twit> followingTwits = new ArrayList<Twit>();
        
        for(int follow = 0; follow < following.size(); follow++){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String query = "SELECT * FROM Twit "
                    + " WHERE userid = ?";
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, Integer.toString(following.get(follow).getUserID()));

                rs = ps.executeQuery();
                
                Twit twit = null;
                while (rs.next()) {
                    twit = new Twit();

                    twit.setTwitID(Integer.parseInt(rs.getString("twitid")));
                    twit.setFullName(rs.getString("fullname"));
                    twit.setEmailAddress(rs.getString("useremail"));
                    String text = rs.getString("text");
                    String[] splt = text.split(" ");
                    // make any hashtag or @ tag blue.
                    for(int i=0; i < splt.length; i++){
                        if (splt[i].charAt(0) == '#'){
                            splt[i] = "<span style = 'color: blue'>" 
                                    + "<a href=\"hashtag.jsp?hashtag=" + splt[i].substring(1) + "\">"
                                    + splt[i] + 
                                    "</a>"
                                    + "</span>";
                        }else if(splt[i].charAt(0) == '@'){
                            if (i < splt.length){
                                if (Integer.parseInt(rs.getString("mentionedUserID")) != -1){
                                    splt[i] = "<span style = 'color: blue'>" + splt[i] + "</span>";
                                    splt[i+1] = "<span style = 'color: blue'>" + splt[i+1] + "</span>";
                                }
                            }
                        }
                    }
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 0; i < splt.length; i++) {
                       strBuilder.append(splt[i]+ " ");
                    }
                    text = strBuilder.toString();

                    twit.setText(text);
                    twit.setDate(rs.getString("twitdate"));
                    twit.setMentionedUserID(Integer.parseInt(rs.getString("mentionedUserID")));
                    twit.setUserID(Integer.parseInt(rs.getString("userid")));
                    followingTwits.add(twit);
                }

            } catch (SQLException e) {
                System.out.println(e);
                return null;
            } finally {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        }
        // reverse sort twitList (so newest ones are first)
        Collections.reverse(followingTwits);
        return followingTwits;
    }
        
    public static int delete(String twitID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM Twit "
                + "WHERE twitid = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twitID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean hasTwit(String twitID)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Twit "
                + " WHERE twitID = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twitID);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                if (rs.getString("twitid") != null){
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return false;
    }
    
    public static Twit getTwitByID(String twitID)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Twit twit = null;
        String query = "SELECT * FROM Twit "
                + " WHERE twitID = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twitID);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                twit = new Twit();
                twit.setTwitID(Integer.parseInt(rs.getString("twitid")));
                twit.setFullName(rs.getString("fullname"));
                twit.setEmailAddress(rs.getString("useremail"));
                twit.setText(rs.getString("text"));
                twit.setDate(rs.getString("twitdate"));
                twit.setMentionedUserID(Integer.parseInt(rs.getString("mentionedUserID")));
                twit.setUserID(Integer.parseInt(rs.getString("userid")));
                return twit;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return twit;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return twit;
    }
    
    public static int getTwitID(Twit twit){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT twitid FROM Twit "
                + " WHERE fullname = ?"
                + " AND useremail = ?"
                + " AND text = ?"
                + " AND twitdate = ?"
                + " AND mentionedUserID = ?"
                + " AND userid = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twit.getFullName());
            ps.setString(2, twit.getEmailAddress());
            ps.setString(3, twit.getText());
            ps.setString(4, twit.getDate());
            ps.setString(5, Integer.toString(twit.getMentionedUserID()));
            ps.setString(6, Integer.toString(twit.getUserID()));

            rs = ps.executeQuery();
            
            while (rs.next()) {
                return Integer.parseInt(rs.getString("twitid"));
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

    public static ArrayList<Twit> getNotifications(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Twit "
                + " WHERE mentionedUserID = ? "
                + " AND twitdate >= ?";
        try {
                        
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(user.getUserID()));
            ps.setString(2, user.getLastLogin());

            rs = ps.executeQuery();
            ArrayList<Twit> twitList = new ArrayList<Twit>();
            
            Twit twit = null;
            while (rs.next()) {
                twit = new Twit();
                                
                twit.setTwitID(Integer.parseInt(rs.getString("twitid")));
                twit.setFullName(rs.getString("fullname"));
                twit.setEmailAddress(rs.getString("useremail"));
                String text = rs.getString("text");
                String[] splt = text.split(" ");
                // make any hashtag or @ tag blue.
                for(int i=0; i < splt.length; i++){
                    if (splt[i].charAt(0) == '#'){
                        splt[i] = "<span style = 'color: blue'>" + splt[i] + "</span>";
                    }else if(splt[i].charAt(0) == '@'){
                        if (i < splt.length){
                            if (Integer.parseInt(rs.getString("mentionedUserID")) != -1){
                                splt[i] = "<span style = 'color: blue'>" + splt[i] + "</span>";
                                splt[i+1] = "<span style = 'color: blue'>" + splt[i+1] + "</span>";
                            }
                        }
                    }
                }
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < splt.length; i++) {
                   strBuilder.append(splt[i]+ " ");
                }
                text = strBuilder.toString();
                
                twit.setText(text);
                twit.setDate(rs.getString("twitdate"));
                twit.setMentionedUserID(Integer.parseInt(rs.getString("mentionedUserID")));
                twit.setUserID(Integer.parseInt(rs.getString("userid")));
                twitList.add(twit);
            }
            
            // reverse sort twitList (so newest ones are first)
            Collections.reverse(twitList);

            return twitList;
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