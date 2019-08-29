import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class Histogram extends ConsoleProgram {
	private ArrayList<Integer> gradeList = new ArrayList<Integer>();

	public void run() {
		try {
			readFile("MidtermScores.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readFile(String filename) throws IOException {
		String str;
		// 读文件
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while ((str = reader.readLine()) != null) {
			// 将字符转int
			gradeList.add(Integer.parseInt(str));
		}
		for (int i = 0; i < 11; i++) {
			// 输出第一行
			if (i == 0) {
				print("00-09: ");
			}
			// 输出最后一行
			if (i == 10) {
				print("  100: ");
			}
			// 输出中间行
			if (i != 0 && i != 10) {
				print((i * 10) + "-" + (i * 10 + 9) + ": ");
			}
			// 输出*
			for (int j = 0; j < gradeList.size(); j++) {
				if (gradeList.get(j) / 10 == i) {
					print("*");
				}
			}
			println();
		}
	}
}
