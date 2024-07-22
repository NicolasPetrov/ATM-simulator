import java.util.ArrayList;

public class ATM {
    private static final int USER_PIN = 1234;
    private static final double STARTING_BALANCE = 1000.00;

    private double balance;
    private ArrayList<String> transactions;

    public ATM() {
        this.balance = STARTING_BALANCE;
        this.transactions = new ArrayList<>();
        logTransaction("Account created with balance: $" + balance);
    }

    public boolean checkPin(int pin) {
        return pin == USER_PIN;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void logTransaction(String transaction) {
        transactions.add(transaction);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        logTransaction("Withdrawal of $" + amount + ". New balance: $" + balance);
        return true;
    }

    public void deposit(double amount) {
        balance += amount;
        logTransaction("Deposit of $" + amount + ". New balance: $" + balance);
    }
}