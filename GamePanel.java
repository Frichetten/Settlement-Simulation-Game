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
	private ArrayList<Point> homes = new ArrayList<Point>();
	private ArrayList<Point> farms = new ArrayList<Point>();
	private boolean displayGrid;
	
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
		
		//Drawing Homes
		g.setColor(Color.RED);
		if (point.getX() != 0){
			g.drawRect((int)point.getX(), (int)point.getY(), 60, 30);
		}
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
	}
	
	public void setPoint(Point p){
		point = p;
	}
	
	public void setHomes(Point p){
		point = p;
		homes.add(p);
	}
	
	public void setFarms(Point p){
		point = p;
		farms.add(p);
	}
	
	public void setDisplayGrid(boolean b){
		displayGrid = b;
	}
}
