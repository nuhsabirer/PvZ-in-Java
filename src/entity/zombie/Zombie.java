package entity.zombie;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.LivingEntity;

public abstract class Zombie extends LivingEntity {
	
	// FSM for a zombie's 4 possible states
	public enum State {
		IDLE, WALKING, EATING, DYING
	}
	
	public int damage;	// Bite damage of a zombie
	public State currentState;
	
	public Zombie(double startX, double startY) {
		this.x = startX;
		this.y = startY;
		this.currentState = State.IDLE;
	}
	
	/**
	 * Extracts frames from a sprite sheet, saves each frame into a
	 * BufferedImage array and returns it.
	 * 
	 * @param path filepath of the spritesheet
	 * @param totalFrames amount of frames in the spritesheet, 30 for this project
	 * @param cols column amount of the sheet
	 * @param rows row amount of the sheet
	 * @return a BufferedImage array containing extracted frames
	 */
	public BufferedImage[] loadSheet(String path, int totalFrames, int cols, int rows) {
		BufferedImage[] frames = new BufferedImage[totalFrames];
		try {
			BufferedImage sheet = ImageIO.read(getClass().getResource(path));
			
			// SIZE OF FRAMES TO BE EXTRACTED
			// For this project, I kept every frame 250x200 for all zombies.
			// For more versatility, frameWidth and frameHeight can be taken as arguments.
			int frameWidth = 250;
			int frameHeight = 200;
			int count = 0;
			
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					if (count < totalFrames) {
						frames[count] = sheet.getSubimage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
						count++;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Could not load sprite sheet: " + path);
			e.printStackTrace();
		}
		return frames;
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