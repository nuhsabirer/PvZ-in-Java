package entity.plant;

import java.awt.image.BufferedImage;
import main.GamePanel; // NEW IMPORT
import entity.projectile.Pea; // NEW IMPORT

public class Peashooter extends Plant {
	
	private BufferedImage[] idleFrames;
	private int currentFrame = 0;
	private int frameTick = 0;
	private final int ANIM_SPEED = 2; 
	
	private int shootTimer = 0; // NEW: Timer to track shooting
	
	// Notice we added GamePanel gp here!
	public Peashooter(GamePanel gp, double startX, double startY) {
		super(gp, startX, startY);
		
		this.cost = 100;
		this.maxHealth = 300; 
		this.currentHealth = maxHealth;
		
		this.width = 10;
		this.height = 50;
		this.xOffset = 150;
		this.yOffset = 80;
		
		this.x -= this.xOffset;
		this.y -= this.yOffset;
		
		updateHitBox();
		
		idleFrames = loadSheet("/peashooter/peashooter-idle-ss.png", 30, 5, 6);
		
		if (idleFrames != null) {
			this.sprite = idleFrames[0];
		}
	}
	
	@Override
	public void update() {
		// 1. Process Idle Animation
		if (idleFrames != null && idleFrames.length > 0) {
			frameTick++;
			if (frameTick >= ANIM_SPEED) {
				frameTick = 0;
				currentFrame++;
				if (currentFrame >= idleFrames.length) {
					currentFrame = 0;
				}
				this.sprite = idleFrames[currentFrame];
			}
		}
		
		// 2. Shooting Logic (30 FPS, so 45 ticks = 1.5 seconds)
		shootTimer++;
		if (shootTimer >= 45) { 
			// Spawn a Pea! We offset the x and y slightly so it comes out of the mouth
			gp.projectiles.add(new Pea(this.x + this.xOffset + 20, this.y + this.yOffset + 5)); 
			shootTimer = 0; 
		}
	}
}