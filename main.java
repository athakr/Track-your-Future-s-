class Main {
  public static void main(String[] args) {
    budget p = new budget();    //So since budget extends profile, to access the previous methods we just make the person's profile as the lowest class
    p.fill("Diddy", "Computer1!", "diddy@thakur.com");
    System.out.println("Id for Diddy: "+ p.getAccountID());
    p.calculate(78000);
    p.addItem("Rent", (double)2000);
    p.addItem("Car", 140.0);
    p.addItem("phone", 36.0); 
    p.addItem("Water", 700.0);
    p.displayItem();
    p.removeItem("Water");
    p.removeItem("House");
    p.remaining();
    System.out.println(p.getSpending());
    System.out.println("Id for Diddy: "+ p.getAccountID());
  }
}
