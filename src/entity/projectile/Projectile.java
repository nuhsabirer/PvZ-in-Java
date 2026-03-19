package entity.projectile;

import entity.Entity;

public abstract class Projectile extends Entity {
	
	public int damage;
	public boolean isActive = true; // We use this to safely remove it when it hits a zombie or goes off-screen
	
	public Projectile(double startX, double startY) {
		this.x = startX;
		this.y = startY;
	}
	
	@Override
	public void update() {
		// projectiles move left to right
		x += speed; 
		
		updateHitBox();
		
		// If it flies completely off the screen, mark it for deletion to save memory
		// FIXME (Assuming screenWidth is 1400 based on your GamePanel)
		if (x > 1400) { 
			isActive = false;
		}
	}
}