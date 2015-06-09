package user;

/**
 * This class provides the go() function, which determines the movement of the
 * prince inside the maze. This is the interactive class, made to be changed by
 * the user
 * 
 * @author dvir42
 *
 */
public class Prince extends Human {

	/**
	 * Here you need to build a prince and tell it what to do
	 */
	public static void go() {
		Prince prince = new Prince();
		prince.moveDown();
		prince.open();
		prince.pickup();
		prince.moveUp();
		prince.moveRight();
	}

}
