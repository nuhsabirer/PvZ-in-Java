package entity.zombie;

import java.awt.Graphics2D;
import entity.LivingEntity;

public abstract class Zombie extends LivingEntity {
	
	// Finite state machine for zombie animations & activity
	public enum State {
		IDLE, WALKING, EATING, DYING
	}
	
	// Zombie-specific stats
	public int damage;       	// How much health it takes from a plant per bite
	public State currentState;
	
	public Zombie(double startX, double startY) {
		this.x = startX;
		this.y = startY;
		this.currentState = State.WALKING;
	}
	
	@Override
	public void update() {
		
		// Only move to the left if the state is WALKING
		if (currentState == State.WALKING) {
			x -= speed;
		}
		
		// Always update the physical collision box to match the visual coordinates
		updateHitBox();
	}
	
	@Override
	public void draw(Graphics2D g2) {
		// A universal drawing method for all zombies
		if (sprite != null) {
			g2.drawImage(sprite, (int) x, (int)y, 250, 200, null);
			g2.drawRect(this.hitBox.x, this.hitBox.y, this.hitBox.width, this.hitBox.height);
		}
	}
}