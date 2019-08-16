/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acm.util.*;
import java.util.*;

public class HangmanLexicon {
	// 定义一个变量表示文件中单词的个数
	private int WordNums = 0;
	// 定义一个数组表来存放每一个单词
	private ArrayList<String> wordlist = new ArrayList<String>();
	public HangmanLexicon() {
		String FileName = "HangmanLexicon.txt";//"ShorterLexicon.txt";//"HangmanLexicon.txt";
		String str;
		try {
			// 读文件
			BufferedReader reader = new BufferedReader(new FileReader(FileName));
			// 当读到的文件行不为空时
			while((str = reader.readLine()) != null) {
				wordlist.add(str);
				WordNums++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		// 返回单词数
		return WordNums;
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		/*switch (index) {
			case 0: return "BOUOY";
			case 1: return "CTOMPUTER";
			case 2: return "CONNOISSEUR";
			case 3: return "DEAHYDRATEE";
			case 4: return "FYUZZY";
			case 5: return "HUBBUB";
			case 6: return "KLEYHOLEE";
			case 7: return "QURAGMIREE";
			case 8: return "SLEITHERR";
			case 9: return "ZOIRCONN";
			default: throw new ErrorException("getWord: Illegal index");
		}*/
		// 返回某个单词
		return wordlist.get(index);
	}
	
}
