package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity {
	
	// Coordinates (using double for smooth, fractional movement)
	public double x, y;
	
	public int xOffset, yOffset;
	
	// Dimensions
	public int width, height;
	
	// Stats
	public double speed;
	// public int maxHealth;		(moved to LivingEntity.java)
	// public int currentHealth;	(moved to LivingEntity.java)
	
	// Rendering and Collision
	public Image sprite;	// GIFs are used in this project
	public Rectangle hitBox;
	
	public Entity() {
		// Initialize the hitbox so we don't get NullPointerExceptions later
		hitBox = new Rectangle(0, 0, 0, 0);
	}
	
	// Abstract methods that every subclass MUST implement
	public abstract void update();
	
	public abstract void draw(Graphics2D g2);
	
	// A helper method to keep the invisible hitbox synced with the visual sprite
	public void updateHitBox() {
		hitBox.setBounds((int)x + xOffset, (int)y + yOffset, width, height);
		
	}
}