package entity.zombie;

import java.awt.Image;

import javax.swing.ImageIcon;

public class NormalZombie extends Zombie {
	
	// Hold the 4 specific GIFs for this zombie
	private Image idleAnim, walkAnim, eatAnim, deathAnim;

	public NormalZombie(double startX, double startY) {
		super(startX, startY);
		
		this.maxHealth = 200;
		this.currentHealth = maxHealth;
		this.speed = 1;
		this.damage = 10;
		
		this.width = 100;
		this.height = 100;
		
		this.xOffset = 100;
		this.yOffset = 100;
		
		loadAnimations();
		
		// Set the starting visual
		this.sprite = idleAnim; 
	}
	
	private void loadAnimations() {
		try {
			// Using ImageIcon to natively support the GIF animation loops
			idleAnim 	= new ImageIcon(getClass().getResource("/normalZombie/zombie-idle.gif")).getImage();
			walkAnim 	= new ImageIcon(getClass().getResource("/normalZombie/zombie-walk.gif")).getImage();
			eatAnim 	= new ImageIcon(getClass().getResource("/normalZombie/zombie-eat.gif")).getImage();
			deathAnim 	= new ImageIcon(getClass().getResource("/normalZombie/zombie-death.gif")).getImage();
		} catch (Exception e) {
			System.err.println("Could not load Normal Zombie animations.");
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// 1. Swap the visual sprite based on the current state
		switch (currentState) {
			case IDLE:
				sprite = idleAnim;
				break;
			case WALKING:
				sprite = walkAnim;
				break;
			case EATING:
				sprite = eatAnim;
				break;
			case DYING:
				sprite = deathAnim;
				break;
		}
		
		// 2. Call super.update() so the base Zombie class handles the actual x -= speed math
		super.update();
	}
}