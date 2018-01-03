package atm_application.exception_handling;

import javax.swing.*;
import java.util.ArrayList;

/***********************************************
 * Note about Exception Window:
 * Making the ExceptionWindow it's own class
 * allows me to reuse the code for error.
 ***********************************************/
public class ExceptionWindow extends JOptionPane {
    public ExceptionWindow(String exceptionType, ArrayList<String> errorMessages) {
        String messageString = exceptionType + ": \n";
        for (String errorMessage : errorMessages) {
            messageString += "- " + errorMessage + "\n";
        }

        this.showMessageDialog(null, messageString);
    }
}
