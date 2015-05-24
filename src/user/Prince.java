package user;

import stuff.Human;

public class Prince extends Human {

	public static void go() {
		Prince prince = new Prince();
		prince.moveDown();
		prince.moveDown();
		prince.moveRight();
		prince.moveRight();
		prince.moveUp();
		prince.moveLeft();
	}

}
