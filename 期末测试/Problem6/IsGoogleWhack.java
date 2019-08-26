import acm.program.ConsoleProgram;

public class IsGoogleWhack extends ConsoleProgram {
	// 初始化两个字符串数组
	private String[] firstpage = new String[] { "aa", "bb", "cc", "dd", "ee", "ff", "gg" };
	private String[] secondpage = new String[] { "wd", "db", "cc" };
	// 记录两个字符串数组中相同字符串的个数
	private int pagenum = 0;

	public void run() {
		if (isGooglewhack("", "")) {
			println("TRUE");
		} else {
			println("FALSE");
		}
	}

	public boolean isGooglewhack(String w1, String w2) {
		for (int i = 0; i < firstpage.length; i++) {
			for (int j = 0; j < secondpage.length; j++) {
				if (firstpage[i].equals(secondpage[j])) {
					pagenum++;
				}
			}
		}
		if (pagenum == 1) {
			return true;
		}
		return false;
	}

}
