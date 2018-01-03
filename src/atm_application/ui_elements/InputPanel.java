package atm_application.ui_elements;

import atm_application.exception_handling.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static atm_application.Application.uiController;

public class InputPanel extends JPanel implements ActionListener {

    private JTextField userInput;
    private JLabel inputLabel;

    public InputPanel(String labelText, Dimension inputSize) {
        super(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 10, 0, 10);

        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;
        inputLabel = new JLabel(labelText);
        inputLabel.setFont(CustomFonts.labelSmall);
        userInput = new JTextField();
        userInput.setFont(CustomFonts.inputFont);
        userInput.setPreferredSize(inputSize);

        this.add(inputLabel, constraints);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        this.add(userInput, constraints);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "CLR") {
            userInput.setText("");
        }
        else if(e.getActionCommand() == "ENT") {
            try {
                uiController.inputPanelSubmit(userInput.getText());
            } catch (InvalidInputException e1) {
                e1.printStackTrace();
            }
        }
        else {
            userInput.setText(userInput.getText() + e.getActionCommand());
        }
    }
}
