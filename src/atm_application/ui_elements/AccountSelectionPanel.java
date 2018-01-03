package atm_application.ui_elements;

import atm_application.Application;
import atm_application.data_model.Account;
import atm_application.data_model.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

/****************************************************************************
 * UI_Element - AccountSelectionPanel:
 * Usage: This creates a panel containing radio buttons for each account associated
 * with the given user.
 * Details: Since my initial plan was to avoid having any of the ui_elements access
 * the data, this class was built to be fed data externally. After redesigning this
 * system, in addition to moving the stored selected user info on the DataController
 * it would also be smart to make this ui_element function more autonomously. You
 * can see a part of this new concept being sloppily implemented in the other
 * AccountSelectionTransferPanel.
 *
 * I feel confident that after a code revision it would be possible to combine the
 * two AccountSelection ui_elements into a single more agile ui_element.
 */
public class AccountSelectionPanel extends JPanel implements  ActionListener{
    // Sets the basic private variables that will be used throughout the code.
    private GridBagConstraints constraints = new GridBagConstraints();
    private ButtonGroup accountSelectionGroup = new ButtonGroup();
    // After code revision store user data here should be unnecessary.
    private Account selectedAccount;
    // Storing each radio button in an ArrayList should also become unnecessary.
    private ArrayList<JRadioButton> accountSelectionButtonList = new ArrayList<>();

    // The constructor for this class primarily establishes layout styling.
    public AccountSelectionPanel(){
        // Initialize the AccountSelectionPanel with the GirdBagLayout by running running the constructor on base JPanel class.
        super(new GridBagLayout());
        // The constraints are used to position the individual JComponents that will be added to this panel.
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 10, 0, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;
        // The border is being created and set in line. It would probably be cleaner to create the border and save it as a variable then reference that instead.
        this.setBorder(BorderFactory.createTitledBorder(null, "Accounts:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CustomFonts.labelSmall, Color.black));
    }

    /************************************************************************
     * This method allows the UIController to add accounts to the panel as it
     * finds them. This functionality could be isolated to this class itself
     * and allow this method to become private.
     * @param accountToAdd
     ************************************************************************/
    public void addAccountToSelection(Account accountToAdd){
        NumberFormat dollarFormatter = NumberFormat.getCurrencyInstance();
        String labelString = accountToAdd.getAccountTypeString() + " - Balance: " +  dollarFormatter.format(accountToAdd.getAccountBalance());
        JRadioButton newRadioButton = new JRadioButton(labelString);
        newRadioButton.setFont(CustomFonts.textSmall);
        newRadioButton.setActionCommand(accountToAdd.getAccountType().toString());
        newRadioButton.addActionListener(this);
        accountSelectionGroup.add(newRadioButton);
        accountSelectionButtonList.add(newRadioButton);
        this.add(newRadioButton, constraints);
        constraints.gridy++;
    }

    /**************************************************************************
     * The setSelectedAccount() method is another method that could probably be
     * isolated to this class. Additionally as it is only ever used when the
     * panel is fist created to set the initially selected radio button it could
     * probably be removed entirely after cleaning up the code a bit.
     * @param account
     ***************************************************************************/
    public void setSelectedAccount(Account account){
        selectedAccount = account;
        for (JRadioButton accountSelectionButton : accountSelectionButtonList) {
            Account.AccountType currentButtonAccountType = Account.parseAccountType(accountSelectionButton.getActionCommand());
            if (currentButtonAccountType == selectedAccount.getAccountType()){
                accountSelectionButton.setSelected(true);
            }
        }
    }

    // The ActionPerformed method is run when one of the radio buttons is clicked.
    // It updates the stored account selection on the UIController to reflect the
    // change.
    public void actionPerformed(ActionEvent e) {
        // The process of getting the information reflected by this variable chain represents the unnecessarily complex nature
        // of the current selected user system.
        User selectedUser = Application.uiController.getDisplayingUser();
        Account.AccountType newAccountType = Account.parseAccountType(e.getActionCommand());
        Account newSelectedAccount = selectedUser.getAccountByType(newAccountType);

        Application.uiController.setSelectedAccount(newSelectedAccount);
    }
}
