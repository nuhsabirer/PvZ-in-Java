package entity.plant;

import java.awt.Graphics2D;
import entity.LivingEntity;
import main.GamePanel;

public abstract class Plant extends LivingEntity {
	
	protected int cost;	
	protected GamePanel gp;
	
	public Plant(GamePanel gp, double startX, double startY) {
		this.gp = gp; // Store the GamePanel
		this.x = startX;
		this.y = startY;
		this.speed = 0;	
	}
	
	@Override
	public void update() {
		// Specific plants will override this
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (sprite != null) {
			g2.drawImage(sprite, (int) x, (int)y, null);
		}
	}
}