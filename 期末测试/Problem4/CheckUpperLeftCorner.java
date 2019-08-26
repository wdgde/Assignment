import acm.program.ConsoleProgram;

public class CheckUpperLeftCorner extends ConsoleProgram {
	// 3x3大小的二维数组
	private int col = 3;
	private int row = 3;
	// 保存数组中的每一个值
	private int matrixnum = 0;
	// 初始化一个二维数组
	private int[][] matrix = new int[][] { { 1, 2, 3 }, { 6, 5, 4 }, { 8, 7, 9 } };

	public void run() {
		for (int i = 0; i < col * row; i++) {
			// 取出数组中的每一个值
			matrixnum = matrix[i / col][i % col];
			if (!checkTable(i)) {
				return;
			}
		}
		println("TRUE");
	}

	// 二维数组的每一个值与后面的值比较
	private boolean checkTable(int start) {
		// 列数加1
		int jstart = (start + 1) % col;
		// 行数加1
		for (int i = (start + 1) / col; i < col; i++) {
			for (int j = jstart; j < row; j++) {
				if (matrixnum > 9 || matrixnum < 1) {
					println("FALSE");
					return false;
				}
				if (matrixnum == matrix[i][j]) {
					println("FALSE");
					return false;
				}
			}
			// 新的一行，列数从0开始
			jstart = 0;
		}
		return true;
	}
}
