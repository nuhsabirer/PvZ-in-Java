package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	//	Screen Properties
	public final int screenWidth = 1400, screenHeight = 600;
	public final int fps = 30;
	
	//	Thread
	Thread gameThread;
	
	//	Background Picture
	private BufferedImage pVz_background;
	
	public GamePanel() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.BLUE);
		setDoubleBuffered(true);
		setFocusable(true);
		
		try {
			pVz_background = ImageIO.read(getClass().getResource("/pVz_background.png"));
		} catch(IOException | IllegalArgumentException exc) {
			System.err.println("Background image could not be read.");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(pVz_background, 0, 0, null);
		
		g2.dispose();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		//	Game loop that runs ${fps} times every second.
		double drawInterval = 1_000_000_000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				//	1: UPDATE
				update();
				//	2: DRAW
				repaint();
				Toolkit.getDefaultToolkit().sync();	//	Check README.txt for the reason behind this line.
				delta--;
			}
			try {
				Thread.sleep(1);	//	Here to prevent the while loop to run billions of times every second.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		//	Updating the values of elements such as their states, coordinates will happen here.
	}
}
