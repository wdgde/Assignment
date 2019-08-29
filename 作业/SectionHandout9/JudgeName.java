import acm.program.ConsoleProgram;

public class JudgeName extends ConsoleProgram {
	public void run() {
		while (true) {
			// 第一种方式
			if (IsNameQ1()) {
				println("TRUE");
			} else {
				println("FALSE");
			}
			// 第二种方式
			if (IsNameQ2()) {
				println("TRUE");
			} else {
				println("FALSE");
			}
		}

	}

	private boolean IsNameQ1() {
		String name = readLine("Enter name: ");
		return (name == "Q");
	}

	private boolean IsNameQ2() {
		String name = readLine("Enter name: ");
		char ch = name.charAt(0);
		return (ch == 'Q') && (name.length() == 1);
	}
}
