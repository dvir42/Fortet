package stuff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JPrince extends JLabel {

	private static final long serialVersionUID = 7139877797953720431L;

	private static final int rows = 4, cols = 4;
	private static final ImageIcon[][] pics = new ImageIcon[rows][cols];

	static {
		ImageIcon ic = new ImageIcon("imgs/prince.png");
		int imgHeight = ic.getIconHeight();
		int imgWidth = ic.getIconWidth();
		BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(ic.getImage(), 0, 0, null);
		int px = 0, py = 0, w = imgWidth / cols, h = imgHeight / rows;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				pics[i][j] = new ImageIcon(bi.getSubimage(px, py, w, h));
				px += w;
			}
			py += h;
			px = 0;
		}
	}

	private int direction;
	private int status;

	public JPrince() {
		status = 0;
		pointRight();
	}

	public void pointDown() {
		direction = 0;
		set();
	}

	public void pointLeft() {
		direction = 1;
		set();
	}

	public void pointRight() {
		direction = 2;
		set();
	}

	public void pointUp() {
		direction = 3;
		set();
	}

	public void step() {
		status = (status + 1) % 4;
		set();
	}

	private void set() {
		setIcon(pics[direction][status]);
	}

}
