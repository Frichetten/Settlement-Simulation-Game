import java.awt.Point;

/**
 * 
 */

/**
 * @author Nicholas Frichette
 * This class should contain all the calculations necessary to allow the
 * Soldier units to travel around the map as well as keep track their 
 * positions. In addition it should also house their collision detection
 * and combat systems. I can already tell this is going to be super 
 * complex. Yay.
 *
 */
public class Soldier {
	private Point location;
	private Point targetLocation = null;
	private boolean flagged;
	
	public Soldier(Point p){
		location = p;
	}
	
	public int getX(){
		return (int)location.getX();
	}
	
	public void setPoint(Point p){
		location = p;
	}
	
	public int getY(){
		return (int)location.getY();
	}
	
	public void setFlag(){
		flagged = !flagged;
	}
	
	public boolean isFlagged(){
		return flagged;
	}
	
	public void setTargetLocation(Point p){
		targetLocation = p;
	}
	
	public Point getTargetLocation(){
		return targetLocation;
	}

}
