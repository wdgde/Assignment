import acm.program.ConsoleProgram;

public class AddCommas extends ConsoleProgram {
	public void run() {
		while (true) {
			String digits = readLine("Enter a numeric string: ");
			// 输入为空时结束
			if (digits.length() == 0)
				break;
			println(addCommasToNumericString(digits));
		}
	}

	private String addCommasToNumericString(String digits) {
		// 保存分隔之后的字符串
		String str = "";
		// 计数，每隔3个加一个逗号
		int eachThree = 0;
		// 从最后面开始
		for (int i = digits.length() - 1; i >= 0; i--) {
			eachThree++;
			if (eachThree == 4) {
				str = "," + digits.substring(i + 1, i + 4) + str;
				// i重新加1
				i++;
				eachThree = 0;
			}

		}
		str = digits.substring(0, eachThree) + str;
		return str;
	}
}
