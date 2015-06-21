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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputListener;


/**
 * @author Nicholas Frichette
 * This class is the "client" of the game meaning that it is the frame
 * that everything runs in.
 */
public class Client extends JFrame implements ActionListener {
	
	private GamePanel gameScreen;
	private Point point;
	private boolean running = true;
	private boolean buildHome = false;
	private boolean buildFarm = false;
	private boolean displayGrid = false;
	private int gold = 5000;
	private int food = 250;
	private int people = 50;
	private int totalPeople = 8;
	private JPanel topPanel = new JPanel();
	private JTextArea goldCounter;
	private JButton Options;
	private Container client;
	
	/**
	 * Building the frame for the game
	 */
	public Client(){
		
		//Building the basics of the "Client" The frame the player interacts in
		super("Client");
		client = getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setLayout(new BorderLayout());
		setSize(1000,600);
		
		//Creating the top panel that houses information such as money/food
		topPanel.setLayout(new BorderLayout());
		goldCounter = new JTextArea("  Gold: " + gold + "  Food: " + food +
				"  People: " + people + "/" + totalPeople + "  ");
		JButton options = new JButton("Options");
		topPanel.add(options, BorderLayout.WEST);
		topPanel.add(goldCounter, BorderLayout.EAST);
		options.setOpaque(false);
		topPanel.setBackground(Color.GREEN);
		goldCounter.setBackground(Color.GREEN);
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		goldCounter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		//Creating the orderPanel, which will house player build choices
		JPanel orderPanel = new JPanel();
		orderPanel.setLayout(new GridLayout(2,2));
		JButton home = new JButton("Home");
		JButton farm = new JButton("Farm");
		JButton barracks = new JButton("Barracks");
		JButton well = new JButton("Well");
		orderPanel.add(well);
		orderPanel.add(barracks);
		orderPanel.add(home);
		orderPanel.add(farm);
		farm.setOpaque(false);
		home.setOpaque(false);
		barracks.setOpaque(false);
		well.setOpaque(false);
		
		//Creating the unitPanel which will hold the orderPanel as well
		//as the box that displays info to the player.
		JPanel unitPanel = new JPanel();
		unitPanel.setLayout(new GridLayout(1,3));
		JButton playerInfo = new JButton("");
		playerInfo.setRolloverEnabled(false);
		unitPanel.add(playerInfo);
		unitPanel.add(orderPanel);
		playerInfo.setOpaque(false);
		unitPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		//Creating the gamescreen where the actual gameplay takes place.
		gameScreen = new GamePanel();
		MouseMotionDetector mouser = new MouseMotionDetector();
		gameScreen.addMouseListener(mouser);
		gameScreen.addMouseMotionListener(mouser);
		
		//Adding actionlistener to all buttons
		options.addActionListener(this);
		home.addActionListener(this);
		farm.addActionListener(this);
		
		//Adding the gameScreen, topPanel, and unitPanel all to the client window
		client.add(topPanel, BorderLayout.NORTH);
		client.add(gameScreen, BorderLayout.CENTER);
		client.add(unitPanel, BorderLayout.SOUTH);
		
		//Button on Hover for Home
		home.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 100 Gold, 20 Food  Creates: 5 people");
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
		
		//Button on Hover for Farm
		farm.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 50 Gold, 3 People  Creates: 20 Food");
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
	}
	
	/**
	 * This method creates the thread that the game runs in
	 */
	public void runGameLoop(){
		Thread loop = new Thread(){
			public void run(){
				gameLoop();
			}
		};
		loop.start();
	}
	
	/**
	 * This method is called whenever there is a change to the players income.
	 * Whether positively or negatively
	 */
	public void updateTopPanel(){
		goldCounter = new JTextArea("  Gold: " + gold + "  Food: " + food +
				"  People: " + people + "/" + totalPeople + "  ");
		topPanel.removeAll();
		gameScreen.remove(topPanel);
		topPanel.setLayout(new BorderLayout());
		JButton option = new JButton("Options");
		option.addActionListener(this);
		topPanel.add(option, BorderLayout.WEST);
		topPanel.add(goldCounter, BorderLayout.EAST);
		client.add(topPanel, BorderLayout.NORTH);
		goldCounter.setBackground(Color.GREEN);
		goldCounter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
				gameScreen.paintImmediately(client.getBounds());;
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
		else if (e.getActionCommand().equals("Options")){
			System.out.println("Accessing Options");
			int select = JOptionPane.showConfirmDialog(null,"Exit?", "",JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.YES_OPTION)
				System.exit(1);
		}
		else if(e.getActionCommand().equals("Stop")){
			running = !running;
			System.out.println("Stopping FPS");
		}
		else if (e.getActionCommand().equals("Home")){
			if (displayGrid && buildFarm)
				;
			else{
				displayGrid = !displayGrid;
				gameScreen.setDisplayGrid(displayGrid);
			}
			negateStructures();
			buildHome = !buildHome;
			gameScreen.setIsStructure("home");
		}
		else if (e.getActionCommand().equals("Farm")){
			if (displayGrid && buildHome)
				;
			else {
				displayGrid = !displayGrid;
				gameScreen.setDisplayGrid(displayGrid);
			}
			negateStructures();
			buildFarm = !buildFarm;
			gameScreen.setIsStructure("farm");
		}
		
	}
	
	/**
	 * This method will "reset" which structure is being selected for building
	 */
	private void negateStructures(){
		buildFarm = false;
		buildHome = false;
	}
	
	public class MouseMotionDetector implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (displayGrid && buildHome && gold >= 100 && food >= 20){
				gold -= 100;
				food -= 20;
				people += 5;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setHomes(point);
			}
			else if (displayGrid && buildFarm && gold >= 50 && people >= 3){
				gold -= 50;
				people -= 3;
				food += 20;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setFarms(point);
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
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (buildHome){
				point = new Point(e.getX(),e.getY());
				gameScreen.setPoint(point);
			}
			else if (buildFarm){
				point = new Point(e.getX(),e.getY());
				gameScreen.setPoint(point);
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
