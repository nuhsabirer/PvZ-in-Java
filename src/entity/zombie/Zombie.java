package entity.zombie;

import java.awt.Graphics2D;
import entity.LivingEntity;

public abstract class Zombie extends LivingEntity {
	
	// FSM for a zombie's 4 possible states
	public enum State {
		IDLE, WALKING, EATING, DYING
	}
	
	public int damage;	// Bite damage of a zombie
	public State currentState;
	
	/**
	 * Creates a zombie.
	 * @param startX zombie's top left corner's x value
	 * @param startY zombie's top left corner's y value
	 */
	public Zombie(double startX, double startY) {
		this.x = startX;
		this.y = startY;
		this.currentState = State.IDLE;
	}
	
	@Override
	public void update() {
		if (currentState == State.WALKING) {
			x -= speed;
		}
		updateHitBox();
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (sprite != null) {
			// Drawn at native size (250x200) since the array slices are perfectly sized
			g2.drawImage(sprite, (int) x, (int)y, null);
			
			// Hitbox visualizer (TODO remove)
			// g2.drawRect(this.hitBox.x, this.hitBox.y, this.hitBox.width, this.hitBox.height);
		}
	}
}