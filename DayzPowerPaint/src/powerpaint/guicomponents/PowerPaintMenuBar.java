/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
package powerpaint.guicomponents;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import powerpaint.actions.PowerPaintColor;
import powerpaint.gui.PowerPaintGui;

/**
 * This is the Menu Bar class that is placed onto the
 * JFrame holds the various menu options for the PowerPaint
 * program.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
@SuppressWarnings("serial")
public class PowerPaintMenuBar extends JMenuBar
{
  /**
   * This static Field is used to initialize and hold the JMenuItem redo
   * button. 
   */
  private static final JMenuItem MY_REDO_MENU_ITEM = new JMenuItem("Redo", KeyEvent.VK_Z);    
  
  /**
   * This static Field is used to initialize and hold the JMenuItem undo
   * button. 
   */
  private static final JMenuItem MY_UNDO_MENU_ITEM = new JMenuItem("Undo", KeyEvent.VK_U);

  /**
   * This static Field is used to represent the index
   * for some of the arrays used.
   */
  private static final int THICKNESS_FOUR = 4;
  
  /**
   * This field is used for the menu item under tools for the
   * color chooser. This menu item's button will change color
   * when the various colors are chosen.
   */
  private static JMenuItem my_color_item;
  
  /**
   * This field is used for the button group that holds the various
   * tool types. 
   */
  private static final ButtonGroup MY_TOOL_GROUP = new ButtonGroup();
  
  /**
   * This field is used for the menu bar item called TOOLS. 
   * It holds the options to choose the color and holds the 
   * various tools used to draw on the paint area.
   */
  private static final JMenu MY_TOOL_MENU = new JMenu("Tools");
  
  /**
   * This field is used for the menu bar item called FILE. 
   * It holds the options to clear the panel, or to quit.
   */
  private final JMenu my_file_menu = new JMenu("File");
  
  /**
   * This field is used for the menu bar item called EDIT. 
   * It holds the options to UNDO and REDO.
   */
  private final JMenu my_edit_menu = new JMenu("Edit");
  
  /**
   * This field is used for the menu bar item called OPTIONS. 
   * It holds the options to set the Thickness and to enable the Grid.
   */
  private final JMenu my_options_menu = new JMenu("Options");
  
  /**
   * This field is used for the menu bar item called HELP. 
   * It holds the about option to explain what the program
   * is.
   */
  private final JMenu my_help_menu = new JMenu("Help");
  
  /**
   * This field is used for the menu bar item called under the
   * options part of the main menu. It holds the options to set the 
   * various Thickness.
   */
  private JMenu my_thickness_menu;
    
  /**
   * This field is used for the button group that holds the various
   * thickness types. 
   */
  private final ButtonGroup my_thickness_group = new ButtonGroup();
    
  /**
   * Constructor used to create Menu Bar Object. It calls to its super class
   * then calls various helper methods to setup the different menu
   * items and sub menus. It then sets its mnemonics and adds the 
   * menus to this object.
   */
  public PowerPaintMenuBar() 
  {
    super();
    setupFileMenu();
    setupEditMenu();
    setupOptionMenu();
    MY_TOOL_MENU.setMnemonic(KeyEvent.VK_T); 
    setupHelpMenu();
    addMainMenuItems();
  }
  
  /**
   * Primary method used to add the primary menu items onto the JMenu.
   */
  private void addMainMenuItems()
  {
    add(my_file_menu);
    add(my_edit_menu);
    add(my_options_menu);
    add(MY_TOOL_MENU);
    add(my_help_menu);
  }

  /**
   * Primary method used to set up the file menu. initializes
   * the various menus under the FILE menu section. Also
   * used to add actionListener via inner classes.
   */
  private void setupFileMenu()
  { 
    // Creates and adds the Clear option to the file menu
    my_file_menu.setMnemonic(KeyEvent.VK_F);
    final JMenuItem clear = new JMenuItem("Clear", KeyEvent.VK_C);
    clear.setAccelerator(KeyStroke.getKeyStroke(
      KeyEvent.VK_C, ActionEvent.ALT_MASK));
    clear.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        PowerPaintGui.clearDrawPanel();
      }
    });
    my_file_menu.add(clear);
    my_file_menu.addSeparator();
    
    // Creates and adds the Quit option to the file menu
    final JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
    quit.setAccelerator(KeyStroke.getKeyStroke(
      KeyEvent.VK_Q, ActionEvent.ALT_MASK));
    quit.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        System.exit(0);
      }
    });
    my_file_menu.add(quit);    
  }
  
  /**
   * Primary method used to set up the edit menu. Configures
   * the various menus under the EDIT menu section. Also
   * used to add actionListener via inner classes.
   */
  private void setupEditMenu()
  {
    // Creates and adds the Undo option to the edit menu
    my_edit_menu.setMnemonic(KeyEvent.VK_E);

    MY_UNDO_MENU_ITEM.setAccelerator(KeyStroke.getKeyStroke(
      KeyEvent.VK_U, ActionEvent.ALT_MASK));
    MY_UNDO_MENU_ITEM.setEnabled(false);
    MY_UNDO_MENU_ITEM.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        PowerPaintGui.undoItem();
      }
    });
    my_edit_menu.add(MY_UNDO_MENU_ITEM);
    
    // Creates and adds the re do option to the edit menu
    MY_REDO_MENU_ITEM.setAccelerator(KeyStroke.getKeyStroke(
                                               KeyEvent.VK_Z, ActionEvent.ALT_MASK));
    MY_REDO_MENU_ITEM.setEnabled(false);
    MY_REDO_MENU_ITEM.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        PowerPaintGui.redoItem();
      }
    });
    my_edit_menu.add(MY_REDO_MENU_ITEM);
  }
  
  /**
   * Public method used to enable/disable the undo and re do
   * menu items when this feature is not available.
   * @param the_type String takes either "redo" or "undo" as parameter.
   * @param the_change Boolean true to enable, false to disable
   */
  public static void changeEditMenuItemAvailable(final String the_type, 
                                                 final boolean the_change)
  {
    if ("redo".equals(the_type))
    {
      MY_REDO_MENU_ITEM.setEnabled(the_change);
    }
    else if ("undo".equals(the_type))
    {
      MY_UNDO_MENU_ITEM.setEnabled(the_change);
    }
    //else do nothing  
  }

  
  /**
   * Primary method used to set up the option menu. initializes
   * the various menus under the OPTION menu section. Also
   * used to add actionListener via inner classes.
   */
  private void setupOptionMenu()
  {
    my_options_menu.setMnemonic(KeyEvent.VK_O);
    
    // Creates and Adds the Grid Option with a checkMenuBox
    final JCheckBoxMenuItem grid_menu = new JCheckBoxMenuItem("Grid");
    grid_menu.setMnemonic(KeyEvent.VK_G);
    grid_menu.setAccelerator(KeyStroke.getKeyStroke(
                         KeyEvent.VK_G, ActionEvent.ALT_MASK));
    grid_menu.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        if (grid_menu.isSelected())
        {
          PowerPaintGui.drawGrid();
        }
        else
        {
          PowerPaintGui.unDrawGrid();
        }
      }
    });
    my_options_menu.add(grid_menu);

    // Creates and Adds the Thickness Option with a RadioButton Panel
    my_thickness_menu = new JMenu("Thickness");
    my_thickness_menu.setMnemonic(KeyEvent.VK_T);
    createNewThickness(new JRadioButtonMenuItem(), 1, KeyEvent.VK_1);
    createNewThickness(new JRadioButtonMenuItem(), 2, KeyEvent.VK_2);
    createNewThickness(new JRadioButtonMenuItem(), THICKNESS_FOUR, KeyEvent.VK_4);
    my_options_menu.add(my_thickness_menu);

  }
  
  /**
   * Helper method used to set up the various thickness options
   * under the sub menu for Thickness.
   * @param the_button button passed into so have it configured.
   * @param the_thickness thickness value passed into to set thickness
   * @param the_vk mnemonic value passed into to set the button up
   */
  private void createNewThickness(final JRadioButtonMenuItem the_button, 
                                  final int the_thickness, final int the_vk)
  {
    // creates the thickness for the button passed in
    // used to easily create new thickness
    final String name = " " + the_thickness;
    the_button.setText(name);
    if (the_thickness == 1)
    {
      the_button.setSelected(true);
    }
    the_button.setMnemonic(the_vk);
    the_button.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        PowerPaintGui.changeThickness(the_thickness);
      }
    });
    my_thickness_menu.add(the_button);
    my_thickness_group.add(the_button);
    
  }
  
  /**
   * Helper method for the setupToolMenu method
   * used to set up the tool menu bar and it's various
   * tool options.
   * @param the_menu_item menu item passed in to be configured.
   * @param the_vk mnemonic value passed into to set the button up
   */
  private static void createToolMenus(final JMenuItem the_menu_item, final int the_vk)
  {
    the_menu_item.setAccelerator(KeyStroke.getKeyStroke(
                         the_vk, ActionEvent.ALT_MASK));
    if (the_vk == KeyEvent.VK_P)
    {
      the_menu_item.setSelected(true);
    }
    MY_TOOL_MENU.add(the_menu_item);
    MY_TOOL_GROUP.add(the_menu_item);
  }
  
  /**
   * Primary method used to set up the tool menu bar and it's various
   * tool options. this method adds the Actions for the various
   * tools to be linked with the buttons in the tool bar.
   * @param the_action Action attached to the menu item.
   * @param the_vk mnemonic value passed into to set the button up
   */
  public static void setupToolMenu(final Action the_action, final int the_vk)
  {
    // Creates and Adds various Options under the tools menu
    if (the_action.getClass() == PowerPaintColor.class)
    {
      my_color_item = new JMenuItem(the_action);
      my_color_item.setBackground(Color.BLACK);
      my_color_item.setForeground(Color.WHITE);
      createToolMenus(my_color_item, the_vk);
      MY_TOOL_MENU.addSeparator();
    }
    else
    {
      createToolMenus(new JRadioButtonMenuItem(the_action), the_vk);
    } 
  }

  /**
   * Primary method used to set up the about menu.
   */
  private void setupHelpMenu()
  {
    my_help_menu.setMnemonic(KeyEvent.VK_H);
    final JMenuItem about = new JMenuItem("About...", KeyEvent.VK_A);
    about.setAccelerator(KeyStroke.getKeyStroke(
      KeyEvent.VK_A, ActionEvent.ALT_MASK));
    about.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        PowerPaintGui.getHelpPopup();
      }
    });
    my_help_menu.add(about);

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
