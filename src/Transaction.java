import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final String type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String details;

    public Transaction(String type, BigDecimal amount, LocalDateTime timestamp, String details){
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.details = details;
    }

    public String getType() {
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
