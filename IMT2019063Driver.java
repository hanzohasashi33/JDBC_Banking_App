import java.util.*;

public class IMT2019063Driver {

    public static int printWelcomeScreen(Scanner sc) {
        System.out.println("Welcome to the DressianKiolsh Bank");

        System.out.println("Login as admin or user");
        System.out.println("1.Admin");
        System.out.println("2.User");
        System.out.println("3.Exit");
        System.out.println();

        System.out.println("Enter your login privileges: ");
        int userChoice = sc.nextInt();
        System.out.println("Your choice is: " + userChoice);

        return userChoice;
    }

    public static int printUserOptions(Scanner sc) {
        System.out.println();
        System.out.println("Welcome to the user page");
        System.out.println("1.Create Account");
        System.out.println("2.Deposit");
        System.out.println("3.Withdraw");
        System.out.println("4.Transfer");
        System.out.println("5.Print Account Details");
        System.out.println("6.Update Account Details");
        System.out.println("7.Update Customer Details");
        System.out.println("8.Delete Account");
        System.out.println("9.Show all accounts");
        System.out.println("10.Show transactions from account");
        System.out.println("11.Logout as User");
        System.out.println();


        System.out.println("Enter your choice: ");
        int userChoice = sc.nextInt();
        sc.nextLine();
        System.out.println("Your choice is: " + userChoice);

        return userChoice;
    }




    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank();
        bank.initialize();

        while (true) {
            int userChoice = printWelcomeScreen(sc);

            if (userChoice == 1) {
                System.out.print("Enter your admin username: ");
                String username = sc.next();
                System.out.println("Your username is: " + username);

                System.out.print("Enter your admin password: ");
                String password = sc.next();
                System.out.println("Your password is: " + password);

                if (username.equals("admin") && password.equals("admin")) {
                    System.out.println("Welcome to the admin page");
                    while (true) {
                        System.out.println();
                        System.out.println("You are logged in as athe Bank Admin");
                        System.out.println("1.Add a new customer");
                        System.out.println("2.Delete a customer");
                        System.out.println("3.Show all customers");
                        System.out.println("4.Logout as admin");
                        System.out.println();
                        
                        System.out.print("Enter your admin option: ");
                        int adminOption = sc.nextInt();
                        System.out.println("Your option is: " + adminOption);
    
                        if (adminOption == 1) {
                            bank.addCustomer();
                        } else if (adminOption == 2) {
                            bank.deleteCustomer();
                        } else if (adminOption == 3) {
                            bank.showAllCustomers();
                        } else if (adminOption == 4) {
                            break;
                        } else {
                            System.out.println("Invalid option");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password");
                }

               

            } else if (userChoice == 2) {
                System.out.print("Enter your customer ID: ");
                int userId = sc.nextInt();
                // System.out.println("Your username is: " + userId);

                System.out.print("Enter your password: ");
                String password = sc.next();
                // System.out.println("Your password is: " + password);

                if (bank.verifyCustomer(userId, password) == 1) {
                    while (true) {
                        int userOption = printUserOptions(sc);

                        if(userOption == 1) {
                            bank.createAccount(userId,sc);
                        } else if(userOption == 2) {
                            bank.depositToAccount(userId); 
                        } else if(userOption == 3) {
                            bank.withdrawFromAccount(userId);
                        } else if(userOption == 4) {
                            bank.transaction(userId);
                        } else if(userOption == 5) {
                            bank.printAccountDetails(userId);
                        } else if(userOption == 6) {
                            bank.updateAccountDetails(userId);
                        } else if(userOption == 7) {
                            bank.updateCustomerDetails(userId);
                        } else if(userOption == 8) {
                            bank.deleteAccount(userId);
                        } else if(userOption == 9) {
                            bank.showAllAccounts(userId);
                        } else if(userOption == 10) {
                            bank.showAllTransactions(userId);
                        } else if(userOption == 11) {
                            break;
                        } else {
                            System.out.println("Invalid option");
                        }

                    }
                } else {
                    System.out.println("Invalid username or password");
                }

            } else {
                bank.endprocess();
                System.out.println("Thank you for using the DressianKiolsh Bank");
                break;
            }

        }

    }

}
