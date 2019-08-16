/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	// 定义一个变量来表示最下面一行第一个矩形的x轴坐标
	private int OriginCoordinate = 0;
	public void run() {
		/* You fill this in. */	
		// 计算最下面一行第一个矩形的x轴坐标
		originCoordinateOfFirstRow();
		// 第一层循环表示每一行
		for(int i = 0; i < BRICKS_IN_BASE; i++) {
			// 计算每一行第一个矩形的x和y坐标，注意矩形的坐标为左上角坐标，不是左下角
			int x = OriginCoordinate + (i * (BRICK_WIDTH / 2));
			int y = getHeight() - BRICK_HEIGHT - (i * BRICK_HEIGHT);
			// 第二层循环表示每一行上的矩形个数
			for(int j = 0; j < BRICKS_IN_BASE - i; j++) {
				GRect rect = new GRect(x + (j * BRICK_WIDTH), y, BRICK_WIDTH, BRICK_HEIGHT);
				add(rect);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void originCoordinateOfFirstRow() {
		// 判断最下面一行是否为偶数个矩形
		if(BRICKS_IN_BASE % 2 == 0) {
			OriginCoordinate=(getWidth() / 2) - ((BRICKS_IN_BASE / 2) * BRICK_WIDTH);
		}else {
			OriginCoordinate=(getWidth() / 2) - (((BRICKS_IN_BASE - 1) / 2) * BRICK_WIDTH) + BRICK_WIDTH / 2;
		}
	}
}

