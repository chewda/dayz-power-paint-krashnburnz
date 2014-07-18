/*
* Daniel Henderson
*
* TCSS 305 – Spring 2012
* Assignment 4 - PowerPaint
* May 15th 2012
*/
package powerpaint.guicomponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import powerpaint.actions.PowerPaintColor;
import powerpaint.gui.PowerPaintGui;

/**
 * This is the drawing panel class that is placed onto the
 * JFrame and paints the actual 2d graphics.
 * @author Daniel Henderson (dhende32@u.washington.edu)
 * @version Spring 2012
 */
@SuppressWarnings("serial")
public class PowerPaintDrawPanel extends JPanel
  /* complains about to many fields, but I cut down 
   * on as many field as I could
  */
{

  /**
   * This static Field is used to set the grid spacing
   * for the show grid option.
   */
  private static final int GRID_SPACING = 10;
  
  /**
   * This static Field is used to set represent the
   * out of bounds area we use to reset location fields to
   * when clearing the board or clearing these locations.
   */
  private static final int OUT_OF_BOUNDS_AREA = -100;
  
  /**
   * This static Field is used to represent the index
   * for some of the arrays used.
   */
  private static final int INDEX_THREE = 3;
  
  /**
   * This static Field is used to represent the index
   * for some of the arrays used.
   */
  private static final int INDEX_FOUR = 4;
  
  /**
   * This static Field is used to represent the string
   * for the Ellipse tool.
   */
  private static final String MY_ELLIPSE = "Ellipse";
  
  /**
   * This static Field is used to represent the string
   * for the Line tool.
   */
  private static final String MY_LINE = "Line";  
  
  /**
   * This static Field is used to represent the string
   * for the Rectangle tool.
   */
  private static final String MY_RECTANGLE = "Rectangle";
  
  /**
   * This static Field is used to represent the string
   * for the Pencil tool.
   */
  private static final String MY_PENCIL = "Pencil";
    
  /**
   * Boolean value used when the grid option is enabled
   * or enabled. True means the grid is enabled, false
   * means the grid is not enabled.
   */
  private boolean my_drawgrid;
  
  /**
   * Boolean value used when the grid option is enabled
   * or enabled. True means the grid is enabled, false
   * means the grid is not enabled.
   */
  private boolean my_currently_drawing;
  
  /**
   * Field used to represent the current brush thickness
   * chosen on the menu.
   */
  private int my_brush_thickness = 1;
  
  /**
   * List used to hold the String of all the various tools used
   * previously.
   */
  private final List<String> my_last_tool_type = new ArrayList<String>();
  
  /**
   * Map used to hold the rectangle shapes drawn on the panel.
   * It holds the shape and the color. This map is used for
   * both Ellipses and Rectangles.
   */
  private final Map<RectangularShape, Color> my_shapes_map = 
                new HashMap<RectangularShape, Color>();
  
  /**
   * Map used to hold the rectangle shapes thickness drawn on 
   * the panel. It holds the shape and the integer value of the
   * thickness. This map is used for both Ellipses and Rectangles.
   */
  private final Map<RectangularShape, Integer> my_shapes_thickness_map = 
                new HashMap<RectangularShape, Integer>(); 
  
  /**
   * Map used to hold the pencil shapes drawn on the panel.
   * It holds the shape and the color.
   */
  private final Map<GeneralPath, Color> my_pencil_map = 
                new HashMap<GeneralPath, Color>();
  
  /**
   * Map used to hold the pencil shapes thickness drawn on 
   * the panel. It holds the shape and the integer value of the
   * thickness.
   */
  private final Map<GeneralPath, Integer> my_pencil_thickness_map = 
                new HashMap<GeneralPath, Integer>();
  
  /**
   * Map used to hold the line shapes drawn on the panel.
   * It holds the shape and the color.
   */
  private final Map<int[], Color> my_line_map = new HashMap<int[], Color>();
  
  /**
   * Map used to hold the line undo shapes thickness drawn on 
   * the panel. It holds the shape and the integer value of the
   * thickness.
   */
  private final Map<int[], Integer> my_line_thickness_map = new HashMap<int[], Integer>();
  
  /**
  * Map used to hold the rectangle undo shapes for both Ellipses and Rectangles.
  */
  private final Map<RectangularShape, Color> my_undo_shapes_map = 
      new HashMap<RectangularShape, Color>();

/**
* Map used to hold the rectangle undo shapes thickness drawn on 
* the panel. It holds the shape and the integer value of the
* thickness. This map is used for both Ellipses and Rectangles.
*/
  private final Map<RectangularShape, Integer> my_undo_shapes_thickness_map = 
      new HashMap<RectangularShape, Integer>(); 

/**
* Map used to hold the pencil undo shapes drawn on the panel.
* It holds the shape and the color.
*/
  private final Map<GeneralPath, Color> my_undo_pencil_map = 
      new HashMap<GeneralPath, Color>();

/**
* Map used to hold the pencil undo shapes thickness drawn on 
* the panel. It holds the shape and the integer value of the
* thickness.
*/
  private final Map<GeneralPath, Integer> my_undo_pencil_thickness_map = 
      new HashMap<GeneralPath, Integer>();

/**
* Map used to hold the line undo shapes drawn on the panel.
* It holds the shape and the color.
*/
  private final Map<int[], Color> my_undo_line_map = new HashMap<int[], Color>();

/**
* Map used to hold the line undo shapes thickness drawn on 
* the panel. It holds the shape and the integer value of the
* thickness.
*/
  private final Map<int[], Integer> my_undo_line_thickness_map = new HashMap<int[], Integer>();
  
  /**
   * List used to hold the Ellipse and Rectangles drawn on the panel. 
   * Each element holds an array of the coordinates for that shape.
   */
  private final List<RectangularShape> my_shapes_array = new ArrayList<RectangularShape>();
  
  /**
   * List used to hold the pencil shapes drawn on the panel. 
   * Each element holds the GeneralPath object for that shape.
   */
  private final List<GeneralPath> my_path_array = new ArrayList<GeneralPath>();
  
  /**
   * List used to hold the line shapes drawn on the panel. 
   * Each element holds the line shape for that line.
   */
  private final List<int[]> my_line_array = new ArrayList<int[]>();
  
  /**
   * List used to hold the undo Ellipse and Rectangles drawn on the panel. 
   * Each element holds an array of the coordinates for that shape.
   */
  private final List<RectangularShape> my_undo_shapes_array = 
      new ArrayList<RectangularShape>();
  
  /**
   * List used to hold the undo pencil shapes drawn on the panel. 
   * Each element holds the GeneralPath object for that shape.
   */
  private final List<GeneralPath> my_undo_path_array = new ArrayList<GeneralPath>();
  
  /**
   * List used to hold the undo line shapes drawn on the panel. 
   * Each element holds the line shape for that line.
   */
  private final List<int[]> my_undo_line_array = new ArrayList<int[]>();
  
  /**
   * Integer array used to hold the coordinates for the shape of
   * the line shape.
   */
  private int[] my_point_array = new int[INDEX_FOUR];
  
  /**
   * List used to hold the grid being displayed on the panel.
   */
  private final List<int[]> my_grid_array = new ArrayList<int[]>();
  
  /**
   * Integer array used to hold the coordinates for the grid array.
   */
  private int[] my_gridpoint_array = new int[INDEX_FOUR];
  
  /**
   * This field is used to hold the start x location when
   * the mouse is pressed.
   */
  private int my_start_x;
  
  /**
   * This field is used to hold the start y location when
   * the mouse is pressed.
   */
  private int my_start_y;
  
  /**
   * This field is used to hold the current x location when
   * the mouse is dragged.
   */
  private int my_current_x;
  
  /**
   * This field is used to hold the current y location when
   * the mouse is dragged.
   */
  private int my_current_y;
  
  /**
   * This field is used to hold the previous location for the
   * x coordinate. Used in numerous helper methods.
   */
  private int my_previous_x;
  
  /**
   * This field is used to hold the previous location for the
   * y coordinate. Used in numerous helper methods.
   */
  private int my_previous_y;
  
  /**
   * This field is used to hold the x location for the
   * x coordinate. Used in numerous helper methods.
   */
  private int my_new_x;
  
  /**
   * This field is used to hold the y location for the
   * y coordinate. Used in numerous helper methods.
   */
  private int my_new_y;  
  
  /**
   * RectangleShape rectangle shape field is used to hold the current shape being drawn
   * Once the mouse is released, this shape is saved into the maps
   * for its shape type.
   */
  private final RectangularShape my_rectangle_shape = new Rectangle2D.Double();
  
  /**
   * RectangleShape ellipse shape field is used to hold the current shape being drawn
   * Once the mouse is released, this shape is saved into the maps
   * for its shape type.
   */
  private final RectangularShape my_ellipse_shape = new Ellipse2D.Double();
  
  /**
   * GeneralPath Object field is used to hold the current shape being drawn
   * with the pencil tool. Once the mouse is released, this shape is saved 
   * into the map for GeneralPath objects.
   */
  private final GeneralPath my_draw_shape = new GeneralPath();
   

  
  private Image img;
  
  private double Scalar = 1.0;
  private int translateX;
  private int translateY;
  private int width = 3825;
  private int height = 3464;
  private int newW;
  private int newH;
  private int my_drag_start_x;
  private int my_drag_start_y;
  

  
  private List<CanvasImage> picturesToDrawList;
  
  
  /**
   * Constructor used to create the JPanel used for drawing on.
   * This constructor sets the main size for the panel, sets
   * the color, and adds the mouse listeners to the panel
   * upon initialization.
   */
  public PowerPaintDrawPanel() 
  {
    super();
    picturesToDrawList = new ArrayList<CanvasImage>();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
    // load the background image
    try {
    	File sourceimage = new File("src/images/chernarus_xlarge_updated_ver1.png");
      img = ImageIO.read(sourceimage);
    } catch(IOException e) {
      e.printStackTrace();
    }
    this.addMouseListener(new MyMouseListener());
    this.addMouseWheelListener(new MyMouseListener());
    this.addMouseMotionListener(new MyMouseMotionListener());
    newW = (int)(width * Scalar);
    newH = (int)(height * Scalar);
	translateX = (this.getWidth() / 2) - (newW / 2);
	translateY = (this.getHeight() / 2) - (newH / 2);
  }
  
  /**
   * PaintComponent used to draw the Graphics2D objects onto
   * the panel. This method will react in various ways
   * depending on what tool is chosen through either the menu
   * or the tool bar.
   * @param the_graphics Graphics object passed into the method,
   * then casted into a Graphics2D object.
   */
  @Override
  public void paintComponent(final Graphics the_graphics) 
  {
    super.paintComponent(the_graphics);
    ((Graphics2D) the_graphics).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    
    //Now lets Draw the map
    the_graphics.drawImage(img, translateX, translateY, newW, newH, null);


    final Graphics2D graphics2d = (Graphics2D) the_graphics;
    graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2d.setStroke(new BasicStroke(my_brush_thickness));
    graphics2d.setColor(PowerPaintColor.getColor());
    
    //calls this method to draw previously drawn shapes that are stored in maps
    parseMap(graphics2d);
    
    //calls this method to draw currently in progress shapes
    drawToolBeingUsed(graphics2d);
    if (my_drawgrid)
    {
      createVerticalLines();
      createHorizontalLines();
      graphics2d.setStroke(new BasicStroke(1));
      for (int i = 0; i < my_grid_array.size(); i++)
      {
        final int [] coordinates = my_grid_array.get(i);  
        graphics2d.setColor(Color.BLACK); 
        graphics2d.drawLine(coordinates[0], coordinates[1], 
                            coordinates[2], coordinates[INDEX_THREE]);
      }
      graphics2d.setStroke(new BasicStroke(my_brush_thickness));
      my_grid_array.clear();
    }

    if(!picturesToDrawList.isEmpty()) {
    	String thefilePath;
        ListIterator<CanvasImage> litr = picturesToDrawList.listIterator();
        while(litr.hasNext()) {
        	CanvasImage temp = litr.next();
        	if(temp.getType() == 1) {
        		thefilePath = "src/images/player_large.png";
        	} else if(temp.getType() == 2) {
        		thefilePath = "src/images/destination_large.png";
        	} else if(temp.getType() == 3) {
        		thefilePath = "src/images/enemy_large.png";
        	} else if(temp.getType() == 4) {
        		thefilePath = "src/images/group_large.png";
        	} else if(temp.getType() == 5) {
         		thefilePath = "src/images/friendly.png";
         	}else {
        		thefilePath = "src/images/error_large.png";
        	}
        	File sourceimage = new File(thefilePath);
        	Image canvasImage = null;
            try {
            	canvasImage = ImageIO.read(sourceimage);
            	the_graphics.drawImage(canvasImage, temp.getX(), temp.getY(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }
    
  }
  
  /**
   * Primary  method used to store the pencil type shapes that
   * are being undone with the UNDO button. It then removes these
   * elements from the various collections and repaints by 
   * calling the 3 helper methods depending on the shape type.
   */
  public void undoItems()
  {
    if ((my_last_tool_type.get(my_last_tool_type.size() - 1).equals(MY_ELLIPSE) ||
        my_last_tool_type.get(my_last_tool_type.size() - 1).equals(MY_RECTANGLE)) &&
        !my_shapes_array.isEmpty() && !my_shapes_map.isEmpty())
    {
      undoRectangles();
    }
    else if (my_last_tool_type.get(my_last_tool_type.size() - 1).equals(MY_LINE) &&
          !my_line_array.isEmpty() && !my_line_map.isEmpty())
    {
      undoLine();

    }
    else
    {
      undoPencil();
    }
    undoWrapUp();
    PowerPaintGui.changeEditMenuItemAvailable("redo", true);
    // not worth making this a field because of this warning
    
  }

  /**
   * Primary  method used to restore previous shapes.
   */
  public void redoItems()
  {
    // Removed a large portion of the redo code because I ran out of time.
    //checkEllipseRedo();
    //checkRectangleRedo();
    //checkLineRedo();
    //checkPencilRedo();
    redoWrapUp();
    
  }
  

    
  /**
   * Helper method used to store the rectangle and ellipse type shapes that
   * are being undone with the UNDO button. It then removes these
   * elements from the various collections and repaints.
   */
  private void undoRectangles()
  {
    my_undo_shapes_map.put(my_shapes_array.get(my_shapes_array.size() - 1), 
      my_shapes_map.get(my_shapes_array.get(my_shapes_array.size() - 1)));
    my_undo_shapes_thickness_map.put(my_shapes_array.get(my_shapes_array.size() - 1), 
       my_shapes_thickness_map.get(my_shapes_array.get(my_shapes_array.size() - 1)));
    my_shapes_map.remove(my_shapes_array.get(my_shapes_array.size() - 1));
    my_shapes_thickness_map.remove(my_shapes_array.get(my_shapes_array.size() - 1));
    my_shapes_array.remove(my_shapes_array.size() - 1);
    my_last_tool_type.remove(my_last_tool_type.size() - 1);
    repaint();
  }
  
  /**
   * Helper method used to store the line type shapes that
   * are being undone with the UNDO button. It then removes these
   * elements from the various collections and repaints.
   */
  private void undoLine()
  {
    my_undo_line_map.put(my_line_array.get(my_line_array.size() - 1), 
      my_line_map.get(my_line_array.get(my_line_array.size() - 1)));
    my_undo_line_thickness_map.put(my_line_array.get(my_line_array.size() - 1), 
      my_line_thickness_map.get(my_line_array.get(my_line_array.size() - 1)));
    my_line_map.remove(my_line_array.get(my_line_array.size() - 1));
    my_line_thickness_map.remove(my_line_array.get(my_line_array.size() - 1));
    my_line_array.remove(my_line_array.size() - 1);
    my_last_tool_type.remove(my_last_tool_type.size() - 1);
    repaint();
  }
  
  /**
   * Helper method used to store the pencil type shapes that
   * are being undone with the UNDO button. It then removes these
   * elements from the various collections and repaints.
   */
  private void undoPencil()
  {
    if (my_last_tool_type.get(my_last_tool_type.size() - 1).equals(MY_PENCIL) &&
        !my_path_array.isEmpty() && !my_pencil_map.isEmpty())
    {
      my_undo_pencil_map.put(my_path_array.get(my_path_array.size() - 1), 
        my_pencil_map.get(my_path_array.get(my_path_array.size() - 1)));
      my_undo_pencil_thickness_map.put(my_path_array.get(my_path_array.size() - 1), 
         my_pencil_thickness_map.get(my_path_array.get(my_path_array.size() - 1)));
      my_pencil_map.remove(my_path_array.get(my_path_array.size() - 1));
      my_pencil_thickness_map.remove(my_path_array.get(my_path_array.size() - 1));
      my_path_array.remove(my_path_array.size() - 1);
      my_last_tool_type.remove(my_last_tool_type.size() - 1);
      repaint();
    }
  }
  
  /**
   * Helper method used to check if there are any other undo options
   * available. If not and all the shape arrays are empty, it calls
   * the GUI to set the undo menu item to false.
   */
  private void undoWrapUp()
  {
    if (my_shapes_array.isEmpty() && 
        my_path_array.isEmpty() &&
        my_line_array.isEmpty())
    {
      PowerPaintGui.changeEditMenuItemAvailable("undo", false);
   // not worth making this a field because of this warning
    }
  }
  
  /**
   * Helper method used to check if there are any other redo options
   * available. If not and all the undo shape arrays are empty, it calls
   * the GUI to set the undo menu item to false.
   */
  private void redoWrapUp()
  {
    if (my_undo_shapes_array.isEmpty() && 
        my_undo_path_array.isEmpty() &&
        my_undo_line_array.isEmpty())
    {
      PowerPaintGui.changeEditMenuItemAvailable("redo", false);
    }
  }

  /**
   * Helper method used to draw the Graphics2D objects onto
   * the panel for the current shape being drawn. Each will check
   * for the tool being used, and then also check to be sure 
   * the user is still actively draw as to no redraw the most
   * recent shape.
   * @param the_graphics2d Graphics2d object passed into the method,
   * then used to draw the current shape in progress.
   */
  private void drawToolBeingUsed(final Graphics2D the_graphics2d)
  {
    /*Each check below checks to see if the user is currently drawing and that this 
      certain tool is selected.
    */
    if (PowerPaintGui.getSelectedTool().equals(MY_ELLIPSE) &&
        my_currently_drawing)
    {
      the_graphics2d.setStroke(new BasicStroke(my_brush_thickness));
      the_graphics2d.setColor(PowerPaintColor.getColor()); 
      the_graphics2d.draw(drawShape(my_ellipse_shape));
    }
    else if (PowerPaintGui.getSelectedTool().equals(MY_LINE) &&
        my_currently_drawing)
    {
      the_graphics2d.setStroke(new BasicStroke(my_brush_thickness));
      the_graphics2d.setColor(PowerPaintColor.getColor()); 
      the_graphics2d.drawLine(my_start_x, my_start_y, my_current_x, my_current_y);
    }
    else if (PowerPaintGui.getSelectedTool().equals(MY_RECTANGLE) &&
        my_currently_drawing)
    {
      the_graphics2d.setStroke(new BasicStroke(my_brush_thickness));
      the_graphics2d.setColor(PowerPaintColor.getColor()); 
      the_graphics2d.draw(drawShape(my_rectangle_shape));
    }
    else  if (PowerPaintGui.getSelectedTool().equals(MY_PENCIL) &&
        my_currently_drawing)
    {
      the_graphics2d.setStroke(new BasicStroke(my_brush_thickness));
      the_graphics2d.setColor(PowerPaintColor.getColor()); 
      my_draw_shape.moveTo(my_current_x, my_current_y);
      my_draw_shape.lineTo(my_previous_x, my_previous_y);
      the_graphics2d.draw(my_draw_shape);
    }
  }
  
  /**
   * Helper method used to draw the Graphics2D objects onto
   * the panel after parsing through the maps for the previous shapes
   * drawn. It will then draw these shapes.
   * @param the_graphics2d Graphics2d object passed into the method,
   * then used to draw the various shapes stored in the maps.
   */
  private void parseMap(final Graphics2D the_graphics2d)
  {
    /*Parses through the maps for each type of shape/tool type.
     *The shapes are stored in a Map with shapes/color
     *Then a list of integer arrays containing coordinates of the 
     *various pieces are iterated through.
    */
    for (int i = 0; i < my_shapes_array.size(); i++)
    {
      final Color temp_color = my_shapes_map.get(my_shapes_array.get(i));
      final int temp_thickness = my_shapes_thickness_map.get(my_shapes_array.get(i));
      the_graphics2d.setStroke(new BasicStroke(temp_thickness));
      the_graphics2d.setColor(temp_color);
      the_graphics2d.draw(my_shapes_array.get(i));
    }
    for (int i = 0; i < my_path_array.size(); i++)
    {
      final Color temp_color = my_pencil_map.get(my_path_array.get(i));
      final int temp_thickness = my_pencil_thickness_map.get(my_path_array.get(i));
      the_graphics2d.setStroke(new BasicStroke(temp_thickness));
      the_graphics2d.setColor(temp_color);
      the_graphics2d.draw(my_path_array.get(i));
    }
    for (int i = 0; i < my_line_array.size(); i++)
    {
      final Color temp_color = my_line_map.get(my_line_array.get(i));
      final int temp_thickness = my_line_thickness_map.get(my_line_array.get(i));
      the_graphics2d.setStroke(new BasicStroke(temp_thickness));
      the_graphics2d.setColor(temp_color);
      final int [] coordinates = my_line_array.get(i);      
      the_graphics2d.drawLine(coordinates[0], coordinates[1], 
                          coordinates[2], coordinates[INDEX_THREE]);
    }
  }
  
  /**
   * Helper method used to create the vertical lines for the Grid
   * options. When called it will create and array of vertical lines
   * to be drawn later by the paintComponent method.
   */
  private void createVerticalLines()
  {
    int x_counter = GRID_SPACING - 1;
    final int y_counter = 0 - 0;
    int index_counter = 0;
    final int panel_max_width = this.getWidth();
    final int panel_max_height = this.getHeight();
    while (x_counter < panel_max_width)
    {
      my_gridpoint_array[index_counter] = x_counter;
      index_counter++;   
      my_gridpoint_array[index_counter] = y_counter;
      index_counter++;   
      my_gridpoint_array[index_counter] = x_counter;
      index_counter++;   
      my_gridpoint_array[index_counter] = panel_max_height - 1;
      index_counter++;
      my_grid_array.add(my_gridpoint_array.clone());
      index_counter = 0;
      x_counter = x_counter + GRID_SPACING;
    }
  }
  
  /**
   * Helper method used to create the horizontal lines for the Grid
   * options. When called it will create and array of vertical lines
   * to be drawn later by the paintComponent method.
   */
  private void createHorizontalLines()
  {
    final int x_counter = 0 - 0;
    int y_counter = GRID_SPACING - 1;
    int index_counter = 0;
    final int panel_max_width = this.getWidth();
    final int panel_max_height = this.getHeight();
    while (y_counter < panel_max_height)
    {
      my_gridpoint_array[index_counter] = x_counter;
      index_counter++;   
      my_gridpoint_array[index_counter] = y_counter;
      index_counter++;   
      my_gridpoint_array[index_counter] = panel_max_width - 1;
      index_counter++;   
      my_gridpoint_array[index_counter] = y_counter;
      index_counter++;
      my_grid_array.add(my_gridpoint_array.clone());
      index_counter = 0;
      y_counter = y_counter + GRID_SPACING;
    }
  }
  
  /**
   * Helper method used to clear the panel contents and all
   * the various maps used to store previous shapes. This 
   * method calls the clearCoordinates method as well to reset
   * those fields to points outside the bounds of the panel.
   */
  public void clearDrawArea()
  {
    my_shapes_array.clear();
    my_path_array.clear();
    my_line_array.clear();
    my_draw_shape.reset();
    my_shapes_map.clear();
    my_pencil_map.clear();
    my_line_map.clear();
    my_shapes_thickness_map.clear();
    my_pencil_thickness_map.clear();
    my_line_thickness_map.clear();
    clearCoordinates();
    picturesToDrawList.clear();
    repaint();
  }
  
  /**
   * Helper method used to decide which way the current rectangle
   * shape is being drawn to. It will react depending on the starting
   * X and Y coordinates, and the ending X and Y coordinates. 
   * @param the_shape Shape used to set the frame of the shape.
   * @return RectangularShape passed back to the calling method
   * after the modifications are done on the shape.
  */
  private RectangularShape drawShape(final RectangularShape the_shape)
  {
    /*The checks below are to determine which way the user is dragging
     * the mouse and to react appropriately.
    */
    
    // DOWN AND TO THE RIGHT
    if (my_start_x < my_current_x && my_start_y < my_current_y)
    {
      the_shape.setFrame(my_start_x, my_start_y, 
                         my_current_x - my_start_x, 
                         my_current_y - my_start_y);
    }
    
    // UP AND TO THE RIGHT
    else if (my_start_x < my_current_x && my_start_y > my_current_y)
    {
      the_shape.setFrame(my_start_x, my_current_y, 
                         my_current_x - my_start_x, 
                         my_start_y - my_current_y);
    }
    
    // DOWN AND TO THE LEFT
    else if (my_start_x > my_current_x && my_start_y < my_current_y)
    {
      the_shape.setFrame(my_current_x, my_start_y, 
                         my_start_x - my_current_x, 
                         my_current_y - my_start_y);
    }
    
    // UP AND TO THE LEFT
    else
    {
      the_shape.setFrame(my_current_x, my_current_y, 
                         my_start_x - my_current_x, 
                         my_start_y - my_current_y);
    }
    return the_shape;
    
  }
  
  /**
   * Helper method used to reset the various fields to
   * out of bound states so they are not drawn on the visible
   * panel.
   */
  public void clearCoordinates()
  {
    my_current_x = OUT_OF_BOUNDS_AREA;
    my_current_y = OUT_OF_BOUNDS_AREA;
    my_start_x = OUT_OF_BOUNDS_AREA;
    my_start_x = OUT_OF_BOUNDS_AREA;
    my_previous_x = OUT_OF_BOUNDS_AREA;
    my_previous_y = OUT_OF_BOUNDS_AREA;
    my_new_x = OUT_OF_BOUNDS_AREA;
    my_new_y = OUT_OF_BOUNDS_AREA;
  }
  
  /**
   * Public method used to set the boolean value
   * to true depending on if the Grid option was selected. This method will set
   * the option to true.
   */
  public void drawGrid()
  {
    my_drawgrid = true;
    repaint();
  }
  
  /**
   * Public method used to set the boolean value
   * to false depending on if the Grid option was unselected. This method will set
   * the option to false.
   */
  public void unDrawGrid()
  {
    my_drawgrid = false;
    repaint();
  }
  
  /**
   * Public method used to set the thickness of the brush
   * when it is changed inside the menu.
   * @param the_thickness selected value of the thickness
   * sent in from the menu class.
   */
  public void changeThickness(final int the_thickness)
  {
    my_brush_thickness = the_thickness;
  }

  /**
   * This is the MouseMotionAdapter inner class used
   * to detect mouse drags.
   */
  private class MyMouseMotionListener extends MouseMotionAdapter 
  {
    @Override
    public void mouseDragged(final MouseEvent the_event)
    {
      my_previous_x = my_new_x;
      my_previous_y = my_new_y;
      my_current_x = the_event.getX();
      my_current_y = the_event.getY();
      if (PowerPaintGui.getSelectedTool().equals("Drag")) {
          translateX -= my_drag_start_x - my_current_x;
          translateY -= my_drag_start_y - my_current_y;
          my_drag_start_x = my_current_x;
          my_drag_start_y = my_current_y;
      }
      my_new_x = my_current_x;
      my_new_y = my_current_y;

      repaint();    
    }
  }
  
  /**
   * This is the mouseAdapter inner class used
   * to detect mouse clicks and actions.
   */
  private class MyMouseListener extends MouseAdapter 
  {
    /**
     * When the mouse is pressed this Listener will set the
     * start x and start y coordinates. It will also check
     * to see if the tool is the pencil tool, if it is
     * the method will set the fields for the new x and new y
     * to be used by the paint component. Lastly, this method
     * will set the boolean field to show that a draw is in 
     * progress. 
     * @param the_event the mouse event
     */
    @Override
    public void mousePressed(final MouseEvent the_event)
    {
      my_start_x = the_event.getX();
      my_start_y = the_event.getY();
      if (PowerPaintGui.getSelectedTool().equals(MY_PENCIL))
      {
        my_new_x = my_start_x;
        my_new_y = my_start_y;
        my_draw_shape.reset();
      } else if(PowerPaintGui.getSelectedTool().equals("Drag")) {
    	  my_drag_start_x = the_event.getX();
    	  my_drag_start_y = the_event.getY();
      }
      my_currently_drawing = true;
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent the_event)
    {
    	int scrollCount = -(the_event.getWheelRotation());
        my_current_x = the_event.getX();
        my_current_y = the_event.getY();
    	if(scrollCount > 0.1) { 
    		if(Scalar < 2) {
            	Scalar += scrollCount * 0.03;
            	translateX -= 65;
            	translateY -= 75;
    		}
    	} else {
    		if(Scalar > 0.1) {
            	Scalar += scrollCount * 0.03;
            	translateX += 65;
            	translateY += 75;
    		}
    	}
    	
        newW = (int)(width * Scalar);
        newH = (int)(height * Scalar);
        
    	//translateX = (referenceToThis.getWidth() / 2) - (newW / 2);
    	//translateY = (referenceToThis.getHeight() / 2) - (newH / 2);
    	//translateX = (referenceToThis.getWidth() / 2) - (my_current_x + (newW / 2));
    	//translateY = (referenceToThis.getHeight() / 2) - (my_current_y + (newH / 2));
    	repaint();
    }
    
    
    /**
     * When the mouse is released this Listener will set the
     * current x and current y coordinates. It will also check
     * to see tool is being used and store that shaped inside 
     * the map for the appropriate shape type. Lastly, this method
     * will set the boolean field to show that a draw is not in 
     * progress any longer eliminating redundant drawing in the
     * paint component method. 
     * @param the_event the mouse event
     */
    @Override
    public void mouseReleased(final MouseEvent the_event)
    {
      my_current_x = the_event.getX();
      my_current_y = the_event.getY();
      if (PowerPaintGui.getSelectedTool().equals(MY_ELLIPSE))
      {
        final RectangularShape ellipse_shape = 
            (RectangularShape) drawShape(my_ellipse_shape).clone();
        my_shapes_array.add(ellipse_shape);
        my_shapes_map.put(ellipse_shape, PowerPaintColor.getColor());
        my_shapes_thickness_map.put(ellipse_shape, my_brush_thickness);
      }
      else if (PowerPaintGui.getSelectedTool().equals(MY_LINE))
      {
        my_point_array[0] =  my_start_x; 
        my_point_array[1] =  my_start_y; 
        my_point_array[2] =  my_current_x; 
        my_point_array[INDEX_THREE] =  my_current_y; 
        final int[] point_array = my_point_array.clone();
        my_line_array.add(point_array);
        my_line_map.put(point_array, PowerPaintColor.getColor());
        my_line_thickness_map.put(point_array, my_brush_thickness);
      }
      else if (PowerPaintGui.getSelectedTool().equals(MY_RECTANGLE))
      {
        final RectangularShape rectangle_shape = 
            (RectangularShape) drawShape(my_rectangle_shape).clone();
        my_shapes_array.add(rectangle_shape);
        my_shapes_map.put(rectangle_shape, PowerPaintColor.getColor());
        my_shapes_thickness_map.put(rectangle_shape, my_brush_thickness);
        
        //1 = Player, 2 = Destination, 3 = Enemy, 4 = Group , 5 = friendly
      } else if(PowerPaintGui.getSelectedTool().equals("Destination") ||
    		  PowerPaintGui.getSelectedTool().equals("Enemy") ||
    		  PowerPaintGui.getSelectedTool().equals("Group") ||
    		  PowerPaintGui.getSelectedTool().equals("Friendly") ||
    		  PowerPaintGui.getSelectedTool().equals("Player")) {
    	  int type;
    	  if(PowerPaintGui.getSelectedTool().equals("Player")) {
    		  type = 1;
    	  } else if(PowerPaintGui.getSelectedTool().equals("Destination")) {
    		  type = 2;
    	  } else if(PowerPaintGui.getSelectedTool().equals("Enemy")) {
    		  type = 3;
    	  } else if(PowerPaintGui.getSelectedTool().equals("Friendly")) {
    		  type = 5;
    	  }else { //default is group
    		  type = 4;
    	  }
    	  picturesToDrawList.add(new CanvasImage(type, the_event.getX()-35, the_event.getY()-35));
    	  
      }
      else
      {
        final GeneralPath draw_shape = 
            (GeneralPath) my_draw_shape.clone();
        my_path_array.add(draw_shape);
        my_pencil_map.put(draw_shape, PowerPaintColor.getColor());
        my_pencil_thickness_map.put(draw_shape, my_brush_thickness);
      }
      clearCoordinates();
      repaint();
      my_currently_drawing = false;
      my_last_tool_type.add(PowerPaintGui.getSelectedTool());
      PowerPaintGui.changeEditMenuItemAvailable("undo", true);
    }
    
  }
}
