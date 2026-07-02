import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;

public class Bank {
    private Map<String, Account> accounts;

    public Bank(){
        accounts = new HashMap<String, Account>();
        Account firstAccount = new Account("1", "1234", new BigDecimal("120.0"));
        Account secondAccount = new Account("2", "1234", new BigDecimal("120.0"));
        Account thirdAccount = new Account("3", "1234", new BigDecimal("120.0"));
        accounts.put("1", firstAccount);
        accounts.put("2", secondAccount);
        accounts.put("3", thirdAccount);
    }

    public boolean authenticate(String id, String pin){
        Account requstedAccount = accounts.get(id);
        if (requstedAccount == null){
            return false;
        }

        return requstedAccount.getPin().equals(pin);
    }

    public Account getAccount(String id){
        return accounts.get(id);
    }
}
