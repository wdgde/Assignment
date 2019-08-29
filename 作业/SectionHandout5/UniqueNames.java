import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class UniqueNames extends ConsoleProgram {
	// 保存输入的名字的数组
	private ArrayList<String> nameList = new ArrayList<String>();

	public void run() {
		// 保存输入的名字
		String name;
		// 是否添加的进数组的标志
		boolean isAdd;
		// 当输入不为空时
		while (!(name = readLine("Enter name: ")).equals("")) {
			isAdd = true;
			for (int i = 0; i < nameList.size(); i++) {
				// 如果输入的值和数组中的某个值相同时
				if (nameList.get(i).equals(name)) {
					isAdd = false;
					break;
				}
			}
			if (isAdd) {
				nameList.add(name);
			}
		}
		println("Unique name list contains:");
		for (int i = 0; i < nameList.size(); i++) {
			println(nameList.get(i));
		}

	}
}
