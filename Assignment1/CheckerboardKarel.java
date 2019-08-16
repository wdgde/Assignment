/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	// You fill in this part
	// 定义一个变量来记录行数
	int NumOfRow = 0;
	// 定义一个变量来记录是否只有1列
	int NumOfCol = 1;
	// 定义一个变量来表示while循环的状态
	boolean state = true;
	public void run() {
		while(state) {
			// 检查当前开始位置是否有箱子
			if(noBeepersPresent()) {
				putBeeper();
			}
			// 在每一行放箱子
			putBeeperForEachRow();
			// 判断当前位置是否有箱子且前方无法前进  奇数列的情况
			beeperAndNotClear();
		}
	}
	
	private void putBeeperForEachRow() {
		while(frontIsClear()) {
			move();
			// 判断是否走到了一行中最后一个格子
			if(frontIsClear()) { 
    			move();
    			putBeeper();
			}else {
				// 转向
				turnRound();
				// 跳出while循环，开始新一行
				break;
			}
			NumOfCol = 0;
		}
		// 只有一列的情况
		if(NumOfCol == 1) {
			turnLeft();
		}
	}
	
	private void turnRound() {
		// 判断是从左往右走还是从右往左走
		if(NumOfRow % 2 == 0) {
			leftToRight();
		}else {
			rightToLeft();
		}
		NumOfRow++;
	}
	
	// 从左往右走时转向
	private void leftToRight() {
		turnLeft();
		// 判断是否最后一行
		if(!frontIsClear()) {
			state = false;
			return;
		}
		move();
		turnLeft();
	}
	
	// 从右往左时转向
	private void rightToLeft() {
		turnRight();
		if(!frontIsClear()) {
			state = false;
			return;
		}
		move();
		turnRight();
	}
	
	private void beeperAndNotClear() {
		//当前位置有箱子且已经到了一行的最后一格，只有一列的情况不走这一步
		if(!noBeepersPresent() && !frontIsClear()) {
			turnRound();
			if(frontIsClear()) {
				move();
			}
		}
	}
	
}
