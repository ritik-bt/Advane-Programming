package org.example.bank;

public class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;
    private final double interestRate = 0.05; // 5% interest

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit must be greater than zero.");
        balance += amount;
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal must be greater than zero.");
        if (amount > balance) throw new InsufficientFundsException("Insufficient balance.");
        balance -= amount;
    }

    public void transfer(BankAccount recipient, double amount) throws InvalidAmountException, InsufficientFundsException {
        this.withdraw(amount);
        recipient.deposit(amount);
    }

    public double calculateInterest() {
        return balance * interestRate;
    }

    public void applyInterest() {
        balance += calculateInterest();
    }

    public double getBalance() { return balance; }

    public String getAccountHolder() { return accountHolder; }

    public String getAccountNumber() { return accountNumber; }

    @Override
    public String toString() {
        return accountHolder + " (" + accountNumber + ")";
    }
}
