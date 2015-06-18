import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputListener;


/**
 * @author Nicholas Frichette
 *	This class is the "client" of the game meaning that it is the frame
 * that everything runs in.
 */
public class Client extends JFrame implements ActionListener {
	private boolean running = true;
	private GamePanel gameScreen;
	private Point point;
	private boolean buildHome = false;
	private boolean displayGrid = false;
	private int gold = 500;
	private JPanel topPanel = new JPanel();
	private JTextArea goldCounter;
	private JButton options;
	private Container client;
	
	/**
	 * Building the panel for the game
	 */
	public Client(){
		super("Client");
		client = getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setLayout(new BorderLayout());
		setSize(1000,600);
		
		//Creating the top panel that houses information such as money, food
		//JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		goldCounter = new JTextArea("Gold: " + String.valueOf(gold) + "   ");
		JButton options = new JButton("Options");
		topPanel.add(options, BorderLayout.WEST);
		topPanel.add(goldCounter, BorderLayout.EAST);
		topPanel.setBackground(Color.GREEN);
		goldCounter.setBackground(Color.GREEN);
		
		JPanel orderPanel = new JPanel();
		orderPanel.setLayout(new GridLayout(2,2));
		JButton home = new JButton("Home");
		JButton farm = new JButton("Farm");
		JButton barracks = new JButton("Barracks");
		JButton well = new JButton("Well");
		orderPanel.add(well);
		orderPanel.add(barracks);
		orderPanel.add(home);
		home.addActionListener(this);
		orderPanel.add(farm);
		
		JPanel unitPanel = new JPanel();
		unitPanel.setLayout(new GridLayout(1,3));
		JButton one = new JButton("Start");
		JButton playerInfo = new JButton("");
		JButton three = new JButton("Stop");
		unitPanel.add(one);
		unitPanel.add(playerInfo);
		one.addActionListener(this);
		three.addActionListener(this);
		
		unitPanel.add(orderPanel);
		
		gameScreen = new GamePanel();
		MouseMotionDetector mouser = new MouseMotionDetector();
		gameScreen.addMouseListener(mouser);
		gameScreen.addMouseMotionListener(mouser);
		
		client.add(topPanel, BorderLayout.NORTH);
		client.add(gameScreen, BorderLayout.CENTER);
		client.add(unitPanel, BorderLayout.SOUTH);
		
		//Button on Hover
		home.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 100G, 20 Food  Creates: 5 people");
				
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
		
	}
	
	public void runGameLoop(){
		Thread loop = new Thread(){
			public void run(){
				gameLoop();
			}
		};
		loop.start();
	}
	
	public void updateTopPanel(){
		goldCounter = new JTextArea("Gold: " + String.valueOf(gold) + "   ");
		topPanel.removeAll();
		gameScreen.remove(topPanel);
		topPanel.setLayout(new BorderLayout());
		topPanel.add(new JButton("options"), BorderLayout.WEST);
		topPanel.add(goldCounter, BorderLayout.EAST);
		client.add(topPanel, BorderLayout.NORTH);
		goldCounter.setBackground(Color.GREEN);
		client.revalidate();
		client.repaint();
	}
	
	private void gameLoop(){
		//This is where the loop will be. This includes both the repainting
		//of the game panel and the updates to the panel and the internal
		//information.
		int FPSCounter = 0;
		long originalMS = System.currentTimeMillis();
		long MSCounter = System.currentTimeMillis();
				
		while(running){
			if (System.currentTimeMillis() >= MSCounter + 15){
				gameScreen.paintImmediately(getBounds());;
				FPSCounter++;
				MSCounter = System.currentTimeMillis();
			}
			if (originalMS + 1000 <= MSCounter){
				System.out.println(FPSCounter);
				FPSCounter = 0;
				MSCounter = System.currentTimeMillis();
				originalMS = System.currentTimeMillis();
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")){
			System.out.println("Starting FPS");
			running = !running;
			if (running){
				runGameLoop();
			}
		}
		else if(e.getActionCommand().equals("Stop")){
			running = !running;
			System.out.println("Stopping FPS");
		}
		else if (e.getActionCommand().equals("Home")){
			displayGrid = !displayGrid;
			gameScreen.setDisplayGrid(displayGrid);
			buildHome = !buildHome;
		}
		
	}
	
	public class MouseMotionDetector implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (buildHome && gold > 0){
				gold -= 100;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setClicked(point);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			point = new Point(e.getX(),e.getY());
			gameScreen.setPoint(point);
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
//			point = new Point(e.getX(),e.getY());
//			gameScreen.setPoint(point);
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (buildHome){
				point = new Point(e.getX(),e.getY());
				gameScreen.setPoint(point);
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
