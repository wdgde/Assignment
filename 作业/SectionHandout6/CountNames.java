import java.util.HashMap;
import java.util.Iterator;

import acm.program.ConsoleProgram;

public class CountNames extends ConsoleProgram {
	// 保存输入的name
	private HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

	public void run() {
		// 添加name
		addName();
		// 输出计数结果
		printNameCount();
	}

	private void addName() {
		// 保存输入的name
		String name;
		// 是否添加标记
		boolean isAdd;
		// 当输入不为空时
		while (!(name = readLine("Enter name: ")).equals("")) {
			isAdd = true;
			if (nameMap.isEmpty()) {
				nameMap.put(name, 1);
				continue;
			}
			// 迭代器
			Iterator<String> hashmapIter = nameMap.keySet().iterator();
			while (hashmapIter.hasNext()) {
				// 取下一个key值ֵ
				String keyName = hashmapIter.next();
				// 判断是否有相同的key值
				if (keyName.equals(name)) {
					isAdd = false;
					int nameCount = nameMap.get(keyName);
					nameCount++;
					nameMap.put(keyName, nameCount);
				}
			}
			if (isAdd) {
				nameMap.put(name, 1);
			}
		}
	}

	private void printNameCount() {
		Iterator<String> hashmapIter = nameMap.keySet().iterator();
		while (hashmapIter.hasNext()) {
			String keyName = hashmapIter.next();
			println("Entry [" + keyName + "] has count " + nameMap.get(keyName));
		}
	}
}
