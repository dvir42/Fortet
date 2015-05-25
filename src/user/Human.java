package user;

import maze.Maze;

public class Human {

	public void moveDown() {
		addMove(Move.Down);
	}

	public void moveLeft() {
		addMove(Move.Left);
	}

	public void moveRight() {
		addMove(Move.Right);
	}

	public void moveUp() {
		addMove(Move.Up);
	}

	public void open() {
		addMove(Move.Open);
	}

	public void pickup() {
		addMove(Move.Pickup);
	}

	private void addMove(Move move) {
		Maze.moves.add(move);
	}

}
