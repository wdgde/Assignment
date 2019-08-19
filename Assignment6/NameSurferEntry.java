/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	// 保存名字
	private String name;
	// 保存每一年的排名
	private String rank;
	// 定义一个字符串数组，保存每个名字对应的每一年的排名
	private String[] ranklist = new String[NDECADES];
	// 定义一个字符串，用于组合数据
	private String strdata = " ";

	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers giving
	 * the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {
		// You fill this in //
		// 按指定要求切割字符串
		name = line.substring(0, line.indexOf(" "));
		rank = line.substring(line.indexOf(" ") + 1);
		// 以空格为间隔分割字符串，并把每一个子串存到数组
		ranklist = rank.split(" ");
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The decade
	 * value is an integer indicating how many decades have passed since the first
	 * year in the database, which is given by the constant START_DECADE. If a name
	 * does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		return Integer.parseInt(ranklist[(decade - START_DECADE) / (NDECADES - 1)]);
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a NameSurferEntry.
	 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		for (int i = 0; i < NDECADES; i++) {
			strdata += ranklist[i] + " ";
		}
		return name + "  [" + strdata + "]";
	}
}
