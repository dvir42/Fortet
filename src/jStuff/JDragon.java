package jStuff;

import javax.swing.ImageIcon;

/**
 * Contains pictures of dragons
 * 
 * @author dvir42
 *
 */
public class JDragon {

	/**
	 * 
	 * @return an {@link ImageIcon} of a sleeping dragon
	 */
	public static ImageIcon sleeping() {
		return new ImageIcon("imgs/sleepingdragon.png");
	}

	/**
	 * 
	 * @return an {@link ImageIcon} of an awake dragon
	 */
	public static ImageIcon awake() {
		return new ImageIcon("imgs/dragon.png");
	}

}
