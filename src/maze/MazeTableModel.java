package maze;

import jStuff.JPrincess;
import jStuff.JTreasure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class MazeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4347071534736678347L;

	private static final ImageIcon PRINCESS = JPrincess.cry();
	private static final ImageIcon TREASURE = JTreasure.closed();
	private static final ImageIcon BLOCKER = new ImageIcon("imgs/blocker.png");

	private static final Object[][] data;

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

	static {
		data = new Object[Maze.HEIGHT][Maze.WIDTH];
		try {
			List<String> temp = Files.readAllLines(new File("maze").toPath());
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
					default:
						data[i][j] = null;
						break;
					}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
