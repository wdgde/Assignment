import java.util.ArrayList;

public class StringQueue implements MinimalStringQueue {
	private ArrayList<String> queueList;

	public StringQueue() {
		queueList = new ArrayList<String>();
	}

	// 添加一个字符串
	@Override
	public void add(String str) {
		// TODO Auto-generated method stub
		queueList.add(str);
	}

	// 移除一个字符串，队列先进先出
	@Override
	public String poll() {
		// TODO Auto-generated method stub
		if (queueList.get(0) != null) {
			String firstQueueString = queueList.get(0);
			queueList.remove(0);
			return firstQueueString;
		}
		return null;
	}

	// 大小
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return queueList.size();
	}
}
