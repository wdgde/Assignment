
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/* Method: run() */
	/** Runs the Breakout program. */
	// 上方砖块
	private GRect RectOfBrick;
	// 下方可移动滑板
	private GRect RectOfPaddle;
	// 球
	private GOval OvalOfBall;
	// Object对象
	private GObject collider;
	// 游戏开始的提示Label
	private GLabel StartOfLabel;
	// 记录分数的Label
	private GLabel ScoreOfLabel;
	// 在x轴和y轴移动的距离
	private double vx, vy;
	// 随机数
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// 上方砖块数量
	private int NumsOfBrick;
	// 鼠标点击次数
	private int NumsOfClick;
	// 分数
	private int Score;
	// 球运动速度
	private int Speed;
	// 第1档速度
	private int Gear_a;
	// 第2档速度
	private int Gear_b;
	// 第3档速度
	private int Gear_c;
	// 第4档速度
	private int Gear_d;
	// 存放x轴移动的距离
	private double vx_temp;
	// 重玩标志
	private boolean Replay = false;
	// 颜色数组
	private Color[] color = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };

	public void run() {
		/* You fill this in, along with any subsidiary methods */
		// 添加鼠标点击监听
		addMouseListeners();
		// 初始化游戏，绘图
		initGame();
		// 判断用户是否点击屏幕开始游戏
		while (true) {
			if (NumsOfClick == 1) {
				// 开始游戏
				startGame();
			}
			pause(1);
		}
	}

	// 初始化游戏，包括砖块，滑块，球，开始时x轴和y轴移动的距离，文字提示
	private void initGame() {
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			for (int j = 0; j < NBRICK_ROWS; j++) {
				chooseColor(i, j);
			}
		}
		NumsOfBrick = 0;
		// 鼠标点击次数
		NumsOfClick = 0;
		// 分数
		Score = 0;
		// 球运动速度
		Speed = 50;
		// 第1档速度
		Gear_a = 1;
		// 第2档速度
		Gear_b = 2;
		// 第3档速度
		Gear_c = 3;
		// 第4档速度
		Gear_d = 4;
		initPaddle();
		initBall();
		// 在(-3,3)的范围内随机取一个值表示开始时x轴移动的距离
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		vy = 5;
		StartOfLabel = new GLabel("点击屏幕开始游戏");
		StartOfLabel.setColor(Color.PINK);
		StartOfLabel.setFont("SansSerif-32");
		StartOfLabel.setLocation(getWidth() / 2 - (StartOfLabel.getWidth() / 2),
				getHeight() / 2 - (StartOfLabel.getAscent() / 2));
		add(StartOfLabel);
		ScoreOfLabel = new GLabel("分数：" + Score);
		ScoreOfLabel.setColor(Color.RED);
		ScoreOfLabel.setFont("SansSerif-16");
		;
		ScoreOfLabel.setLocation(getWidth() / 2 - (ScoreOfLabel.getWidth() / 2),
				getHeight() - (ScoreOfLabel.getAscent() / 2));
		add(ScoreOfLabel);
	}

	// 为不同行的砖块选择不同颜色
	private void chooseColor(int i, int j) {
		RectOfBrick = new GRect(BRICK_SEP * (j + 1) + BRICK_WIDTH * j, BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * i,
				BRICK_WIDTH, BRICK_HEIGHT);
		RectOfBrick.setColor(Color.WHITE);
		RectOfBrick.setFilled(true);
		/*
		 * if (i == 0 || i == 1) { RectOfBrick.setFillColor(Color.RED); } else if (i ==
		 * 2 || i == 3) { RectOfBrick.setFillColor(Color.ORANGE); } else if (i == 4 || i
		 * == 5) { RectOfBrick.setFillColor(Color.YELLOW); } else if (i == 6 || i == 7)
		 * { RectOfBrick.setFillColor(Color.GREEN); } else if (i == 8 || i == 9) {
		 * RectOfBrick.setFillColor(Color.CYAN); }
		 */
		RectOfBrick.setFillColor(color[i / 2]);
		add(RectOfBrick);
	}

	// 初始化滑块位置，大小和颜色
	private void initPaddle() {
		RectOfPaddle = new GRect(0, APPLICATION_HEIGHT - BRICK_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		RectOfPaddle.setFilled(true);
		RectOfPaddle.setFillColor(Color.BLACK);
		add(RectOfPaddle);
	}

	// 初始化球的位置，大小和颜色
	private void initBall() {
		OvalOfBall = new GOval(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, BALL_RADIUS * 2,
				BALL_RADIUS * 2);
		OvalOfBall.setFilled(true);
		OvalOfBall.setFillColor(Color.BLACK);
		OvalOfBall.setColor(Color.WHITE);
		add(OvalOfBall);
	}

	// 添加鼠标移动事件的库函数，mouseDragged()函数为鼠标拖动
	public void mouseMoved(MouseEvent e) {
		RectOfPaddle.move(e.getX() - RectOfPaddle.getX(), 0);
		if (RectOfPaddle.getX() > (WIDTH - PADDLE_WIDTH)) {
			RectOfPaddle.setLocation(WIDTH - PADDLE_WIDTH, RectOfPaddle.getY());
		}
	}

	// 添加鼠标点击事件
	public void mouseClicked(MouseEvent e) {
		remove(StartOfLabel);
		// 记录鼠标点击次数
		NumsOfClick = e.getClickCount();
		// 判断是否符合重玩游戏的规则
		if (Replay) {
			NumsOfClick = 0;
			removeAll();
			initGame();
			Replay = false;
		}
	}

	// 开始游戏
	private void startGame() {
		vx_temp = vx;
		// 判断游戏是否结束
		while (isGameOver()) {
			// 开始玩游戏
			playGame();
			// 控制球移动的速度
			pause(Speed);
			// 获取球碰撞到的对象
			collider = getCollidingObject();
			// 如果球碰撞到的对象为滑板，则反弹
			if (collider == RectOfPaddle) {
				// 避免出现球和滑板在同一水平线，球上下跳动
				if (OvalOfBall.getY() + (BALL_RADIUS * 2) <= collider.getY() + BRICK_HEIGHT) {
					// 音效
					AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
					bounceClip.play();
					vy = -vy;
					// 判断球是否碰撞到滑板的特殊位置
					specialTime(collider);
					OvalOfBall.move(vx, vy);
				}
			}
			// 如果球碰撞到的对象为砖块，则移除砖块，然后球反弹
			else if (collider != null && collider != ScoreOfLabel) {
				remove(collider);
				NumsOfBrick++;
				keepScore(collider);
				vy = -vy;
				OvalOfBall.move(vx, vy);
			}
		}
	}

	// 开始游戏后，判断球碰撞到上方或左边和右边反弹的情况
	private void playGame() {
		OvalOfBall.move(vx, vy);
		// 左边
		if (OvalOfBall.getX() < 0) {
			vx = -vx;
			OvalOfBall.move(vx, vy);
		}
		// 右边
		if (OvalOfBall.getX() > (getWidth() - (BALL_RADIUS * 2))) {
			vx = -vx;
			OvalOfBall.move(vx, vy);
		}
		// 上边
		if (OvalOfBall.getY() < 0) {
			vy = -vy;
			OvalOfBall.move(vx, vy);
		}
		// 下边
		/*
		 * if( OvalOfBall.getY() > (getHeight() - (BALL_RADIUS * 2))) { vy = -vy;
		 * OvalOfBall.move(vx, vy); }
		 */
	}

	// 获取球四个角碰撞到的对象
	private GObject getCollidingObject() {
		GObject object = null;
		if (getElementAt(OvalOfBall.getX(), OvalOfBall.getY()) != null) {
			return getElementAt(OvalOfBall.getX(), OvalOfBall.getY());
		} else if (getElementAt(OvalOfBall.getX() + (BALL_RADIUS * 2), OvalOfBall.getY()) != null) {
			return getElementAt(OvalOfBall.getX() + (BALL_RADIUS * 2), OvalOfBall.getY());
		} else if (getElementAt(OvalOfBall.getX(), OvalOfBall.getY() + (BALL_RADIUS * 2)) != null) {
			return getElementAt(OvalOfBall.getX(), OvalOfBall.getY() + (BALL_RADIUS * 2));
		} else if (getElementAt(OvalOfBall.getX() + (BALL_RADIUS * 2), OvalOfBall.getY() + (BALL_RADIUS * 2)) != null) {
			return getElementAt(OvalOfBall.getX() + (BALL_RADIUS * 2), OvalOfBall.getY() + (BALL_RADIUS * 2));
		} else {
			return object;
		}
	}

	// 判断游戏是否结束
	private boolean isGameOver() {
		// 如果球触碰到最下面的位置，则游戏失败
		if (OvalOfBall.getY() > (getHeight() - (BALL_RADIUS * 2))) {
			GLabel label_lost = new GLabel("You are lost!");
			label_lost.setColor(Color.RED);
			label_lost.setFont("SansSerif-64");
			label_lost.setLocation(getWidth() / 2 - (label_lost.getWidth() / 2),
					getHeight() / 2 - (label_lost.getAscent() / 2));
			add(label_lost);
			GLabel label_replay = new GLabel("点击屏幕重玩游戏");
			label_replay.setColor(Color.PINK);
			label_replay.setFont("SansSerif-32");
			label_replay.setLocation(getWidth() / 2 - (label_replay.getWidth() / 2),
					getHeight() / 2 - (label_replay.getAscent() / 2) + (label_lost.getAscent() / 2));
			add(label_replay);
			Replay = true;
			return false;
		}
		// 如果消掉了全部的砖块，则赢得游戏
		if (NumsOfBrick == NBRICKS_PER_ROW * NBRICK_ROWS) {
			GLabel label = new GLabel("Win!" + "Win!" + "Win!");
			label.setColor(Color.RED);
			label.setFont("SansSerif-64");
			;
			label.setLocation(getWidth() / 2 - (label.getWidth() / 2), getHeight() / 2 - (label.getAscent() / 2));
			add(label);
			return false;
		}
		return true;
	}

	// 记录游戏得分 (每两行砖块的距离范围都通过一个for循环算出并保存到一个数组中，在这里则通过for循环判断球的位置在哪个区间，再计算分数，改变速度)
	private void keepScore(GObject object) {
		// 消掉的砖块属于第9、10行，则每一块砖积1分
		if (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 2) <= object.getY()
				&& object.getY() <= BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 1)) {
			Score = Score + 1;
		}
		// 消掉的砖块属于第7、8行，则每一块砖积2分，球运动速度加快
		else if (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 4) <= object.getY()
				&& object.getY() <= BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 3)) {
			Score = Score + 2;
			if (Gear_a == 1) {
				Speed = Speed - 10;
				Gear_a = 0;
			}
		}
		// 消掉的砖块属于第5、6行，则每一块砖积3分，球运动速度加快
		else if (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 6) <= object.getY()
				&& object.getY() <= BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 5)) {
			Score = Score + 3;
			if (Gear_b == 2) {
				Speed = Speed - 10;
				Gear_b = 0;
			}
		}
		// 消掉的砖块属于第3、4行，则每一块砖积4分，球运动速度加快
		else if (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 8) <= object.getY()
				&& object.getY() <= BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 7)) {
			Score = Score + 4;
			if (Gear_c == 3) {
				Speed = Speed - 10;
				Gear_c = 0;
			}
		}
		// 消掉的砖块属于第1、2行，则每一块砖积5分，球运动速度加快
		else if (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 10) <= object.getY()
				&& object.getY() <= BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (NBRICK_ROWS - 9)) {
			Score = Score + 5;
			if (Gear_d == 4) {
				Speed = Speed - 10;
				Gear_d = 0;
			}
		}
		ScoreOfLabel.setLabel("分数：" + Score);
	}

	// 判断球是否碰撞到滑板的两端，如果是则使球在y轴方向上 上下移动
	private void specialTime(GObject object) {
		// 滑板左边
		if (OvalOfBall.getX() + (BALL_RADIUS * 2) > object.getX()
				&& OvalOfBall.getX() + (BALL_RADIUS * 2) < object.getX() + BALL_RADIUS) {
			vx = 0;
		}
		// 滑板右边
		else if (OvalOfBall.getX() > object.getX() + PADDLE_WIDTH - BALL_RADIUS
				&& OvalOfBall.getX() < object.getX() + PADDLE_WIDTH) {
			vx = 0;
		}
		// 不是滑板左边和右边时，恢复原来的轨迹
		else if (vx == 0) {
			vx = vx_temp;
		}
	}
}
