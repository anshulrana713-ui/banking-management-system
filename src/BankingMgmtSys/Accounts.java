package BankingMgmtSys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public long open_account(String email) {
        if(!account_exists(email)) {
            String query = "INSERT INTO accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?);";
            scanner.nextLine();
            System.out.print("Enter Full Name : ");
            String full_name = scanner.nextLine();
            System.out.print("Enter Initial Amount : ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter security Pin : ");
            String security_pin = scanner.nextLine();
            try {
                long account_number = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);

                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows>0) {
                    return account_number;
                }
                else {
                    throw new RuntimeException("Account Creation Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account Already Exists");
    }


    public long getAccountNumber(String email) {
        String query = "SELECT account_number FROM accounts WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return rs.getLong("account_number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number does not exist");
    }


    private long generateAccountNumber() {
        String query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if(rs.next()) {
                long last_account_number = rs.getLong("account_number");
                return last_account_number+1;
            }
            else {
                return 10001100;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 10001100;
    }


    public boolean account_exists(String email) {
        String query = "SELECT account_number FROM accounts WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
