import acm.program.ConsoleProgram;

public class Problem5 extends ConsoleProgram {
	private String simpleword = "";
	private String complexword = "";

	public void run() {
		String word;
		println("Please input a complexword:");
		while (!(word = readLine()).matches("[a-zA-Z]+$")) {
			println("Wrong input!!");
		}
		removeRepeatedLetters(word);
	}

	private void removeRepeatedLetters(String word) {
		complexword = word;
		word = word.toLowerCase();
		for (int i = 0; i < word.length() - 1; i++) {
			// 当i不超过 word.length() - 1且相邻字母相同时
			while (i < word.length() - 1 && word.charAt(i) == word.charAt(i + 1)) {
				i++;
			}
			// 将字母添加到字符串中
			simpleword += complexword.charAt(i);
			// 将最后一个字母添加
			if (i == word.length() - 2) {
				simpleword += complexword.charAt(i + 1);
			}
		}
		println("The simpleword is: " + simpleword);
	}
}
