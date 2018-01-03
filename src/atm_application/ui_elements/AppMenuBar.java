package atm_application.ui_elements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMenuBar extends JMenuBar {

    public AppMenuBar() {
        genFileMenu();
    }

    // This method generates and returns the file menu that will be added to the main menu bar.
    private void genFileMenu() {
        // Creates the File menu
        JMenu fileMenu = new JMenu("File");

        // Creates the menu items that will appear in the drop down window under the File menu
        JMenuItem fileExit = new JMenuItem("Exit");

        // Defines and runs an action that will close the application when the Exit menu button is clicked.
        fileExit.addActionListener(new ActionListener(){
            // Creates the action in-line rather than referencing an externally specified event
            // ---- Coded in-line due to the relative simplicity of the event being run.
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adds the menu items to the file menu
        fileMenu.add(fileExit);

        this.add(fileMenu);
    }

}
