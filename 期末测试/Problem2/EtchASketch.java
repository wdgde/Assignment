import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;

public class EtchASketch extends GraphicsProgram {
	// X的长和宽的像素
	private int xwide = 10;
	private int xhigh = 10;
	private GLine line1;
	private GLine line2;
	// 每次移动的像素
	private int movepixels = 20;

	public void run() {
		// 添加4个按钮
		JButton northButton = new JButton("North");
		add(northButton, SOUTH);
		JButton southButton = new JButton("South");
		add(southButton, SOUTH);
		JButton eastButton = new JButton("East");
		add(eastButton, SOUTH);
		JButton westButton = new JButton("West");
		add(westButton, SOUTH);
		// 添加两条线
		line1 = new GLine(getWidth() / 2 - xwide / 2, getHeight() / 2 - xhigh / 2, getWidth() / 2 + xwide / 2,
				getHeight() / 2 + xhigh / 2);
		add(line1);
		line2 = new GLine(getWidth() / 2 + xwide / 2, getHeight() / 2 - xhigh / 2, getWidth() / 2 - xwide / 2,
				getHeight() / 2 + xhigh / 2);
		add(line2);

		// 添加监听
		addActionListeners();
	}

	public void actionPerformed(ActionEvent e) {
		// 向北绘制红线
		if (e.getActionCommand().equals("North")) {
			GLine northLine = new GLine(line1.getX() + xwide / 2, line1.getY() + xhigh / 2, line1.getX() + xwide / 2,
					line1.getY() + xhigh / 2 - movepixels);
			northLine.setColor(Color.RED);
			add(northLine);
			line1.move(0, -movepixels);
			line2.move(0, -movepixels);
		}
		// 向南绘制红线
		else if (e.getActionCommand().equals("South")) {
			GLine southLine = new GLine(line1.getX() + xwide / 2, line1.getY() + xhigh / 2, line1.getX() + xwide / 2,
					line1.getY() + xhigh / 2 + movepixels);
			southLine.setColor(Color.RED);
			add(southLine);
			line1.move(0, movepixels);
			line2.move(0, movepixels);
		}
		// 向东绘制红线
		else if (e.getActionCommand().equals("East")) {
			GLine eastLine = new GLine(line1.getX() + xwide / 2, line1.getY() + xhigh / 2,
					line1.getX() + xwide / 2 + movepixels, line1.getY() + xhigh / 2);
			eastLine.setColor(Color.RED);
			add(eastLine);
			line1.move(movepixels, 0);
			line2.move(movepixels, 0);
		} // 向西绘制红线
		else if (e.getActionCommand().equals("West")) {
			GLine westLine = new GLine(line1.getX() + xwide / 2, line1.getY() + xhigh / 2,
					line1.getX() + xwide / 2 - movepixels, line1.getY() + xhigh / 2);
			westLine.setColor(Color.RED);
			add(westLine);
			line1.move(-movepixels, 0);
			line2.move(-movepixels, 0);
		}
	}
}
