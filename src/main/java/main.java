import main.java.net.TrackYourFuture.backend.model.Profile;
class Main {
  public static void main(String[] args) {
    //Profile testing

    /*This includes our functional test cases UPT-1 to UPT-3 and UPT-7, each one of them highlight for what line of code it will take in */
    //Setup for the profiles
    Profile p = new Profile();    
    Profile a = new Profile();
    p.fill("Diddy", "Computer1!", "diddy@thakur.com"); //UPT-1, should result in a error thrown to the user
    a.fill("Bob","Computer1!","ugmail.com"); //UPT-2, should result in a error thrown to the user
    a.fill("Bob","a","u@gmail.com"); //UPT-3
    
    /* UPT-7 */
     a.fill("Bob","Computer1!","u@gmail.com"); //creating proper profile to demonstrate the Ids 
    System.out.println("Id for Diddy: "+ p.getAccountID()); 
    System.out.println("Id for Bob: "+ a.getAccountID());

    //UPT-4 through UPT-6 will be implemented in the last build as we need to 
    
    //Budget testing

    /*This includes our functional test cases BT-1 to BT-4, each one of them highlight for what line of code it will take in */
      p.getBudget().calculate(78000); //BT-1

      /*BT-2 */
      p.getBudget().addItem("Rent", (double)2000);
      p.getBudget().addItem("Car", 140.0);
      p.getBudget().addItem("phone", 36.0); 
      p.getBudget().addItem("Water", 700.0);
      p.getBudget().addItem("McDonalds", -100.0); //should result in an error thown to the user

      p.getBudget().displayItem();//BT-4

      /*BT-3 */
      p.getBudget().removeItem("Water");
      p.getBudget().removeItem("House"); //should result in a error thrown to the user
      p.getBudget().removeItem("Bike"); //should result in a error thrown to the user

      /*BT-4 */
      p.getBudget().remaining();
      System.out.println("Spending: " + p.getBudget().getSpending());
      //Payment testing
      /*This includes our functional test cases MPTT-1 to MPTT-3, each one of them highlight for what line of code it will take in */
        /*MPTT-1 */
      p.getPayment().input("car loan1", 5000.00, 0.20, 4, "2024-08-01");
      p.getPayment().input("car loan2", 3000.00, 0.25, 4, "2024-07-30");

    /* MPTT-2 : Incorrect loan terms or information */
      p.getPayment().input("car loan3", 30000.00, 0.15, -4, "2023-07-30"); //improper terms
      p.getPayment().input("car loan3", 30000.00, 10.15, 1, "2023-07-30"); //improper interest rate
      p.getPayment().input("car loan3", -10000.00, 0.15, 30, "2052-07-30"); //improper amount
    /*MPTT-3 Display loan payments */
      p.getPayment().getMonthlyPay();
      p.getPayment().display();
  }
}