package jStuff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * Contains everything to do with treasure graphics, including switching
 * pictures corresponding to the opening of a treasure chest and finding a ring
 * inside
 * 
 * @author dvir42
 *
 */
public class JTreasure {

	private static final int rows = 4, cols = 1;
	private static final ImageIcon[][] pics = new ImageIcon[rows][cols];

	public final static int WIDTH;
	public final static int HEIGHT;

	static {
		ImageIcon ic = new ImageIcon("imgs/treasure.png");
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

	private int count;
	private final boolean full;

	private final static ImageIcon withRing = new ImageIcon(
			"imgs/treasurewithring.png");

	public JTreasure(boolean full) {
		count = 0;
		this.full = full;
	}

	public boolean full() {
		return full;
	}

	/**
	 * 
	 * @return the next {@link ImageIcon} in the cycle of opening the treasure
	 *         chest
	 */
	public ImageIcon step() {
		if (count < rows)
			return pics[count++][0];
		return opened();
	}

	/**
	 * 
	 * @return an {@link ImageIcon} of a closed chest
	 */
	public static ImageIcon closed() {
		return pics[0][0];
	}

	/**
	 * 
	 * @return an {@link ImageIcon} of an open chest
	 */
	public static ImageIcon opened() {
		return pics[3][0];
	}

	/**
	 * 
	 * @return an {@link ImageIcon} of an open chest with a ring inside
	 */
	public static ImageIcon withRing() {
		return withRing;
	}

}
