package atm_application;

/**
 * The ATM_Machine program emulates basic ATM functions including:
 * Logging on by user number; Viewing transaction history; handling multiple accounts; withdrawing, depositing, and transferring funds.
 *
 *
 * @author  Nicholas Webster
 * @version 1.0
 * @since   2017-09-25
 */

// This is the main Application class that instantiates and stores references to the Controllers
public class Application {
    public static DataController dataController;
    public static UIController uiController;

    // The main function runs when the program is launched
    public static void main(String[] args) {
        // Instantiate the DataController
        dataController = new DataController();
        // Generate some users and data for testing purposes
        dataController.genTestData();
        // Instantiate the UIController
        uiController = new UIController();

        // Since the application is entirely UI driven and the UIController
        // handles all the UI, the main method requires no additional code.
    }
}