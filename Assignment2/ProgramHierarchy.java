/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	/**���εĳ�*/
	private static final int Box_WIDTH = 120;
    /**���εĸ�*/
	private static final int Box_HEIGHT = 60;
	public void run() {
		/* You fill this in. */
		// 第一行第1个矩形
		firstRowFirstRect();
		// 第二行第1个矩形
		secondRowFirstRect();
		// 第二行第2个矩形
		secondRowSecondRect();
		// 第二行第3个矩形
		secondRowThirdRect();
		// 第一条线
		firstLine();
		// 第二条线
		secondLine();
		// 第三条线
		thirdLine();
	}
	
	private void firstRowFirstRect() {
		// 第一行第1个矩形
		GRect rect = new GRect(getWidth() / 2 - Box_WIDTH / 2, getHeight() / 2 - 120, Box_WIDTH, Box_HEIGHT);
		add(rect);
		// 第一行第1个矩形里面的文本内容
		GLabel label = new GLabel("Program");
		label.setLocation(getWidth() / 2 - Box_WIDTH / 2 + (Box_WIDTH - label.getWidth()) / 2, getHeight() / 2 - 120 + Box_HEIGHT / 2 + label.getAscent() / 2);
		add(label);
	}
	
	private void secondRowFirstRect() {
		// 第二行第1个矩形
		GRect rect = new GRect(getWidth() / 2 - Box_WIDTH / 2 - Box_WIDTH - 50, getHeight() / 2, Box_WIDTH, Box_HEIGHT);
		add(rect);		
		// 第二行第1个矩形里面的文本内容
		GLabel label = new GLabel("GraphicsProgram");
		label.setLocation(getWidth() / 2 - Box_WIDTH / 2- Box_WIDTH - 50+ (Box_WIDTH - label.getWidth()) / 2, getHeight() / 2 + Box_HEIGHT / 2 + label.getAscent() / 2);
		add(label);
	}
	
	private void secondRowSecondRect() {		
		// 第二行第2个矩形
		GRect rect = new GRect(getWidth() / 2 - Box_WIDTH / 2, getHeight() / 2, Box_WIDTH, Box_HEIGHT);
		add(rect);
		// 第二行第2个矩形里面的文本内容
		GLabel label = new GLabel("ConsoleProgram");
		label.setLocation(getWidth() / 2 - label.getWidth() / 2 , getHeight() / 2 + Box_HEIGHT / 2 + label.getAscent() / 2);
		add(label);
	}
	
	private void secondRowThirdRect() {
		// 第二行第3个
		GRect rect = new GRect(getWidth() / 2 - Box_WIDTH / 2 + Box_WIDTH + 50, getHeight() / 2, Box_WIDTH,Box_HEIGHT);
		add(rect);
		// 第二行第3个矩形里面的文本内容
		GLabel label = new GLabel("DialogProgram");
		label.setLocation(getWidth() / 2 + Box_WIDTH + 50 - label.getWidth() / 2 , getHeight() / 2 + Box_HEIGHT / 2 + label.getAscent() / 2);
		add(label);
	}
	
	// 第一条连接线
	private void firstLine() {
		GLine line = new GLine(getWidth() / 2, getHeight() / 2 - 120 + Box_HEIGHT, getWidth() / 2, getHeight() / 2);
		add(line);
	}
	
	// 第二条连接线
	private void secondLine() {
		GLine line = new GLine(getWidth() / 2, getHeight() / 2 - 120 + Box_HEIGHT, getWidth() / 2 - Box_WIDTH - 50, getHeight() / 2);
		add(line);
	}
	
	// 第三条连接线
	private void thirdLine() {
		GLine line = new GLine(getWidth() / 2, getHeight() / 2 - 120 + Box_HEIGHT, getWidth() / 2 + Box_WIDTH + 50, getHeight() / 2);
		add(line);
	}
	
}

