package dontuse;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
import javax.swing.ImageIcon;

class JFood {
	private static final ImageIcon[] FOOD = new ImageIcon[3];

	static ImageIcon getIcon(int paramInt1, int paramInt2) {
		if (paramInt2 <= 0)
			return null;
		return FOOD[(FOOD.length - 1 - ((paramInt2 - 1) * FOOD.length / paramInt1))];
	}

	static {
		for (int i = 0; i < 3; ++i)
			FOOD[i] = new ImageIcon("pic/food" + i + ".gif");
	}
}