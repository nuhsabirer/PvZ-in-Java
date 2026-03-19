package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {
	
	// Coordinates (using double for smooth, fractional movement)
	public double x, y;
	public int xOffset, yOffset;
	
	// Dimensions
	public int width, height;
	
	// Stats
	public double speed;
	
	// Rendering and Collision
	public BufferedImage sprite; // Will be extracted from a sheet
	public Rectangle hitBox;
	
	public Entity() {
		hitBox = new Rectangle(0, 0, 0, 0); // Initializing this to prevent null pointer exceptions
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g2);
	
	/**
	 * Forces the hitbox to stay on the zombie.
	 * 
	 * Hitbox is drawn around the legs of a zombie so that we can simplify the
	 * collision logic of bullets (only bullets that are on the same line with the
	 * zombie should damage it)
	 * 
	 * For plants, hitbox is drawn as a thin wall in front of the plant. Offsets and
	 * heights are tweaked so that no hitbox will collide with other
	 * 
	 * For projectiles, they have the same coordinates and size as the visible projecile,
	 * but rectangle instead of circle
	 * 
	 * Offset is used to draw the hitbox over the legs
	 */
	public void updateHitBox() {
		hitBox.setBounds((int)x + xOffset, (int)y + yOffset, width, height);
	}
	
	/**
	 * Universal collision detector to be used with other entities' hitboxes
	 * @param other the other Entity to check collision with
	 * @return true if hitboxes collide, false otherwise
	 */
	public boolean collidesWith(Entity other) {
		if (this.hitBox != null && other.hitBox != null) {
			return this.hitBox.intersects(other.hitBox);
		}
		return false;
	}
}