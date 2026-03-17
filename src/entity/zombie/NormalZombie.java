package entity.zombie;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class NormalZombie extends Zombie {
	
	// Sprite Sheet Arrays
	// Each sheet is 6x5, containing 30 frames
	private BufferedImage[] idleFrames, walkFrames, eatFrames, deathFrames;
	
	// Animation Tracking
	private int currentFrame = 0;
	private int frameTick = 0;
	private State previousState;    // To detect when we switch actions
	
	public NormalZombie(double startX, double startY) {
		super(startX, startY);
		
		// STATS
		this.maxHealth = 200;
		this.currentHealth = maxHealth;
		this.speed = 0.5;
		this.damage = 10;
		
		// SIZE
		this.width = 100;
		this.height = 100;
		this.xOffset = 100;
		this.yOffset = 100;
		
		this.previousState = this.currentState;
		
		loadAnimations();
		
		// Set the starting visual
		if (idleFrames != null) {
			this.sprite = idleFrames[0];
		}
	}
	
	private void loadAnimations() {
		// Each spritesheet has exactly 6 rows and 5 columns.
		walkFrames 	= loadSheet("/normalZombie/zombie-walk-ss.png", 30, 5, 6);
		idleFrames  = loadSheet("/normalZombie/zombie-idle-ss.png", 30, 5, 6); 
		eatFrames   = loadSheet("/normalZombie/zombie-eat-ss.png", 30, 5, 6); 
		deathFrames = loadSheet("/normalZombie/zombie-death-ss.png", 30, 5, 6); 
	}
	
	

	@Override
	public void update() {
		// 1. Check if the state just changed
		if (currentState != previousState) {
			currentFrame = 0; 
			frameTick = 0;
			previousState = currentState;
		}
		
		// 2. Figure out the array AND the speed for the current state
		BufferedImage[] currentAnimation = null;
		int currentAnimSpeed = 2; // Default speed fallback
		
		switch (currentState) {
			case IDLE:    
				currentAnimation = idleFrames;  
				currentAnimSpeed = 3; // Increased: Plays slower
				break;
			case WALKING: 
				currentAnimation = walkFrames;  
				currentAnimSpeed = 4; // Increased: Plays slower (Tweak this to match his movement!)
				break;
			case EATING:  
				currentAnimation = eatFrames;   
				currentAnimSpeed = 2; // Kept fast
				break;
			case DYING:   
				currentAnimation = deathFrames; 
				currentAnimSpeed = 2; // Kept fast
				break;
		}
		
		// 3. Process the animation frame swapping using the custom speed
		if (currentAnimation != null && currentAnimation.length > 0) {
			frameTick++;
			if (frameTick >= currentAnimSpeed) { // Uses the state-specific speed!
				frameTick = 0;
				currentFrame++;
				
				// Loop the animation if it hits the end
				if (currentFrame >= currentAnimation.length) {
					currentFrame = 0; 
				}
				this.sprite = currentAnimation[currentFrame];
			}
		}
		// 4. Call super.update() so the base Zombie class handles x -= speed
		super.update();
	}
}