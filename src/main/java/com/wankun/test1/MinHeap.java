package com.wankun.test1;

/**
 * 最小堆，用数组实现，解决top-N问题
 * 
 * @author zhshl
 * @date 2014-10-22
 * @param <t>
 */

public class MinHeap {

	private int heap[];
	private int maxSize; // //最多可容纳数目
	private int n;// /元素数目

	/**
	 * @param num
	 *            堆的大小
	 */
	public MinHeap(int num) {
		n = 0;
		maxSize = num;
		heap = new int[maxSize];
	}

	/*
    *//**
	 * 初始堆是一颗任意次序的完全二叉树,从（n-2)/2处开始向下调整
	 * 
	 * @param heap
	 * @param n
	 */
	/*
	 * public void createHeap(int[] heap2,int n){
	 * 
	 * heap=heap2;
	 * 
	 * for(int i=(n-2)/2;i>=0;i--){ ///从（n-2)/2处开始向下调整 adjustDown(i,n-1); } }
	 */

	/**
	 * 元素入堆
	 * 
	 * @param v
	 */
	public void append(int v) {
		if (isFull()) {
			System.out.println("heap is full ,can't append elements !");
			return;
		}
		// //元素插在末尾
		heap[n] = v;
		n++;
		// /向上调整
		adjustUp(n - 1);

	}

	/**
	 * 取出堆顶元素
	 * 
	 * @return
	 */
	public int serve() {
		if (isEmpty()) {
			System.out.println("heap is empty!");
			return Integer.MIN_VALUE;
		}

		int temp = heap[0];
		// //用最后一个元素取代第一个元素
		heap[0] = heap[n - 1];
		n--;
		// 向下调整
		adjustDown(0, n - 1);

		return temp;
	}

	/**
	 * 求最大的n个数据
	 * 
	 * @param data
	 * @param n
	 * @return null if n is bigger than the heap size, otherwise
	 */
	public int[] getTopN(int[] data, int n) {
		if (n > data.length)
			n = data.length;
		else if (n <= 0)
			return new int[0];
		heap = new int[n];
		maxSize = n;
		this.n = 0;

		// 构建初始堆
		for (int i = 0; i < data.length; i++) {
			if (i < n) {
				heap[i] = data[i];
				adjustUp(i);
			} else if (data[i] > heap[0]) {
				heap[0] = data[i];
				adjustDown(0, n);
			}
		}

		return heap;
	}

	/**
	 * 对元素i进行向下调整，用于删除元素时调整
	 * 
	 * @param i
	 * @param j
	 */
	private void adjustDown(int i, int j) {
		// TODO Auto-generated method stub
		int child = 2 * i + 1;
		int temp = heap[i];// /记录待调整的节点的值
		while (child < j) {
			if ((child + 1) < j && (heap[child] > heap[child + 1])) {
				// 如果左孩子比右孩子大, 则指向较小的右孩子 且右节点存在
				child++;
			}

			if (heap[child] > temp) {
				// /如果较小的孩子都比自己大，则退出
				break;
			}

			heap[(child - 1) / 2] = heap[child];
			child = child * 2 + 1;
		}
		// //循环结束，child指向最终位置的节点的左孩子
		heap[(child - 1) / 2] = temp;

	}

	/**
	 * 将i处的元素向上调整为堆，用于插入时候
	 * 
	 * @param i
	 */
	private void adjustUp(int i) {
		int temp = heap[i];
		while (i > 0 && heap[(i - 1) / 2] > temp) {
			// /当父节点的值比temp大的时候，交换值
			heap[i] = heap[(i - 1) / 2];
			i = (i - 1) / 2;
		}
		heap[i] = temp;
	}

	/**
	 * 堆是否满了
	 * 
	 * @return
	 */
	public boolean isFull() {
		return n >= maxSize;
	}

	/**
	 * 是否为空堆
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return 0 == n;
	}
}