/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		// 定义一个变量来接收输入的值
		int number = readInt("Enter a number: ");
		// 定义一个变量来表示经过计算后的值
		int result = 0;
		// 定义一个变量来表示计算的次数
		int i = 0;
		// 当计算结果不为1时
		while(number != 1) {
			// 判断当前值是否为奇数
			if(number % 2 != 0) {
				result = 3 * number + 1;
				println(number + " is odd, so I make 3n + 1: " + result);
				number = result;
			}
			else {
				result = number / 2;
				println(number + " is even, so I take half: " + result);
				number = result;
			}
			i++;
		}
		println("The process took " + i + " to reach 1");
	}
}

