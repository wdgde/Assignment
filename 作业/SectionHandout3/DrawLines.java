import java.awt.event.MouseEvent;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;

public class DrawLines extends GraphicsProgram {
	// 起点的x, y坐标
	private int startx;
	private int starty;
	private GLine line;

	public void run() {
		addMouseListeners();
	}

	// 当鼠标点下时绘制起点
	public void mousePressed(MouseEvent e) {
		startx = e.getX();
		starty = e.getY();
		line = new GLine(startx, starty, startx, starty);
		add(line);
	}

	// 拖动鼠标时，绘制终点
	public void mouseDragged(MouseEvent e) {
		line.setEndPoint(e.getX(), e.getY());
	}
}
