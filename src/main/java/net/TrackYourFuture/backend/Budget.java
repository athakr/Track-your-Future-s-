package net.TrackYourFuture.backend;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.TrackYourFuture.backend.model.Profile;

@Entity
@Table(name = "budget")
public class Budget{

    @Id
    private long budgetId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Profile userb;

    @Column(name = "budget_items")
    private ArrayList<String> items;
    @Column(name = "budget_costs")
    private ArrayList<Double> costs;
    
    @Column(name = "salary")
    private double salary = 0;
    @Column(name = "spending")
    private double totalCurSpend = 0;
    @Column(name = "annualbudget")
    private double annualBudget = 0;
    // private String allocMethod = "50-30-20";

/*Constructor */
    public Budget (){
        this.items = new ArrayList<String>();
        this.costs = new ArrayList<Double>();
        
    }
/*Setting and getting user */
    public void setUser(Profile user){
        this.userb = user;
    }

    public Profile getUser(Profile user){
        return this.userb;
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
    public void setSpend(Double spend){
        this.totalCurSpend = spend;
    }
    public double getSpending(){
        return totalCurSpend;
    }
/*annual Budget methods */
    public void setAnnualBudget(double sal){
        annualBudget = sal;
    }
    public double getAnnualBudget(){
        return annualBudget;
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
            if(items.get(i).equals(hold)){
                pos = i;
                i = items.size();
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
        setAnnualBudget(afterTaxSal*0.8);
        percentRecommend(annualBudget);
    }
    public void remaining(){
        double monthlyspend = getAnnualBudget()/12;
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
