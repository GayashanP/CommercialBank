import java.util.ArrayList;

public class Customer {
    private final String username;
    private String password;
    private ArrayList<Double> transactions = new ArrayList<Double>();
    private double balance =0;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addAmount(double amount){
        transactions.add(amount);
    }

    public double getBalance() {
        balance =0;
        for(Double transaction : transactions){
            balance += transaction;
        }
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Double> getTransactions() {
        return transactions;
    }

}
