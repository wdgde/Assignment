import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class FlipHorizontal extends GraphicsProgram {
	public void run() {
		// 正常图片
		GImage image1 = new GImage("AaronB.jpg");
		add(image1, 50, 50);
		// 左右变换后的图片
		GImage image2 = flipHorizontal(new GImage("AaronB.jpg"));
		add(image2, 270, 50);
		// 上下变化后的图片
		GImage image3 = flipVertical(new GImage("AaronB.jpg"));
		add(image3, 500, 50);
	}

	// 左边像素赋值给右边，右边像素赋值给左边
	private GImage flipHorizontal(GImage image) {
		int[][] array = image.getPixelArray();
		int width = array[0].length;
		int height = array.length;
		for (int row = 0; row < height; row++) {
			for (int p1 = 0; p1 < width / 2; p1++) {
				int p2 = width - p1 - 1;
				int temp = array[row][p1];
				array[row][p1] = array[row][p2];
				array[row][p2] = temp;
			}
		}
		return new GImage(array);
	}

	// 上边像素赋值给下边，下边像素赋值给上边
	private GImage flipVertical(GImage image) {
		int[][] array = image.getPixelArray();
		int width = array[0].length;
		int height = array.length;
		for (int col = 0; col < width; col++) {
			for (int p1 = 0; p1 < height / 2; p1++) {
				int p2 = height - p1 - 1;
				int temp = array[p1][col];
				array[p1][col] = array[p2][col];
				array[p2][col] = temp;
			}
		}
		return new GImage(array);
	}
}
