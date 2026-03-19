package entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public abstract class LivingEntity extends Entity {
	
	// Health stats
	public int maxHealth, currentHealth;
	
	// Universal method for taking damage
	public void takeDamage(int damage) {
		currentHealth -= damage;
	}
	
	// Universal method to check if the entity should be removed from the playground
	public boolean isDead() {
		return currentHealth <= 0;
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
	
}
