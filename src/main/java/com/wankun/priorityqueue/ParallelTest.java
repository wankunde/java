package com.wankun.priorityqueue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ParallelTest {

	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			Thread th = new Thread() {
				@Override
				public void run() {
					long i = 0;
					i++;
					double ts = 125;
					double target = 0.6;
					while (true) {
						if (i > Long.MAX_VALUE)
							i = 0;
						i++;

						double idle = getCpuIdle();
						ts = ts * target / idle;
						System.out.println("Thread: " + getName() + "  idle :" + idle + "  ts:" + ts);
						try {
							Thread.currentThread().sleep((long) ts);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			th.setName("work" + i);
			th.start();
		}
	}

	public static double getCpuIdle() {
		Map<String, Long> cpuinfomap = cpuinfo();
		Map<String, Long> cpuinfomap2 = cpuinfo();

		long idle = cpuinfomap2.get("idle") - cpuinfomap.get("idle");
		long total = cpuinfomap2.get("total") - cpuinfomap.get("total");
		if (total == 0)
			return 0.5;
		return (total - idle) * 1.0 / total;

	}

	public static Map<String, Long> cpuinfo() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		Map<String, Long> map = new HashMap<String, Long>();
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith("cpu ")) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					List<String> temp = new ArrayList<String>();
					while (tokenizer.hasMoreElements()) {
						String value = tokenizer.nextToken();
						temp.add(value);
					}
					long user = Long.parseLong(temp.get(1));
					long nice = Long.parseLong(temp.get(2));
					long system = Long.parseLong(temp.get(3));
					long idle = Long.parseLong(temp.get(4));
					long iowait = Long.parseLong(temp.get(5));
					long irq = Long.parseLong(temp.get(6));
					long softirq = Long.parseLong(temp.get(7));
					long stealstolen = Long.parseLong(temp.get(8));
					long total = user + nice + system + idle + iowait + irq + softirq + stealstolen;
					map.put("user", user);
					map.put("nice", nice);
					map.put("system", system);
					map.put("idle", idle);
					map.put("iowait", iowait);
					map.put("irq", irq);
					map.put("softirq", softirq);
					map.put("stealstolen", stealstolen);
					map.put("total", total);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
}
