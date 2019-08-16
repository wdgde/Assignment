
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import acm.io.IODialog;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	/* Private instance variables */
	// 玩家数
	private int nPlayers;
	// 每个玩家姓名
	private String[] playerNames;
	// 玩家姓名
	private String playerName;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	// 保存每个骰子数的数组
	private int[] dice = new int[N_DICE];
	// 每个玩家在每个得分类别的分数
	private int[][] playerScores;
	// 每个玩家的每个得分类别的可选状态
	private int[][] categoryState;
	// 排行榜文件名
	private String FileName = "RankingList.txt";
	// 排行榜玩家数
	private int Ranker = 10;
	// 玩家排行榜数组
	private ArrayList<RankList> ranklist = new ArrayList<RankList>();

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		// 检查玩家数量的合法性
		checkPlayers();
		// 玩家姓名
		playerNames = new String[nPlayers];
		// 每个玩家的分数
		playerScores = new int[nPlayers][N_CATEGORIES];
		// 每个玩家的骰子类别
		categoryState = new int[nPlayers][N_CATEGORIES];
		for (int i = 1; i <= nPlayers; i++) {
			playerName = dialog.readLine("Enter name for player " + i);
			// 检查每个玩家姓名的合法性
			checkPlayerName(i);
			playerNames[i - 1] = playerName;
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		try {
			// 开始游戏
			playGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 玩家数量只能为1-4
	private void checkPlayers() {
		while (nPlayers > MAX_PLAYERS || nPlayers <= 0) {
			IODialog dialog = getDialog();
			dialog.println("The right players was 1-4 !!!");
			nPlayers = dialog.readInt("Enter number of players");
		}
	}

	// 玩家姓名只能是有数字和字母组成的字符串
	private void checkPlayerName(int num) {
		while (!playerName.matches("^[A-Za-z0-9]+$")) {
			IODialog dialog = getDialog();
			dialog.println("The playerNmae wasn't legal !!!");
			playerName = dialog.readLine("Enter name for player " + num);
		}
	}

	// 开始游戏
	private void playGame() throws IOException {
		/* You fill this in */
		// N_SCORING_CATEGORIES轮游戏
		for (int i = 0; i < 2; i++) {
			// nPlayers个玩家
			for (int j = 0; j < nPlayers; j++) {
				display.printMessage(playerNames[j] + "'s turn! Click \"Roll Dice\" button to roll the dice.");
				// 等待玩家点击掷骰子按钮
				display.waitForPlayerToClickRoll(j + 1);
				// 随机生成骰子数
				randomDice();
				// 将骰子数显示
				display.displayDice(dice);
				// 玩三个回合
				threeRound();
				// 选择得分类别
				selectCategory(j);
			}
		}
		// 游戏结束，计算总得分
		calculateTotalScore();
		// 判断胜者
		judgeWinner();
		// 更新排行榜
		updateRankList();
	}

	// 为N_DICE个骰子随机生成1-6的骰子数
	private void randomDice() {
		for (int q = 0; q < N_DICE; q++) {
			dice[q] = rgen.nextInt(1, 6);
		}
	}

	// 每个玩家在每一轮的三个回合中的后面两个回合任意选择0-N_DICE个骰子进行重掷
	private void threeRound() {
		for (int p = 1; p < 3; p++) {
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
			// 等待玩家选择骰子重掷
			display.waitForPlayerToSelectDice();
			for (int n = 0; n < N_DICE; n++) {
				// 如果该骰子被选中，则重掷
				if (display.isDieSelected(n)) {
					dice[n] = rgen.nextInt(1, 6);
					display.displayDice(dice);
				}
			}
		}
	}

	// 没轮游戏结束，选择得分类别，并计算分数
	private void selectCategory(int player) {
		display.printMessage("Select a category for this roll.");
		// 等待用户选择得分类别
		int category = display.waitForPlayerToSelectCategory();
		// 判断是否重复选择
		while (categoryState[player][category - 1] == 1) {
			display.printMessage("This category was selected!! Please select another.");
			category = display.waitForPlayerToSelectCategory();
		}
		// 更新玩家得分
		updateScore(category, player);
		// 将该得分类别置为已选过
		categoryState[player][category - 1] = 1;
	}

	// 更新得分
	private void updateScore(int category, int player) {
		// 判断骰子数是否符合用户选择的得分类别，符合则计算分数，不符合则置为0分
		if (myCheckCategory(dice, category)) {
			// 计算分数
			display.updateScorecard(category, player + 1, calculateScores(category, player));
		} else {
			display.updateScorecard(category, player + 1, 0);
		}
		// 更新总得分
		display.updateScorecard(TOTAL, player + 1, calculateScores(TOTAL, player));
	}

	// 计算每个玩家的总得分
	private void calculateTotalScore() {
		for (int i = 0; i < nPlayers; i++) {
			display.updateScorecard(UPPER_SCORE, i + 1, calculateScores(UPPER_SCORE, i));
			display.updateScorecard(UPPER_BONUS, i + 1, calculateScores(UPPER_BONUS, i));
			display.updateScorecard(LOWER_SCORE, i + 1, calculateScores(LOWER_SCORE, i));
			playerScores[i][TOTAL - 1] = calculateScores(TOTAL, i) - playerScores[i][UPPER_SCORE - 1]
					- playerScores[i][LOWER_SCORE - 1];
			display.updateScorecard(TOTAL, i + 1, playerScores[i][TOTAL - 1]);
		}
	}

	// 判断谁是赢家
	private void judgeWinner() {
		int winnerScore = 0;
		int winnerNum = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (playerScores[i][TOTAL - 1] > winnerScore) {
				winnerScore = playerScores[i][TOTAL - 1];
				winnerNum = i;
			}
		}
		display.printMessage("Congratulations, " + playerNames[winnerNum] + ",you're the winner with a total score of "
				+ winnerScore);
	}

	// 分别计算每个得分类别的分数
	private int calculateScores(int category, int player) {
		int scores = 0;
		switch (category) {
		case ONES:
			scores = onesToSixesScore(dice, category);
			break;
		case TWOS:
			scores = onesToSixesScore(dice, category);
			break;
		case THREES:
			scores = onesToSixesScore(dice, category);
			break;
		case FOURS:
			scores = onesToSixesScore(dice, category);
			break;
		case FIVES:
			scores = onesToSixesScore(dice, category);
			break;
		case SIXES:
			scores = onesToSixesScore(dice, category);
			break;
		case UPPER_SCORE:
			scores = upperScore(playerScores, player);
			break;
		case UPPER_BONUS:
			scores = upperBonusScore(playerScores, player);
			break;
		case THREE_OF_A_KIND:
			scores = threeOrFourOfAKindScore(dice);
			break;
		case FOUR_OF_A_KIND:
			scores = threeOrFourOfAKindScore(dice);
			break;
		case FULL_HOUSE:
			scores = FULL_HOUSE_SCORE;
			break;
		case SMALL_STRAIGHT:
			scores = SMALL_STRAIGHT_SCORE;
			break;
		case LARGE_STRAIGHT:
			scores = LARGE_STRAIGHT_SCORE;
			break;
		case YAHTZEE:
			scores = YAHTZEE_SCORE;
			break;
		case CHANCE:
			scores = chanceScore(dice);
			break;
		case LOWER_SCORE:
			scores = lowerScore(playerScores, player);
			break;
		case TOTAL:
			scores = totalScore(playerScores, player);
			break;
		}
		// 将分数保存到数组中
		playerScores[player][category - 1] = scores;
		return scores;
	}

	// 第1种到第6种类别
	private int onesToSixesScore(int[] dice, int category) {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == category) {
				score += category;
			}
		}
		return score;
	}

	// 第7种到第8种类别
	private int threeOrFourOfAKindScore(int[] dice) {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			score += dice[i];
		}
		return score;
	}

	// 第13种类别
	private int chanceScore(int[] dice) {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			score += dice[i];
		}
		return score;
	}

	// 前6种类别的总分
	private int upperScore(int[][] playerscores, int player) {
		int score = 0;
		for (int i = 0; i < SIXES; i++) {
			score += playerscores[player][i];
		}
		return score;
	}

	// 前6种类别的奖励分
	private int upperBonusScore(int[][] playerscores, int player) {
		int score = 0;
		if (playerscores[player][UPPER_BONUS - 1] >= MAX_UPPER_SCORE) {
			score = UPPER_BONUS_SCORE;
		}
		return score;
	}

	// 后7中类别的总分
	private int lowerScore(int[][] playerscores, int player) {
		int score = 0;
		for (int i = THREE_OF_A_KIND - 1; i < CHANCE; i++) {
			score += playerscores[player][i];
		}
		return score;
	}

	// 13中类别的总分，包括了前6种类别的总分，前6种类别的奖励分，后7中类别的总分
	private int totalScore(int[][] playerscores, int player) {
		int score = 0;
		for (int i = 0; i < N_CATEGORIES - 1; i++) {
			score += playerscores[player][i];
		}
		return score;
	}

	// 分别检查当前骰子数是否符合被选中的得分类别
	private boolean myCheckCategory(int dice[], int category) {
		boolean state = false;
		switch (category) {
		case ONES:
			state = true;
			break;
		case TWOS:
			state = true;
			break;
		case THREES:
			state = true;
			break;
		case FOURS:
			state = true;
			break;
		case FIVES:
			state = true;
			break;
		case SIXES:
			state = true;
			break;
		case THREE_OF_A_KIND:
			state = checkThreeOrFourOfAKindAndYahtzee(dice, 3);
			break;
		case FOUR_OF_A_KIND:
			state = checkThreeOrFourOfAKindAndYahtzee(dice, 4);
			break;
		case FULL_HOUSE:
			state = checkFullHouse(dice);
			break;
		case SMALL_STRAIGHT:
			state = checkSmallOrLargeStraight(dice, 4);
			break;
		case LARGE_STRAIGHT:
			state = checkSmallOrLargeStraight(dice, 5);
			break;
		case YAHTZEE:
			state = checkThreeOrFourOfAKindAndYahtzee(dice, 5);
			break;
		case CHANCE:
			state = true;
			break;
		}
		return state;
	}

	// THREE_OF_A_KIND，FOUR_OF_A_KIND，YAHTZEE 3种得分类别
	private boolean checkThreeOrFourOfAKindAndYahtzee(int dice[], int flag) {
		int num;
		for (int i = 0; i < N_DICE; i++) {
			num = 0;
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					num++;
				}
				if (num >= flag) {
					return true;
				}
			}
		}
		return false;
	}

	// FULL_HOUSE 得分类别
	private boolean checkFullHouse(int dice[]) {
		boolean state = true;
		int numDice[] = new int[5];
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					numDice[i] += 1;
				}
			}
		}
		for (int i = 0; i < N_DICE; i++) {
			if (2 > numDice[i] || numDice[i] > 3) {
				state = false;
			}
		}
		return state;
	}

	// SMALL_STRAIGHT， LARGE_STRAIGHT 两种得分类别
	private boolean checkSmallOrLargeStraight(int dice[], int flag) {
		int num = 1;
		int[] temp = new int[N_DICE];
		for (int i = 0; i < N_DICE; i++) {
			temp[i] = dice[i];
		}
		// 排序
		temp = bubbleSort(temp);
		for (int i = 0; i < N_DICE - 1; i++) {
			// 判断是否为等差数列
			if (temp[i] + 1 == temp[i + 1]) {
				num++;
			}
		}
		if (num >= flag) {
			return true;
		}
		return false;
	}

	// 冒泡排序
	private int[] bubbleSort(int array[]) {
		int p = 0;
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					p = array[j];
					array[j] = array[j + 1];
					array[j + 1] = p;
				}
			}
		}
		return array;
	}

	// 更新排行榜
	private void updateRankList() throws IOException {
		// 读文件
		readFile();
		for (int j = 0; j < nPlayers; j++) {
			RankList temp = new RankList();
			temp.setPlayerName(playerNames[j]);
			temp.setPlayerScore(playerScores[j][TOTAL - 1]);
			ranklist.add(temp);
		}
		// 排序
		bubbleSortForRankList(ranklist);
		// 写文件
		writeFile();

	}

	// 读文件，将排行榜里的玩家名字和分数全部读出来
	private void readFile() throws IOException {
		String str;
		BufferedReader reader = new BufferedReader(new FileReader(FileName));
		while ((str = reader.readLine()) != null) {
			RankList temp = new RankList();
			temp.setPlayerName(str.substring(0, str.indexOf(" ")));
			temp.setPlayerScore(Integer.parseInt(str.substring(str.indexOf(" ") + 1)));
			ranklist.add(temp);
		}
	}

	// 写文件，将更新后的排行榜写到文件中，只写前Ranker位
	private void writeFile() {
		try {
			int ranklistnum = 0;
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
			for (int i = ranklist.size() - 1; i >= 0; i--) {
				if (ranklistnum < Ranker) {
					writer.write(ranklist.get(i).getPlayerName() + " " + ranklist.get(i).getPlayerScore() + "\r\n");
					ranklistnum++;
				}
			}
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 冒泡排序
	private void bubbleSortForRankList(ArrayList<RankList> ranklist) {
		RankList temp = new RankList();
		for (int i = 0; i < ranklist.size() - 1; i++) {
			for (int j = 0; j < ranklist.size() - 1 - i; j++) {
				if (ranklist.get(j).getPlayerScore() > ranklist.get(j + 1).getPlayerScore()) {
					temp = ranklist.get(j);
					ranklist.set(j, ranklist.get(j + 1));
					ranklist.set(j + 1, temp);
				}
			}
		}
	}
}

// 定义一个RankList类，包括了玩家姓名和玩家分数
class RankList {
	private String PlayerName;
	private int PlayerScore;

	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	public int getPlayerScore() {
		return PlayerScore;
	}

	public void setPlayerScore(int playerScore) {
		PlayerScore = playerScore;
	}

}