package Tennis;

import java.awt.*;

public class Paddle {
	private int x,y;
	private int vel = 0;
	private int speed = 10;
	private int width = 22, height = 85;
	private int pad = 10;
	private int score = 0 ;
	private Color color;
	private boolean left;
	
	public Paddle(Color c, boolean left) {
		this.color = c;
		this.left = left;
		
		//initialize position
		
		if (left)
			x = pad;
		else
			x = LegallyDistinctPong.WIDTH - width-pad;
		y = LegallyDistinctPong.HEIGHT/2-height/2;
		
	}

	public void addPoint() {
		score++;
	}

	public void draw(Graphics g) {
		//draw paddle
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		//draw score
		int sx;
		String scoreText = Integer.toString(score);
		Font font = new Font("Arial", Font.PLAIN, 50);
				
		int strWidth = g.getFontMetrics(font).stringWidth(scoreText);
		int strHeight = g.getFontMetrics(font).getHeight();
		int padding = 25;
		
		if (left)
			sx = LegallyDistinctPong.WIDTH/2- padding - strWidth;
		else
			sx = LegallyDistinctPong.WIDTH/2+padding;
		
		g.setFont(font);
		g.drawString(scoreText, sx, strHeight);
	}

	public void update(Ball b) {
		//update position
		y = LegallyDistinctPong.ensureRange(y+=vel,0, LegallyDistinctPong.HEIGHT-height);
		int ballX = b.getX();
		int ballY = b.getY();
		
		//collisions w ball
		if (left) {
			if (ballX <= width+pad && ballY+Ball.SIZE >= y && ballY <= y+height) 
				b.changeXDir();
			
		}
		else {
			if (ballX + Ball.SIZE >= LegallyDistinctPong.WIDTH-width-pad && ballY + Ball.SIZE >= y && ballY <= y+height)
				b.changeXDir();
		}
		
	}

	public void switchDirection(int direction) {
		vel = speed * direction;		
	}
	
	public void stop() {
		vel = 0;
	}
	
	
}
