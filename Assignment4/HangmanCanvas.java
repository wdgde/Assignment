/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */


import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		// 竖杆
		GLine ScaffoldLine = new GLine(getWidth() / 2 - BEAM_LENGTH, TOP, getWidth() / 2 - BEAM_LENGTH, TOP + SCAFFOLD_HEIGHT);
		add(ScaffoldLine);
		// 横杆
		GLine BeamLine = new GLine(getWidth() / 2 - BEAM_LENGTH, TOP, getWidth() / 2, TOP);
		add(BeamLine);
		// 绳子
		GLine RopeLine = new GLine(getWidth() / 2, TOP, getWidth() / 2, TOP + ROPE_LENGTH);
		add(RopeLine);
		// 玩家猜测正确的单词
		WordLabel = new GLabel("");
		WordLabel.setFont("SansSerif-24");
		WordLabel.setLocation(Lable_LEFT, getHeight() - Lable_BOTTOM);
		add(WordLabel);
		// 玩家猜测错误的单词
		LetterLabel = new GLabel("");
		LetterLabel.setFont("SansSerif-12");
		LetterLabel.setLocation(Lable_LEFT, getHeight() - Lable_BOTTOM + Lable_SEPARATION);
		add(LetterLabel);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		// 更新玩家猜对的单词
		WordLabel.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		/* You fill this in */
		// 将用户猜错的字母累加
		StrLetter += letter;
		LetterLabel.setLabel(StrLetter);
		switch(Turns) {
		case 8:
			// 第1次猜错，头部
			GOval HeadOval=new GOval(getWidth() / 2 - HEAD_RADIUS, TOP + ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
			add(HeadOval);
			Turns--;
			break;
		case 7:
			// 第2次猜错，身体
			GLine BodyLine = new GLine(getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2, getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
			add(BodyLine);
			Turns--;
			break;
		case 6:
			// 第3次猜错，左臂
			GLine LeftArmLine = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
			add(LeftArmLine);
			GLine LeftHandLine = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, getWidth() / 2 - UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add(LeftHandLine);
			Turns--;
			break;
		case 5:
			// 第4次猜错，右臂
			GLine RightArmLine = new GLine(getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, getWidth() / 2 + UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
			add(RightArmLine);
			GLine RightHandLine = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, getWidth() / 2 + UPPER_ARM_LENGTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add(RightHandLine);
			Turns--;
			break;
		case 4:
			// 第5次猜错，左腿
			GLine LeftLegLineX = new GLine(getWidth() / 2 - HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH, getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
			add(LeftLegLineX);
			GLine LeftLegLineY = new GLine(getWidth() / 2 - HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH, getWidth() / 2 - HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
			add(LeftLegLineY);
			Turns--;
			break;
		case 3:
			// 第6次猜错，右腿
			GLine RightLegLineX = new GLine(getWidth() / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
			add(RightLegLineX);
			GLine RightLegLineY = new GLine(getWidth() / 2 + HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
			add(RightLegLineY);
			Turns--;
			break;
		case 2:
			// 第7次猜错，左脚
			GLine LeftFootLineX = new GLine(getWidth() / 2 - HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH, getWidth() / 2 - HIP_WIDTH / 2 - FOOT_LENGTH, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
			add(LeftFootLineX);
			Turns--;
			break;
		case 1:
			// 第8次猜错，右脚
			GLine RightFootLineX = new GLine(getWidth() / 2 + HIP_WIDTH / 2, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH, getWidth() / 2 + HIP_WIDTH / 2 + FOOT_LENGTH, TOP + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
			add(RightFootLineX);
			Turns--;
			break;
		}
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 120;
	private static final int LOWER_ARM_LENGTH = 40;
	private static final int HIP_WIDTH = 60;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int TOP = 20;
	private static final int Lable_LEFT = 20;
	private static final int Lable_BOTTOM = 50;
	private static final int Lable_SEPARATION = 30;
	private int Turns = 8;
	private GLabel WordLabel;
	private GLabel LetterLabel;
	private String StrLetter = "";
	
}
