import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Nicholas Frichette
 *
 */
public class GamePanel extends JPanel {
	
	private Point point = new Point(0,0);
	private Point point1 = new Point(0,0);
	private Point point2 = new Point(0,0);
	private ArrayList<Point> homes = new ArrayList<Point>();
	private ArrayList<Point> farms = new ArrayList<Point>();
	private ArrayList<Point> walls = new ArrayList<Point>();
	private ArrayList<Point> barracks = new ArrayList<Point>();
	private boolean displayGrid;
	private String isStructure = "";
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.GREEN);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		//Displays the build grid
		if (displayGrid){
			g.setColor(Color.GRAY);
			for (int i = 0; i < this.getWidth(); i++){
				g.drawLine(i,0,i,this.getHeight());
				i += 29;
			}
			for (int i = 0; i < this.getHeight(); i++){
				g.drawLine(0,i,this.getWidth(),i);
				i += 29;
			}
		}
		
		//Drawing Current Selection tied to mouse
		if (isStructure.equals("home") && displayGrid){
			g.setColor(Color.RED);
			int x = (int)point.getX()/30;
			int y = (int)point.getY()/30;
			g.drawRect(x*30, y*30, 60, 30);
		}
		else if (isStructure.equals("farm") && displayGrid){
			g.setColor(Color.BLUE);
			int x = (int)point.getX()/30;
			int y = (int)point.getY()/30;
			g.drawRect(x*30, y*30, 60, 60);
		}
		else if (isStructure.equals("wall") && displayGrid){
			g.setColor(Color.black);
			int x = (int)point.getX()/30;
			int y = (int)point.getY()/30;
			g.drawRect(x*30, y*30, 30, 30);
		}
		else if (isStructure.equals("barracks") && displayGrid){
			g.setColor(Color.WHITE);
			int x = (int)point.getX()/30;
			int y = (int)point.getY()/30;
			g.drawRect(x*30, y*30, 120, 60);
			g.drawRect((int)point.getX(), (int)point.getY(), 120, 60);
		}
		else {
			g.setColor(Color.YELLOW);
			int width = (int) (point2.getX()-point1.getX());
			int height = (int) (point2.getY()-point1.getY());
			g.fillRect((int)point1.getX(), (int)point.getY(), width, height);
		}
		
		//Drawing Homes
		g.setColor(Color.RED);
		for (int i = 0; i < homes.size(); i++){
			g.fillRect((int)homes.get(i).getX(), (int)homes.get(i).getY(), 60, 30);
		}
		g.setColor(Color.BLACK);
		for (int i = 0; i < homes.size(); i++){
			g.drawRect((int)homes.get(i).getX()-1, (int)homes.get(i).getY()-1, 60, 30);
			g.drawRect((int)homes.get(i).getX()-2, (int)homes.get(i).getY()-2, 60, 30);
			g.drawRect((int)homes.get(i).getX()-3, (int)homes.get(i).getY()-3, 60, 30);
			g.drawString("Home", (int)homes.get(i).getX()+12, (int)homes.get(i).getY()+17);
		}
		
		//Drawing Farms
		g.setColor(Color.BLUE);
		for (int i = 0; i < farms.size(); i++){
			g.fillRect((int)farms.get(i).getX(), (int)farms.get(i).getY(), 60, 60);
		}
		g.setColor(Color.BLACK);
		for (int i = 0; i < farms.size(); i++){
			g.drawRect((int)farms.get(i).getX()-1, (int)farms.get(i).getY()-1, 60, 60);
			g.drawRect((int)farms.get(i).getX()-2, (int)farms.get(i).getY()-2, 60, 60);
			g.drawRect((int)farms.get(i).getX()-3, (int)farms.get(i).getY()-3, 60, 60);
			g.drawString("Farm", (int)farms.get(i).getX()+15, (int)farms.get(i).getY()+30);
		}
		
		//Drawing Walls
		g.setColor(Color.black);
		for (int i = 0; i < walls.size(); i++){
			g.fillRect((int)walls.get(i).getX(), (int)walls.get(i).getY(), 30, 30);
		}
		
		//Drawing Barracks
		g.setColor(Color.WHITE);
		for (int i = 0; i < barracks.size(); i++){
			g.fillRect((int)barracks.get(i).getX(), (int)barracks.get(i).getY(), 120, 60);
		}
		g.setColor(Color.BLACK);
		for (int i = 0; i < barracks.size(); i++){
			g.drawRect((int)barracks.get(i).getX()-1, (int)barracks.get(i).getY()-1, 120, 60);
			g.drawRect((int)barracks.get(i).getX()-2, (int)barracks.get(i).getY()-2, 120, 60);
			g.drawRect((int)barracks.get(i).getX()-3, (int)barracks.get(i).getY()-3, 120, 60);
			g.drawString("Barracks", (int)barracks.get(i).getX()+33, (int)barracks.get(i).getY()+30);
		}
		
	}
	
	public void setPoint(Point p){
		point = p;
	}
	
	public void setHomes(Point p){
		point = p;
		int x = (int)p.getX()/30;
		int y = (int)p.getY()/30;
		Point pnt = new Point(x*30,y*30);
		homes.add(pnt);
	}
	
	public void setFarms(Point p){
		point = p;
		int x = (int)p.getX()/30;
		int y = (int)p.getY()/30;
		Point pnt = new Point(x*30,y*30);
		farms.add(pnt);
	}
	
	public void setWalls(Point p){
		point = p;
		int x = (int)p.getX()/30;
		int y = (int)p.getY()/30;
		Point pnt = new Point(x*30,y*30);
		walls.add(pnt);
	}
	
	public void setBarracks(Point p){
		point = p;
		int x = (int)p.getX()/30;
		int y = (int)p.getY()/30;
		Point pnt = new Point(x*30,y*30);
		barracks.add(pnt);
	}
	
	public void setBox(Point p1, Point p2){
		point1 = p1;
		point2 = p2;
	}
	
	public void setDisplayGrid(boolean b){
		displayGrid = b;
	}
	
	public void setIsStructure(String s){
		isStructure = s;
	}
}
