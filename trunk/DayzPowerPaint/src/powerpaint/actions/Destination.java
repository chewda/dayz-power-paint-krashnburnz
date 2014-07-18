package powerpaint.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import powerpaint.gui.PowerPaintGui;

public class Destination extends AbstractAction{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   * Constructor used to create the Destination Action Object. It calls to its super class
	   * passing in the text "Color" and the Icon chosen for the line button.
	   * It then sets up the mnemonics for the button along with whether
	   * this object is selected by default.
	   * @param the_text Text for the button
	   * @param the_icon Image icon used for Object
	   * @param the_description description of the button Object
	   * @param the_mnemonic mnemonic to setup the shortcut
	   */
	  public Destination(final String the_text, final ImageIcon the_icon,
	          final String the_description, 
	          final Integer the_mnemonic) 
	  {
	  super(the_text, the_icon);
	    putValue(SHORT_DESCRIPTION, the_description);
	    putValue(MNEMONIC_KEY, the_mnemonic);
	    putValue(SELECTED_KEY, false);
	  }
	  
	  /**
	   * ActionPerformed method used to set the selected tool when the
	   * button is clicked, and to clear the coordinates so the tool
	   * can draw when the user uses it.
	   * @param the_event action event passed in.
	   */
	  @Override
	  public void actionPerformed(final ActionEvent the_event)
	  {
	    PowerPaintGui.setSelectedTool("Destination");
	    PowerPaintGui.clearCoordinates();
	  }
}
