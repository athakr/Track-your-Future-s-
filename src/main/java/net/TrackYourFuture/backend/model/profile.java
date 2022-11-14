package net.TrackYourFuture.backend.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String user;

    @Column(name = "password")
    private String password;


    private String eMail;
    public int count = 0;
    private budget userBudget = new budget();
    private payment userPayment = new payment();

    public Profile(){

    }
    public Profile(String user, String password, String email){
        super();
        this.user = user;
        this.password = password;
        this.email = email;
    }
/*fill methods */ 
    public void fill(String username, String password, String email){
        setUserName(username);
        setEmail(email);
        setPassword(password);
        System.out.println(username + ", " + password +", " + email);
    }

/*username methods */    
    public void setUserName(String name){
        this.user = name;
    }
    public String getUserName(){
        return this.user;
    }
    
/*account ID methods */    
    public void setAccountID(){
        this.id = genAccountId();
    }
    public long genAccountId(){
        this.count++;
        return this.count;
    }
    public long getAccountID(){
        return this.id;
    }

/*Password methods */
    public void setPassword(String pass){
        boolean valid = checkPassword(pass);
        if (valid){
            this.password = pass;
        }
        else{
            System.out.println("Please choose a proper password!");
            this.password = null;
        }
    }
    public boolean checkPassword(String pass){
        boolean proper = false;
        char[] sym = {'~','`', '!', '@', '#', '$', '%', '^','&', '*', '(', ')', '|', '[', ']'};
        int exists = 0;
        if(pass.length() >= 8){
            proper = true;
            
            for(int i = 0; i < pass.length(); i++){
                for(int j = 0; j < sym.length; j++){
                    if(pass.charAt(i) == sym[j]){
                        exists = 1;
                    }
                }
            }
        }
        if (exists == 1){
            proper = true;
        }
        else{
            proper = false;
        }
        return proper;
    }

    public String getPassword(){
        return this.password;
    }
/*Email methods */
    public void setEmail(String mail){
        boolean valid = checkEmail(mail);
        if(valid){
            this.eMail = mail;
        }
        else{
            System.out.println("Please enter a valid email address!");
            this.eMail = null;
        }
    }
    public boolean checkEmail(String mail){
        boolean valid = false;
        char check = '@';
        for(int i = 0; i < mail.length(); i++){
            if(mail.charAt(i) == check){
                valid = true;
            }
        }
        return valid;
    }

    public String getEMail(){
        return this.eMail;
    }

/* Budget and Payment methods*/
    public void setBudget(budget budgett){
        this.userBudget = budgett;
    }    


    public budget getBudget(){
        return this.userBudget;
    }

    public void setPayment(payment paymentt){
        this.userPayment = paymentt;
        return;
    }    


    public payment getPayment(){
        return this.userPayment;
    }
}