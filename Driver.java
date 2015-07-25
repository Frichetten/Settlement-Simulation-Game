import javax.swing.JFrame;

/**
 * @author Nicholas Frichette
 */
public class Driver {

	/**
	 * The driver of the driver so to say. The basics of this method are as follows
	 * game_is_running = true;
	 * while(game_is_running){
	 * 	updateGame();
	 * 	displayGame();
	 * }
	 * Obviously this is not optimal, however that is the basics of how the method
	 * will work. 
	 */
	public static void main(String[] args) {
		//Creation of the game Client
		Client game = new Client();
		game.runGameLoop();
		game.setVisible(true);
	}
}
