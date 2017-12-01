/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //define attributes fullname, ...
    
    //define set/get methods for all attributes.
    private int userid;
    private String fullName;
    private String email;
    private String password;
    private String DoB;
    private String securityQuestion;
    private String securityAnswer;
    private String numTwits;
    private String lastLogin;
    private String salt;
    
    public User()
    {
        userid = -1;
        fullName = "";
        email = "";
        password = "";
        DoB = "";
        securityQuestion = "";
        securityAnswer = "";
        numTwits = "";
        lastLogin = "";
        salt = "";
    }
    public User(String fullName, String email, String password, String DoB, String securityQuestion, String securityAnswer, String numTwits, String lastLogin, String salt)
    {
        //String[] data = fromString.replace("[", "").split(",");
        this.setUserID(-1);
        this.setFullName(fullName);
        this.setEmail(email);
        this.setPassword(password);
        this.setDoB(DoB);
        this.setSecurityQuestion(securityQuestion);
        this.setSecurityAnswer(securityAnswer);
        this.setNumTwits(numTwits);
        this.setLastLogin(lastLogin);
        this.setSalt(salt);
    }
    public int getUserID()
    {
        return this.userid;
    }
    public void setUserID(int userid)
    {
        this.userid = userid;
    }
    public String getFullName()
    {
        return this.fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getSecurityQuestion(){
        return this.securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
    }
    public String getSecurityAnswer(){
        return this.securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer){
        this.securityAnswer = securityAnswer;
    }
    public String getDoB(){
        return this.DoB;
    }
    public void setDoB(String DoB){
        this.DoB = DoB;
    }
    public String getNumTwits(){
        return this.numTwits;
    }
    public void setNumTwits(String numTwits){
        this.numTwits = numTwits;
    }
    public String getLastLogin(){
        return this.lastLogin;
    }
    public void setLastLogin(String lastLogin){
        this.lastLogin = lastLogin;
    }
    public String getSalt(){
        return this.salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", this.getFullName(), 
                    this.getEmail(), this.getPassword(),this.getDoB(),
                    this.getSecurityQuestion(),this.getSecurityAnswer(),
                    this.getNumTwits(),this.getLastLogin()));
      return sb.toString();
    }
}
