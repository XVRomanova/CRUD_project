package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
     private Connection conn;

     public DataBase() {

        String URL = "jdbc:postgresql://localhost:5432/test_database";
        String USER = "myuser";
        String PASSWORD = "Ludik675205";

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException exception) {
            System.out.println("Failed to create the database connection.");
            System.exit(1);
        }
    }

    public ArrayList<Account> getAllDataFromAccounts() {
        ArrayList<Account> allAccount = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM accounts;");

            while (resultSet.next()) {

                Account currentAccount = new Account();
                currentAccount.setName(resultSet.getString(1));
                currentAccount.setBalance(resultSet.getInt(2));
                allAccount.add(currentAccount);
            }
            st.close();

        } catch (SQLException exception) {
            System.out.println("Failed to execute SELECT.");
        }

        return allAccount;
    }

    public void addNewAccount(Account account) {
        String name = account.getName();
        int balance = account.getBalance();
        String values = String.format("VALUES ('%s',%s);", name, balance);

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO accounts (name, balance)" + values);
            st.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteAccount(String accountName) {
        String query = String.format("DELETE FROM accounts WHERE name = '%s'", accountName);

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateAccountBalance(Account account) {
        String name = account.getName();
        int balance = account.getBalance();
        String query = String.format("UPDATE accounts SET balance = %s WHERE name = '%s'", balance, name);

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


}
