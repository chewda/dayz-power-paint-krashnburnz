/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
package powerpaint.guicomponents;

import java.awt.Color;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;


import powerpaint.actions.PowerPaintColor;

/**
 * This is the Tool Bar class that is placed onto the
 * JFrame in the southern part of the frame. This object
 * holds the various components for the tools and color
 * changer.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
@SuppressWarnings("serial")
public final class PowerPaintToolBar extends JToolBar
{
  /**
   * This static field is used to create the my_color_item button
   * for the color chooser.
   */
  private static JButton my_color_item;
  
  /**
   * This static field is used for primary tool bar setup in the
   * Horizontal position.
   */
  private static JToolBar my_tool_bar = new JToolBar(JToolBar.HORIZONTAL);

  /**
   * This static field is used to setup the button group for the
   * various tools located on the tool bar.
   */
  private static ButtonGroup my_tool_group = new ButtonGroup();
  
  /**
   * Constructor used to create Tool Bar Object. It calls to its super class
   * to create the Object.
   */
  private PowerPaintToolBar()
  {
    super();
  }
  
  /**
   * Primary method to add the tool bar item to the actual
   * tool bar.
   * @param the_toolbar_item tool bar item to be added to the tool bar.
   */
  private static void createToolMenus(final JToggleButton the_toolbar_item)
  {
    my_tool_bar.add(the_toolbar_item);
    my_tool_group.add(the_toolbar_item);
  }
  
  /**
   * Primary method to create and setup the various tool bar items
   * including adding the Action to the tool bar item.
   * @param the_action Action passed in to add to the tool bar item.
   */
  public static void setupToolBarMenu(final Action the_action)
  {
    // Creates and Adds various Options under to the tool bar.
    if (the_action.getClass() == PowerPaintColor.class)
    {
      my_color_item = new JButton(the_action);
      my_color_item.setBackground(Color.BLACK);
      my_color_item.setForeground(Color.WHITE);
      my_tool_bar.add(my_color_item);
      
      my_tool_bar.addSeparator();
    }
    else
    {
      createToolMenus(new JToggleButton(the_action));
    }

  }
  
  /**
   * Public getter method used to retrieve the set up tool bar.
   * @return JToolBar returns the created tool bar.
   */
  public static JToolBar getToolBar()
  {
    return my_tool_bar; 
  }
  
  /**
   * Public setter method used to set the background color
   * of the color button.
   * @param the_new_color Color passed in to set the appropriate color.
   */
  public static void setColorButton(final Color the_new_color)
  {
    my_color_item.setBackground(the_new_color);
  }
  
  /**
   * Public setter method used to set the foreground color (font)
   * of the color button.
   * @param the_new_color Color passed in to set the appropriate color.
   */
  public static void setColorFont(final Color the_new_color)
  {
    my_color_item.setForeground(the_new_color);
  }
}
