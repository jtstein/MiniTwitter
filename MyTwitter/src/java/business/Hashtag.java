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
public class Hashtag implements Serializable {
    private int hashtagID;
    private String hashtagText;
    private int hashtagCount;
    
    public Hashtag(){
        hashtagID = -1;
        hashtagText = "";
        hashtagCount = 0;
    }
    
    public Hashtag(int hashtagID, String hashtagText, int hashtagCount){
        this.hashtagID = hashtagID;
        this.hashtagText = hashtagText;
        this.hashtagCount = hashtagCount;
    }
    
    public void setHashtagID(int hashtagID){
        this.hashtagID = hashtagID;
    }
    public int getHashtagID(){
        return this.hashtagID;
    }
    public void setHashtagText(String hashtagText){
        this.hashtagText = hashtagText;
    }
    public String getHashtagText(){
        return this.hashtagText;
    }
    public void setHashtagCount(int hashtagCount){
        this.hashtagCount = hashtagCount;
    }
    public int getHashtagCount(){
        return this.hashtagCount;
    }
    public void incHashtagCount(){
        this.hashtagCount += 1;
    }
    public void decHashtagCount(){
        this.hashtagCount -= 1;
    }   
}
