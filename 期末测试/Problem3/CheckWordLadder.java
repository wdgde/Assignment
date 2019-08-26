import acm.program.ConsoleProgram;

public class CheckWordLadder extends ConsoleProgram {
	public void run() {
		// 接收输入的字符串
		String word = "";
		// 存放上一个输入的合法的字符串
		String lastword = "";
		// 记录字符串的输入次数
		int wordnum = 0;
		// 记录两个字符串不同字母的个数
		int differentnum;
		println("Program to check a word ladder.");
		println("Enter a sequence of words ending with a blank line.");
		// 当输入不为空时
		while (!(word = readLine()).equals("")) {
			word.toUpperCase();
			differentnum = 0;
			// 输入的第一个字符串
			if (wordnum == 0) {
				lastword = word;
				wordnum++;
				continue;
			}
			// 两个字符串长度不一致时
			if (word.length() != lastword.length()) {
				println("That word is not legal.  Try again.");
				continue;
			}
			// 判断两个字符串相同位置的字母是否相同
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) != lastword.charAt(i)) {
					differentnum++;
				}
			}
			if (differentnum == 0 || differentnum > 1) {
				println("That word is not legal.  Try again.");
				continue;
			}
			lastword = word;
		}
		println("Input over.");
	}
}
