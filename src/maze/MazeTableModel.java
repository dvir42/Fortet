package maze;

import jStuff.JDragon;
import jStuff.JPrincess;
import jStuff.JTreasure;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;

/**
 * A class that takes care of the table in use in {@link Maze}
 * 
 * @author dvir42
 *
 */
public class MazeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4347071534736678347L;

	public static final ImageIcon PRINCESS = JPrincess.cry();
	public static final ImageIcon TREASURE = JTreasure.closed();
	public static final ImageIcon BLOCKER = new ImageIcon("imgs/blocker.png");
	public static final ImageIcon DRAGON = JDragon.sleeping();

	private static Object[][] data;

	@Override
	public int getColumnCount() {
		return data[0].length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int i, int j) {
		return data[i][j];
	}

	@Override
	public void setValueAt(Object value, int i, int j) {
		if (data[i][j] != value) {
			data[i][j] = value;
			fireTableCellUpdated(i, j);
		}
	}

	@Override
	public Class<ImageIcon> getColumnClass(int j) {
		return ImageIcon.class;
	}

	public static void init() {
		data = new Object[Maze.HEIGHT][Maze.WIDTH];
		try {
			JFileChooser chooser = new JFileChooser(".");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				List<String> temp = Files.readAllLines(chooser
						.getSelectedFile().toPath());
				for (int i = 0; i < Maze.HEIGHT; i++)
					for (int j = 0; j < Maze.WIDTH; j++)
						switch (Integer.parseInt(temp.get(i).split(" ")[j])) {
						case 1:
							data[i][j] = BLOCKER;
							Maze.blockers[i][j] = true;
							break;
						case 2:
							data[i][j] = PRINCESS;
							Maze.princessI = i;
							Maze.princessJ = j;
							break;
						case 3:
							data[i][j] = TREASURE;
							Maze.treasures[i][j] = new JTreasure(false);
							break;
						case 4:
							data[i][j] = TREASURE;
							Maze.treasures[i][j] = new JTreasure(true);
							break;
						case 5:
							Maze.princeI = i;
							Maze.princeJ = j;
							break;
						case 6:
							data[i][j] = DRAGON;
							Maze.startingDragonI = i;
							Maze.startingDragonJ = j;
							break;
						default:
							data[i][j] = null;
							break;
						}
			} else
				System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
