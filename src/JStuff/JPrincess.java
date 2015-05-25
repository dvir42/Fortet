package JStuff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JPrincess extends JLabel {

	private static final long serialVersionUID = -8660634819580426545L;

	private static final int rows = 4, cols = 3;
	private static final ImageIcon[][] pics = new ImageIcon[rows][cols];

	public final static int WIDTH;
	public final static int HEIGHT;

	static {
		ImageIcon ic = new ImageIcon("imgs/princess.png");
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

	public JPrincess() {
		super(pics[3][0]);
	}

	public void cry() {
		setIcon(pics[3][0]);
	}

	public void sniffle() {
		setIcon(pics[3][2]);
	}

	public void hope() {
		setIcon(pics[0][1]);
	}

	public void muchHope() {
		setIcon(pics[0][2]);
	}

	public void happy() {
		setIcon(pics[1][2]);
	}

}