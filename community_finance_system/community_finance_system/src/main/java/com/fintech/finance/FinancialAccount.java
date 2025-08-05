package com.fintech.finance;

public class FinancialAccount {
    private final String accountId;
    private final String customerName;
    private double currentBalance;
    private final double annualInterestRate = 0.04; // 4% annual interest

    public FinancialAccount(String accountId, String customerName, double initialBalance) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.currentBalance = initialBalance;
    }

    public void addFunds(double amount) throws InvalidTransactionException {
        if (amount <= 0) throw new InvalidTransactionException("Deposit amount must be positive.");
        currentBalance += amount;
    }

    public void removeFunds(double amount) throws InvalidTransactionException, InsufficientBalanceException {
        if (amount <= 0) throw new InvalidTransactionException("Withdrawal amount must be positive.");
        if (amount > currentBalance) throw new InsufficientBalanceException("Insufficient funds available.");
        currentBalance -= amount;
    }

    public void transferFunds(FinancialAccount targetAccount, double amount) throws InvalidTransactionException, InsufficientBalanceException {
        this.removeFunds(amount);
        targetAccount.addFunds(amount);
    }

    public double computeInterest() {
        return currentBalance * annualInterestRate;
    }

    public void creditInterest() {
        currentBalance += computeInterest();
    }

    public double getCurrentBalance() { return currentBalance; }

    public String getCustomerName() { return customerName; }

    public String getAccountId() { return accountId; }

    @Override
    public String toString() {
        return customerName + " (ID: " + accountId + ")";
    }
}

