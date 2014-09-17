package com.wankun.eightqueue;

/**
 * 原理：每一行有一个皇后，在确定8行中每个皇后位置后，打印棋谱，列后移，继续上面循环
 * 
 * @author internet
 * @date 2014年9月17日
 * @version 1.0
 */
public class EightQueen {
	// 同栏是否有皇后，1表示有
	private int[] column;

	// 右上至左下是否有皇后
	private int[] rup;

	// 左上至右下是否有皇后
	private int[] lup;

	// 解答
	private int[] queen;

	// 解答编号
	private int num;

	public EightQueen() {
		column = new int[8 + 1];
		rup = new int[(2 * 8) + 1];
		lup = new int[(2 * 8) + 1];

		for (int i = 1; i <= 8; i++)
			column[i] = 1;

		for (int i = 1; i <= (2 * 8); i++)
			rup[i] = lup[i] = 1;

		queen = new int[8 + 1];
	}

	public void backtrack(int i) {
		// i 代表行，当超过8行时，说明找到8个皇后位置，直接打印，返回上一个列+1的位置继续匹配
		if (i > 8) {
			showAnswer();
		} else {
			// 在每一行的8个位置寻找机会 j：列
			for (int j = 1; j <= 8; j++) {
				if ((column[j] == 1) && (rup[i + j] == 1) && (lup[i - j + 8] == 1)) {
					queen[i] = j;
					// 设定为占用
					column[j] = rup[i + j] = lup[i - j + 8] = 0;
					backtrack(i + 1);
					column[j] = rup[i + j] = lup[i - j + 8] = 1;
				}
			}
		}
	}

	protected void showAnswer() {
		num++;
		System.out.println("\n解答" + num);

		for (int y = 1; y <= 8; y++) {
			for (int x = 1; x <= 8; x++) {
				if (queen[y] == x) {
					System.out.print("Q");
				} else {
					System.out.print(".");
				}
			}

			System.out.println();
		}
	}

	public static void main(String[] args) {
		EightQueen queen = new EightQueen();
		queen.backtrack(1);
	}
}