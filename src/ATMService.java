import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ATMService{
    private final Bank bank;

    public ATMService(Bank bank){
        this.bank = bank;
    }

    private void validateAccount(Account account) throws AccountNotFoundException {
        if (account == null) {
            throw new AccountNotFoundException("Аккаунт не существует.");
        }
    }

    private void validateAmount(BigDecimal amount) throws AmountException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountException("Сумма должна быть строго больше нуля.");
        }
    }

    private void validateBalance(Account account, BigDecimal amount) throws InsufficientFundsException {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете.");
        }
    }

    public void deposit(Account account, BigDecimal amount) 
            throws AccountNotFoundException, AmountException {
        validateAccount(account);
        validateAmount(amount);

        account.addBalance(amount);
        Transaction newTransaction = new Transaction(TransactionType.DEPOSIT, amount, LocalDateTime.now(), "Пополнение счёта");
        account.addTransaction(newTransaction);
    }

    public void withdraw(Account account, BigDecimal amount) 
            throws AccountNotFoundException, AmountException, InsufficientFundsException {
        validateAccount(account);
        validateAmount(amount);
        validateBalance(account, amount);

        account.subtractBalance(amount);
        Transaction newTransaction = new Transaction(TransactionType.WITHDRAWAL, amount, LocalDateTime.now(), "Снятие со счёта");
        account.addTransaction(newTransaction);
    }

    public void transfer(Account sender, String recipientId, BigDecimal amount) 
            throws AccountNotFoundException, AmountException, InsufficientFundsException {
        validateAccount(sender);
        validateAmount(amount);
        validateBalance(sender, amount);

        Account recipient = bank.getAccount(recipientId);
        if (recipient == null) {
            throw new AccountNotFoundException("Аккаунт получателя не найден.");
        }

        if (sender.getId().equals(recipientId)) {
            throw new IllegalArgumentException("Нельзя перевести деньги на собственный счет.");
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