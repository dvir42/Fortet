package maze;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import JStuff.JPrince;
import user.Move;
import user.Prince;

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

	public Maze() {
		JFrame frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((WIDTH + 1) * ROW_HEIGHT, (HEIGHT + 1) * ROW_HEIGHT);
		pane = new JLayeredPane();
		prince = new JPrince();
		pane.setBorder(new EtchedBorder());
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
		prince.setLocation(table.getBounds().x + (ROW_HEIGHT - JPrince.WIDTH)
				/ 2, table.getBounds().y + (ROW_HEIGHT - JPrince.HEIGHT) / 2);
		frame.setVisible(true);
		moveCount = 0;
		currX = 0;
		currY = 0;
		timer = new Timer(66, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (moves.isEmpty() && moveCount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(frame, "Good Job",
							"Successful", 1);
				} else {
					if (moveCount == 0) {
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
							break;
						case Pickup:
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
						default:
							break;
						}
					}
					if (currX >= 0 && currX < WIDTH && currY >= 0
							&& currY < HEIGHT) {
						prince.setLocation(prince.getLocation().x + x,
								prince.getLocation().y + y);
						prince.step();
						moveCount--;
					} else {
						timer.stop();
						JOptionPane.showMessageDialog(frame,
								"You fell off the earth", "Failed", 0);
					}
				}
			}
		});
		timer.start();
	}

	public static void main(String[] args) {
		Prince.go();
		new Maze();
	}

}
