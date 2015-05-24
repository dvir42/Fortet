package stuff;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

public class Maze {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int ROW_HEIGHT = 60;

	private final JTable table;
	private final JPrince prince;
	private final JLayeredPane pane;

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
		prince.setLocation(ROW_HEIGHT / 3 + ROW_HEIGHT / WIDTH, ROW_HEIGHT / 4);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Maze();
	}

}
