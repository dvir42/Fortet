package user;

public class Prince extends Human {

	public static void go() {
		Prince prince = new Prince();
		prince.moveRight();
		prince.open();
		// prince.pickup();
		for (int i = 0; i < 4; i++) {
			prince.moveDown();
		}
		for (int i = 0; i < 3; i++) {
			prince.moveRight();
		}
	}

}
