package com.wankun.test1;

/**
 * 最小堆测试类
 * 
 * @author zhshl
 * @date 2014-10-22
 *
 */
public class MinHeapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MinHeap heap = new MinHeap(10);
		heap.append(2);
		heap.append(2);
		heap.append(13);
		heap.append(21);
		heap.append(2);
		heap.append(2);
		heap.append(53);
		heap.append(6);

		int temp;
		while (!heap.isEmpty()) {
			temp = heap.serve();
			System.out.print(temp + "  ");
		}

		System.out.println("\n================");

		// 求top-N问题
		int data[] = { 4, 51, 52, 12, 123, 52, 7643, 234, 123, 33, 44, 2 };

		data = heap.getTopN(data, 6);
		for (int i : data) {
			System.out.println(i);
		}
	}
}