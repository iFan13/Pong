package Tennis;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class LegallyDistinctPong extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1000, HEIGHT = WIDTH * 9/16; //modify to 16:9 aspect ratio
	
	public boolean running = false; 
	private Thread gameThread;
	
	private Ball ball;
	private Paddle paddleL;
	private Paddle paddleR;
	
	private MainMenu menu;
	
	//Constructor
	public LegallyDistinctPong()  {
		
		canvasSetup();
		
		new Window("Legally Distinct Pong",this);
		
		initialize();
		
		this.addKeyListener(new KeyIn(paddleL, paddleR));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);
	}

	
	private void initialize() {
		// create ball
		ball = new Ball();
				
		// create paddles
		paddleL = new Paddle(Color.cyan, true); //true = left
		paddleR = new Paddle(Color.orange, false); //false = right
		
		// create main menu
		menu = new MainMenu(this);
		
	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
	}

	
	//Game Loop
	@Override
	public void run() {
		this.requestFocus();
		
		//game timer
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if (delta >=1) {
				update();
				delta--;
				draw();
				frames++;
			}
			
			if (System.currentTimeMillis()-timer > 1000) {
				timer+=1000;
				System.out.println("FPS: "+frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void draw() {
		// initialize drawing tools
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return; 
		}
		Graphics g = buffer.getDrawGraphics();
						
		// draw background
		drawBackground(g);
		
		
		// draw main menu contents
		if (menu.active)
			menu.draw(g);
		
		
		// draw paddle & score
		paddleL.draw(g);
		paddleR.draw(g);

		// draw ball
		ball.draw(g);
				
		// dispose (actually draw)
		g.dispose();
		buffer.show();
	}

	public synchronized void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running= true;
	}
	
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void drawBackground(Graphics g) {
		
		//ground
		g.setColor(Color.black);
		g.fillRect(0,0, WIDTH, HEIGHT);
		
		//net
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {10}, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
	}

	private void update() {
		if(!menu.active) { //only update if menu not on screen.
			// update ball
			ball.update(paddleL, paddleR);
		
			// update paddles
			paddleL.update(ball);
			paddleR.update(ball);
		}
	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;
		return 1;
	}

	//set boundaries
	public static int ensureRange(int value, int min, int max) {
		return Math.min(Math.max(value, min),max);
	}
	
	
	public static void main(String[] args) {
		new LegallyDistinctPong();
	}
	
}
