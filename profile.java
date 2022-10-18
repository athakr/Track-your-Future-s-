public class profile{
    private String user;
    private int id;
    private String password;
    private String eMail;
    public int count = 0;

/*username methods */    
    public void setUserName(String name){
        user = name;
    }
    public String getUserName(){
        return user;
    }
    
/*account ID methods */    
    public void setAccountID(){
        id = genAccountId();
    }
    public int genAccountId(){
        count++;
        return count;
    }
    public int getAccountID(){
        return id;
    }

/*Password methods */
    public void setPassword(String pass){
        boolean valid = checkPassword(pass);
        if (valid){
            password = pass;
        }
        else{
            System.out.println("Please choose a proper password!");
            password = null;
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
        return password;
    }
/*Email methods */
    public void setEmail(String mail){
        boolean valid = checkEmail(mail);
        if(valid){
            eMail = mail;
        }
        else{
            System.out.println("Please enter a valid email address!");
            eMail = null;
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
        return eMail;
    }
}