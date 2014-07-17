/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
import powerpaint.gui.PowerPaintGui;

/**
 * This is the main class that initializes the GUI
 * for the powerPaint program.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
public final class PowerPaintMain
{

  /**
   * Private constructor for this Main class.
   */
  private PowerPaintMain()
  {
    
  }
  
  /**
   * Main method that initializes the GUI
   * for the powerPaint program.
   * @param the_args Arguments sent into the main method.
   */
  public static void main(final String[] the_args)
  {
    new PowerPaintGui();

  }

}
