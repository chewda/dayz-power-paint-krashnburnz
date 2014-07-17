/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
package powerpaint.actions;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import powerpaint.guicomponents.PowerPaintMenuBar;
import powerpaint.guicomponents.PowerPaintToolBar;

/**
 * This is the Paint Color class that is used to select
 * and set the color with a color chooser.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
@SuppressWarnings("serial")
public class PowerPaintColor extends AbstractAction
{
  /**
   * This static Field is used to hold the object of the
   * currently selected color.
   */
  private static Color my_selected_color = Color.ORANGE;
  
  /**
   * This static Field is used to avoid magic numbers.
   */
  private static final int THREE = 3;
  
  /**
   * This static Field is used to hold the value for the
   * HUE used to change the font color depending on
   * the background color.
   */
  private static final float MAX_HUE_SAT = (float) .15;
  
  /**
   * This static Field is used to hold the value for the
   * BRIGHTNESS used to change the font color depending on
   * the background color.
   */
  private static final float MAX_BRIGHTNESS = (float) .75;
  
  /**
   * This static Field is used to hold the value for the
   * SATURATION used to change the font color depending on
   * the background color.
   */
  private static final float MAX_HALF_BRIGHTNESS = (float) .50;
  
  /**
   * This Field is used to hold the value for the color black.
   */
  private final Color my_black_holder = new Color(0, 0, 0);
  
  /**
   * Constructor used to create the Paint Color Object. It calls to its super class
   * passing in the text "Color" and the Icon chosen for the color button.
   * It then sets up the mnemonics for the button along with whether
   * this object is selected by default.
   * @param the_text Text for the button
   * @param the_icon Image icon used for Object
   * @param the_description description of the button Object
   * @param the_mnemonic mnemonic to setup the shortcut
   */
  public PowerPaintColor(final String the_text, final ImageIcon the_icon,
                         final String the_description, 
                         final Integer the_mnemonic) 
  {
    super(the_text, the_icon);
    putValue(SHORT_DESCRIPTION, the_description);
    putValue(MNEMONIC_KEY, the_mnemonic);
    putValue(SELECTED_KEY, false);
  }

  /**
   * ActionPerformed method used to set the colors of the button.
   * Also used to bring up the color chooser dialogue so the user
   * can choose the color they want.
   * @param the_arg action event passed in.
   */
  @Override
  public void actionPerformed(final ActionEvent the_arg)
  {
    final Color temp_color = my_selected_color;
    my_selected_color = JColorChooser.showDialog(new JPanel(), 
                        "Choose your color", my_selected_color);
    if (my_selected_color == null)
    {
      my_selected_color = temp_color;
    }
    checkToChangeFontColor();
    PowerPaintMenuBar.setColorButton(my_selected_color);
    PowerPaintToolBar.setColorButton(my_selected_color);
  }
  
  /**
   * Method that calculates the foreground color of the button
   * depending on the hue, saturation, and brightness of the
   * background color.
   */
  private void checkToChangeFontColor()
  {
    final int red_amount = my_selected_color.getRed();
    final int green_amount = my_selected_color.getGreen();
    final int blue_amount = my_selected_color.getBlue();
    final float [] hsb_array = new float [THREE];
    Color.RGBtoHSB(red_amount, green_amount, blue_amount, hsb_array);
    
    // checks for very dark colors to set the text to white
    if (my_selected_color.equals(my_black_holder) ||
            ((hsb_array[0] > MAX_HUE_SAT && 
              hsb_array[1] > MAX_HUE_SAT) && 
              hsb_array[2] < MAX_HALF_BRIGHTNESS) ||
            ((hsb_array[0] == 0 && hsb_array[1] == 0) && 
              hsb_array[2] < MAX_BRIGHTNESS))
    {
      PowerPaintMenuBar.setColorFont(Color.WHITE);
      PowerPaintToolBar.setColorFont(Color.WHITE);
    }
    
    // if not a dark color, sets the font color to black
    else
    {
      PowerPaintMenuBar.setColorFont(Color.BLACK);
      PowerPaintToolBar.setColorFont(Color.BLACK);
    }
  }
  
  /**
   * Public getter to get the selected color.
   * @return Color of the currently selected color.
   */
  public static Color getColor()
  {
    return my_selected_color;
    
  }
}
