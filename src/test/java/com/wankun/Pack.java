package com.wankun;

import java.util.List;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * <pre>
 * 利用动态规划解决背包问题
 * 
 * [0-1背包问题]有一个背包，背包容量是M=150。有7个物品，物品不可以分割成任意大小。
 * 要求尽可能让装入背包中的物品总价值最大，但不能超过总容量。
 * 物品 A B C D E F G
 * 重量 35kg 30kg 6kg 50kg 40kg 10kg 25kg
 * 价值 10$ 40$ 30$ 50$ 35$ 40$ 30$
 * </pre>
 * 
 * @author wankun
 * @date 2014年9月18日
 * @version 1.0
 */

public class Pack {

	public static void main(String[] args) {
		Pack p = new Pack();
		TreeSet<ObjectEntry> enset = Sets.newTreeSet();
		enset.add(new ObjectEntry(35, 10));
		enset.add(new ObjectEntry(30, 40));
		enset.add(new ObjectEntry(6, 30));
		enset.add(new ObjectEntry(50, 50));
		enset.add(new ObjectEntry(40, 35));
		enset.add(new ObjectEntry(10, 40));
		enset.add(new ObjectEntry(25, 30));
		int capacity = 150;
		List<ObjectEntry> list = p.getObject(new ObjectEntry(), enset, capacity);
		for (ObjectEntry en : list)
			System.out.println(en);
	}

	// 物品：重量-价值
	public List<ObjectEntry> getObject(ObjectEntry en, TreeSet<ObjectEntry> enset, int capacity) {
		List<ObjectEntry> list = Lists.newArrayList();
		int lm = 0;
		for (ObjectEntry cen : enset) {
			TreeSet<ObjectEntry> subset = Sets.newTreeSet(enset);
			subset.remove(cen);
			List<ObjectEntry> list1 = getObject(cen, subset, capacity - cen.getWeight());
			int w = 0;
			int m = 0;
			for (ObjectEntry tmp : list1) {
				w += tmp.getWeight();
				m += tmp.getMoney();
			}
			if (w + en.getWeight() < capacity || m > lm) {
				list = list1;
				lm = m;
			}
		}
		if(en.getWeight()>0)
			list.add(en);
		return list;
	}

}

class ObjectEntry implements Comparable<ObjectEntry> {
	private int weight;
	private int money;

	public ObjectEntry() {
		this.weight = 0;
		this.money = 0;
	}

	public ObjectEntry(int weight, int money) {
		this.weight = weight;
		this.money = money;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int compareTo(ObjectEntry o) {
		return this.weight - o.weight;
	}

	@Override
	public boolean equals(Object obj) {
		ObjectEntry en = (ObjectEntry) obj;
		return this.weight == en.weight && this.money == en.money;
	}

	@Override
	public int hashCode() {
		return this.weight * this.money;
	}

	@Override
	public String toString() {
		return "(en:" + weight + "," + money + ")";
	}

}
