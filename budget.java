import java.util.*;
public class budget extends profile{
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<Double> costs = new ArrayList<Double>();
    private double salary = 0;
    private double totalCurSpend = 0;
    private double annualBudget = 0;
    private int userAccountId = 0;
    private String allocMethod = "50-30-20";

/*fill methods */ //Note: move this to the lowest class in the hierarchy later  
    public void fill(String username, String password, String email){
        setUserName(username);
        setEmail(email);
        setPassword(password);
        System.out.println(username + ", " + password +", " + email);
    }
/*salary methods */
    public void setSalary(double sal){
        salary = sal;
    }
    public double getSalary(){
        return salary;
    }
/*Spending methods */
    public void setSpending(ArrayList<Double> cost){
        double total = 0;
        for(int i = 0; i < cost.size(); i++){
            total = total + cost.get(i);
        }
        totalCurSpend = total;
    }
    public double getSpending(){
        return totalCurSpend;
    }
/*annual Budget methods */
    public void setBudget(double sal){
        annualBudget = sal;
    }
    public double getBudget(){
        return annualBudget;
    }

/*Id methods */
    public void setID(){
        userAccountId = super.getAccountID();
    }
    public int getID(){
        return userAccountId;
    }
/*Adding item and its cost to the arraylist set */
    public void addItem(String name){
        items.add(name);
        costs.add(0.0);
    }
    public void addItem(String name, Double cost){
        items.add(name);
        costs.add(cost);
    }
/*removing an item */
    public void removeItem(String name){
        String hold = name;
        int pos = 0;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i) == hold){
                pos = i;
            }
            else{
                pos = -1;
            }
        }
        if(pos == -1){
            System.out.println("That item does not exist");
        }
        else{
            items.remove(pos);
            costs.remove(pos);
            System.out.println(name + " has been removed from the budget.");
        }
    }
/*Calculate and annualBudget */
    public void calculate(double sal){
        setSalary(sal);
        double afterTaxSal = afterTax(sal);
        setBudget(afterTaxSal*0.8);
        percentRecommend(annualBudget);
    }
    public void remaining(){
        double monthlyspend = getBudget()/12;
        setSpending(costs);
        if(totalCurSpend >= monthlyspend){
            System.out.println("You have reached or exceed your monthly budget");
        }
        else{
            System.out.printf("You still have $%.2f to spend for this month.\n", (monthlyspend - totalCurSpend));
        }
    }
    public double afterTax(double sal){
        double afterTax;
        double fedTax = 0;
        double stateTax = 0;

        /*fed */
        if(sal > 523601.0){
            fedTax = 157804.25 + ((sal - 523600)*.37);
        }
        else if(sal > 209426){
            fedTax = 47843 + ((sal - 209425)*.35);
        }
        else if(sal > 164926){
            fedTax = 33603 + ((sal - 164925)*.32);
        }
        else if(sal > 86376){
            fedTax = 14751 + ((sal - 86375)*.24);
        }
        else if(sal > 40526){
            fedTax = 4664 + ((sal - 40525)*.22);
        }
        else if(sal > 9951){
            fedTax = 995 + ((sal - 9950)*.12);
        }
        else{
            fedTax = sal * .1;
        }

        /*state for Ohio*/
        stateTax = sal * 0.0399;
    /*after tax calculation */
        afterTax = sal - fedTax - stateTax;
        return afterTax;

    }
/*Displaying all the items */
    public void displayItem(){
        System.out.println("Current Item in Budget");
        for(int i = 0; i < items.size(); i++){
            System.out.println(items.get(i) + ": " + costs.get(i));
        }
    }
    public void percentRecommend(double netSal){
        
        double needs = (netSal/12)*0.5;
        double wants = (netSal/12)*0.3;
        double savings = (netSal/12)*0.2;

        System.out.printf("monthly allocation for needs: $%.2f \nmonthly allocation for wants: $%.2f\nmonthly allocation for savings: $%.2f\n", needs, wants, savings);
    }
}
