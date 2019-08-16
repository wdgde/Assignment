/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	// You fill in this part
	// 定义一个变量来记录走过的步数
	private int i = 0;
	public void run() {
		while(true) {
			// 每一步都要判断当前位置是否有门口
			if(findBeeper() == 0) {
				break;
			}
		}
		// 返回起始位置
		changeDirection();
		while(true) {
			if(i == 0)
			{
				turnAround();
				break;
			}
			backToStart();
		}
	}
	
	// 选择箱子的位置并捡起
	private int findBeeper() {
		turnLeft();
		if(!frontIsClear()) {
			findDoor();
		}else {
			if(!noBeepersPresent()) {
				pickBeeper();
				return 0;
			}
			move();
			i++;
		}
		return 1;
	}
	
	// 找到可以出去的门口
	private void findDoor() {
		turnRight();
		while(!frontIsClear()) {
			turnRight();
		}
		move();
		i++;
	}
	
	// 改变方向
	private void changeDirection() {
		turnLeft();
		move();
		i--;
	}
	
	// 按原路返回
	private void backToStart() {
		turnRight();
		if(!frontIsClear()) {
			judgeDirection();
		}else {
			move();
			i--;
		}
	}
	
	// 判断方向
	private void judgeDirection() {
		turnLeft();
		while(!frontIsClear()) {
			turnLeft();
		}
		move();
		i--;
	}
}
