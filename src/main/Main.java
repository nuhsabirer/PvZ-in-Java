package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("PvZ");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		GamePanel playPanel = new GamePanel();
		mainFrame.add(playPanel);
		playPanel.startGameThread();
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}
