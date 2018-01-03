package atm_application;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

import atm_application.data_model.Account;
import atm_application.data_model.Transaction;
import atm_application.data_model.User;
import atm_application.exception_handling.CheckInput;
import atm_application.exception_handling.ExceptionWindow;
import atm_application.exception_handling.InvalidInputException;
import atm_application.ui_elements.*;

/************************************************************
 * Class: UIController
 *
 * Usage: The UIController handles creating and rendering all
 * the ui_elements.
 *
 * Details: The purpose of using this controller is two-fold.
 * First, it gives a degree of separation between the data and
 * User. Second, it allows for more modular code by abstracting
 * the view to individual ui_elements. This means the individual
 * elements such the DigitPadPanel and InputPanel can be reused on
 * different screens without altering the code.
 ************************************************************/
public class UIController {
    // The UIStatus enum is used to store which screen the user is currently on.
    // Note: all enums are considered static, no need to define them as such.
    public enum UIStatus {USER_LOGON, USER_INFO_SCREEN, DEPOSIT_SCREEN, WITHDRAW_SCREEN, TRANSFER_SCREEN1, TRANSFER_SCREEN2}
    // The mainDisplayFrame is the main window that the user will see.
    // Note: it extends the frame class and allows the UI controller to
    // simply an ArrayList of display elements and pass it to the
    // mainDisplayFrame which then renders the elements intelligently.
    private MainDisplayFrame mainDisplayFrame = new MainDisplayFrame();
    // uiStatus stores the enum value representing the currently displaying screen.
    private static UIStatus uiStatus;
    /*********************************************************
     * NOTE ABOUT SUBOPTIMAL CODE:
     * The 'displayingUser, transferReceivingUser selectedAccount
     * and transferReceivingAccount really shouldn't be stored
     * on the UIController. I initially stored them here when
     * needed and expanded from there.
     *
     * NOTE FOR POSSIBLE FUTURE WORK:
     * It would be far more elegant and inline with the program
     * structure to refactor the code and move these values to
     * the DataController. It would be best to avoid storing
     * data in the UIController.
     *********************************************************/
    private User displayingUser;
    private User transferReceivingUser;
    private Account selectedAccount;
    private Account transferReceivingAccount;

    /********************************************************
     * NOTE ABOUT SUBOPTIMAL CODE:
     * Originally I attempted to keep the UIController from
     * storing any specific UI elements as that work should
     * be handled by the MainDisplayFrame. However, time
     * constraints required me to store it here as a short
     * cut so that it is easier to refresh the log panel
     * when a different account is selected.
     *
     * NOTE FOR POSSIBLE FUTURE WORK:
     * It would be smart to beef up the MainDisplayFrame
     * to have methods that allow for intelligent
     * manipulation of individual ui_elements. That the
     * UIController doesn't have to do any low level work
     * when the TransactionLogPanel needs to be refreshed.
     *********************************************************/
    private TransactionLogPanel transactionLogPanel;

