package atm_application.ui_elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***************************************************************
 * UI_Element - DigitPadPanel
 * Usage:
 * The DigitPadPanel contains 12 buttons. 1-0 and two functional
 * buttons. It must be linked to an input element in order to
 * function.
 *
 * Details:
 * ATM's don't have keyboards, inputting user information is usually
 * done through some sort of number pad. I attempted to emulate that
 * with my program.
 *
 * The constructor takes an ActionListener. This is because the
 * digit pad is functionally useless on its own. It requires an
 * input field to send information to.
 *
 * The DigitPadPanel sends an ActionEvent to the assigned listener
 * with the ActionCommand being the value of the button pressed.
 ****************************************************************/
public class DigitPadPanel extends JPanel {

    /***********************************************************
     * By using an Array of strings representing the eventual
     * ActionCommands for each button, I was able to generate
     * the buttons by looping through this Array thus keeping
     * repetitive code to a minimum.
     ***********************************************************/
    private static String digitStrings[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "CLR", "0", "ENT"};

    // The DigitPadPanel takes an ActionListener and links it to the panel's buttons.
    public DigitPadPanel(ActionListener attachedInput ) {
        // Initializes the panel and layout style by running the base classes constructor.
        super(new GridBagLayout());

        // Sets the layout options that will be used for this panel.
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        Dimension uniformBtnDimension = new Dimension(90,90);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;

        /********************************************************
         * The constructor loops through the Array of strings
         * specified at the beginning of the class. For each string
         * it creates a new button and sets it's action command
         * to the value of the string at the same position in the
         * Array.
         ********************************************************/
        for (String digitString : digitStrings) {
            // Creates a new button.
            JButton newButton = new JButton(digitString);
            // Formats the new button.
            newButton.setPreferredSize(uniformBtnDimension);
            // Sets the font and styling for the button depending on if it is a digit or a functional button.
            if(digitString == "CLR") {
                // Clear will remove all currently entered information in the linked input.
                newButton.setFont(CustomFonts.labelLarge);
                newButton.setForeground(new Color(107, 48, 48));
            }
            else if(digitString == "ENT") {
                // Enter will cause the input to send it's value to the the UIController which acts accordingly
                newButton.setFont(CustomFonts.labelLarge);
                newButton.setForeground(new Color(48, 107, 48));
            }
            else {
                // The digit buttons send their value to the linked input to be added to it's current input.
                newButton.setFont(CustomFonts.digitButton);
            }

            // Sets the action command for the button to be the same as the string at this position in the Array.
            newButton.setActionCommand(digitString);
            // Links the button to the given Action Listener
            newButton.addActionListener(attachedInput);
            // Adds the button to the panel with the given layout styling.
            this.add(newButton, constraints);

            // Increments the positioning of the buttons to create a 3x4 gird of buttons.
            constraints.gridx++;
            if(constraints.gridx > 2) {
                constraints.gridx = 0;
                constraints.gridy++;
            }
        }
    }
}
