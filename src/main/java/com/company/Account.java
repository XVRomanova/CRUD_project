package com.company;

public class Account {
    private String name;
    private int balance;

    public Account () {
        this.name = null;
        this.balance = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }
}
