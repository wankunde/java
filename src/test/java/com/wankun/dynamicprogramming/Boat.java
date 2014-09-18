package com.wankun.dynamicprogramming;

import java.util.Arrays;

public class Boat {

	/**
	 * <pre>
	 *    0 1 2 3
	 *  0 0 3 3 6
	 *  1 0 0 1 4
	 *  2 0 0 0 1
	 *  3 0 0 0 0
	 * </pre>
	 */
	int[][] fees = { { 0, 3, 3, 6 }, { 0, 0, 1, 4 }, { 0, 0, 0, 1 } };

	public static void main(String[] args) {
		Boat boat = new Boat();
		Path p = boat.getRoad(0, 3);
		System.out.println(Arrays.toString(p.getPoint()));
		System.out.println(p.getFee());
	}

	public Path getRoad(int start, int end) {
		int[] point = { start, end };
		int fee = fees[start][end];
		for (int i = start + 1; i < end; i++) {
			Path p1 = getRoad(start, i);
			Path p2 = getRoad(i, end);
			if ((p1.getFee() + p2.getFee()) < fee) {
				point = Arrays.copyOf(p1.getPoint(), p1.getPoint().length + p2.getPoint().length - 1);
				System.arraycopy(p2.getPoint(), 1, point, p1.getPoint().length, p2.getPoint().length - 1);
				fee = (p1.getFee() + p2.getFee());
			}
		}
		return new Path(point, fee);
	}

	class Path {
		private int[] point;
		private int fee;

		public Path(int[] point, int fee) {
			this.point = point;
			this.fee = fee;
		}

		public int getFee() {
			return fee;
		}

		public void setFee(int fee) {
			this.fee = fee;
		}

		public int[] getPoint() {
			return point;
		}

		public void setPoint(int[] point) {
			this.point = point;
		}
	}
}
