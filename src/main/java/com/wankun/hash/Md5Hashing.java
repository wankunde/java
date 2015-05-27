package com.wankun.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <pre>
 * 一般Hash数据分布和查找
 * 
 * 1. init node(TreeMap).
 * ---------------
 * key1   |  node1-virtual1
 * key2   |  node2-virtual1
 * key3   |  node3-virtual1
 * key4   |  node1-virtual2
 * key5   |  node2-virtual2
 * key6   |  node3-virtual2
 * ---------------
 * 2. 计算hash值，这里再根据md5结果进行了加盐，(分为4组，随机取其中一组的值)做为hash code.(个人感觉这里不用VIRTUAL_NUM，结果也照样是平均的)
 * 
 * 3. 根据hash结果找对应的key所对应的node
 * 
 * </pre>
 * 
 * @author wankun
 * 
 */
public class Md5Hashing {
	private TreeMap<Long, Object> nodes = null;
	// 真实服务器节点信息
	private List<Object> shards = new ArrayList<>();
	// 设置虚拟节点数目
	private int VIRTUAL_NUM = 4;

	/**
	 * 初始化一致环
	 * 
	 * <pre>
	 * {
	 * 118282692=192.168.0.1-服务器1, 
	 * 221500148=192.168.0.4-服务器4, 
	 * 255045541=192.168.0.2-服务器2, 
	 * 303864714=192.168.0.3-服务器3, 
	 * 539096190=192.168.0.1-服务器1, 
	 * 575071786=192.168.0.0-服务器0, 
	 * 576950847=192.168.0.0-服务器0, 
	 * 1098997992=192.168.0.3-服务器3, 
	 * 1217558326=192.168.0.4-服务器4, 
	 * 1267663101=192.168.0.2-服务器2, 
	 * 1460650174=192.168.0.3-服务器3, 
	 * 1642767961=192.168.0.3-服务器3, 
	 * 1654492625=192.168.0.2-服务器2, 
	 * 2043002859=192.168.0.1-服务器1, 
	 * 2750408027=192.168.0.2-服务器2, 
	 * 3044491558=192.168.0.4-服务器4, 
	 * 3841303531=192.168.0.4-服务器4, 
	 * 3992280081=192.168.0.0-服务器0, 
	 * 4092405372=192.168.0.1-服务器1, 
	 * 4193559948=192.168.0.0-服务器0
	 * }
	 * </pre>
	 */
	public void init() {
		shards.add("192.168.0.0-服务器0");
		shards.add("192.168.0.1-服务器1");
		shards.add("192.168.0.2-服务器2");
		shards.add("192.168.0.3-服务器3");
		shards.add("192.168.0.4-服务器4");

		nodes = new TreeMap<Long, Object>();
		for (int i = 0; i < shards.size(); i++) {
			Object shardInfo = shards.get(i);
			for (int j = 0; j < VIRTUAL_NUM; j++) {
				nodes.put(hash(computeMd5("SHARD-" + i + "-NODE-" + j), j), shardInfo);
			}
		}
	}

	/**
	 * 根据key的hash值取得服务器节点信息
	 * 
	 * @param hash
	 * @return
	 */
	public Object getShardInfo(long hash) {
		Long key = hash;
		SortedMap<Long, Object> tailMap = nodes.tailMap(key);
		if (tailMap.isEmpty()) {
			key = nodes.firstKey();
		} else {
			key = tailMap.firstKey();
		}
		return nodes.get(key);
	}

	/**
	 * 根据2^32把节点分布到圆环上面。
	 * 
	 * digest为16位byte数组，这里设置了VIRTUAL_NUM=4，将byte分为4组，取其中4个连续byte确定hash值
	 * 
	 * @param digest
	 * @param nTime
	 * @return
	 */
	public long hash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24) | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
				| ((long) (digest[1 + nTime * 4] & 0xFF) << 8) | (digest[0 + nTime * 4] & 0xFF);

		return rv & 0xffffffffL; /* Truncate to 32-bits */
	}

	/**
	 * Get the md5 of the given key.
	 * 
	 * @return 存放md5结果的byte数组
	 */
	public byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unknown string :" + k, e);
		}

		md5.update(keyBytes);
		return md5.digest();
	}

	public static void main(String[] args) {
		Random ran = new Random();
		Md5Hashing hash = new Md5Hashing();
		hash.init();
		System.out.println(hash.nodes);
		// 循环50次，是为了取50个数来测试效果，当然也可以用其他任何的数据来测试
		for (int i = 0; i < 50; i++) {
			System.out.println(hash.getShardInfo(hash.hash(hash.computeMd5(String.valueOf(i)),
					ran.nextInt(hash.VIRTUAL_NUM))));
		}
	}
}
