import stanford.karel.SuperKarel;

public class SquareKarel extends SuperKarel {
	// 四边形
	private int side = 4;
	// 整个Karel世界的边长
	private int length = 1;

	public void run() {
		for (int i = 0; i < side; i++) {
			// 从左下角开始第一条边时
			if (i == 0) {
				turnLeft();
				move();
			} else {
				// 后面三条边
				turnAround();
				move();
			}
			turnRight();
			while (frontIsClear()) {
				move();
				if (noBeepersPresent()) {
					putBeeper();
				}
				length++;
			}
			// 每条边的最后一个点是否有箱子
			if (!noBeepersPresent()) {
				pickBeeper();
			}
			// 如果Karel世界的边长小于3，return
			if (length < 3) {
				return;
			}
		}
	}
}
