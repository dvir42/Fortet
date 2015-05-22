package stuff;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Maze extends JPanel {

	private static final long serialVersionUID = -7550760796122646082L;

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int ROW_HEIGHT = 50;

	private final JTable table;

	public Maze() {
		table = new JTable(new MazeTableModel());
		table.setEnabled(false);
		table.setRowHeight(ROW_HEIGHT);
		JFrame frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		add(table);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Maze();
	}

}
