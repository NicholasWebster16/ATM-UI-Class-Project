package atm_application.ui_elements;

import java.awt.*;

/*****************************************************************
 * The default font settings made the application ugly and the
 * layout of information unclear.
 *
 * I decided to create an abstract class to hold the various
 * font styles I would use for the ui_elements. That way
 * if I want to change the header font the change will be
 * reflected across all ui_elements.
 *
 * The class is an abstract class because it wouldn't make any
 * sense to attempt to instantiate this class.
 */
public abstract class CustomFonts {
    public static final Font inputFont = new Font("Arial", Font.BOLD, 30);
    public static final Font digitButton = new Font("Arial", Font.BOLD, 35);
    public static final Font labelLarge = new Font("Arial", Font.BOLD, 26);
    public static final Font labelNormal = new Font("Arial", Font.BOLD, 20);
    public static final Font labelSmall = new Font("Arial", Font.BOLD, 15);
    public static final Font textLarge = new Font("Arial", Font.PLAIN, 26);
    public static final Font textNormal = new Font("Arial", Font.PLAIN, 20);
    public static final Font textSmall = new Font("Arial", Font.PLAIN, 15);
}
