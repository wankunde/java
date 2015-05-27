# Java 位运算

```java

	String hex = Integer.toHexString(b & 0xFF);

	byte b = -62;
	System.out.println(Integer.toBinaryString(b & 0xff));  // 11000010
	System.out.println(Integer.toBinaryString(b));  // 11111111111111111111111111000010
	
```
 
 为何要和 0xFF进行与运算呢?
 
1. byte的大小为8bits而int的大小为32bits
2. java的二进制采用的是补码形式
3. Java中的一个byte，其范围是-128~127的，而Integer.toHexString的参数本来是int，如果不进行&0xff，那么当一个byte会转换成int时，对于负数，会做位扩展，举例来说，一个byte的-1（即0xff），会被转换成int的-1（即0xffffffff），那么转化出的结果就不是我们想要的了。而0xff默认是整形，所以，一个byte跟0xff相与会先将那个byte转化成整形运算，这样，结果中的高的24个比特就总会被清0，于是结果总是我们想要的。