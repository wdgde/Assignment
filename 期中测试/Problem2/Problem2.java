import acm.program.ConsoleProgram;

public class Problem2 extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		problem2a();
		problem2b();
	}

	// 问题2a
	public void problem2a() {
		println(5.0 / 4 - 4 / 5);
		if (!(7 < 9 - 5 && 3 % 0 == 3)) {
			println("False");
		}
		println("B" + 8 + 4);
	}

	// 问题2b
	public void problem2b() {
		int num1 = 2;
		int num2 = 13;
		println(Mystery(num1, 6));
		println(Mystery(num2 % 5, 1 + num1 * 2));
	}

	private int Mystery(int num1, int num2) {
		num1 = Unknown(num1, num2);
		num2 = Unknown(num2, num1);
		return num2;
	}

	private int Unknown(int num1, int num2) {
		int num3 = num1 + num2;
		num2 += num3 * 2;
		return num2;
	}
}