import acm.program.ConsoleProgram;

public class DeleteCharacters extends ConsoleProgram {
	public void run() {
		println(removeAllOccurrences("----88----6--8--9--8--", '-'));
	}

	// 删除字符串str中的所有ch字符
	public String removeAllOccurrences(String str, char ch) {
		// 保存除ch字符之外的字符
		String simplestr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ch) {
				simplestr += str.charAt(i);
			}
		}
		return simplestr;
	}
}
