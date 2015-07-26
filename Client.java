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
import javax.swing.SwingUtilities;
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
	private boolean buildWall = false;
	private boolean buildMarket = false;
	private boolean displayGrid = false;
	private int gold = 500;
	private int food = 50;
	private int people = 5;
	private JPanel topPanel = new JPanel();
	private JTextArea goldCounter;
	private JButton market;
	private JButton playerInfo;
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
				"  Unemployed People: " + people + " ");
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
		market = new JButton("Market");
		JButton wall = new JButton("Wall");
		orderPanel.add(wall);
		orderPanel.add(market);
		orderPanel.add(home);
		orderPanel.add(farm);
		farm.setOpaque(false);
		home.setOpaque(false);
		market.setOpaque(false);
		wall.setOpaque(false);
		
		//Creating the unitPanel which will hold the orderPanel as well
		//as the box that displays info to the player.
		JPanel unitPanel = new JPanel();
		unitPanel.setLayout(new GridLayout(1,3));
		playerInfo = new JButton("");
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
		wall.addActionListener(this);
		market.addActionListener(this);
		
		//Adding the gameScreen, topPanel, and unitPanel all to the client window
		client.add(topPanel, BorderLayout.NORTH);
		client.add(gameScreen, BorderLayout.CENTER);
		client.add(unitPanel, BorderLayout.SOUTH);
		
		//Button on Hover for Home
		home.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 50 Gold, 15 Food  Creates: 15 people");
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
		
		//Button on Hover for Farm
		farm.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 50 Gold, 5 People  Creates: 25 Food");
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
		
		//Button on Hover for wall
		wall.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 10 Gold  Creates: A "
						+ "defensive perimeter");
			}
			public void mouseExited(MouseEvent e){
				playerInfo.setText("");
			}
		});
		
		//Button on Hover for market
		market.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				playerInfo.setText("Costs: 25 People  Creates: "
						+ "750 Gold");
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
				"  Unemployed People: " + people + " ");
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
		long originalMS = System.currentTimeMillis();
		long MSCounter = System.currentTimeMillis();
				
		while(running){
			if (System.currentTimeMillis() >= MSCounter + 15){
				gameScreen.paintImmediately(client.getBounds());;
				MSCounter = System.currentTimeMillis();
			}
			if (originalMS + 1000 <= MSCounter){
				MSCounter = System.currentTimeMillis();
				originalMS = System.currentTimeMillis();
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Options")){
			System.out.println("Accessing Options");
			int select = JOptionPane.showConfirmDialog(null,"Exit?", "",JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.YES_OPTION)
				System.exit(1);
		}
		else if (e.getActionCommand().equals("Home")){
			if (displayGrid && buildFarm || buildWall || buildMarket)
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
			if (displayGrid && buildHome || buildWall || buildMarket)
				;
			else {
				displayGrid = !displayGrid;
				gameScreen.setDisplayGrid(displayGrid);
			}
			negateStructures();
			buildFarm = !buildFarm;
			gameScreen.setIsStructure("farm");
		}
		else if (e.getActionCommand().equals("Wall")){
			if (displayGrid && buildFarm || buildHome || buildMarket)
				;
			else {
				displayGrid = !displayGrid;
				gameScreen.setDisplayGrid(displayGrid);
			}
			negateStructures();
			buildWall = !buildWall;
			gameScreen.setIsStructure("wall");
		}
		else if (e.getActionCommand().equals("Market")){
			if (displayGrid && buildFarm || buildHome || buildWall)
				;
			else {
				displayGrid = !displayGrid;
				gameScreen.setDisplayGrid(displayGrid);
			}
			negateStructures();
			buildMarket = !buildMarket;
			gameScreen.setIsStructure("market");
		}
	}
	
	/**
	 * This method will "reset" which structure is being selected for building
	 */
	private void negateStructures(){
		buildFarm = false;
		buildHome = false;
		buildWall = false;
		buildMarket = false;
	}
	
	private class MouseMotionDetector implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (displayGrid && buildHome && gold >= 50 && food >= 15){
				gold -= 50;
				food -= 15;
				people += 15;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setHomes(point);
			}
			else if (displayGrid && buildFarm && gold >= 50 && people >= 5){
				gold -= 50;
				people -= 5;
				food += 25;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setFarms(point);
			}
			else if (displayGrid && buildWall && gold >= 10){
				gold -= 10;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setWalls(point);
			}
			else if (displayGrid && buildMarket && people >= 25){
				people -= 25;
				gold += 750;
				updateTopPanel();
				point = new Point(e.getX(),e.getY());
				gameScreen.setMarket(point);
			}
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
			else if (buildWall){
				point = new Point(e.getX(),e.getY());
				gameScreen.setPoint(point);
			}
			else if (buildMarket){
				point = new Point(e.getX(),e.getY());
				gameScreen.setPoint(point);
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseDragged(MouseEvent e) {
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
