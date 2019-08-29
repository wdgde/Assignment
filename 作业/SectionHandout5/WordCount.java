import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class WordCount extends ConsoleProgram {
	private ArrayList<String> lineList = new ArrayList<String>();

	public void run() {
		// 输入文件名
		String filename = readLine("Please input the file name: ");
		try {
			readFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readFile(String filename) throws IOException {
		// 保存从文件中读出的每一行
		String str;
		// 单词数
		int wordNum = 0;
		// 字符数
		int charNum = 0;
		// 读文件
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while ((str = reader.readLine()) != null) {
			lineList.add(str);
		}
		println("Lines = " + lineList.size());

		for (int i = 0; i < lineList.size(); i++) {
			for (int j = 0; j < lineList.get(i).length(); j++) {
				if (lineList.get(i).charAt(j) == '\'' || lineList.get(i).charAt(j) == ' ') {
					wordNum++;
				}
			}
			// 计算字符数
			charNum += lineList.get(i).length();
			// 把每一行最后一个单词加上
			wordNum++;
		}
		println("Words = " + wordNum);
		println("Chars = " + charNum);
	}

}
