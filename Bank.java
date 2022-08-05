import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;

public class Bank {

    // Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql://localhost/companydb";
    static final String DB_URL = "jdbc:mysql://localhost/bank?useSSL=false";
    // Database credentials
    static final String USER = "<USER>";
    static final String PASS = "<PASSWORD>";

    static Scanner sc = new Scanner(System.in);

    static int customer_id = 0;
    static int account_num = 0;
    static int transaction_id = 0;

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs;

    public void initialize() 
    {
        try 
        {
            // STEP 2b: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

        } catch (SQLException se) 
        {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) 
        {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    // end main



    public void addCustomer() {

        System.out.println("Enter name:");
        String customerName = sc.nextLine();

        System.out.println("Enter customer phone number:");
        String customerPhone = sc.nextLine();

        System.out.println("Enter customer Email Id:");
        String customerEmail = sc.nextLine();

        System.out.println("Set a Password: ");
        String passwrd = sc.nextLine();


        try {
            String sql1, sql2;
            sql1 = "select * from customer;";
            ResultSet rs1 = stmt.executeQuery(sql1);
            
            while (rs1.next()) 
            {
                customer_id = rs1.getInt("id");
            }

            rs1.close();
            customer_id += 1;
    
            sql2 = "insert into customer values (" + customer_id + "," + "'" + customerName + "'" + "," + "'" + customerEmail
                    + "'" + "," +"'" + customerPhone + "'," + "'" + passwrd + "'" + ");";
            
            System.out.println(sql2);
            stmt.executeUpdate(sql2);
            

            // System.out.println("Customer Added!");
            System.out.println("\n Customer Created successful!");
            System.out.println("customer id: " + customer_id);
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void showAllCustomers() {
        try {
            String sql1;
            sql1 = "select * from customer;";
            ResultSet rs1 = stmt.executeQuery(sql1);

            System.out.println("Printing out all the customers");
            
            while (rs1.next()) 
            {
                customer_id = rs1.getInt("id");
                String customer_name = rs1.getString("name");
                String customer_email = rs1.getString("email");
                String customer_phone = rs1.getString("phone");

                System.out.print("Customer id: " + customer_id + " ");
                System.out.print("Customer name: " + customer_name + " ");
                System.out.print("Customer email: " + customer_email + " ");
                System.out.print("Customer phone: " + customer_phone + "\n");
            }

            rs1.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void deleteCustomer() 
    {
        System.out.println("Enter customer id to delete:");
        int customer_id = sc.nextInt();

        try 
        {
            String sql1;
            sql1 = "delete from customer where id = " + customer_id + ";";
            stmt.executeUpdate(sql1);
            System.out.println("Customer deleted!");
        } catch (SQLException se) 
        {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) 
        {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public int verifyCustomer(int custId,String passwrd) 
    {
        try 
        {
            String sql1;
            sql1 = "select * from customer where id = " + custId + ";";
            ResultSet rs1 = stmt.executeQuery(sql1);

            while(rs1.next())
            {
                String customer_passwrd = rs1.getString("passwrd");
                if(customer_passwrd.equals(passwrd))
                {
                    System.out.println("Customer verified!");
                    System.out.println("Customer Details: ");
                    System.out.println("Customer id: " + custId);
                    System.out.println("Customer name: " + rs1.getString("name"));
                    System.out.println("Customer email: " + rs1.getString("email"));
                    System.out.println("Customer phone: " + rs1.getString("phone"));
                    return 1;
                }
                else
                {
                    System.out.println("Customer not verified!");
                    return 0;
                }
            }
            

        } catch (SQLException se) 
        {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) 
        {
            // Handle errors for Class.forName
            e.printStackTrace();
        }

        return -1;

    }



    public void createAccount(int userId, Scanner sn)
    {

        System.out.print("Enter pin:");
        String pin = sn.nextLine();

        System.out.print("Enter initial amount deposited:");
        double balance = sn.nextDouble();


        try 
        {
            String sql1;
            sql1 = "select * from account;";
            ResultSet rs1 = stmt.executeQuery(sql1);
            
            while (rs1.next()) 
            {
                account_num = rs1.getInt("id");
            }

            rs1.close();
            account_num += 1;
    
            String sql2 = "insert into account values ('" + account_num + "','" + pin + "','" + balance + "','" + userId + "');";
            stmt.executeUpdate(sql2);
            

            // System.out.println("Customer Added!");
            System.out.println("\n Account Created successful!");
            System.out.println("Account Number: " + account_num);
        } catch (SQLException se) 
        {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) 
        {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void depositToAccount(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter pin number: ");
        String pin_num = sc.nextLine();

        System.out.println("Enter amount to be deposited:");
        double amount = sc.nextDouble();

        try {
            String sql1;
            sql1 = "select * from account where id = " + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                String pin = rs5.getString("pin");
                double balance = rs5.getDouble("balance");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId && pin.equals(pin_num)) 
                {
                    balance += amount;
                    String sql2 = "update account set balance = " + balance + " where id = " + account_num + ";";
                    stmt.executeUpdate(sql2);
                    System.out.println("Deposit successful!");
                    System.out.println("New Balance: " + balance);

                    String sql9;
                    sql9 = "select max(id) as number from transaction;";
                    ResultSet rs9 = stmt.executeQuery(sql9);
                    while(rs9.next())
                    {
                        transaction_id = rs9.getInt("number");
                    }
                    rs9.close();
                    transaction_id +=1;
                    
                    // get current date 
                    LocalDateTime txnTime = LocalDateTime.now();
                    java.sql.Timestamp sqlTxnTime = java.sql.Timestamp.valueOf(txnTime);

                    String sql4 = "insert into transaction values ('" + transaction_id + "','" + account_num + "','" + "credit" + "','" + amount + "','" + sqlTxnTime +  "');";
                    stmt.executeUpdate(sql4);
                    System.out.println("Transaction successful!");

                    break;
                } 
                else 
                {
                    System.out.println("Account number does not exist!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void withdrawFromAccount(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter pin number: ");
        String pin_num = sc.nextLine();

        System.out.println("Enter amount to be withdrawn:");
        double amount = sc.nextDouble();

        try {
            String sql1;
            sql1 = "select * from account where id = " + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                String pin = rs5.getString("pin");
                double balance = rs5.getDouble("balance");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId && pin.equals(pin_num)) 
                {
                    if(amount <= balance) {
                        balance -= amount;
                        String sql2 = "update account set balance = " + balance + " where id = " + account_num + ";";
                        stmt.executeUpdate(sql2);
                        System.out.println("Withdraw successful!");
                        System.out.println("New Balance: " + balance);

                        String sql9;
                        sql9 = "select max(id) as number from transaction;";
                        ResultSet rs9 = stmt.executeQuery(sql9);
                        while(rs9.next())
                        {
                            transaction_id = rs9.getInt("number");
                        }
                        rs9.close();
                        transaction_id +=1;

                        // get current date 
                        LocalDateTime txnTime = LocalDateTime.now();
                        java.sql.Timestamp sqlTxnTime = java.sql.Timestamp.valueOf(txnTime);

                        String sql4 = "insert into transaction values ('" + transaction_id + "','" + account_num + "','" + "debit" + "','" + amount + "','" + sqlTxnTime +  "');";
                        stmt.executeUpdate(sql4);
                        System.out.println("Transaction successful!");
                        break;
                    } else {
                        System.out.println("Insufficient balance!");
                        break;
                    }
                    
                } 
                else 
                {
                    System.out.println("Account number does not exist!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void transaction(int userId) {
        System.out.println("Enter from account number:");
        int from_account_num = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter to account number:");
        int to_account_num = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter pin number: ");
        String pin_num = sc.nextLine();

        System.out.println("Enter amount to be transferred:");
        double amount = sc.nextDouble();


        try {
            String sql1;
            sql1 = "select * from account where id = " + from_account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                String pin = rs5.getString("pin");
                double balance = rs5.getDouble("balance");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId && pin.equals(pin_num)) 
                {
                    if(amount <= balance) {
                        balance -= amount;
                        String sql2 = "update account set balance = " + balance + " where id = " + from_account_num + ";";
                        String sql3 = "update account set balance = balance + " + amount + " where id = " + to_account_num + ";";
                        stmt.executeUpdate(sql2);
                        stmt.executeUpdate(sql3);
                        


                        String sql9;
                        sql9 = "select max(id) as number from transaction;";
                        ResultSet rs9 = stmt.executeQuery(sql9);
                        while(rs9.next())
                        {
                            transaction_id = rs9.getInt("number");
                        }
                        rs9.close();
                        transaction_id +=1;

                        LocalDateTime txnTime = LocalDateTime.now();
                        java.sql.Timestamp sqlTxnTime = java.sql.Timestamp.valueOf(txnTime);

                        String sql4 = "insert into transaction values ('" + transaction_id + "','" + from_account_num + "','" + "debit" + "','" + amount + "','" + sqlTxnTime +  "');";

                        transaction_id +=1;
                        String sql5 = "insert into transaction values ('" + transaction_id + "','" + to_account_num + "','" + "credit" + "','" + amount + "','" + sqlTxnTime +  "');";
                        stmt.executeUpdate(sql4);
                        stmt.executeUpdate(sql5);
                        System.out.println("Transfer successful!");
                        System.out.println("New Balance: " + balance);
                        break;
                    } else {
                        System.out.println("Insufficient balance!");
                        break;
                    }
                    
                } 
                else 
                {
                    System.out.println("Account number does not exist!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void printAccountDetails(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();

        try {
            String sql1;
            sql1 = "select * from account where id = " + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                // String pin = rs5.getString("pin");
                double balance = rs5.getDouble("balance");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId) 
                {
                    System.out.println("Account Number: " + account_num);
                    System.out.println("Balance: " + balance);
                    break;
                } 
                else 
                {
                    System.out.println("Account number does not exist!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void updateAccountDetails(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter pin number: ");
        String pin_num = sc.nextLine();

        System.out.println("Enter new pin number: ");
        String new_pin_num = sc.nextLine();

        try {
            String sql1;
            sql1 = "select * from account where id = " + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                String pin = rs5.getString("pin");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId && pin.equals(pin_num)) 
                {
                    String sql2 = "update account set pin = '" + new_pin_num + "' where id = " + account_num + ";";
                    stmt.executeUpdate(sql2);
                    System.out.println("Update successful!");
                    break;
                } 
                else 
                {
                    System.out.println("Account number does not exist or pin is incorrect!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void updateCustomerDetails(int userId) {
        
        System.out.println("Enter new name:");
        String customerName = sc.nextLine();

        System.out.println("Enter new phone number:");
        String customerPhone = sc.nextLine();

        System.out.println("Enter new Email Id:");
        String customerEmail = sc.nextLine();

        System.out.println("Set new Password: ");
        String passwrd = sc.nextLine();
        
        try {
            String sql1;
            sql1 = "select * from customer where id = " + userId + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                // String name = rs5.getString("name");
                // String phone = rs5.getString("phone");
                // String email = rs5.getString("email");
                // String password = rs5.getString("password");

               
                String sql2 = "update customer set name = '" + customerName + "', phone = '" + customerPhone + "', email = '" + customerEmail + "', passwrd = '" + passwrd + "' where id = " + userId + ";";
                stmt.executeUpdate(sql2);
                System.out.println("Update successful!");
                break;
                
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }

    }


    // function to delete account
    public void deleteAccount(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();

        try {
            String sql1;
            sql1 = "select * from account where id = " + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                // String pin = rs5.getString("pin");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId) 
                {
                    String sql2 = "delete from account where id = " + account_num + ";";
                    stmt.executeUpdate(sql2);
                    System.out.println("Account deleted!");
                    break;
                } 
                else 
                {
                    System.out.println("Account number does not exist!");
                }
            }

            rs5.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void showAllAccounts(int userId) {
        try {
            String sql1;
            sql1 = "select * from account where customer_id_fk =" + userId + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                // String pin = rs5.getString("pin");
                double balance = rs5.getDouble("balance");
                int user_id = rs5.getInt("customer_id_fk");

                if (user_id == userId) 
                {
                    System.out.println("Account Number: " + rs5.getInt("id"));
                    System.out.println("Balance: " + balance);
                }
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void showAllTransactions(int userId) {
        System.out.println("Enter account number:");
        int account_num = sc.nextInt();

        try {
            String sql1;
            sql1 = "select * from transaction where account_num_fk =" + account_num + ";";
            ResultSet rs5 = stmt.executeQuery(sql1);

            while (rs5.next()) 
            {
                // String pin = rs5.getString("pin");
                double amount = rs5.getDouble("amount");
                int accountnum = rs5.getInt("account_num_fk");
                String type = rs5.getString("type");
                // Date txnDate = rs.getDate("txntimestamp");



                if (account_num == accountnum) 
                {
                    System.out.println("Transaction Number: " + rs5.getInt("id"));
                    System.out.println("Type of transaction:" + type);
                    System.out.println("Balance: " + amount);
                    // System.out.println("Date of transaction: " + txnDate);
                }
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    



    public void endprocess(){
        try
        {
            if(stmt!=null)
                stmt.close();
        } catch(SQLException se2)
        {
        }
        try
        {
            if(conn!=null)
                conn.close();
        } catch(SQLException se)
        {
            se.printStackTrace();
        }
    }
}
