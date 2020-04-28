package Tennis;

import java.awt.event.*;


public class KeyIn extends KeyAdapter {
	private Paddle player1;
	private boolean upL= false;
	private boolean downL = false;
	private Paddle player2;
	private boolean upR = false;
	private boolean downR = false;
	
	public KeyIn(Paddle pd1, Paddle pd2) {
		player1 = pd1;
		player2 = pd2;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			player1.switchDirection(-1);
			upL = true;
		}
		if (key == KeyEvent.VK_S) {
			player1.switchDirection(1);
			downL = true;
		}
		if (key == KeyEvent.VK_UP) {
			player2.switchDirection(-1);
			upR = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			player2.switchDirection(1);
			downR = true;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			upL = false;
		}
		if (key == KeyEvent.VK_S) {
			downL = false;
		}
		if (key == KeyEvent.VK_UP) {
			upR = false;
		}
		if (key == KeyEvent.VK_DOWN) {
			downR = false;
		}
		
		
		if (!upL && !downL)
			player1.stop();
		if (!upR && !downR)
			player2.stop();
	}
}
