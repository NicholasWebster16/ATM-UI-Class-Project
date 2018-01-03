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

/********************************************************************
 * UI_Element - AccountSelectionTransferPanel.
 * Usage:
 * This element is created and added to the transfer screen allowing
 * the user to select the destination account for the transfer.
 *
 * Details:
 * As stated many times in other parts of this code, this could be
 * combined with the other AccountSelectionPanel. I was running low
 * on time when finishing this portion of the code and the
 * unintuitive code reflects that.
 *
 * I plan on revisiting this after the project's completion and
 * cleaning up a lot.
 */
public class AccountSelectionTransferPanel extends JPanel implements ActionListener {
    // Designates all the variables being used by this panel.
    // A couple of them could be removed entirely upon correcting the selected user storage system.
    private GridBagConstraints constraints = new GridBagConstraints();
    private ButtonGroup accountSelectionGroup = new ButtonGroup();
    private ArrayList<JRadioButton> accountSelectionButtonList = new ArrayList<>();
    private Account selectedReceivingAccount;
    private User receivingUser;

    /*****************************************************************
     * Unlike the other AccountSelectionPanel this one takes a user in
     * its constructor. This means that almost all the code required to
     * generate this panel is handled entirely inside the panel itself.
     * This is closer to what I envision for the final simplified
     * version of the code.
     * @param receivingUser
     */
    public AccountSelectionTransferPanel(User receivingUser) {
        super(new GridBagLayout());
        this.receivingUser = receivingUser;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 10, 0, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.setBorder(BorderFactory.createTitledBorder(null, "Accounts:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CustomFonts.labelSmall, Color.black));

        for (Account.AccountType accountType : Account.AccountType.values()) {
            Account foundAccount = (Account) receivingUser.getAccounts().get(accountType);
            if (foundAccount != null) {
                JRadioButton newRadioButton = addAccountToSelection(foundAccount);
                if (selectedReceivingAccount == null) {
                    selectedReceivingAccount = foundAccount;
                    newRadioButton.setSelected(true);
                }
            }
        }
        Application.uiController.setTransferReceivingAccount(selectedReceivingAccount);
    }

    // This AccountSelection panel still uses an addAccountToSelection method but it is called internally only.
    private JRadioButton addAccountToSelection(Account accountToAdd) {
        String labelString = "Transfer to " + accountToAdd.getAccountTypeString();
        JRadioButton newRadioButton = new JRadioButton(labelString);
        newRadioButton.setFont(CustomFonts.textSmall);
        newRadioButton.setActionCommand(accountToAdd.getAccountType().toString());
        newRadioButton.addActionListener(this);
        accountSelectionGroup.add(newRadioButton);
        accountSelectionButtonList.add(newRadioButton);
        this.add(newRadioButton, constraints);
        constraints.gridy++;
        return newRadioButton;
    }

    // The action performed method is also significantly simpler and better reflects what I envision for the final version.
    public void actionPerformed(ActionEvent e) {
        // Gets the receivingUser's selected Account for the transfer
        selectedReceivingAccount = receivingUser.getAccountByType(Account.parseAccountType(e.getActionCommand()));
        // Passes that selection onto the UIController.
        Application.uiController.setTransferReceivingAccount(selectedReceivingAccount);
    }
}