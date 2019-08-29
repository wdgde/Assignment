import acm.program.ConsoleProgram;

public class FibonacciSequence extends ConsoleProgram {
	// 最大值
	private static final int MAX_TERM_VALUE = 10000;
	// Fib(0) = 0
	private int firstNum = 0;
	// Fib(1) = 1
	private int secondNum = 1;
	// 第三个数
	private int term = 0;

	public void run() {
		while (firstNum < MAX_TERM_VALUE) {
			println(firstNum);
			term = firstNum + secondNum;
			firstNum = secondNum;
			secondNum = term;
		}
	}
}
