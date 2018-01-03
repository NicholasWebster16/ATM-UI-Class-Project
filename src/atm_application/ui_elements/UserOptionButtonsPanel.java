package atm_application.ui_elements;

import atm_application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/***********************************************************************************
 * UI_Element - UserOptionButtonsPanel
 * Usage:
 * The UserOptionButtonsPanel contains buttons for all the options a logged on user
 * has. It contains the following buttons: Log Off, Deposit Tender, Withdraw Cash,
 * and Transfer Funds.
 *
 * Details:
 * The panel itself is the Action Listener to the buttons. When a button is clicked
 * it determine which button and sends the appropriate command to the UIController.
 *
 * I probably could have looped through an array of strings like I did with the Digit
 * Pad, with only four buttons, hand coding each wasn't an overly taxing process.
 */
public class UserOptionButtonsPanel extends JPanel implements ActionListener{
    // Establishes the buttons that will be added to the panel.
    private JButton btnWithdraw, btnDeposit, btnTransfer, btnLogOff;

    // The constructor does all the real work, generating buttons and adding them to the panel itself.
    public UserOptionButtonsPanel() {
        // Initialize the layout by running the constructor of the base class.
        super(new GridBagLayout());

        // Sets the button dimensions foe all the buttons on the panel.
        // By setting this at the top, it is possible to resize the buttons by changing only one variable.
        Dimension uniformButtonDimension = new Dimension(200,50);

        // Sets the layout options for the panel.
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;

        // Creates, stylizes and sets Action details for the "Log off" Button.
        btnLogOff = new JButton("Log Off");
        btnLogOff.setPreferredSize(uniformButtonDimension);
        btnLogOff.setFont(CustomFonts.labelNormal);
        btnLogOff.setActionCommand("LOG_OFF");
        btnLogOff.addActionListener(this);

        // Creates, stylizes and sets Action details for the "Deposit Tender" Button.
        btnDeposit = new JButton("Deposit Tender");
        btnDeposit.setPreferredSize(uniformButtonDimension);
        btnDeposit.setFont(CustomFonts.labelNormal);
        btnDeposit.setActionCommand("DEPOSIT");
        btnDeposit.addActionListener(this);

        // Creates, stylizes and sets Action details for the "Withdraw Cash" Button.
        btnWithdraw = new JButton("Withdraw Cash");
        btnWithdraw.setPreferredSize(uniformButtonDimension);
        btnWithdraw.setFont(CustomFonts.labelNormal);
        btnWithdraw.setActionCommand("WITHDRAW");
        btnWithdraw.addActionListener(this);

        // Creates, stylizes and sets Action details for the "Transfer Funds" Button.
        btnTransfer = new JButton("Transfer Funds");
        btnTransfer.setPreferredSize(uniformButtonDimension);
        btnTransfer.setFont(CustomFonts.labelNormal);
        btnTransfer.setActionCommand("TRANSFER");
        btnTransfer.addActionListener(this);

        // Adds each button to the panel at the appropriate gird location.
        this.add(btnDeposit, constraints);
        constraints.gridx = 1;
        this.add(btnWithdraw, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        this.add(btnLogOff, constraints);
        constraints.gridx = 1;
        this.add(btnTransfer, constraints);

    }

    // Switches through all possible buttons clicked and runs the corresponding methods on the UIController.
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "LOG_OFF":
                Application.uiController.userLogOff();
                break;
            case "DEPOSIT":
                Application.uiController.switchToDepositScreen();
                break;
            case "WITHDRAW":
                Application.uiController.switchToWithdrawScreen();
                break;
            case "TRANSFER":
                Application.uiController.switchToTransferScreenOne();
                break;
        }
    }
}
