package dontuse;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class Labyrinth extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2202343429950227016L;
	static final int IS_EMPTY = 0;
	static final int IS_HOUSE = 1;
	static final int IS_DOG_H = 2;
	static final int IS_FOOD = 10;
	static final int[][] objects = { { 0, 0, 0, 0, 0, 10, 1, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 10 }, { 2, 0, 0, 1, 0, 1, 1, 0 },
			{ 1, 10, 0, 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0 },
			{ 0, 0, 1, 1, 10, 0, 0, 0 } };

	private final Vector<Integer> moves = new Vector<Integer>();
	private final JDoggie doggie = new JDoggie();
	private final Timer animator;
	private final JLayeredPane jLayeredPane1;
	private final JTable jTable1;
	private int totalFood;
	private int foodCount = 0;
	private boolean eating = false;
	private int hMove;
	private int vMove;
	private int count = 0;
	private int currentRow = 0;
	private int currentColumn = 0;

	private boolean validState = true;
	private boolean isEating = false;
	private int row = 0;
	private int column = 0;
	private int food = 0;
	private static Random random;

	public Labyrinth() {
		super("Labyrinth");
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.jLayeredPane1 = new JLayeredPane();
		this.jLayeredPane1.setBorder(new EtchedBorder());
		super.getContentPane().add(this.jLayeredPane1);

		this.jTable1 = new JTable();
		this.jLayeredPane1.add(this.jTable1, JLayeredPane.DEFAULT_LAYER);
		this.jTable1.setBounds(2, 2, 632, 380);
		this.jTable1.setRowHeight(54);
		this.jTable1.setModel(new LabyrinthTableModel());
		this.jTable1.setRowSelectionAllowed(false);

		this.jLayeredPane1.add(this.doggie, JLayeredPane.PALETTE_LAYER);
		this.doggie.setSize(36, 36);
		this.doggie.setLocation(30, 12);

		this.animator = new Timer(66, this);

		super.pack();
		super.setSize(640, 356);

		go();
		start();
	}

	private static void addFood() {
		Random localRandom = new Random();
		for (int i = 0; i < objects.length; ++i)
			for (int j = 0; j < objects[0].length; ++j)
				if (objects[i][j] == 10)
					objects[i][j] += localRandom.nextInt(6) + 10;
	}

	public void start() {
		this.animator.start();
	}

	public void go() {
		while (!(isFood()))
			moveRight();
		while (isFood())
			eat();
		moveDown();
		moveRight();
		moveRight();
		while (isFood())
			eat();
		moveDown();
		moveDown();
		moveDown();
		moveDown();
		while (!(isFood()))
			moveLeft();
		while (isFood())
			eat();
		moveUp();
		moveLeft();
		moveUp();
		moveLeft();
		moveLeft();
		while (isFood())
			eat();
		moveUp();
		moveLeft();
	}

	private boolean foodLeft() {
		for (int i = 0; i < objects.length; ++i)
			for (int j = 0; j < objects[0].length; ++j)
				if (objects[i][j] > 10)
					return true;
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((this.moves.isEmpty()) && (this.count == 0)) {
			this.animator.stop();
			this.doggie.stop();
			if (objects[this.currentRow][this.currentColumn] == 2)
				if (!(foodLeft()))
					JOptionPane.showMessageDialog(this, "Successfull",
							"Doggie came home!", 1);
				else
					JOptionPane.showMessageDialog(this, "Failed",
							"There is still some food!", 0);
			else
				JOptionPane.showMessageDialog(this, "Failed",
						"This is not doggie's house!", 0);
			return;
		}

		if (this.count == 0) {
			int i = 0;
			Integer localInteger = this.moves.remove(0);
			switch (localInteger.intValue()) {
			case 0:
				this.hMove = 0;
				this.vMove = -2;
				this.count = 27;
				this.currentRow -= 1;
				i = (this.currentRow < 0) ? 1 : 0;
				this.doggie.turnUp();
				this.eating = false;
				System.out.println("Up");
				break;
			case 1:
				this.hMove = 0;
				this.vMove = 2;
				this.count = 27;
				this.currentRow += 1;
				i = (this.currentRow >= this.jTable1.getRowCount()) ? 1 : 0;
				this.doggie.turnDown();
				this.eating = false;
				System.out.println("Down");
				break;
			case 2:
				this.hMove = -2;
				this.vMove = 0;
				this.count = 39;
				this.currentColumn -= 1;
				i = (this.currentColumn < 0) ? 1 : 0;
				this.doggie.turnLeft();
				this.eating = false;
				System.out.println("Left");
				break;
			case 3:
				this.hMove = 2;
				this.vMove = 0;
				this.count = 39;
				this.currentColumn += 1;
				i = (this.currentColumn >= this.jTable1.getColumnCount()) ? 1
						: 0;
				this.doggie.turnRight();
				this.eating = false;
				System.out.println("Right");
				break;
			case 4:
				System.out.println("Eat");
				if (this.eating) {
					this.foodCount -= 1;
				} else {
					this.eating = true;
					this.foodCount = (objects[this.currentRow][this.currentColumn] - 10 - 1);
					this.totalFood = (this.foodCount + 1);
				}
				if (this.foodCount >= 0) {
					this.jTable1.setValueAt(
							JFood.getIcon(this.totalFood, this.foodCount),
							this.currentRow, this.currentColumn);
					if (this.foodCount == 0)
						objects[this.currentRow][this.currentColumn] = 0;
				} else {
					this.animator.stop();
					JOptionPane.showMessageDialog(this, "Cannot eat here",
							"Nothing to eat!!!.", 0);
				}
				return;
			}

			if ((i != 0) || (objects[this.currentRow][this.currentColumn] == 1)) {
				this.animator.stop();
				System.out.println("error" + " i=" + i + " ob="
						+ objects[this.currentRow][this.currentColumn]);
				JOptionPane.showMessageDialog(this, "Invalid move", "Boom!!!.",
						0);
				return;
			}
		}
		Point localPoint = this.doggie.getLocation();
		localPoint.x += this.hMove;
		localPoint.y += this.vMove;

		this.doggie.setLocation(localPoint);
		this.count -= 1;
	}

	public void moveRight() {
		if (this.validState) {
			this.moves.add(new Integer(3));
			this.column += 1;
			this.isEating = false;
			this.validState = ((this.column < objects[0].length) && (objects[this.row][this.column] != 1));
			System.out.println("move right " + objects[this.row][this.column]
					+ " state=" + this.validState);

		}
	}

	public void moveLeft() {
		if (this.validState) {
			this.moves.add(new Integer(2));
			this.column -= 1;
			this.isEating = false;
			this.validState = ((this.column >= 0) && (objects[this.row][this.column] != 1));
			System.out.println("move left " + objects[this.row][this.column]
					+ " state=" + this.validState);

		}
	}

	public void moveDown() {
		if (this.validState) {
			this.moves.add(new Integer(1));
			this.row += 1;
			this.isEating = false;
			this.validState = ((this.row < objects.length) && (objects[this.row][this.column] != 1));
			System.out.println("move down " + objects[this.row][this.column]
					+ " state=" + this.validState);

		}
	}

	public void moveUp() {
		if (this.validState) {
			this.moves.add(new Integer(0));
			this.row -= 1;
			this.isEating = false;
			this.validState = ((this.row >= 0) && (objects[this.row][this.column] != 1));
			System.out.println("move up " + objects[this.row][this.column]
					+ " state=" + this.validState);

		}
	}

	public void eat() {
		if (this.validState) {
			this.moves.add(new Integer(4));
			if (this.isEating) {
				this.food -= 1;
				this.validState = (this.food >= 0);
			} else {
				this.isEating = true;
				this.food = (objects[this.row][this.column] - 10 - 1);

				this.validState = (this.food >= 0);
			}
		}
	}

	public boolean isFood() {
		System.out.println("in isFood="
				+ objects[this.currentRow][this.currentColumn]
				+ " this.validState=" + this.validState);
		if (!(this.validState))
			return random.nextBoolean();
		return (this.food > 0);
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] paramArrayOfString) {
		new Labyrinth().show();
	}

	static {
		addFood();

		random = new Random();
	}
}