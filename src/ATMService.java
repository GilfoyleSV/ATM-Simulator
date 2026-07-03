import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ATMService{
    private final Bank bank;

    public ATMService(Bank bank){
        this.bank = bank;
    }

    public void deposit(Account account, BigDecimal amount){
        if (account == null){
            System.out.println("Account does not exist");
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("Amount is negative");
            return;
        }

        account.addBalance(amount);
        Transaction newTransaction = new Transaction(TransactionType.DEPOSIT, amount, LocalDateTime.now(), "Пополнение счёта");
        account.addTransaction(newTransaction);
    }

    public void withdraw(Account account, BigDecimal amount){
        if (account == null){
            System.out.println("Account does not exist");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("Amount is negative");
            return;
        }

        if (account.getBalance().compareTo(amount) < 0){
            System.out.println("Do not have enough money");
            return;
        }

        account.subtractBalance(amount);
        Transaction newTransaction = new Transaction(TransactionType.WITHDRAWAL, amount, LocalDateTime.now(), "Снятие со счёта");
        account.addTransaction(newTransaction);
    }

    public void transfer(Account sender, String recipientId, BigDecimal amount) {
        if (sender == null) {
            System.out.println("Sender account does not exist");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be positive");
            return;
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            System.out.println("Do not have enough money");
            return;
        }

        Account recipient = bank.getAccount(recipientId);
        if (recipient == null) {
            System.out.println("Recipient account not found");
            return;
        }

        if (sender.getId().equals(recipientId)) {
            System.out.println("Cannot transfer to the same account");
            return;
        }

        sender.subtractBalance(amount);
        recipient.addBalance(amount);

        LocalDateTime now = LocalDateTime.now();
        Transaction debit = new Transaction(TransactionType.TRANSFER_OUT, amount, now, "Перевод на счет " + recipientId);
        Transaction credit = new Transaction(TransactionType.TRANSFER_IN, amount, now, "Перевод со счета " + sender.getId());

        sender.addTransaction(debit);
        recipient.addTransaction(credit);
    }

    public void printTransactionHistory(Account account) {
        if (account == null) {
            System.out.println("Account does not exist");
            return;
        }

        List<Transaction> history = account.getHistoryTransactions();
        if (history.isEmpty()) {
            System.out.println("История транзакций пуста.");
            return;
        }

        System.out.println("=== История транзакций ===");
        for (Transaction t : history) {
            System.out.printf("%s | %-12s | %s | %s%n",
                t.getTimestamp(),
                t.getType(),
                t.getAmount(),
                t.getDetails()
            );
        }
    }
}