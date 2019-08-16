/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	// You fill in this part
	// 定义并初始化一个变量，用来记录步数
	private int StepNumbers = 1;
	public void run() {
		stepsOfRow();
		turnAround();
		backToHalfOfRow();
		putBeeper();
		turnAround();
	}
	
	private void stepsOfRow() {
		// 走完一行并对步数进行累加
		while(frontIsClear()) {
			move();
			StepNumbers++;
		}
	}
	
	private void backToHalfOfRow() {
		// 计算返回到一行中间所需要走的步数
		for(int j = 0; j < ((StepNumbers - 1) / 2); j++) {
			move();
		}
	}

}
