package atm_application.ui_elements;

import atm_application.data_model.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/*******************************************************************************
 * UI_Element - UserInfoPanel
 * Usage:
 * The UserInfoPanel displays details about the user currently logged on to the
 * ATM. At the moment it only displays the users name and user number.
 *
 * Details:
 * This header for the logged on screen allows the logged on user to confirm they
 * are logged into the correct account.
 */

public class UserInfoPanel extends JPanel {
    // Designates the labels used to display the information about the user.
    private JLabel lblUserNumber, lblCustName;

    // The constructor takes a given user and prints their details to the two labels above.
    // NOTE: As with the AccountSelection panels, this could be simplified to no longer require a passed user.
    public UserInfoPanel(User user) {
        // Initializes the layout style by running the constructor on the base class.
        super(new GridBagLayout());

        // Sets the layout options for this panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;

        // Gets the user number to display
        int userNumber = user.getUserNumber();
        // Builds the user name to display by getting the first and last names.
        String custNameFirst = user.getUserNameFirst();
        String custNameLast = user.getUserNameLast();

        // Creates and stylizes the labels
        lblCustName = new JLabel("Customer Name: " + custNameFirst + " " + custNameLast);
        lblCustName.setFont(CustomFonts.textSmall);
        lblUserNumber = new JLabel("Customer Number: " + userNumber);
        lblUserNumber.setFont(CustomFonts.textSmall);

        // Adds the labels to the panel at the correct grid locations.
        this.add(lblUserNumber, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(lblCustName, constraints);
        // Creates and sets the border in a single line.
        this.setBorder(BorderFactory.createTitledBorder(null, "User Information:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CustomFonts.labelSmall, Color.black));
    }
}
