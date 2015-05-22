package dontuse;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class JDoggie extends JLabel {

	private static final long serialVersionUID = 6027414805797797162L;

	private static final ImageIcon runLeft = new ImageIcon("pic/doggie_l.gif");
	private static final ImageIcon runRight = new ImageIcon("pic/doggie_r.gif");
	private static final ImageIcon runDown = new ImageIcon("pic/doggie_d.gif");
	private static final ImageIcon runUp = new ImageIcon("pic/doggie_u.gif");
	private static final ImageIcon stopRight = new ImageIcon(
			"pic/dog_stop_r.gif");
	private static final ImageIcon stopLeft = new ImageIcon(
			"pic/dog_stop_l.gif");
	private int state = 0;

	private final boolean isRight = true;

	public JDoggie() {
		super(runRight);
	}

	public void turnLeft() {
		if (this.state != 2) {
			super.setIcon(runLeft);
			this.state = 2;
		}
	}

	public void turnRight() {
		if (this.state != 1) {
			super.setIcon(runRight);
			this.state = 1;
		}
	}

	public void turnDown() {
		if (this.state != 3) {
			super.setIcon(runDown);
			this.state = 3;
		}
	}

	public void turnUp() {
		if (this.state != 4) {
			super.setIcon(runUp);
			this.state = 4;
		}
	}

	public void stop() {
		if (this.isRight)
			super.setIcon(stopRight);
		else
			super.setIcon(stopLeft);
	}
}