import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class RobotFace extends GraphicsProgram {
	// 头的高度和宽度
	private static final int HEAD_WIDHT = 150;
	private static final int HEAD_HEIGHT = 200;
	// 眼睛的半径
	private static final int EYE_RADIUS = 15;
	// 嘴的高度和宽度
	private static final int MOUTH_WIDHT = 100;
	private static final int MOUTH_HEIGHT = 30;

	public void run() {
		// 绘制头
		GRect headrect = new GRect((getWidth() - HEAD_WIDHT) / 2, (getHeight() - HEAD_HEIGHT) / 2, HEAD_WIDHT,
				HEAD_HEIGHT);
		headrect.setFillColor(Color.CYAN);
		headrect.setFilled(true);
		add(headrect);

		// 绘制左眼
		GOval leftoval = new GOval((getWidth() - HEAD_WIDHT) / 2 + HEAD_WIDHT / 4 - EYE_RADIUS,
				(getHeight() - HEAD_HEIGHT) / 2 + HEAD_HEIGHT / 4 - EYE_RADIUS, EYE_RADIUS * 2, EYE_RADIUS * 2);
		leftoval.setFilled(true);
		leftoval.setFillColor(Color.YELLOW);
		leftoval.setColor(Color.CYAN);
		add(leftoval);

		// 绘制右眼
		GOval rightoval = new GOval((getWidth() - HEAD_WIDHT) / 2 + HEAD_WIDHT / 4 - EYE_RADIUS + HEAD_WIDHT / 2,
				(getHeight() - HEAD_HEIGHT) / 2 + HEAD_HEIGHT / 4 - EYE_RADIUS, EYE_RADIUS * 2, EYE_RADIUS * 2);
		rightoval.setFilled(true);
		rightoval.setFillColor(Color.YELLOW);
		rightoval.setColor(Color.CYAN);
		add(rightoval);

		// 绘制嘴巴
		GRect mouthrect = new GRect((getWidth() - HEAD_WIDHT) / 2 + (HEAD_WIDHT - MOUTH_WIDHT) / 2,
				(getHeight() + HEAD_HEIGHT) / 2 - (HEAD_HEIGHT / 4) - MOUTH_HEIGHT, MOUTH_WIDHT, MOUTH_HEIGHT);
		mouthrect.setFillColor(Color.WHITE);
		mouthrect.setFilled(true);
		mouthrect.setColor(Color.CYAN);
		add(mouthrect);
	}

}
