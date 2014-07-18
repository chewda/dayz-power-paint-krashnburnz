package powerpaint.guicomponents;

public class CanvasImage{
	
	private int my_x;
	private int my_y;
	private int my_type;
	
	public CanvasImage(int type, int x, int y) {
		my_type = type;
		setCoords(x, y);
	}
	public void setCoords(int x, int y) {
		my_x = x;
		my_y = y;
	}
	
	public int getX() {
		return my_x;
	}
	
	public int getY() {
		return my_y;
	}
	
	public int getType() {
		return my_type;
	}
}
