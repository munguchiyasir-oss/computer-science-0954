import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double balance = 100000;
        double amount;
        int choice;

        do {
            System.out.println("\n======ATM======");
            System.out.println("1.Check Balance");
             System.out.println("2.Depost Money");
              System.out.println("3.Withdraw Money");
               System.out.println("4.Exit");
               System.out.println("Enter your choice");
               choice = input.nextInt();

               switch (choice) {
                case 1:
                    System.out.print("Your balance is:" + balance);
                    
                    break;
                    case 2:
                    System.out.print("Enter amount to deposit");
                    amount = input.nextDouble();
                    balance = balance + amount;
                    System.out.println("Deposit successful.");
                    System.out.println("New balance: + balance");
                    
                    break;
                    case 3:
                    System.out.print("Enter amount to withdraw:");
                    amount = input.nextDouble();
                    if(amount <= balance) {
                        balance=balance-amount;
                        System.out.print("Withdrawal successful.");
                        System.out.println("New balance:" + balance);
                    } else {
                        System.out.print("Insufficient balance.");
                    }
                    break;
                    case 4:
                        System.out.print("Thanking for using ATM.");
                        break;
                
                default: 

                  System.out.print("Invalid choice");
               }
               } while (choice!=4); 
                
               }
        }
    
