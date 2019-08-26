import acm.program.ConsoleProgram;

public class Problem3 extends ConsoleProgram {
	// 定义一个表示输入结束的值
	private static final int END = 0;

	public void run() {
		/* You fill this in */
		println("This program finds the largest and secondlargest numbers.");
		// 定义一个变量接收输入的值
		int number = 0;
		// 定义并初始化一个变量表示最大值
		int largest = 0;
		// 定义并初始化一个变量表示第二大值
		int secondlargest = 0;
		// 定义一个变量用来记录输入次数
		int i = 0;
		// 当输入的值不表示输入结束时
		while ((number = readInt("? ")) != END) {
			// 把第一次输入的值都赋值给最大值和第二大值
			if (i == 0) {
				secondlargest = number;
				largest = number;
			}
			if (largest <= number) {
				int temp = largest;
				largest = number;
				secondlargest = temp;
			} else if (secondlargest <= number) {
				secondlargest = number;
			}
			i++;
		}
		if (i == 0) {
			// 第一次就输入了表示输入结束的值
			println("No values have been entered!");
		} else {
			println("secondlargest: " + secondlargest);
			println("largest: " + largest);
		}
	}
}
