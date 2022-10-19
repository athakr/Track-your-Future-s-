class Main {
  public static void main(String[] args) {
    //Profile testing
    profile p = new profile();    //So since budget extends profile, to access the previous methods we just make the person's profile as the lowest class
    p.fill("Diddy", "Computer1!", "diddy@thakur.com");
    System.out.println("Id for Diddy: "+ p.getAccountID());
    //Budget testing
    p.getBudget().calculate(78000);
    p.getBudget().addItem("Rent", (double)2000);
    p.getBudget().addItem("Car", 140.0);
    p.getBudget().addItem("phone", 36.0); 
    p.getBudget().addItem("Water", 700.0);
    p.getBudget().displayItem();
    p.getBudget().removeItem("Water");
    p.getBudget().removeItem("House");
    p.getBudget().remaining();
    System.out.println("Spending: " + p.getBudget().getSpending());
    //Payment testing
    p.getPayment().input("car loan1", 5000.00, 0.20, 300, "2024-08-01");
    p.getPayment().input("car loan2", 3000.00, 0.25, 299, "2024-07-30");
    p.getPayment().setMonthlyPay(6228);
    p.getPayment().setMonthlyPay(6228);
    p.getPayment().setMonthlyPay(6228);
    p.getPayment().calculate("car loan1", 1000);
    p.getPayment().calculate("car loan2", 1000);
    p.getPayment().display();
  }
}
