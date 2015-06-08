package maze.builder;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import maze.Maze;

/**
 * A class that takes care of the table in use in {@link MazeBuilder}
 * 
 * @author dvir42
 *
 */
public class BuilderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 2987477801446326377L;

	private static final Object[][] data = new Object[Maze.HEIGHT][Maze.WIDTH];

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

}
