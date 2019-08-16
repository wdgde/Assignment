
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

public class Hangman extends ConsoleProgram {
	// 定义一个随机数生成器
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// 定义一个变量存放随机数
	private int index;
	// 定义一个变量存放正确的单词
	private String CorrectWord;
	// 定义一个变量存放不完整的单词
	private String word;
	// 定义一个变量存放玩家猜测的单词
	private String unword = "";
	// 玩家剩下猜词次数
	private int turns = 8;
	// 获取单词的类
	private HangmanLexicon hangmanlexicon;
	// 绘制图画的类
	private HangmanCanvas canvas;

	public void run() {
		/* You fill this in */
		// 设置窗口字体大小
		// setFont("Courier-24");
		// 开始游戏
		startGame();
	}

	private void startGame() {
		// 初始化画面
		canvas.reset();
		println("Welcome to Hangman!");
		hangmanlexicon = new HangmanLexicon();
		// 随机生成一个一定范围内的整数
		index = rgen.nextInt(0, hangmanlexicon.getWordCount() - 1);
		// 获取一个单词
		CorrectWord = word = hangmanlexicon.getWord(index);
		// 计算该单词长度，并以‘-’表示
		for (int i = 0; i < word.length(); i++) {
			unword += "-";
		}
		println(word);
		println("The word now looks like this: " + unword);
		// 玩家开始猜词
		guessWord();
	}

	private void guessWord() {
		// 当剩下猜词次数不等于0时
		while (turns != 0) {
			println("You have " + turns + " guesses left.");
			String Letter = readLine("Your guess: ");
			// 判断玩家输入是否合法
			/*
			 * if (Letter.equals("") || Letter.length() == 0) { println("Wrong input!!!");
			 * continue; }
			 */
			if (!Letter.matches("[a-zA-Z]")) {
				println("Wrong input!!!");
				continue;
			}
			// 将输入的内容全部转换成大写
			Letter = Letter.toUpperCase();
			// 判断玩家是否猜对
			checkWord(Letter);
		}
	}

	private void checkWord(String letter) {
		// 判断正确的单词中是否含有玩家猜测的字母
		boolean status = word.contains(letter);
		// 猜词正确
		if (status) {
			correctGuess(letter);
			// 猜词失败
		} else {
			wrongGuess(letter);
		}
	}

	// 初始化右边框
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	private void correctGuess(String letter) {
		// 将猜测正确的字母替换掉“-”
		unword = unword.substring(0, word.indexOf(letter)) + letter
				+ unword.substring(word.indexOf(letter) + 1, word.length());
		// 用“-”替换猜测正确的字母
		word = word.substring(0, word.indexOf(letter)) + "-" + word.substring(word.indexOf(letter) + 1, word.length());
		println("That guess is correct.");
		// 右边框显示猜测正确的字母
		canvas.displayWord(unword);
		// 判断是否赢得游戏
		isWin();
		if (turns != 0) {
			println("The word now looks like this: " + unword);
		}
	}

	private void wrongGuess(String letter) {
		println("There are no " + letter + "'s in the word.");
		// 右边框显示猜测错误的字母
		canvas.noteIncorrectGuess(letter.charAt(0));
		turns--;
		// 判断是否输掉比赛
		isLose();
	}

	private void isWin() {
		// 如果玩家猜测的单词中不再含有“-”，则赢得游戏
		if (!unword.contains("-")) {
			println("You guessed the word: " + CorrectWord);
			println("You win.");
			turns = 0;
		}
	}

	private void isLose() {
		// 如果剩余猜词次数为0，则输掉游戏
		if (turns == 0) {
			println("You're completely hung.");
			println("The word was: " + CorrectWord);
			println("You lose.");
		}
	}

}
