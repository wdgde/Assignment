import java.awt.Color;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class RandomCircles extends GraphicsProgram {
	// 随机数
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// 颜色数组
	private Color[] color = new Color[] { Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.RED, Color.BLUE,
			Color.PINK, Color.YELLOW };
	// x,y坐标
	private double x;
	private double y;

	public void run() {
		for (int i = 0; i < 10; i++) {
			// 随机生成半径
			int radius = rgen.nextInt(10, 60);
			// 随机生成颜色数组的下标
			int colornum = rgen.nextInt(0, color.length - 1);
			// 随机生成x，y坐标
			x = rgen.nextDouble(0.0, getWidth());
			y = rgen.nextDouble(0.0, getHeight());
			// 如果绘制的圆会超出窗口，则再次生成x，y坐标
			while ((x + radius * 2) > getWidth() || (y + radius * 2) > getHeight()) {
				x = rgen.nextDouble(0.0, getWidth());
				y = rgen.nextDouble(0.0, getHeight());
			}
			// 绘制圆
			GOval oval = new GOval(x, y, radius * 2, radius * 2);
			oval.setFilled(true);
			oval.setFillColor(color[colornum]);
			oval.setColor(Color.WHITE);
			add(oval);
		}
	}
}
