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
	private ArrayList<Point> points = new ArrayList<Point>();
	private boolean displayGrid;
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.GREEN);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
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
		g.setColor(Color.RED);
		if (point.getX() != 0){
			g.drawRect((int)point.getX(), (int)point.getY(), 60, 30);
		}
		for (int i = 0; i < points.size(); i++){
			g.fillRect((int)points.get(i).getX(), (int)points.get(i).getY(), 60, 30);
		}
		g.setColor(Color.BLACK);
		for (int i = 0; i < points.size(); i++){
			g.drawRect((int)points.get(i).getX()-1, (int)points.get(i).getY()-1, 60, 30);
			g.drawRect((int)points.get(i).getX()-2, (int)points.get(i).getY()-2, 60, 30);
			g.drawRect((int)points.get(i).getX()-3, (int)points.get(i).getY()-3, 60, 30);
			g.drawString("Home", (int)points.get(i).getX()+12, (int)points.get(i).getY()+17);
		}
	}
	
	public void setPoint(Point p){
		point = p;
	}
	
	public void setClicked(Point p){
		point = p;
		points.add(p);
	}
	
	public void setDisplayGrid(boolean b){
		displayGrid = b;
	}
}
