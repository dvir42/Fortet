package user;

import maze.Maze;

/**
 * Specifies movements that a prince should use. To be inherited by
 * {@link Prince}
 * 
 * @author dvir42
 *
 */
public class Human {

	/**
	 * Adds a downwards move to moves
	 */
	public void moveDown() {
		addMove(Move.Down);
	}

	/**
	 * Adds a left move to moves
	 */
	public void moveLeft() {
		addMove(Move.Left);
	}

	/**
	 * Adds a right move to moves
	 */
	public void moveRight() {
		addMove(Move.Right);
	}

	/**
	 * Adds an upwards move to moves
	 */
	public void moveUp() {
		addMove(Move.Up);
	}

	/**
	 * Adds an open move to moves
	 */
	public void open() {
		addMove(Move.Open);
	}

	/**
	 * Adds a pickup move to moves
	 */
	public void pickup() {
		addMove(Move.Pickup);
	}

	/**
	 * Adds a move to moves
	 */
	private void addMove(Move move) {
		Maze.moves.add(move);
	}

}
