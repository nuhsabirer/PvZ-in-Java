package entity.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pea extends Projectile {

	public Pea(double startX, double startY) {
		super(startX, startY);
		
		// STATS
		this.speed = 10.0; // Moves relatively fast
		this.damage = 20;  // Takes 10 peas to kill a 200HP Normal Zombie
		
		// SIZE & HITBOX
		this.width = 28;
		this.height = 28;
		this.xOffset = 0;
		this.yOffset = 0;
		
		// Load the sprite
		try {
			this.sprite = ImageIO.read(getClass().getResource("/projectile/ProjectilePea.png"));
		} catch (IOException e) {
			System.err.println("Could not load Pea sprite!");
			e.printStackTrace();
		}
		
		// Set initial hitbox
		updateHitBox();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (isActive && sprite != null) {
			g2.drawImage(sprite, (int) x, (int) y, width, height, null);
		}
	}
}