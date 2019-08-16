/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	// You fill in this part
	// 定义并初始化一个变量，用来记录列数
	int ColNumbers = 1; 
	public void run() {
		// 判断是否相隔4列
		while(ColNumbers % 4 == 1) {
			// 相隔4列开始放箱子
			putBeepersForEachCol();
			// 回到最下面一行
			backToFirstRow();
			// 走4列
			walkToFour();
		}
	}

	// 每一符合条件的列，从最下面一行开始向最上面一行放箱子
	private void putBeepersForEachCol() {
		turnLeft();			
		if(noBeepersPresent()) {
				putBeeper();
		}
		while(frontIsClear()) {
			move();
			if(noBeepersPresent()) {
				putBeeper();
			}
		}
	}
	
	// 从最上面一行回到最下面一行
	private void backToFirstRow() {
		turnAround();
		while(frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
	// 每放完一列，向前走4步，不够4步则退出循环
	private void walkToFour() {
		for(int i = 0; i < 4; i++) {
			if(frontIsClear())
			{
				move();
			}
			if(!frontIsClear()) {
				ColNumbers++;
				break;
			}
			ColNumbers++;
		}
	}
	
}
