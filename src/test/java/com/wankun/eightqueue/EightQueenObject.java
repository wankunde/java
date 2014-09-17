package com.wankun.eightqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * 八皇后路径
 * 
 * 核心：迭代 输入N个皇后确定的位置，返回N+1个皇后符合的位置
 * <p>
 * 迭代初始条件：64个位置随机选择一个位置
 * <p>
 * 迭代结束条件：确定了8个皇后的位置，或者在确定N+1个皇后无法满足时结束迭代
 * <p>
 * 
 * @author wankun
 * @date 2014年9月17日
 * @version 1.0
 */
public class EightQueenObject {
	public static int num = 1;

	public static void main(String[] args) {
		EightQueenObject eq = new EightQueenObject();
		List<Set<Queue>> init = new ArrayList<Set<Queue>>();
		for (int i = 0; i < 64; i++) {
			Set<Queue> set = new TreeSet<Queue>();
			set.add(new Queue(i));
			init.add(set);
		}
		List<Set<Queue>> list = eq.getQueues(init);
		for (Set<Queue> queues : list)
			printSet(queues);
	}

	/**
	 * 
	 * @param queues
	 *            已经可以通过的N个Queue
	 * @param start
	 *            开始匹配的位置
	 * @return N+1个Queue组合，情况有很多种，所以用List表示
	 */
	public List<Set<Queue>> getQueues(List<Set<Queue>> list) {
		List<Set<Queue>> newlist = new ArrayList<Set<Queue>>();
		for (Set<Queue> queues : list) {
			int start = 0;
			for (Queue q : queues) {
				if (start < q.v)
					start = q.v;
			}

			for (int i = start; i < 64; i++) {
				Queue q = new Queue(i);
				if (passQueue(queues, q)) {
					Set<Queue> tmpqueues = new TreeSet<Queue>();
					tmpqueues.addAll(queues);
					tmpqueues.add(q);
					newlist.add(tmpqueues);
				}
			}
		}
		if (newlist.size() > 0 && newlist.get(0).size() < 8)
			return getQueues(newlist);
		else
			return newlist;
	}

	public static void printSet(Set<Queue> queues) {
		StringBuilder sb = new StringBuilder();
		sb.append("" + (num++) + "[");
		for (Queue p : queues) {
			sb.append(p + " ,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		System.out.println(sb.toString());
	}

	public boolean passQueue(Set<Queue> queues, Queue q) {
		for (Queue p : queues) {
			if (!p.check(q))
				return false;
		}
		return true;
	}
}

class Queue implements Comparable<Queue> {
	int x; // 行
	int y; // 列
	int v; // 值

	public Queue(int v) {
		this.v = v;
		this.x = v / 8;
		this.y = v % 8;
	}

	@Override
	public boolean equals(Object obj) {
		Queue q = (Queue) obj;
		return this.v == q.v;
	}

	@Override
	public int hashCode() {
		return v;
	}

	// 可以放的Queue返回true
	public boolean check(Queue q) {
		return !(this.x == q.x) && !(this.y == q.y) && !((q.x - this.x) == (q.y - this.y))
				&& !((q.x - this.x) == (this.y - q.y));
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + v + ")";
	}

	public int compareTo(Queue o) {
		return this.v - o.v;
	}
}
