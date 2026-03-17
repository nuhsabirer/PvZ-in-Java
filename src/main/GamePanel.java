package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList; // Added this import

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.zombie.Zombie; // Added this import
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
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		// 1. Draw Background
		g2.drawImage(currentBg, 0, 0, null);
		
		// 2. Draw all Zombies (after the background so they appear on top)
		for (Zombie z : zombies) {
			z.draw(g2);
		}
		
		g2.dispose();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
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
	
	private void update() {
		for (Zombie z : zombies) {
			
			z.currentState = Zombie.State.WALKING;
			/*
			// The Director's Script (30 frames = 1 second)
			if (testFrameCounter < 60) {
				// 0 to 2 seconds
				z.currentState = Zombie.State.IDLE;
			} 
			else if (testFrameCounter < 210) {
				// 2 to 7 seconds (5 seconds total)
				z.currentState = Zombie.State.WALKING;
			} 
			else if (testFrameCounter < 360) {
				// 7 to 12 seconds (5 seconds total)
				z.currentState = Zombie.State.EATING;
			} 
			else {
				// 12+ seconds
				z.currentState = Zombie.State.DYING;
			}
			*/
			// Tell the zombie to process its movement and hitboxes
			z.update();
		}
	}
}