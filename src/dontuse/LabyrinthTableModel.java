package dontuse;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

class LabyrinthTableModel extends AbstractTableModel {
	private static Random r = new Random();
	private static final ImageIcon DOG_HOUSE = new ImageIcon("pic/doghouse.gif");
	private static final ImageIcon HOUSE = new ImageIcon("pic/home.gif");
	private static final Object[][] data;

	@Override
	public int getColumnCount() {
		return Labyrinth.objects[0].length;
	}

	@Override
	public int getRowCount() {
		return Labyrinth.objects.length;
	}

	@Override
	public Object getValueAt(int paramInt1, int paramInt2) {
		return data[paramInt1][paramInt2];
	}

	@Override
	public String getColumnName(int paramInt) {
		return null;
	}

	@Override
	public Class getColumnClass(int paramInt) {
		return ImageIcon.class;
	}

	@Override
	public boolean isCellEditable(int paramInt1, int paramInt2) {
		return false;
	}

	@Override
	public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
		if (data[paramInt1][paramInt2] != paramObject) {
			data[paramInt1][paramInt2] = paramObject;
			super.fireTableCellUpdated(paramInt1, paramInt2);
		}
	}

	static {
		int i = Labyrinth.objects.length;
		int j = Labyrinth.objects[0].length;
		data = new Object[i][j];
		for (int k = 0; k < i; ++k)
			for (int l = 0; l < j; ++l) {
				int i1 = Labyrinth.objects[k][l];
				if (i1 == 1)
					data[k][l] = HOUSE;
				else if (i1 == 2)
					data[k][l] = DOG_HOUSE;
				else if (i1 > 10)
					data[k][l] = JFood.getIcon(3, 3);
				else
					data[k][l] = null;
			}
	}
}