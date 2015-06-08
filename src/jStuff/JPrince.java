package jStuff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Contains everything to do with prince graphics, including switching pictures
 * corresponding to movement and direction
 * 
 * @author dvir42
 * 
 */
public class JPrince extends JLabel {

	private static final long serialVersionUID = 7139877797953720431L;

	private static final int rows = 4, cols = 4;
	private static final ImageIcon[][] pics = new ImageIcon[rows][cols];

	public final static int WIDTH;
	public final static int HEIGHT;

	static {
		ImageIcon ic = new ImageIcon("imgs/prince.png");
		int imgHeight = ic.getIconHeight();
		int imgWidth = ic.getIconWidth();
		BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(ic.getImage(), 0, 0, null);
		int px = 0, py = 0;
		WIDTH = imgWidth / cols;
		HEIGHT = imgHeight / rows;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				pics[i][j] = new ImageIcon(
						bi.getSubimage(px, py, WIDTH, HEIGHT));
				px += WIDTH;
			}
			py += HEIGHT;
			px = 0;
		}
	}

	private int direction;
	private int status;

	public JPrince() {
		status = 0;
		pointRight();
	}

	/**
	 * Points the prince downwards
	 */
	public void pointDown() {
		direction = 0;
		set();
	}

	/**
	 * Points the prince left
	 */
	public void pointLeft() {
		direction = 1;
		set();
	}

	/**
	 * Points the prince right
	 */
	public void pointRight() {
		direction = 2;
		set();
	}

	/**
	 * Points the prince upwards
	 */
	public void pointUp() {
		direction = 3;
		set();
	}

	/**
	 * Cycles through status. Meant to be used in a loop to make the illusion of
	 * a walking prince
	 */
	public void step() {
		status = (status + 1) % 4;
		set();
	}

	/**
	 * Sets the correct picture for the current direction and status
	 */
	private void set() {
		setIcon(pics[direction][status]);
	}

	/**
	 * 
	 * @return an {@link ImageIcon} of the prince in his default position
	 */
	public static ImageIcon defaultImage() {
		return pics[2][0];
	}

}
