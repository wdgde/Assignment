import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	// 定义一个HashMap，用来存放文件中读出来的名字及排名 <name ,NameSurferEntry对象>
	HashMap<String, NameSurferEntry> SurferEntry = new HashMap<String, NameSurferEntry>();

	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 * 
	 * @throws IOException
	 */
	public NameSurferDataBase(String filename) throws IOException {
		// You fill this in //
		String str;
		// 读文件
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while ((str = reader.readLine()) != null) {
			NameSurferEntry enty = new NameSurferEntry(str);
			// 把每一个NameSurferEntry对象都加入到hashmap中
			SurferEntry.put(enty.getName().toLowerCase(), enty);
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		// You need to turn this stub into a real implementation //
		// 通过用户输入的name作为key值去hashmap中查找对应的NameSurferEntry对象
		NameSurferEntry enty = SurferEntry.get(name.toLowerCase());
		if (enty != null) {
			return enty;
		}
		return null;
	}
}
