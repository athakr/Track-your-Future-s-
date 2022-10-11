public class profile{
    private String user;
    private int id;
    private String password;
    private String eMail;
    public int count = 0;

public static void main(String args[]){
    fill("Diddy", "Computer$", "diddy@thakur.com");
}
    public void setUserName(String name){
        user = name;
    }
    public void setAccountID(){
        id = genAccountId();
    }
    public int genAccountId(){
        count++;
        return count;
    }
    public void setPassword(String pass){
        boolean valid = checkPassword(pass);
        if (valid){
            password = pass;
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
    public void setEmail(String mail){
        boolean valid = checkEmail(mail);
        if(valid){
            eMail = mail;
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
    public void fill(String username, String password, String email){
        setUserName(username);
        setEmail(email);
        setPassword(password);
        System.out.println(username + ", " + password +", " + email);
    }
}