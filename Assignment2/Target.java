/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	public void run() {
		/* You fill this in. */
		// 第一个红色外圆
		firstOval();
		// 第二个白色中间圆
		secondOval();
		// 第三个红色内圆
		thirdOval();
	}
	
	private void firstOval() {
		GOval oval=new GOval(getWidth() / 2 - 72, getHeight() / 2 - 72, 72 * 2, 72 * 2);
		oval.setFilled(true);
		oval.setFillColor(Color.RED);
		oval.setColor(Color.WHITE);
		add(oval);
	}
	
	private void secondOval() {
		GOval oval=new GOval(getWidth() / 2 - 46.8, getHeight() / 2 - 46.8, 46.8 * 2, 46.8 * 2);
		oval.setFilled(true);
		oval.setFillColor(Color.WHITE);
		oval.setColor(Color.WHITE);
		add(oval);
	}
	
	private void thirdOval() {
		GOval oval=new GOval(getWidth() / 2 - 21.6, getHeight() / 2 - 21.6, 21.6 * 2, 21.6 * 2);
		oval.setFilled(true);
		oval.setFillColor(Color.RED);
		oval.setColor(Color.WHITE);
		add(oval);
	}
}
