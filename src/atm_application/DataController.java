package atm_application;

import atm_application.data_model.Account;
import atm_application.data_model.Transaction;
import atm_application.data_model.User;
import java.util.ArrayList;
import java.util.Date;

/*****************************************
 * Class: DataController
 *
 * Usage: The DataController loads and handles
 * all the data required for the application.
 *
 * Details: For testing purposes it generates
 * 4 users. Their user numbers are 0-3. The
 * DataController adds those users to itself
 *
 * Structure:
 * User - 1st lvl contains list of Accounts
 *
 * Account - 2nd lvl contains list of Transactions
 *
 * Transaction - 3rd lvl contains details about
 * Deposits, Withdraws and Transfers (Receiving
 * and Sending)
 *
 *****************************************/
public class DataController {
    // The users ArrayList holds all the users that currently exist
    private ArrayList<User> users = new ArrayList<>();

    // findUserByNumber() takes a user number and returns the corresponding account.
    public User findUserByNumber(int userNumber){
        User foundUser = null;
        for (User user: users) {
            if (userNumber == user.getUserNumber()){
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }

    // addUser() takes a user and adds it to the users ArrayList
    public void addUser(User userToAdd, boolean overwrite) {
        //TODO: Add overwrite capabilities.
        users.add(userToAdd);
        // check if a user with that number already exists
        // if overwrite is true, replace existing user.
    }

    // removeUserByNumber() takes a user number and removes any users with that number from the users ArrayList
    public void removeUserByNumber(int userNumber){
        for (User user: users) {
            if (userNumber == user.getUserNumber()){
                 users.remove(user);
                break;
            }
        }
    }


    //TODO: Add load and save data functionality to the program
    //public void loadUsersFromFile(){}
    //public void saveUsersToFile(){}

    /*****************************************
     * Test data generated:
     * User 0 - Savings and Checking accounts
     * User 1 - Checking account only
     * User 2 - Savings account only
     * User 3 - Savings and Checking accounts
     *
     * Multiple example transactions for each
     * account.
     *****************************************/
    public void genTestData(){
        // Set of simply examples for transactions to load test accounts with.
        Transaction exampleWithdraw = new Transaction(-20.00, Transaction.TransType.WITHDRAW, "Withdraw by user", new Date());
        Transaction exampleDeposit = new Transaction(100.00, Transaction.TransType.DEPOSIT, "Deposit by user", new Date());
        Transaction exampleReceivingTransfer = new Transaction(50.00, Transaction.TransType.TRANSFER_RECEIVING, "Transfer from other User", new Date());
        Transaction exampleSendingTransfer = new Transaction(-50.00, Transaction.TransType.TRANSFER_SENDING, "Transfer to other User", new Date());


        User user1 = new User(0, "John", "Smith");
        Account user1Checking = new Account( Account.AccountType.CHECKING,  0.00);
        user1Checking.addTransaction(exampleDeposit);
        user1Checking.addTransaction(exampleDeposit);
        user1Checking.addTransaction(exampleDeposit);
        user1Checking.addTransaction(exampleDeposit);
        user1Checking.addTransaction(exampleWithdraw);
        user1Checking.addTransaction(exampleReceivingTransfer);
        user1Checking.addTransaction(exampleDeposit);
        user1Checking.addTransaction(exampleDeposit);

        Account user1Savings = new Account( Account.AccountType.SAVINGS,  0.00);
        user1Savings.addTransaction(exampleDeposit);
        user1Savings.addTransaction(exampleDeposit);
        user1Savings.addTransaction(exampleDeposit);
        user1Savings.addTransaction(exampleDeposit);
        user1Savings.addTransaction(exampleSendingTransfer);

        user1.addAccount(user1Checking, true);
        user1.addAccount(user1Savings, true);

        this.addUser(user1, true);


        User user2 = new User(1, "Robert", "Frost");
        Account user2Checking = new Account( Account.AccountType.CHECKING,  500.00);
        user2Checking.addTransaction(exampleDeposit);
        user2Checking.addTransaction(exampleWithdraw);
        user2Checking.addTransaction(exampleReceivingTransfer);
        user2Checking.addTransaction(exampleSendingTransfer);
        user2Checking.addTransaction(exampleWithdraw);
        user2Checking.addTransaction(exampleDeposit);
        user2Checking.addTransaction(exampleDeposit);

        user2.addAccount(user2Checking, true);
        this.addUser(user2, true);

        User user3 = new User(2,"Captain", "Crunch");
        Account user3Savings = new Account(Account.AccountType.SAVINGS, 1000.00);
        user3Savings.addTransaction(exampleWithdraw);
        user3Savings.addTransaction(exampleSendingTransfer);
        user3Savings.addTransaction(exampleSendingTransfer);
        user3Savings.addTransaction(exampleDeposit);
        user3Savings.addTransaction(exampleWithdraw);
        user3Savings.addTransaction(exampleReceivingTransfer);

        user3.addAccount(user3Savings, true);
        this.addUser(user3, true);

        User user4 = new User(3, "Nick", "Webster");
        Account user4Checking = new Account(Account.AccountType.CHECKING, 1000000.00);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleReceivingTransfer);
        user4Checking.addTransaction(exampleDeposit);
        user4Checking.addTransaction(exampleDeposit);
        user4Checking.addTransaction(exampleDeposit);

        user4.addAccount(user4Checking, true);

        Account user4Savings = new Account(Account.AccountType.SAVINGS, 50000.00);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);
        user4Savings.addTransaction(exampleDeposit);

        user4.addAccount(user4Savings, true);
        this.addUser(user4,true);
    }
}
