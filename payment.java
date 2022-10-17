public class payment extends budget{
    private ArrayList<Double> loanAmount = new ArrayList<Double>();
    private ArrayList<Double> interestRate = new ArrayList<Double>(); //Annual interest?
    private double monthlyPay;  //Amount that the user can use to pay loans monthly (found from calculate method)
    //private ? loanTerms;
    private ArrayList<String> repaymentPeriod = new ArrayList<String>(); //date in month/day/year date xx/xx/xx 
    //private int userAccountId;


    public static void main(String args[]){

    }

//Input method
    public void input(Double loan, Double interest/* loanT*/, String repayment){
        loanAmount.add(loan);
        interestRate.add(interest);
        //loanTerms.add(loanT);
        repaymentPeriod.add(repayment);//NOTE: add a method to check repaymentPeriod is correct, or automatically calculate it  
    }

//Calculate method
    public void calculate(){
        double monthlySalary = getSalary()/12.0;
        monthlyPay = monthlySalary * 0.10; //from 20-4-10 rule?
    }

/*Displaying all the items */
    public void display(){
        System.out.println("Current Monthly Loan Payments");
        for(int i = 0; i < loanAmount.size(); i++){
            System.out.println("something");
        }
    }
}
