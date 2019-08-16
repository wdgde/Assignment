/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	// 定义一个表示输入结束的值
	private static final int END = 0;
	public void run() {
		/* You fill this in */
		println("This program finds the largest and smallest numbers.");
		// 定义一个变量接收输入的值
		int number = 0;
		// 定义并初始化一个变量表示最小值
		int smallest = 0;
		// 定义并初始化一个变量表示最大值
		int largest = 0;
		// 定义一个变量用来记录输入次数
		int i = 0;
		// 当输入的值不表示输入结束时
		while((number=readInt("? ")) != END) {
			// 把第一次输入的值都赋值给最大值和最小值
			if(i == 0) {
				smallest = number;
				largest = number;
			}
			if(smallest >= number) {
				smallest = number;
			}
			else if(largest <= number){
				largest = number;
			}
			i++;
		}
		if(i == 0) {
			// 第一次就输入了表示输入结束的值
			println("No values have been entered!");
		}else {
		    println("smallest: " + smallest);
			println("largest: " + largest);
		}
	}
}

