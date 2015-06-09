package maze.builder;

import jStuff.JPrince;
import jStuff.JTreasure;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import maze.Maze;
import maze.MazeTableModel;

/**
 * A utility to build mazes
 * 
 * @author dvir42
 *
 */
public class MazeBuilder implements ActionListener {

	private final JTable table;
	private final JFrame frame;

	public MazeBuilder() {
		frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((Maze.WIDTH + 1) * Maze.ROW_HEIGHT + Maze.ROW_HEIGHT,
				(Maze.HEIGHT + 1) * Maze.ROW_HEIGHT + Maze.ROW_HEIGHT);
		table = new JTable(new BuilderTableModel());
		table.setEnabled(true);
		table.setBorder(new EtchedBorder());
		table.setRowHeight(Maze.ROW_HEIGHT);
		table.setBounds(Maze.ROW_HEIGHT / 3 + Maze.ROW_HEIGHT / Maze.WIDTH,
				Maze.ROW_HEIGHT / 4, Maze.WIDTH * Maze.ROW_HEIGHT, Maze.HEIGHT
						* Maze.ROW_HEIGHT);
		table.getSelectedColumn();
		table.getSelectedRow();
		frame.add(table);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(7, 1));
		JButton button;
		button = new JButton(JPrince.defaultImage());
		button.addActionListener(this);
		button.setActionCommand("5");
		buttonPanel.add(button);
		button = new JButton(MazeTableModel.PRINCESS);
		button.addActionListener(this);
		button.setActionCommand("2");
		buttonPanel.add(button);
		button = new JButton(MazeTableModel.BLOCKER);
		button.addActionListener(this);
		button.setActionCommand("1");
		buttonPanel.add(button);
		button = new JButton(MazeTableModel.DRAGON);
		button.addActionListener(this);
		button.setActionCommand("6");
		buttonPanel.add(button);
		button = new JButton(MazeTableModel.TREASURE);
		button.addActionListener(this);
		button.setActionCommand("3");
		buttonPanel.add(button);
		button = new JButton(JTreasure.withRing());
		button.addActionListener(this);
		button.setActionCommand("4");
		buttonPanel.add(button);
		button = new JButton();
		button.addActionListener(this);
		button.setActionCommand("0");
		buttonPanel.add(button);
		frame.add(buttonPanel, BorderLayout.EAST);
		JPanel savePanel = new JPanel();
		button = new JButton("Save");
		button.addActionListener(this);
		button.setActionCommand("-1");
		savePanel.add(button);
		frame.add(savePanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private boolean hasPrince, hasPrincess, hasDragon;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Integer.parseInt(e.getActionCommand()) == -1)
			save();
		else if (table.getSelectedRow() != -1
				&& table.getSelectedColumn() != -1) {
			if (table.getValueAt(table.getSelectedRow(),
					table.getSelectedColumn()) == MazeTableModel.PRINCESS)
				hasPrincess = false;
			else if (table.getValueAt(table.getSelectedRow(),
					table.getSelectedColumn()) == MazeTableModel.DRAGON)
				hasDragon = false;
			else if (table.getValueAt(table.getSelectedRow(),
					table.getSelectedColumn()) == JPrince.defaultImage())
				hasPrince = false;
			switch (Integer.parseInt(e.getActionCommand())) {
			case -1:
				save();
				break;
			case 0:
				table.setValueAt(null, table.getSelectedRow(),
						table.getSelectedColumn());
				break;
			case 1:
				table.setValueAt(MazeTableModel.BLOCKER,
						table.getSelectedRow(), table.getSelectedColumn());
				break;
			case 2:
				if (!hasPrincess) {
					table.setValueAt(MazeTableModel.PRINCESS,
							table.getSelectedRow(), table.getSelectedColumn());
					hasPrincess = true;
				}
				break;
			case 3:
				table.setValueAt(MazeTableModel.TREASURE,
						table.getSelectedRow(), table.getSelectedColumn());
				break;
			case 4:
				table.setValueAt(JTreasure.withRing(), table.getSelectedRow(),
						table.getSelectedColumn());
				break;
			case 5:
				if (!hasPrince) {
					table.setValueAt(JPrince.defaultImage(),
							table.getSelectedRow(), table.getSelectedColumn());
					hasPrince = true;
				}
				break;
			case 6:
				if (!hasDragon) {
					table.setValueAt(MazeTableModel.DRAGON,
							table.getSelectedRow(), table.getSelectedColumn());
					hasDragon = true;
				}
				break;
			}
		}
	}

	/**
	 * Saves the current setup of the map to a file
	 */
	private void save() {
		if (valid()) {
			JFileChooser chooser = new JFileChooser(".");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					PrintWriter out = new PrintWriter(chooser.getSelectedFile());
					for (int i = 0; i < table.getRowCount(); i++) {
						for (int j = 0; j < table.getColumnCount(); j++) {
							ImageIcon thing = (ImageIcon) table
									.getValueAt(i, j);
							if (thing == null)
								out.print(0);
							else if (thing == MazeTableModel.BLOCKER)
								out.print(1);
							else if (thing == MazeTableModel.PRINCESS)
								out.print(2);
							else if (thing == MazeTableModel.TREASURE)
								out.print(3);
							else if (thing == JTreasure.withRing())
								out.print(4);
							else if (thing == JPrince.defaultImage())
								out.print(5);
							else if (thing == MazeTableModel.DRAGON)
								out.print(6);
							out.print(" ");
						}
						out.println();
					}
					out.close();
					JOptionPane.showMessageDialog(frame, "Saved Successfully",
							"Success", 1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Failed to save",
							"Failed", 0);
				}
			}
		}
	}

	/**
	 * 
	 * @return whether the maze is valid
	 */
	private boolean valid() {
		if (!hasPrince) {
			JOptionPane.showMessageDialog(frame, "Missing prince", "Failed", 0);
			return false;
		}
		if (!hasPrincess) {
			JOptionPane.showMessageDialog(frame, "Missing princess", "Failed",
					0);
			return false;
		}
		if (!hasDragon) {
			JOptionPane.showMessageDialog(frame, "Missing dragon", "Failed", 0);
			return false;
		}
		boolean hasRing = false;
		for (int i = 0; i < table.getRowCount(); i++)
			for (int j = 0; j < table.getColumnCount(); j++)
				if (table.getValueAt(i, j) == JTreasure.withRing()) {
					hasRing = true;
					break;
				}
		if (!hasRing) {
			JOptionPane.showMessageDialog(frame, "Missing treasure with ring",
					"Failed", 0);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new MazeBuilder();
	}

}
