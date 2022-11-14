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
    private ArrayList<Double> monthlyPay = new ArrayList<>();  //Amount that the user can use to pay loans monthly (found from calculate method)
    private ArrayList<Date> repaymentPeriod = new ArrayList<>(); //date in yyyy-MM-dd
    private double monthlyBudget;

//monthly payment methods
    public void setMonthlyPay(double amount){
        monthlyPay.add(amount);
    }
    public double getMonthlyPay(String name, double amount){
        int pos = 0;
        for(int i = 0; i < loanName.size(); i++){
            if(loanName.get(i).equals(name)){
                if(loanAmount.get(i) == amount){
                    pos = i;
                    break;
                }
                else{
                    pos = -1;
                }
            }
        }
        if(pos != -1){
            return monthlyPay.get(pos);
        }
        else{
            return -1;
        }
    }
    public void getMonthlyPay(){
        double total = 0;
        for(int i = 0; i < monthlyPay.size(); i++ ){
            System.out.println("Monthly Payment for Loan " + (i+1) + ": " + monthlyPay.get(i));
            total += monthlyPay.get(i);
        }
        System.out.printf("Total Monthly Payment: %.2f", total);
    }
//Input method
    public void input(String name, Double loan, Double interest, Integer term, String repayment){
        loanName.add(name);
        loanAmount.add(loan);
        interestRate.add(interest);
        loanTerms.add(term);    
    }
/*repayment date calculation */
    public void calcRepay(String repayment) throws Exception{
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date repaydate = date.parse(repayment);
            repaymentPeriod.add(repaydate);
    }
    //NOTE:method to check repaymentPeriod is correct, or automatically calculate it  
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
        //position of the loan that is given
        for(int i = 0; i < loanName.size(); i++){
            if(loanName.get(i).equals(name)){
                if(loanAmount.get(i) == amount){
                    pos = i;
                    break;
                }
                else{
                    //pos = -1;
                }
            }
        }
        //financial variables
        double principal_amount = amount - down;
        int terms = (loanTerms.get(pos));
        double rate = (interestRate.get(pos));
        double[] infoList = calMonPay(name, principal_amount, rate, terms);

        System.out.printf("Your monthly payment is %.2f and your total payment with interest is %.2f\n", infoList[0], infoList[1]);
        if(monthlyBudget >= infoList[2]){
            System.out.printf("You can afford to take this loan.");
        }else{
            System.out.printf("You cannot afford to take this loan.");
        }
        

    }

    public void setMonthlyBudget(double yearlyBudget){
        monthlyBudget = yearlyBudget/12;
    }

    public void calculateMonthly(String name, double amount, double rate, int terms){ //terms in years
        double[] list1 = calMonPay(name, amount, rate, terms);
        
        System.out.printf("Your monthly payment is %.2f and your total payment with interest is %.2f\n", list1[0], list1[1]);

    }
    public double[] calMonPay(String name, double amount, double rate, int terms){
        double[] pay = new double[3];
        double principal = amount;
        double mRate = rate/12;
        int totalterm = terms * 12;

        double cRateN = mRate * (Math.pow((1+mRate), totalterm));
        double cRateD = Math.pow((1+mRate), totalterm) - 1;
        
        double monthlyPayment = principal * (cRateN/cRateD);
        double totalpay = monthlyPayment * totalterm;
        double monthlyExpIncome = monthlyPayment / 0.1;

        setMonthlyPay(monthlyPayment);

        pay[0] = monthlyPayment;
        pay[1] = totalpay;
        pay[2] = monthlyExpIncome;
        return pay;
    }

/*Displaying all the items */
    public void display(){
        System.out.println("Current Monthly Loan Payments");
        for(int i = 0; i < loanName.size(); i++){
            System.out.printf(loanName.get(i) + "| Amount: %.2f | Rate: %.2f | Term: %d | Repayment: " /*+ repaymentPeriod.get(i)*/ + "\n", loanAmount.get(i), interestRate.get(i), loanTerms.get(i) );
        }
    }
}