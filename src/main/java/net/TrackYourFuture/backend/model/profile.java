package net.TrackYourFuture.backend.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.type.TrueFalseType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import net.TrackYourFuture.backend.Budget;
import net.TrackYourFuture.backend.Payment;

@Entity
@Table(name = "users")
public class Profile{

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_name")
    private String user;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToOne(
        mappedBy = "userb",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Budget userBudget;

    @OneToOne(
        mappedBy = "userp",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Payment userPayment;

    public int count = 0;

    public Profile(){
        this.userBudget = new Budget();
        this.syncBudget(this.userBudget);
        this.userPayment = new Payment();
        this.syncPayment(this.userPayment);
    }

    public Profile(String user, String password, String email, Budget userBudget, Payment userPayment){
        super();
        this.user = user;
        this.password = password;
        this.email = email;
        if (userBudget == null){
            this.userBudget = new Budget();
            this.syncBudget(this.userBudget);
        }
        if (userPayment == null){
            this.userPayment = new Payment();
            this.syncPayment(this.userPayment);
        }
        this.userBudget = userBudget;
        this.syncBudget(this.userBudget);
        this.userPayment = userPayment;
        this.syncPayment(this.userPayment);
    }

/*Synchronizing method for backend */
    public void syncBudget(Budget budget){
        if (budget == null){
            if(this.userBudget != null){
                this.userBudget.setUser(null);
            }
        }else{
            budget.setUser(this);
        }
        this.userBudget = budget;
    }

    public void syncPayment(Payment payment){
        if (payment == null){
            if(this.userPayment != null){
                this.userPayment.setUser(null);
            }
        }else{
            payment.setUser(this);
        }
        this.userPayment = payment;
    }
/*fill methods */ 
    public void fill(String username, String password, String email){
        setUserName(username);
        setEmail(email);
        setPassword(password);
        System.out.println(username + ", " + password +", " + email);
        // if(this.password != null && this.email != null){
        //   setAccountID();
        // }
    }

/*username methods */    
    public void setUserName(String name){
        this.user = name;
    }
    public String getUserName(){
        return this.user;
    }
    
/*account ID methods */    
    public void setAccountID(long id){
        this.id = id;
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
            this.email = mail;
        }
        else{
            System.out.println("Please enter a valid email address!");
            this.email = null;
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
        return this.email;
    }

/* Budget and Payment methods*/
    public void setBudget(Budget budgett){
        this.userBudget = budgett;
        this.syncBudget(this.userBudget);
    }    


    public Budget getBudget(){
        return this.userBudget;
    }

    public void setPayment(Payment paymentt){
        this.userPayment = paymentt;
        return;
    }    

    public Payment getPayment(){
        return this.userPayment;
    }
}