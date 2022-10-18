import java.util.*;
import java.util.Date;
import java.sql.*;
import java.time.*;
import java.text.*;
public class payment extends budget{
    private ArrayList<String> loanName = new ArrayList<>(); //List of loans
    private ArrayList<Double> loanAmount = new ArrayList<Double>();// list of loan amounts collerated to the loan names
    private ArrayList<Double> interestRate = new ArrayList<Double>(); //List of Interest collerated to the interest amount
    private ArrayList<Integer> loanTerms = new ArrayList<>(); //List of Loanterms for the loans 
    private double monthlyPay;  //Amount that the user can use to pay loans monthly (found from calculate method)
    //private int loanTerms = 0;
    private ArrayList<Date> repaymentPeriod = new ArrayList<>(); //date in yyyy-MM-dd
    private int userAccountId;


    public static void main(String args[]){

    }

//Input method
    public void input(String name, Double loan, Double interest, Integer term, String repayment){
        loanName.add(name);
        loanAmount.add(loan);
        interestRate.add(interest);
        loanTerms.add(term);
        ;//NOTE: add a method to check repaymentPeriod is correct, or automatically calculate it  
    }
/*repayment date calculation */
    public void calcRepay(String repayment) throws Exception{
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date repaydate = date.parse(repayment);
            repaymentPeriod.add(repaydate);
    }
    public void calcRepay(String repayment, int loanTerm) throws Exception{
        int year = loanTerm*12;
        if(repayment.length() > 2){
            System.out.println("Please input 0 if you do not know your repayment day");
        }
        else{
            LocalDate ld = LocalDate.now();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String d1 = date.format(ld);
            try{
                cal.setTime(date.parse(d1));
            }catch(ParseException e){
                e.printStackTrace(); 
            }

            cal.add(Calendar.YEAR, year);
            String dateAfter = date.format(cal.getTime());  
            Date repayDate = date.parse(dateAfter);
            repaymentPeriod.add(repayDate);
        }
    }
//Calculate method
    public void calculate(String name, double amount){ //UNDERWORK
        //implements 20-4-10 rule. 20% downpayment, average of 4 years to be paid and 10% of your monthly income.
        int pos = 0; //position of the loan in the arraylist
        double down = amount * 0.2;
        for(int i = 0; i < loanName.size(); i++){
            if(loanName.get(i).equals(name)){
                if(interestRate.get(i) == amount){
                    pos = i;
                    break;
                }
                else{
                    pos = -1;
                }
            }
        }

    }

    public void calculateMonthly(String name, double amount, double rate, int terms){ //terms in years
        double principal = amount;
        double mRate = rate/12;
        int totalterm = terms * 12;

        double cRateN = mRate * (Math.pow((1+mRate), totalterm));
        double cRateD = Math.pow((1+mRate), totalterm) - 1;
        
        double monthlyPayment = principal * (cRateN/cRateD);
        double totalpay = monthlyPayment * totalterm;

        System.out.printf("Your monthly payment is %.2f and your total payment with interest is %.2f", monthlyPayment, totalpay);
    }

/*Displaying all the items */
    public void display(){
        System.out.println("Current Monthly Loan Payments");
        for(int i = 0; i < loanAmount.size(); i++){
            System.out.println("something");
        }
    }
}