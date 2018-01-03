package atm_application.data_model;

import java.util.*;

public class User {
    private int userNumber;
    private String userNameFirst;
    private String userNameLast;

    /**************************************************
     * I thought it would be a good idea to map the
     * accounts by type using an enum. After working
     * extensively with it, I think it would have been
     * better to create an ArrayList and distill the
     * enum down to its integer representation and use
     * that as the index for the list.
     *
     * The Map data structure is overcomplicated given
     * its current use.
     **************************************************/
    private Map<Account.AccountType, Account> accounts = new EnumMap<Account.AccountType, Account>(Account.AccountType.class);

    // The constructor simply initializes the values.
    public User(int userNumber, String userNameFirst, String userNameLast) {
        this.userNumber = userNumber;
        this.userNameFirst = userNameFirst;
        this.userNameLast = userNameLast;
    }

    // Getter methods:
    public int getUserNumber() {
        return userNumber;
    }
    public String getUserNameFirst() {
        return userNameFirst;
    }
    public String getUserNameLast() {
        return userNameLast;
    }
    public Map getAccounts() {
        return accounts;
    }
    public Account getAccountByType(Account.AccountType accountType){
        return accounts.get(accountType);
    }

    // The add account function has an overwrite ability that replaces any existing accounts of that type.
    public void addAccount(Account newAccount, boolean overwrite) {
        boolean alreadyExists = (accounts.get(newAccount.getAccountType()) != null);
        if (!alreadyExists) {
            accounts.put(newAccount.getAccountType(), newAccount);
        }
        else if(alreadyExists && overwrite){
            // Removes the existing account.
            accounts.remove(newAccount.getAccountType());
            accounts.put(newAccount.getAccountType(), newAccount);
        }
        else{
            //ERROR
            System.out.println("An account of that type already exists and overwrite is false.");
        }
    }

    // The remove account method is never used but should function if needed.
    public void removeAccount(Account.AccountType accountType) {
        accounts.remove(accountType);
    }


}
