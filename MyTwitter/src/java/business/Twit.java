/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import dataaccess.UserDB;
import java.io.Serializable;

/**
 * Date: October 17th, 2017 10:00PM
 * @author jordan
 */
public class Twit implements Serializable {
    //define set/get methods for all attributes.
    private int userID;
    private int twitID;
    private String fullName;
    private String emailAddress;
    private String text;
    private String date;
    private int mentionedUserID;

    public Twit()
    {
        userID = -1;
        twitID = -1;
        fullName = "";
        emailAddress = "";
        text = "";
        date = "";
        mentionedUserID = -1;

    }
    public Twit(String fullName, String emailAddress, String text, String date, int userID)
    {
        //String[] data = fromString.replace("[", "").split(",");
        this.setFullName(fullName);
        this.setEmailAddress(emailAddress);
        this.setText(text);
        this.setDate(date);
        this.setMentionedUserID(-1);
        this.setTwitID(-1);
        this.setUserID(userID);
    }
    
    public int getUserID(){
        return this.userID;
    }
    
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    public int getTwitID()
    {
        return this.twitID;
    }
    public void setTwitID(int twitID)
    {
        this.twitID = twitID;
    }
    public String getFullName()
    {
        return this.fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public String getEmailAddress()
    {
        return this.emailAddress;
    }
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
    public String getText()
    {
        return this.text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public String getDate()
    {
        return this.date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public int getMentionedUserID()
    {
        return this.mentionedUserID;
    }
    public void setMentionedUserID(int userid)
    {
        this.mentionedUserID = userid;
    }
}
