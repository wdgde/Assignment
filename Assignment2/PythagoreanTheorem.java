/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		println("Enter values to compute Pythagorean theorem.");
		// 定义一个变量a来接收输入的第一个值
		int a = readInt("a: ");
		// 定义一个变量b来接收输入的第二个值
		int b = readInt("b: ");
		// 定义一个变量c来表示开平方计算后的结果
		double c = Math.sqrt((a * a) + (b * b));
		println("c = " + c);
	}
}
