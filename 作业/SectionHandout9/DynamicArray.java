import acm.program.ConsoleProgram;

public class DynamicArray extends ConsoleProgram {
	// 下标
	private int index;
	// 存入的值
	private Object value;
	// 通过该下标获取值
	private int getValue;
	// 输入结束标志
	private static final int END = 11111;
	// new一个ExpandableArray对象
	private ExpandableArray myList = new ExpandableArray();

	public void run() {
		saveValue();
		getvalue();
	}

	// 将值存入数组的指定下标位置
	private void saveValue() {
		while (true) {
			index = readInt("Please input a num: ");
			if (index < 0) {
				println("Wrong Input!");
				continue;
			}
			if (index == END) {
				break;
			}
			value = readLine("Please input a value: ");
			myList.set(index, value);
		}
	}

	// 从数组指定下标处取出对应值
	private void getvalue() {
		while (true) {
			getValue = readInt("Please input a num to get value: ");
			if (getValue < 0) {
				println("Wrong Input!");
				continue;
			}
			if (getValue == END) {
				break;
			}
			println(getValue + "----" + myList.get(getValue));
		}
	}
}
