
        //Demonstration of encapsulation in java
        public class BankAccount {
            private String acccountNumber;
            private double balance;

            //construction
            public BankAccount(String acccountNumber,double balance) {
                this.acccountNumber = acccountNumber;
                this.balance = balance;
            }

                //Getter for balance
                public double getBalance() {
                    return balance;
                }

                //Getter for accountNumber
                public String getAcccountNumber() {
                    return acccountNumber;
                }
                public static void main(String[] args) {
                    BankAccount myAccount = new BankAccount("07770770",5000.50);
                    System.out.println("Account Number:" + myAccount.getAcccountNumber());
                    System.out.println("Balance:" + myAccount.getBalance());
                }
            }
        
    