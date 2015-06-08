package maze;

import jStuff.JDragon;
import jStuff.JPrince;
import jStuff.JPrincess;
import jStuff.JTreasure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import user.Move;
import user.Prince;

/**
 * The main class of the game. This is where the magic happens
 * 
 * @author dvir42
 *
 */
public class Maze {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int ROW_HEIGHT = 60;

	private static final int MOVE = 2;
	private int x, y;

	private final JTable table;
	private final JPrince prince;
	private final JLayeredPane pane;

	public static final ArrayList<Move> moves = new ArrayList<Move>();

	private final Timer timer;

	private int moveCount;
	private int currX, currY;

	public static final JTreasure[][] treasures = new JTreasure[HEIGHT][WIDTH];

	public static final boolean[][] blockers = new boolean[HEIGHT][WIDTH];

	private boolean hasRing;
	public static int princessI, princessJ;

	public static int princeI, princeJ;

	private boolean isDragonAwake;
	public static int startingDragonI, startingDragonJ;
	private int dragonI;
	private int dragonJ;
	private int thingsDestroyed;
	public static final int MAX_THINGS_DESTROYED = 4;

	public Maze() {
		JFrame frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((WIDTH + 1) * ROW_HEIGHT, (HEIGHT + 1) * ROW_HEIGHT);
		pane = new JLayeredPane();
		prince = new JPrince();
		pane.setBorder(new EtchedBorder());
		MazeTableModel.init();
		table = new JTable(new MazeTableModel());
		table.setEnabled(false);
		table.setBorder(new EtchedBorder());
		table.setRowHeight(ROW_HEIGHT);
		table.setBounds(ROW_HEIGHT / 3 + ROW_HEIGHT / WIDTH, ROW_HEIGHT / 4,
				WIDTH * ROW_HEIGHT, HEIGHT * ROW_HEIGHT);
		frame.getContentPane().add(pane);
		pane.add(table, JLayeredPane.DEFAULT_LAYER);
		pane.add(prince, JLayeredPane.PALETTE_LAYER);
		prince.setSize(ROW_HEIGHT, ROW_HEIGHT);
		prince.setLocation(table.getBounds().x - 5 + ROW_HEIGHT * princeJ,
				table.getBounds().y + ROW_HEIGHT * princeI);
		frame.setVisible(true);
		moveCount = 0;
		currX = princeJ;
		currY = princeI;
		hasRing = false;
		thingsDestroyed = 0;
		isDragonAwake = false;
		dragonI = startingDragonI;
		dragonJ = startingDragonJ;
		timer = new Timer(66, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (moves.isEmpty() && moveCount == 0) {
					timer.stop();
					if (currX == princessJ && currY == princessI)
						if (hasRing) {
							table.setValueAt(JPrincess.happy(), princessI,
									princessJ);
							JOptionPane.showMessageDialog(frame, "Good Job",
									"Successful", 1);
						} else
							JOptionPane.showMessageDialog(frame,
									"You have no ring", "Failed", 0);

					else if (blockers[currY][currX]) {
						timer.stop();
						JOptionPane.showMessageDialog(frame, "You crashed",
								"Failed", 0);
					} else
						JOptionPane.showMessageDialog(frame,
								"You haven't arrived at the princess",
								"Failed", 0);
				} else {
					if (moveCount == 0) {
						if (isDragonAwake
								&& thingsDestroyed < MAX_THINGS_DESTROYED
								&& somethingToDestroy()) {
							destroySomething();
						} else if (isDragonAwake) {
							putBeastToSleep();
						}
						if (blockers[currY][currX]) {
							timer.stop();
							JOptionPane.showMessageDialog(frame, "You crashed",
									"Failed", 0);
						} else if (currY == dragonI && currX == dragonJ) {
							if (isDragonAwake) {
								timer.stop();
								JOptionPane.showMessageDialog(frame,
										"You were killed", "Failed", 0);
							} else {
								awakenTheBeast();
							}
						}
						moveCount = ROW_HEIGHT / MOVE;
						switch (moves.remove(0)) {
						case Down:
							x = 0;
							y = MOVE;
							prince.pointDown();
							currY++;
							break;
						case Left:
							x = -MOVE;
							y = 0;
							prince.pointLeft();
							currX--;
							break;
						case Open:
							x = 0;
							y = 0;
							moveCount = 0;
							if (((ImageIcon) table.getValueAt(currY, currX))
									.equals(JTreasure.closed()))
								openTreasure();
							else {
								timer.stop();
								JOptionPane.showMessageDialog(frame,
										"Nothing to open", "Failed", 0);
							}
							break;
						case Pickup:
							x = 0;
							y = 0;
							moveCount = 0;
							if (((ImageIcon) table.getValueAt(currY, currX))
									.equals(JTreasure.withRing()))
								pickup();
							else {
								timer.stop();
								JOptionPane.showMessageDialog(frame,
										"Nothing to pick up", "Failed", 0);
							}
							break;
						case Right:
							x = MOVE;
							y = 0;
							prince.pointRight();
							currX++;
							break;
						case Up:
							x = 0;
							y = -MOVE;
							prince.pointUp();
							currY--;
							break;
						case Wait:
							break;
						}
					}
					if (currX >= 0 && currX < WIDTH && currY >= 0
							&& currY < HEIGHT && (x != 0 || y != 0)) {
						prince.setLocation(prince.getLocation().x + x,
								prince.getLocation().y + y);
						prince.step();
						moveCount--;
					} else if (x != 0 || y != 0) {
						timer.stop();
						JOptionPane.showMessageDialog(frame,
								"You fell off the earth", "Failed", 0);
					}
				}
			}
		});
		timer.start();
	}

	/**
	 * Opens the treasure chest at the current location
	 */
	private void openTreasure() {
		for (int k = 0; k < 4; k++) {
			table.setValueAt(treasures[currY][currX].step(), currY, currX);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (treasures[currY][currX].full())
			table.setValueAt(JTreasure.withRing(), currY, currX);
	}

	/**
	 * Picks up whatever is in the current location
	 */
	private void pickup() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		table.setValueAt(JTreasure.opened(), currY, currX);
		hasRing = true;
		table.setValueAt(JPrincess.sniffle(), princessI, princessJ);
	}

	/**
	 * Awakens the dragon
	 */
	private void awakenTheBeast() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		table.setValueAt(JDragon.awake(), currY, currX);
		isDragonAwake = true;
	}

	/**
	 * Randomly picks a treasure chest or a blocker and destroys it, replacing
	 * it with the dragon
	 */
	private void destroySomething() {
		boolean destroyedSomething = false;
		while (!destroyedSomething) {
			int i = (int) (Math.random() * HEIGHT);
			int j = (int) (Math.random() * WIDTH);
			if (blockers[i][j]) {
				blockers[i][j] = false;
				table.setValueAt(JDragon.awake(), i, j);
				table.setValueAt(null, dragonI, dragonJ);
				dragonI = i;
				dragonJ = j;
				destroyedSomething = true;
				thingsDestroyed++;
			} else if (treasures[i][j] != null) {
				treasures[i][j] = null;
				table.setValueAt(null, dragonI, dragonJ);
				dragonI = i;
				dragonJ = j;
				table.setValueAt(JDragon.awake(), i, j);
				destroyedSomething = true;
				thingsDestroyed++;
			}
		}
	}

	/**
	 * 
	 * @return whether there still is something worth destroying
	 */
	private boolean somethingToDestroy() {
		for (boolean[] row : blockers)
			for (boolean blocker : row)
				if (blocker)
					return true;
		for (JTreasure[] row : treasures)
			for (JTreasure treasure : row)
				if (treasure != null)
					return true;
		return false;
	}

	/**
	 * Returns the dragon to its original location and puts it to sleep
	 */
	private void putBeastToSleep() {
		table.setValueAt(null, dragonI, dragonJ);
		table.setValueAt(JDragon.sleeping(), startingDragonI, startingDragonJ);
		dragonI = startingDragonI;
		dragonJ = startingDragonJ;
		if (somethingToDestroy())
			thingsDestroyed = 0;
		isDragonAwake = false;
	}

	public static void main(String[] args) {
		Prince.go();
		new Maze();
	}

}
