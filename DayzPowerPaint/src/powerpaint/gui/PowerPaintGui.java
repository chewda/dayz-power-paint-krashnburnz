/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
package powerpaint.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import powerpaint.actions.Destination;
import powerpaint.actions.Friendly;
import powerpaint.actions.Player;
import powerpaint.actions.DragMap;
import powerpaint.actions.Enemy;
import powerpaint.actions.Groups;
import powerpaint.actions.PowerPaintColor;
import powerpaint.actions.PowerPaintEllipseDraw;
import powerpaint.actions.PowerPaintLineDraw;
import powerpaint.actions.PowerPaintPencilDraw;
import powerpaint.actions.PowerPaintRectangleDraw;
import powerpaint.guicomponents.PowerPaintDrawPanel;
import powerpaint.guicomponents.PowerPaintMenuBar;
import powerpaint.guicomponents.PowerPaintToolBar;

/**
 * This is the GUI class that used the various objects and
 * creates the GUI for the PowerPaint program.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
@SuppressWarnings("serial")
public class PowerPaintGui extends JFrame
{  
  /**
   * Protected Field used to create the Drawing area of the program
   * by creating a PowerPaintDrawPanel Object.
   */
  private static final PowerPaintDrawPanel MY_MIDDLE_PANEL = new PowerPaintDrawPanel();
  
  /**
   * This static Field is used to hold the string for the
   * pencil tool which is the default selected tool when 
   * the GUI initiates.
   */
  private static final String MY_PENCIL_STRING = "Pencil"; 
  
  /**
   * This static Field is used to hold which tool is currently
   * selected. The default value is the pencil tool.
   */
  private static String my_selected_tool = "Drag";
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the Destination Icon placeable on the Dayz Map.
   */
  private final Action my_group_action = new Groups("Group", 
          new ImageIcon(getClass().getResource("/images/group_icon.png")),  
          "Tool used to place a Group icon on the map.", 
           Integer.valueOf(KeyEvent.VK_O));

  /**
   * This Field is used to create and hold the Action Object for 
   * the Destination Icon placeable on the Dayz Map.
   */
  private final Action my_enemy_action = new Enemy("Enemy", 
          new ImageIcon(getClass().getResource("/images/lz_enemy_icon_small.png")),  
          "Tool used to place a Enemy icon on the map.", 
           Integer.valueOf(KeyEvent.VK_M));
  
  
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the Destination Icon placeable on the Dayz Map.
   */
  private final Action my_destination_action = new Destination("Destination", 
          new ImageIcon(getClass().getResource("/images/destination_small.png")),  
          "Tool used to place a destination icon on the map.", 
           Integer.valueOf(KeyEvent.VK_N));
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the Frienbdly Icon placeable on the Dayz Map.
   */
  private final Action my_friendly_action = new Friendly("Friendly", 
          new ImageIcon(getClass().getResource("/images/lz_small.png")),  
          "Tool used to place a friendly icon on the map.", 
           Integer.valueOf(KeyEvent.VK_I));
  
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the DayzGuy icon placeable on the Dayz Map.
   */
  private final Action my_player_action = new Player("Player", 
          new ImageIcon(getClass().getResource("/images/player_icon.png")),  
          "Tool used to place the player's location.", 
           Integer.valueOf(KeyEvent.VK_Y));
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the Paint Color chooser tool.
   */
  private final Action my_drag_action = new DragMap("Drag", 
          new ImageIcon(getClass().getResource("/images/pointer.png")),  
          "Tool used to drag the map.", 
           Integer.valueOf(KeyEvent.VK_D));
  
  /**
   * This Field is used to create and hold the Action Object for 
   * the Paint Color chooser tool.
   */
  private final Action my_color_action = new PowerPaintColor("Color...", 
                  new ImageIcon(getClass().getResource("/images/color_swatch.png")),
                  "Choose the color you would like to use.", 
                  Integer.valueOf(KeyEvent.VK_C));
  /**
   * This Field is used to create and hold the Action Object for 
   * the Pencil tool.
   */                                               
  private final Action my_pencil_action = new PowerPaintPencilDraw(MY_PENCIL_STRING, 
                 new ImageIcon(getClass().getResource("/images/pencil_bw.gif")),  
                 "Pencil tool used to draw free hand.", 
                  Integer.valueOf(KeyEvent.VK_P));
  /**
   * This Field is used to create and hold the Action Object for 
   * the Ellipse tool.
   */                                                
  private final Action my_ellipse_action = new PowerPaintEllipseDraw("Ellipse", 
                new ImageIcon(getClass().getResource("/images/ellipse_bw.gif")),  
                "Ellipse tool used to draw an elliptical shape.", 
                Integer.valueOf(KeyEvent.VK_S));
     
  /**
   * This Field is used to create and hold the Action Object for 
   * the Line tool.
   */       
  private final Action my_linedraw_action = new PowerPaintLineDraw("Line", 
                new ImageIcon(getClass().getResource("/images/line_bw.gif")),  
                "Line tool used to draw straight lines.", 
                Integer.valueOf(KeyEvent.VK_L));
  /**
   * This Field is used to create and hold the Action Object for 
   * the Rectangle tool.
   */                                                  
  private final Action my_rectangle_action = new PowerPaintRectangleDraw("Rectangle", 
                new ImageIcon(getClass().getResource("/images/rectangle_bw.gif")),  
                "Rectangle tool used to draw a rectangle shape.", 
                Integer.valueOf(KeyEvent.VK_R));
  
  /**
   * This Field is used to create the Image Icon storage
   * spot for the icon for the JFrame.
   */       
  private Image my_new_icon;
  
  /**
   * Constructor used to create the GUI JFrame. It first calls
   * to it's super class, then calls to the method called
   * start that does all the work creating the GUI Object
   * and setting all its values.
   */    
  public PowerPaintGui()
  {
    super();
    start();
  }
  
  /**
   * This is the primary method for the GUI JFrame class. This method
   * sets up everything pertaining to the GUI. This includes
   * the Icon, Name, and the various setting for the GUI
   */
  private void start() 
  {
    add(MY_MIDDLE_PANEL, BorderLayout.CENTER);
    getNewImage();
    this.setIconImage(my_new_icon);
    this.setTitle("Dayz Power Paint by Krashnburnz");
    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initilizeActions();
    final PowerPaintMenuBar menu = new PowerPaintMenuBar();
    final JToolBar toolbar = PowerPaintToolBar.getToolBar();
    setJMenuBar(menu);
    add(toolbar, BorderLayout.SOUTH);
    pack();
    
    //Center on Screen
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
    Point newLocation = new Point(middle.x - (this.getWidth() / 2), 
                                  middle.y - (this.getHeight() / 2));
    this.setLocation(newLocation);
    setVisible(true);

  }
  
  /**
   * Public method used by the others class to communicate with the
   * clearDrawPanel option inside the PaintDrawPanel Object
   * which is the drawing area for the PowerPaint program.
   */
  public static void clearDrawPanel()
  {
    MY_MIDDLE_PANEL.clearDrawArea();
  }
  
  /**
   * Public method used by the others class to communicate with the
   * clearCoordinates option inside the PaintDrawPanel Object
   * which is the drawing area for the PowerPaint program.
   */
  public static void clearCoordinates()
  {
    MY_MIDDLE_PANEL.clearCoordinates();
  }
  
  /**
   * Public method used by the others class to communicate with the
   * drawGrid option inside the PaintDrawPanel Object
   * which is the drawing area for the PowerPaint program.
   */
  public static void drawGrid()
  {
    MY_MIDDLE_PANEL.drawGrid();
  }
  
  /**
   * Public method used by the others class to communicate with the
   * unDrawGrid option inside the PaintDrawPanel Object
   * which is the drawing area for the PowerPaint program.
   */
  public static void unDrawGrid()
  {
    MY_MIDDLE_PANEL.unDrawGrid();
  }
  
  /**
   * Public method used by the others class to communicate with the
   * changeThickness option inside the PaintDrawPanel Object
   * which is the drawing area for the PowerPaint program.
   * @param the_thickness selected thickness.
   */
  public static void changeThickness(final int the_thickness)
  {
    MY_MIDDLE_PANEL.changeThickness(the_thickness);
  }
  
  /**
   * Helper method that is used to set up the menu bar. 
   */
  private void initilizeActions()
  {
    //setup tool menu fully
    PowerPaintMenuBar.setupToolMenu(my_color_action, KeyEvent.VK_C);    
    PowerPaintMenuBar.setupToolMenu(my_pencil_action, KeyEvent.VK_P);
    PowerPaintMenuBar.setupToolMenu(my_linedraw_action, KeyEvent.VK_L);
    PowerPaintMenuBar.setupToolMenu(my_rectangle_action, KeyEvent.VK_R);
    PowerPaintMenuBar.setupToolMenu(my_ellipse_action, KeyEvent.VK_S);
    PowerPaintMenuBar.setupToolMenu(my_player_action, KeyEvent.VK_Y);
    PowerPaintMenuBar.setupToolMenu(my_group_action, KeyEvent.VK_O);
    PowerPaintMenuBar.setupToolMenu(my_destination_action, KeyEvent.VK_N);
    PowerPaintMenuBar.setupToolMenu(my_enemy_action, KeyEvent.VK_M);
    PowerPaintMenuBar.setupToolMenu(my_friendly_action, KeyEvent.VK_I);
    PowerPaintMenuBar.setupToolMenu(my_drag_action, KeyEvent.VK_D);


    
    //setup tool bar fully
    PowerPaintToolBar.setupToolBarMenu(my_color_action);    
    PowerPaintToolBar.setupToolBarMenu(my_pencil_action);
    PowerPaintToolBar.setupToolBarMenu(my_linedraw_action);
    PowerPaintToolBar.setupToolBarMenu(my_rectangle_action);
    PowerPaintToolBar.setupToolBarMenu(my_ellipse_action);
    PowerPaintToolBar.setupToolBarMenu(my_player_action);
    PowerPaintToolBar.setupToolBarMenu(my_group_action);
    PowerPaintToolBar.setupToolBarMenu(my_destination_action);
    PowerPaintToolBar.setupToolBarMenu(my_friendly_action);
    PowerPaintToolBar.setupToolBarMenu(my_enemy_action);
    PowerPaintToolBar.setupToolBarMenu(my_drag_action);

  
  }

  /**
   * Helper method that gets the Image ready to be used as the
   * icon for the GUI JFrame.
   */
  private void getNewImage()
  {
    // checks to see if the file exists
    try
    {
      my_new_icon = ImageIO.read(getClass().getResource("/images/Dayz_PP_logo.png"));
    }
    catch (final IOException theexception)
    {
      theexception.printStackTrace();
    }
    
  }
  
  /**
   * Public getter method used by the others class retrieve the
   * currently selected tool.
   * @return String value of the selected tool.
   */
  public static String getSelectedTool()
  {
    return my_selected_tool;
  }

  /**
   * Public setter method used by the others class to set the 
   * currently selected tool.
   * @param the_selected_tool which tools is currently selected.
   */
  public static void setSelectedTool(final String the_selected_tool)
  {
    my_selected_tool = the_selected_tool;
  }
  
  /**
   * Public getter method that will pop up a
   * JDialog box for the ABOUT menu option.
   */
  public static void getHelpPopup()
  {
    JOptionPane.showMessageDialog(MY_MIDDLE_PANEL,
                                  "Dayz Power Paint - July 2014 - Revision 1 \n " +
                                  "Created by: Daniel Henderson aka Krashnburnz \n" +
                                  "Email: krashnburnz@yahoo.com",
                                  "About...",
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Public method to call a enable/disable of the edit options for re do
   * and undo.
   * @param the_type of button to change, either "re do" or "undo".
   * @param the_setting boolean value whether to disable or enable the button.
   */
  public static void changeEditMenuItemAvailable(final String the_type, 
                                                 final boolean the_setting)
  {
    PowerPaintMenuBar.changeEditMenuItemAvailable(the_type, the_setting);
    
  }
  
  /**
   * Public method used to undo one or multiple
   * drawn items on the panel.
   */
  public static void undoItem()
  {
    MY_MIDDLE_PANEL.undoItems();
  }

  /**
   * Public method used to re do one or multiple
   * un drawn previous items on the panel.
   */
  public static void redoItem()
  {
    MY_MIDDLE_PANEL.redoItems();
    
  }
}
