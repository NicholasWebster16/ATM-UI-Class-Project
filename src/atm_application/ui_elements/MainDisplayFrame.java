package atm_application.ui_elements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*********************************************************************************
 * UI_Element - MainDisplayFrame
 * Usage:
 * The MainDisplayFrame creates and controls the main window the user input is
 * loaded to.
 *
 * Details:
 * The MainDisplayFrame stores the currently displayed elements in an ArrayList.
 * By storing the JComponents (ui_elements) in an ArrayList the frame is able to
 * cleanly clear currently existing ui_elements and re-add new ones according to
 * a given ArrayList of ui_elements.
 *
 * By seperating this functionality from the UIController it is much simpler
 * to switch between currently displayed screens and removes the need to create
 * an entirely new frame for every different screen layout.
 */
public class MainDisplayFrame extends JFrame {
    // Designates the class wide variables that will be used.
    private GridBagConstraints constraints;
    private ArrayList<JComponent> renderComponents = new ArrayList<>();

    // The constructor for the frame simply creates a new frame (itself) and styles the layout for it.
    public MainDisplayFrame(){
        super("ATM Application");
        this.setLayout(new GridBagLayout());
        this.setJMenuBar(new AppMenuBar());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Close the application when the window is closed.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultLookAndFeelDecorated(true);
    }

    // Changes the display by clearing the current screen and rendering a new one with the given ArrayList of ui_elements.
    public void changeDisplay(ArrayList newRenderComponents) {
        // Clear existing ui_elements
        clearFrame();
        // Sets the displaying ArrayList to the newly given one.
        renderComponents = newRenderComponents;
        // Renders the new display
        render();
    }

    // Loops through the stored ArrayList of ui_elements and removes each from the frame.
    private void clearFrame(){
        for (JComponent component : renderComponents) {
            this.remove(component);
        }
        // Clears the ArrayList of all stored ui_elements.
        renderComponents.clear();
    }

    // Generates the display by adding the ui_elements from the stored ArrayList in order from top to bottom.
    private void render(){
        // Set the initial grid location to 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 20, 10);

        // Loops through all the ui_elements adding each one below the last.
        for (JComponent component : renderComponents) {
            this.add(component, constraints);
            constraints.gridy++;
            constraints.insets = new Insets(0, 10, 10, 10);
        }

        // Packs the newly created screen.
        this.pack();
        // Sets the frame as visible.
        this.setVisible(true);
    }
}
