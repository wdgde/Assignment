import java.util.HashMap;
import java.util.Iterator;

import acm.program.ConsoleProgram;

public class CommonKeyValuePairs extends ConsoleProgram {
	// 记录两个hashmap中拥有相同键值对的个数
	private int sameNum = 0;
	HashMap<String, String> firstmap = new HashMap<String, String>();
	HashMap<String, String> secondmap = new HashMap<String, String>();

	public void run() {
		// 为两个hashmap放入不同的键值对
		firstmap.put("A", "1");
		firstmap.put("B", "2");
		firstmap.put("C", "3");
		firstmap.put("D", "4");
		secondmap.put("A", "1");
		secondmap.put("B", "3");
		secondmap.put("F", "3");
		secondmap.put("G", "4");
		println(commonKeyValuePairs(firstmap, secondmap));
	}

	private int commonKeyValuePairs(HashMap<String, String> map1, HashMap<String, String> map2) {
		// 迭代器 ，用来遍历hashmap
		Iterator<String> hashmapIter = map1.keySet().iterator();
		while (hashmapIter.hasNext()) {
			// 取出key值
			String keyName = hashmapIter.next();
			// 通过key值得到相应的value
			if (map1.get(keyName).equals(map2.get(keyName))) {
				sameNum++;
			}
		}
		return sameNum;
	}
}
