package maze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class MazeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4347071534736678347L;

	private static final ImageIcon PRINCESS = new ImageIcon("imgs/princess.png");
	private static final ImageIcon TREASURE = new ImageIcon("imgs/treasure.png");

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
	public Class<JLabel> getColumnClass(int j) {
		return JLabel.class;
	}

	static {
		data = new Object[Maze.HEIGHT][Maze.WIDTH];
		try {
			List<String> temp = Files.readAllLines(new File("maze").toPath());
			for (int i = 0; i < temp.size(); i++)
				for (int j = 0; j < temp.get(i).split(" ").length; j++)
					switch (Integer.parseInt(temp.get(i).split(" ")[j])) {
					case 1:
						data[i][j] = PRINCESS;
						break;
					case 2:
						data[i][j] = PRINCESS;
						break;
					case 3:
						data[i][j] = TREASURE;
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
