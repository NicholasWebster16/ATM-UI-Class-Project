package atm_application.ui_elements;

import atm_application.Application;
import atm_application.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonPanel extends JPanel  implements ActionListener{
    private UIController.UIStatus previousStatus;
    private Dimension uniformButtonDimension = new Dimension(200,50);

    /******************************************************************************
     * UI_Element - BackButtonPanel
     * Usage:
     * This button is used to allow the UIController to have some contextual
     * functionality. Upon creation it sets the previous screen loaded.
     *
     * Details:
     * By storing the previously loaded screen type this button allows the
     * user to be able to return to the previous screen regardless of what
     * screen they are currently on.
     *
     * Upon click it informs the UIController of the request to return to
     * the stored screen type.
     * @param previousStatus
     */
    public BackButtonPanel(UIController.UIStatus previousStatus) {
        // Initializes the Layout and panel by running the constructor on the base class.
        super(new GridBagLayout());
        // Stores the passed parameter
        this.previousStatus = previousStatus;

        // Sets the style and layout options for this panel.
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;


        // Creates the "Back" button.
        JButton btnBack = new JButton("Back");
        // Sets the styling for this button.
        btnBack.setFont(CustomFonts.labelNormal);
        btnBack.setPreferredSize(uniformButtonDimension);
        // Sets itself as the action listener for the button click.
        btnBack.addActionListener(this);
        // Adds the button to the panel with the set layout.
        this.add(btnBack, constraints);
    }

    /*************************************************************************
     * Since the action of each individual ui_element is relatively unique I
     * found it more elegant and logical to make most elements listener's to
     * their own input. Upon receiving input the ui_element then uses the
     * UIController to perform the relevant action or process.
     *
     * This means that the UIController does not need to itself be an action
     * listener, and saves it the additional effort of determining which
     * element is sending the ActionEvent.
     *
     * Initial plans involved having the UIController handle all ActionEvents.
     * Each event would set the actionCommand with multiple parameters separated
     * by ' ' in the string. I abandoned this concept in favor of this simpler
     * method of implementation.
     */
    public void actionPerformed(ActionEvent e) {
        Application.uiController.returnToPreviousScreen(previousStatus);
    }
}
