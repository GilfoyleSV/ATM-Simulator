import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ATMService atm = new ATMService(bank);
        Scanner sc = new Scanner(System.in);
        Account currentAccount = null;

        System.out.println("========================================");
        System.out.println("   ДОБРО ПОЖАЛОВАТЬ В ATM SIMULATOR   ");
        System.out.println("========================================");

        while (true) {
            if (currentAccount == null) {
                System.out.println("\n--- АУТЕНТИФИКАЦИЯ ---");
                System.out.print("Введите ID аккаунта: ");
                String id = sc.nextLine().trim();
                System.out.print("Введите PIN-код: ");
                String pin = sc.nextLine().trim();

                if (bank.authenticate(id, pin)) {
                    currentAccount = bank.getAccount(id);
                    System.out.println("Успешный вход! Добро пожаловать, " + id);
                } else {
                    System.out.println("Ошибка: Неверный ID или PIN-код.");
                }
            } else {
                System.out.println("\n========================================");
                System.out.printf("  Аккаунт: %-10s | Баланс: %s%n", 
                    currentAccount.getId(), 
                    currentAccount.getBalance()
                );
                System.out.println("========================================");
                System.out.println("1. Проверить баланс");
                System.out.println("2. Пополнить счет");
                System.out.println("3. Снять наличные");
                System.out.println("4. Перевод средств");
                System.out.println("5. История транзакций");
                System.out.println("6. Выйти");
                System.out.println("========================================");
                System.out.print("Выберите операцию (1-6): ");
                
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1":
                        System.out.println("\nТекущий баланс: " + currentAccount.getBalance());
                        break;
                    case "2":
                        System.out.print("Введите сумму для пополнения: ");
                        String depAmountStr = sc.nextLine().trim();
                        try {
                            BigDecimal amount = new BigDecimal(depAmountStr);
                            atm.deposit(currentAccount, amount);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: Введено некорректное число.");
                        } catch (AccountNotFoundException | AmountException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;
                    case "3":
                        System.out.print("Введите сумму для снятия: ");
                        String withAmountStr = sc.nextLine().trim();
                        try {
                            BigDecimal amount = new BigDecimal(withAmountStr);
                            atm.withdraw(currentAccount, amount);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: Введено некорректное число.");
                        } catch (AccountNotFoundException | AmountException | InsufficientFundsException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;
                    case "4":
                        System.out.print("Введите ID получателя: ");
                        String recipientId = sc.nextLine().trim();
                        System.out.print("Введите сумму для перевода: ");
                        String transAmountStr = sc.nextLine().trim();
                        try {
                            BigDecimal amount = new BigDecimal(transAmountStr);
                            atm.transfer(currentAccount, recipientId, amount);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: Введено некорректное число.");
                        } catch (AccountNotFoundException | AmountException | InsufficientFundsException | IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;
                    case "5":
                        atm.printTransactionHistory(currentAccount);
                        break;
                    case "6":
                        System.out.println("Выход из системы. Всего доброго!");
                        currentAccount = null;
                        break;
                    default:
                        System.out.println("Ошибка: Неверный пункт меню. Пожалуйста, введите 1-6.");
                }
            }
        }
    }
}
