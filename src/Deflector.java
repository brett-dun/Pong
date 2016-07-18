import java.awt.Color;

public class Deflector {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	
	public Deflector(int x, int y, int width, int height) {
		this(x, y, width, height, Color.CYAN);
	}
	
	public Deflector(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void moveUp(int distance) {
		this.y -= distance;
	}
	
	public void moveDown(int distance, int max) {
		this.y += distance;
	}
	
	public void moveLeft(int distance) {
		this.x -= distance;
	}
	
	public void moveRight(int distance, int max) {
		this.x += distance;
	}
	
	public void checkBounds(int maxWidth, int maxHeight) {
		if(this.x < 0)
			this.x = 0;
		if(this.x > maxWidth-this.width)
			this.x = maxWidth-this.width;
		if(this.y < 0)
			this.y = 0;
		if(this.y > maxHeight-this.height)
			this.y = maxHeight-this.height;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	

}
