import java.util.*;
public class budget extends profile{
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<Double> costs = new ArrayList<Double>();
    private double salary = 0;
    private double annualBudget = 0;
    private int userAccountId = 0;
    private String allocMethod = null;
    private double allocPercent = 0.0; /* this is an allocation in *100 */



    public static void main(String args[]){

    }

/*salary methods */
    public void setSalary(double sal){
        salary = sal;
    }
    public double getSalary(){
        return salary;
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
        }
    }
/*Calculate and annualBudget */
    
/*Displaying all the items */
    public void displayItem(){
        System.out.println("Current Item in Budget");
        for(int i = 0; i < items.size(); i++){
            System.out.println(items.get(i) + ": " + costs.get(i));
        }
    }
    public void percentRecommend(double sal){
        setSalary(sal);
        double needs = sal*0.5;
        double wants = sal*0.3;
        double savings = sal*0.2;

        System.out.print("Allocation for needs: $" + needs + "\nAllocation for wants: $" + wants + "\nAllocation for savings: $" + savings);
    }
}
