import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;
    private String pin;
    private BigDecimal balance;
    private List<Transaction> historyTransactions;

    public Account(String id, String pin, BigDecimal balance){
        this.id = id;
        this.pin = pin;
        this.balance = balance;
        this.historyTransactions = new ArrayList<Transaction>();
    }

    public String getId(){
        return id;
    }

    public String getPin(){
        return pin;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public void addBalance(BigDecimal amount){
        balance = balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount){
        balance = balance.subtract(amount);
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public List<Transaction> getHistoryTransactions(){
        return historyTransactions;
    }

    public void addTransaction(Transaction newTransaction){
        historyTransactions.add(newTransaction);
    }
}