    // As stated above this should really be in the DataController
    public User getDisplayingUser() {
        return displayingUser;
    }
    public Account getSelectedAccount() {
        return selectedAccount;
    }
    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
        mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
    }
    public void setTransferReceivingAccount(Account transferReceivingAccount) {
        this.transferReceivingAccount = transferReceivingAccount;
    }

    // The constructor for the UIController is very simple.
    public UIController() {
        // Sets the initial display to the user logon screen.
        mainDisplayFrame.changeDisplay(genUserLogonScreen());
    }

    /*********************************************************
     * The following 'gen...Screen' methods all generate and
     * return an ArrayList of ui_elements to display they are
     * ordered from the top of the screen to the bottom. The
     * ArrayList that is returned can simply be passed as is
     * to the MainDisplayFrame which then clears the display
     * window(frame) of all existing ui_elements and loads the
     * new elements in the ArrayList.
     *
     * @return an ArrayList of ui_elements making up the
     * respective screen.
     *********************************************************/
    private ArrayList<JComponent> genUserLogonScreen() {
        // Sets the currently displaying screen to the enum representing the Logon screen.
        uiStatus = UIStatus.USER_LOGON;
        // This is the ArrayList that will be returned at the end of this method.
        ArrayList<JComponent> userLogonDisplay = new ArrayList<>();

        // The headerLabel simply denotes which screen is currently being displayed
        // as it is a single simple label it has not been abstracted to a unique
        // ui_element yet.
        JLabel headerLabel = new JLabel("User Login");
        // Sets the font according to the labelLarge font stored in the CustomFonts class.
        headerLabel.setFont(CustomFonts.labelLarge);

        // Creates an InputPanel with label explaining desired input and designates the display size.
        InputPanel inputPanel = new InputPanel("Please enter User Number:", new Dimension(300,50));
        // Creates a DigitPadPanel giving it the inputPanel that will be attached as a listener.
        DigitPadPanel digitPadPanel = new DigitPadPanel(inputPanel);
        // Since the digitPadPanel requires an input to link to, the input panel must be created first.

        // Adds the newly created ui_elements to the return ArrayList
        userLogonDisplay.add(headerLabel);
        userLogonDisplay.add(inputPanel);
        userLogonDisplay.add(digitPadPanel);

        // Returns the newly filled ArrayList
        return  userLogonDisplay;
    }

    private ArrayList<JComponent> genUserInfoScreen(User user){
        /*****************************************************
         * NOTE ABOUT SUBOPTIMAL CODE:
         * This screen requires a user be passed to its
         * constructor. This is due to an originally different
         * design plan, in which the displayingUser was never
         * stored anywhere. Due to a required alteration of the
         * code design, this parameter could be obtained
         * implicitly.
         *
         * NOTE FOR POSSIBLE FUTURE WORK:
         * Since this screen needs to know which user to display
         * info for, and the displayingUser should be stored on
         * the DataController, it would be best to correct this
         * after relocating the stored data to the DataController
         *******************************************************/
        // Sets the displaying user as a method local variable.
        // This is redundant and should be cleaned up in future.
        displayingUser = user;
        // Sets the currently displaying screen as the User Info Screen.
        uiStatus = UIStatus.USER_INFO_SCREEN;
        // Create the ArrayList that will be returned at the end of this method.
        ArrayList<JComponent> userInfoScreen = new ArrayList<>();

        // Creates the header label designating the screen being displayed
        JLabel headerLabel = new JLabel("Customer Details");
        // Sets the labels font using a font from CustomFonts.
        headerLabel.setFont(CustomFonts.labelLarge);
        // The new label to the returning ArrayList.
        userInfoScreen.add(headerLabel);

        // Creates a UserInfoPanel. The UserInfoPanel takes a user and creates
        // a panel with information about the given user, such as Name and UserNumber.
        UserInfoPanel userInfoPanel = new UserInfoPanel(user);

        /*******************************************************
         * NOTE ABOUT SUBOPTIMAL CODE:
         * Honestly, the AccountSelectionPanel's design is kind
         * of a mess. As stated above, initially I intended to
         * never store the currently selected user or account
         * After multiple attempts I was unable to find any
         * elegant way to handle the AccountSelectionPanel
         * without storing the selected user and account. Due to
         * this the current code design contains artifacts of the
         * initial attempts.
         *
         * NOTE FOR POSSIBLE FUTURE WORK:
         * This entire ui_element and its implementation could be
         * greatly simplified after moving the stored user data to
         * the DataController. I believe it could also be abstracted
         * more effectively and remove entirely the need for the
         * AccountSelectionTransferPanel, the two AccountSelection
         * panels have very similar code and could be abstracted to
         * a single more elegantly implemented ui_element.
         ********************************************************/
        AccountSelectionPanel accountSelectionPanel = new AccountSelectionPanel();

        // Loops through all possible account types and adds any existing accounts
        // to the selection panel. It also stores the first found account as the
        // initially selected account.
        // NOTE: This process could be more elegantly moved to the ui_element itself.
        for (Account.AccountType accountType: Account.AccountType.values()) {
            Account foundAccount = (Account) user.getAccounts().get(accountType);
            if (foundAccount != null) {
                accountSelectionPanel.addAccountToSelection(foundAccount);
                if(selectedAccount == null){
                    selectedAccount = foundAccount;
                }
            }
        }

        // Sets the selected account on the panel to ensure the correct radio button is selected.
        accountSelectionPanel.setSelectedAccount(selectedAccount);
        // Creates a new transaction log panel for the selected account.
        transactionLogPanel = new TransactionLogPanel(selectedAccount);

        // The user option buttons are self contained and require no external action listener.
        UserOptionButtonsPanel userOptionButtonsPanel = new UserOptionButtonsPanel();

        // Adds the newly created elements to return ArrayList.
        userInfoScreen.add(userInfoPanel);
        userInfoScreen.add(accountSelectionPanel);
        userInfoScreen.add(transactionLogPanel);
        userInfoScreen.add(userOptionButtonsPanel);

        // Returns the ArrayList for the new display screen
        return userInfoScreen;
    }

    // The Deposit screen is very similar to the user logon screen.
    // The main difference is how the input is handled.
    private ArrayList<JComponent> genDepositScreen() {
        // Sets the current screen to the Deposit Screen.
        uiStatus = UIStatus.DEPOSIT_SCREEN;

        // Creates the ArrayList that will be returned
        ArrayList<JComponent> depositDisplay = new ArrayList<>();
        // Creates and stylizes the headerLabel.
        JLabel headerLabel = new JLabel("Deposit Funds");
        headerLabel.setFont(CustomFonts.labelLarge);

        // Creates an InputPanel with appropriate label.
        InputPanel inputPanel = new InputPanel("Enter deposit amount:", new Dimension(300,50));
        // Creates a DigitPadPanel and links it to the newly made inputPanel.
        DigitPadPanel digitPadPanel = new DigitPadPanel(inputPanel);
        // Creates a BackButtonPanel. The BackButtonPanel creates a button allowing the user to return to the previous screen.
        BackButtonPanel backButtonPanel = new BackButtonPanel(UIStatus.USER_INFO_SCREEN);

        // Adds the ui_elements to the returning ArrayList
        depositDisplay.add(headerLabel);
        depositDisplay.add(inputPanel);
        depositDisplay.add(digitPadPanel);
        depositDisplay.add(backButtonPanel);

        // Returns the newly created screen.
        return  depositDisplay;
    }

    // The Withdraw screen is almost identical to the deposit screen
    private ArrayList<JComponent> genWithdrawScreen() {
        // Sets the currently displaying screen to the Withdraw Screen
        uiStatus = UIStatus.WITHDRAW_SCREEN;

        // Creates the returning ArrayList.
        ArrayList<JComponent> withdrawDisplay = new ArrayList<>();
        // Creates and stylizes the headerLabel.
        JLabel headerLabel = new JLabel("Withdraw Funds");
        headerLabel.setFont(CustomFonts.labelLarge);

        // Creates an input panel with the appropriate label.
        InputPanel inputPanel = new InputPanel("Enter withdraw amount (increments of 20):", new Dimension(300,50));
        // Creates a DigitPadPanel and links it to the newly created inputPanel.
        DigitPadPanel digitPadPanel = new DigitPadPanel(inputPanel);
        // Creates a back button, allowing the user to return to the previous screen.
        BackButtonPanel backButtonPanel = new BackButtonPanel(UIStatus.USER_INFO_SCREEN);

        // Adds the new ui_elements to the return ArrayList.
        withdrawDisplay.add(headerLabel);
        withdrawDisplay.add(inputPanel);
        withdrawDisplay.add(digitPadPanel);
        withdrawDisplay.add(backButtonPanel);

        // Returns the newly created screen.
        return  withdrawDisplay;
    }

    /*******************************************************
     * NOTE ABOUT TRANSFER PROCESS.
     * The TransferScreen is unique in a couple of ways.
     * Firstly, it has two stages. The first stage requests
     * that the user enter the receiving user's number. The
     * second stage asks the user to select which of the
     * receiving user's accounts to transfer funds to, and
     * the amount for transfer.
     *
     * Secondly it has a unique example of the AccountSelection
     * panel. As stated above it would be best to combine their
     * functionality, but for now they are separate.
     ********************************************************/
    private ArrayList<JComponent> genTransferScreenOne() {
        // Sets the currently displaying screen as the first Transfer Screen.
        uiStatus = UIStatus.TRANSFER_SCREEN1;

        // Creates the returning ArrayList
        ArrayList<JComponent> transferDisplay1 = new ArrayList<>();

        // Creates and stylizes the headerLabel.
        JLabel headerLabel = new JLabel("Transfer Funds to other User");
        headerLabel.setFont(CustomFonts.labelLarge);

        // Creates a new InputPanel with the appropriate label.
        InputPanel inputPanel = new InputPanel("Enter receiving user's number:", new Dimension(300,50));
        // Creates a DigitPadPanel and links it to the inputPanel.
        DigitPadPanel digitPadPanel = new DigitPadPanel(inputPanel);
        // Creates a back button allowing the user to return to the previous screen.
        BackButtonPanel backButtonPanel = new BackButtonPanel(UIStatus.USER_INFO_SCREEN);

        // Adds the newly created ui_elements to the returning ArrayList
        transferDisplay1.add(headerLabel);
        transferDisplay1.add(inputPanel);
        transferDisplay1.add(digitPadPanel);
        transferDisplay1.add(backButtonPanel);

        // Returns the newly created screen
        return  transferDisplay1;
    }

    // Creates and returns the ArrayList of ui_elements that make up the second transfer screen.
    private ArrayList<JComponent> genTransferScreenTwo(User receivingUser) {
        // Sets the currently displaying screen to the second transfer screen.
        uiStatus = UIStatus.TRANSFER_SCREEN2;


        // Creates the returning ArrayList.
        ArrayList<JComponent> transferDisplay2 = new ArrayList<>();
        // Creates and stylizes the headerLabel.
        JLabel headerLabel = new JLabel("Transfer Funds to User " + receivingUser.getUserNumber());
        headerLabel.setFont(CustomFonts.labelLarge);

        // Creates an AccountSelection panel specific to the transfer screen.
        AccountSelectionTransferPanel accountSelectionTransferPanel = new AccountSelectionTransferPanel(receivingUser);

        // Creates an InputPanel.
        InputPanel inputPanel = new InputPanel("Enter amount to transfer:", new Dimension(300,50));
        // Creates a new DigitPadPanel
        DigitPadPanel digitPadPanel = new DigitPadPanel(inputPanel);
        BackButtonPanel backButtonPanel = new BackButtonPanel(UIStatus.TRANSFER_SCREEN1);

        // Adds the newly created ui_elements to the returning ArrayList
        transferDisplay2.add(headerLabel);
        transferDisplay2.add(accountSelectionTransferPanel);
        transferDisplay2.add(inputPanel);
        transferDisplay2.add(digitPadPanel);
        transferDisplay2.add(backButtonPanel);

        // Returns the newly created screen.
        return  transferDisplay2;
    }

    /*******************************************************
     * NOTE ABOUT inputPanelSubmit():
     * Since all the user input is gathered through the
     * inputPanel the inputPanelSubmit is run every time
     * regardless of input. To deal with the fact that
     * input handling is dependent on the purpose of input
     * I run everything through an enum switch to determine
     * which screen the user is on.
    ********************************************************/
    public void inputPanelSubmit(String input) throws InvalidInputException {
        // Switches response depending on current screen.
        switch(uiStatus) {
            // The cases are all wrapped in if statements that run the exception checks.
            case USER_LOGON:
                // If the user input passes all the checks for a user number input
                if (CheckInput.userNumberInputCheck(input)) {
                    int numberInput = Integer.parseInt(input);
                    User userFound = Application.dataController.findUserByNumber(numberInput);
                    // Set the display as a generated user info screen with the found user.
                    mainDisplayFrame.changeDisplay(genUserInfoScreen(userFound));
                }
                break;
            case DEPOSIT_SCREEN:
                if (CheckInput.depositInputCheck(input)){
                    double inputDouble = Double.parseDouble(input);
                    selectedAccount.addTransaction(new Transaction(inputDouble ,Transaction.TransType.DEPOSIT, "Deposit by User", new Date()));
                    mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
                }
                break;
            case WITHDRAW_SCREEN:
                if (CheckInput.withdrawInputCheck(input, selectedAccount)){
                    int intInput = Integer.parseInt(input);
                    double possibleOverageCost = intInput + Account.overageFee;
                    // Check number of withdraws and apply overage fees if valid.
                    if (selectedAccount.getNumWithdraws() > Account.maxNumWithdraws){
                        // Since the ExceptionWindow's constructor isn't overloaded to accept a single String
                        // An ArrayList must be created to create overage fee alert.
                        ArrayList<String> alertOverage = new ArrayList<>();
                        // Add the alert message to the newly created list
                        alertOverage.add("You have withdrawn too many times, we have attached an overage fee of $1.50");
                        // Create an alert window explaining overage
                        new ExceptionWindow("Overage Fee",alertOverage);
                        // Runs a new the sufficientFundsCheck() again with the new amount.
                        // Since this check is running outside of a larger process check, we will throw an error on find.
                        // This means that I don't have to worry about throwing errors here.
                        CheckInput.sufficientFundsCheck(possibleOverageCost, selectedAccount, true);
                        selectedAccount.addTransaction(new Transaction((intInput * -1), Transaction.TransType.WITHDRAW, "Withdraw by User", new Date()));
                        mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
                    }
                    else {
                        selectedAccount.addTransaction(new Transaction((intInput * -1), Transaction.TransType.WITHDRAW, "Withdraw by User", new Date()));
                        selectedAccount.incNumWithdraws();
                        mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
                    }
                }
                break;
            case TRANSFER_SCREEN1:
                if (CheckInput.transferUserInputCheck(input)) {
                    int numberInput = Integer.parseInt(input);
                    User userFound = Application.dataController.findUserByNumber(numberInput);
                    transferReceivingUser = userFound;
                    mainDisplayFrame.changeDisplay(genTransferScreenTwo(userFound));
                }
                break;
            case TRANSFER_SCREEN2:
                if (CheckInput.transferAmountInputCheck(input, selectedAccount)) {
                    double inputDouble = Double.parseDouble(input);
                    selectedAccount.addTransaction(new Transaction((inputDouble * -1), Transaction.TransType.TRANSFER_SENDING, "Transfer to user " + transferReceivingUser.getUserNumber(), new Date()));
                    transferReceivingAccount.addTransaction(new Transaction(inputDouble, Transaction.TransType.TRANSFER_RECEIVING, "Transfer from user " + displayingUser.getUserNumber(), new Date()));
                    mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
                }
        }
    }

    // When the user logs off the stored user info needs to be cleared.
    public void userLogOff(){
        displayingUser = null;
        selectedAccount = null;
        transactionLogPanel = null;
        mainDisplayFrame.changeDisplay(genUserLogonScreen());
    }
    // These methods switch the screen being displayed.
    public void switchToDepositScreen() {
        mainDisplayFrame.changeDisplay(genDepositScreen());
    }
    public void switchToWithdrawScreen() { mainDisplayFrame.changeDisplay(genWithdrawScreen());}
    public void switchToTransferScreenOne() {mainDisplayFrame.changeDisplay(genTransferScreenOne());}

    public void returnToPreviousScreen(UIStatus previousStatus){
        switch (previousStatus) {
            case USER_INFO_SCREEN:
                mainDisplayFrame.changeDisplay(genUserInfoScreen(displayingUser));
                break;
            case TRANSFER_SCREEN1:
                mainDisplayFrame.changeDisplay(genTransferScreenOne());
        }
    }
}
