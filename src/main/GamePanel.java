package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList; // Added this import
import entity.projectile.Projectile;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.zombie.Zombie; // Added this import
import entity.plant.Peashooter;
import entity.plant.Plant;
import entity.zombie.NormalZombie; // Added this import

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Screen Properties
	public final int screenWidth = 1400, screenHeight = 600;
	public final int fps = 30;
	
	// Thread
	Thread gameThread;
	
	// Background Picture
	private BufferedImage bgElements, currentBg;
	
	// --- NEW: Entity Lists and Timers ---
	private ArrayList<Zombie> zombies = new ArrayList<>();
	private ArrayList<Plant> plants = new ArrayList<>();
	public ArrayList<Projectile> projectiles = new ArrayList<>();
	
	public GamePanel() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.BLUE);
		setDoubleBuffered(true);
		setFocusable(true);
		
		try {
			bgElements = ImageIO.read(getClass().getResource("/pvz_bg_elements.png"));
			currentBg = bgElements.getSubimage(0, 0, screenWidth, screenHeight);	
		} catch(IOException | IllegalArgumentException e) {
			System.err.println("Background image could not be read.");
			e.printStackTrace();
		}
		
		// Spawn our test actor on the top lane, right side of the screen
		zombies.add(new NormalZombie(1000, 80));
		// Update the Peashooter spawn to pass "this" (the GamePanel itself)
		plants.add(new Peashooter(this, 320, 80));	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		// Draw background first, then projectiles, then plants and then zombies.
		// This way everything looks natural
		
		g2.drawImage(currentBg, 0, 0, null);
		
		for(Projectile p : projectiles) {
			p.draw(g2);
		}
		
		for(Plant p : plants) {
			p.draw(g2);
		}
		
		for(Zombie z : zombies) {
			z.draw(g2);
		}
		
		
		g2.dispose();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	/**
	 * Game loop is handled here. FPS and game loop is based on 30 ticks per seocnd.
	 */
	public void run() {
		double drawInterval = 1_000_000_000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				Toolkit.getDefaultToolkit().sync();	
				delta--;
			}
			try {
				Thread.sleep(1);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This method updates each Entity on the field and removes them if needed.
	 * Every iteration had to be done with a backwards for loop (starting from the end)
	 * 
	 * Reason is, when I tried to do this with a normal for-each loop or a forwards loop,
	 * I kept getting ConcurrentModificationException or out of bounds exception.
	 * Doing it this way will prevent both, since on each iteration the size() is checked.
	 */
	private void update() {
		
		// 1. UPDATE PROJECTILES & CHECK HITS
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile p = projectiles.get(i);
			p.update();
			
			// Check if this pea hit any zombie
			for (Zombie z : zombies) {
				if (p.collidesWith(z)) {
					z.takeDamage(p.damage); // Zombie takes damage
					p.isActive = false;     // Pea breaks
					break;                  // A pea can only hit one zombie!
				}
			}
			
			if (!p.isActive) {
				projectiles.remove(i);
			}
		}
		
		// 2. UPDATE ZOMBIES & CHECK EATING
		for (int i = zombies.size() - 1; i >= 0; i--) {
			Zombie z = zombies.get(i);
			boolean isEating = false;
			
			// Check if zombie has reached any plant
			for (Plant plant : plants) {
				if (z.collidesWith(plant)) {
					isEating = true;
					z.currentState = Zombie.State.EATING;
					
					// TODO later: Add an eating timer so it doesn't do damage every single frame
					plant.takeDamage(1); 
					break;
				}
			}
			
			if (!isEating) {
				z.currentState = Zombie.State.WALKING;
			}
			
			z.update();
			
			// If the zombie died from the peas, remove it!
			// TODO later: Trigger DYING animation before removing
			if (z.isDead()) {
				zombies.remove(i);
			}
		}
		
		// 3. UPDATE PLANTS & HANDLE DEATH
		for (int i = plants.size() - 1; i >= 0; i--) {
			Plant p = plants.get(i);
			p.update();
			
			// If the zombie ate the plant completely, remove it!
			if (p.isDead()) {
				plants.remove(i);
			}
		}
	}
}