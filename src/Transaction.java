import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String details;

    public Transaction(TransactionType type, BigDecimal amount, LocalDateTime timestamp, String details){
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.details = details;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}
