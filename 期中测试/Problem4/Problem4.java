import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Problem4 extends GraphicsProgram {
	private static final int SQSSIZE = 75;
	private static final int NCOLS = 7;
	private static final int NROWS = 3;
	private GImage image;

	public void run() {
		// 添加鼠标点击监听
		addMouseListeners();
		// 图片的初始位置
		image = new GImage("TeT.jpg");
		image.scale(SQSSIZE / image.getWidth(), SQSSIZE / image.getHeight());
		add(image, getWidth() / NCOLS * (NCOLS / 2) + (getWidth() / NCOLS - image.getWidth()) / 2,
				getHeight() - getHeight() / NROWS + (getHeight() / NROWS - image.getHeight()) / 2);
	}

	// 鼠标监听事件
	public void mouseClicked(MouseEvent e) {
		// y轴向上移动
		if (e.getY() < image.getY()) {
			if (e.getY() > (getHeight() / NROWS - image.getHeight()) / 2) {
				image.move(0, -getHeight() / NROWS);
				return;
			}
		}
		// y轴向下移动
		if (e.getY() > image.getY() + image.getHeight()) {
			if (e.getY() < getHeight() - (getHeight() / NROWS - image.getHeight()) / 2) {
				image.move(0, getHeight() / NROWS);
				return;
			}
		}
		// x轴向右移动
		if (e.getX() > image.getX() + image.getWidth()) {
			if (e.getX() < getWidth() - (getWidth() / NCOLS - image.getWidth()) / 2) {
				image.move(getWidth() / NCOLS, 0);
				return;
			}
		}
		// x轴向左移动
		if (e.getX() < image.getX()) {
			if (e.getX() > (getWidth() / NCOLS - image.getWidth()) / 2) {
				image.move(-getWidth() / NCOLS, 0);
				return;
			}
		}
	}
}
