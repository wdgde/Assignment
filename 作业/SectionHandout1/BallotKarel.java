import stanford.karel.SuperKarel;

public class BallotKarel extends SuperKarel {
	public void run() {
		while (frontIsClear()) {
			move();
			// 当前位置没有箱子时
			if (noBeepersPresent()) {
				// 向上收箱子
				upCheckBeeper();
				// 向下收箱子
				downCheckBeeper();
				// 找到向右边走的路
				findWay();
			}
			move();
		}
	}

	// 向上走，如果有箱子则捡起
	private void upCheckBeeper() {
		turnLeft();
		while (frontIsClear()) {
			move();
			while (beepersPresent()) {
				pickBeeper();
			}
		}
		turnAround();
	}

	// 向下走，如果有箱子则捡起
	private void downCheckBeeper() {
		while (frontIsClear()) {
			move();
			while (beepersPresent()) {
				pickBeeper();
			}
		}
		turnAround();
	}

	// 向中间走，如果向右边是可以通行的，则向右边走
	private void findWay() {
		while (true) {
			turnRight();
			if (frontIsClear()) {
				break;
			}
			turnLeft();
			move();
		}
	}
}
