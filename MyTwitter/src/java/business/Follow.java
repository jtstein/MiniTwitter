/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author jordan
 */
public class Follow implements Serializable {
    
    private int followID;
    private int userID;
    private int followedUserID;
    private String date;
    
    
    public Follow(){
        this.followID = -1;
        this.userID = -1;
        this.followedUserID = -1;
        this.date = "";
    }
    
    public Follow(int userID, int followedUserID, String date){
        this.userID = userID;
        this.followedUserID = followedUserID;
        this.date = date;
    }
    
    public int getFollowID(){
        return this.followID;
    }
    public void setFollowID(int followID){
        this.followID = followID;
    }
    public int getUserID(){
        return this.userID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    public int getFollowedUserID(){
        return this.followedUserID;
    }
    public void setFollowedUserID(int followedUserID){
        this.followedUserID = followedUserID;
    }
    
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
