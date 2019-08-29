import java.util.ArrayList;

/**
 * This class provides methods for working with an array that expands to include
 * any positive index value supplied by the caller.
 */
public class ExpandableArray {
	// ArrayList数组来存放值
	private ArrayList<Object> expandableArray;

	/**
	 * Creates a new expandable array with no elements.
	 */
	public ExpandableArray() {
		expandableArray = new ArrayList<Object>();
	}

	/**
	 * Sets the element at the given index position to the specified. value. If the
	 * internal array is not large enough to contain that element, the
	 * implementation expands the array to make room.
	 */
	public void set(int index, Object value) {
		// 当下标大于当前数组的大小时，扩展数组
		if (index > expandableArray.size()) {
			for (int i = expandableArray.size(); i < index; i++) {
				expandableArray.add(null);
			}
			expandableArray.add(value);
		} else {
			// 替换值
			expandableArray.set(index, value);
		}
	}

	/**
	 * Returns the element at the specified index position, or null if no such
	 * element exists. Note that this method never throws an out-of-bounds
	 * exception; if the index is outside the bounds of the array, the return value
	 * is simply null.
	 */
	public Object get(int index) {
		if (expandableArray.get(index) != null) {
			return expandableArray.get(index);
		}
		return null;
	}
}
