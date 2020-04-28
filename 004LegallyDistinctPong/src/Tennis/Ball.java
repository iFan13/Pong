package Tennis;

import java.awt.*;

public class Ball {

	public static final int SIZE = 16;
	private int x,y; 
	private int xVel, yVel; //direction -> 1 or -1 for up/down
	private int speed = 5;
	
	public Ball() {
		reset();
	}

	private void reset() { //set initial position & velocity
		// initial position
		x = LegallyDistinctPong.WIDTH/2 - SIZE/2;
		y = LegallyDistinctPong.HEIGHT/2 - SIZE/2;
		
		// initial velocity
		xVel = LegallyDistinctPong.sign(Math.random()*2.0-1);
		yVel = LegallyDistinctPong.sign(Math.random()*2.0-1);
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,y, SIZE, SIZE);
		// TODO Auto-generated method stub
		
	}

	public void update(Paddle p1, Paddle p2) {
		//update movement
		x += xVel * speed;
		y += yVel * speed;
		
		//collisions
		
		//ceiling or floor
		if (y+SIZE >= LegallyDistinctPong.HEIGHT|| y<=0)
			changeYDir();
		
		//end zone walls

		if (x+SIZE >= LegallyDistinctPong.WIDTH) {	//hits right side wall
			p1.addPoint();
			reset();
		}
		
		if (x<=0) { //hits left side wall
			p2.addPoint();
			reset();
		}
		
	}
	
	public void changeYDir() {
		yVel *=-1;
	}
	public void changeXDir() {
		xVel *=-1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
