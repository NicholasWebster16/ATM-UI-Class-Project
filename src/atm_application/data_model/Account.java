package atm_application.data_model;

import java.util.ArrayList;

public class Account {
    // Account types are handled as an enum set
    public static enum AccountType {CHECKING, SAVINGS};
    // These are final static values since they are only here to allow easier editing in future.
    public static final int maxNumWithdraws = 2;
    public static final double overageFee = 1.50;

    // This method takes a string and returns an AccountType
    public static AccountType parseAccountType(String accountTypeString){
        AccountType returnAccountType = null;
        String normalizedAccountTypeString = accountTypeString.toUpperCase();
        for (AccountType accountType : AccountType.values()) {
            if (accountType.toString() == normalizedAccountTypeString) {
                returnAccountType = accountType;
            }
        }
        return returnAccountType;
    }

    // The numWithdraws increments every time a withdraw is run.
    private int numWithdraws;
    private double accountBalance;
    private AccountType accountType;
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    // Getter methods.
    public int getNumWithdraws() {
        return numWithdraws;
    }
    public void incNumWithdraws() {
        this.numWithdraws++;
    }
    public ArrayList<Transaction> getTransactions() {
        return transactionList;
    }
    public double getAccountBalance() {
        return accountBalance;
    }
    public AccountType getAccountType() {
        return accountType;
    }

    // The constructor for the Account sets the initial values.
    public Account(AccountType accountType, double accountBalance) {
        this.numWithdraws = 0;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
    }

    // Adds the transaction to the transaction list and alters the account balance accordingly.
    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
        this.accountBalance += transaction.getTransAmount();
    }

    // This method returns the current account's Type as a user friendly string.
    // This value can be re-parsed into an AccountType if needed.
    // The purpose of this method is to generate a UI displayable string with proper capitalization.
    // Turning the enum into a string will yield all caps and not look good in UI.
    public String getAccountTypeString() {
        switch (accountType) {
            case SAVINGS:
                return "Savings";
            case CHECKING:
                return "Checking";
            default:
                return "INVALID";
        }
    }
}
