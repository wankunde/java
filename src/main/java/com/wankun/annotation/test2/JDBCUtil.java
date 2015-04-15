package com.wankun.annotation.test2;

@JDBCAnnotation(dbName = "db_lucene", port = "3306", host = "192.168.0.119", userName = "root", password = "root")
public class JDBCUtil {

	private static String driver;
	private static String dbName;
	private static String encoding;
	private static String port;
	private static String host;
	private static String passwrod;
	private static String userName;
	private static String url;

	public void checkInterceptor(Class<?> cl) throws Exception {
		boolean flag = cl.isAnnotationPresent(JDBCAnnotation.class);
		if (flag) {
			JDBCAnnotation jdbcAnnotation = cl.getAnnotation(JDBCAnnotation.class);
			driver = jdbcAnnotation.driver();
			dbName = jdbcAnnotation.dbName();
			encoding = jdbcAnnotation.encoding();
			port = jdbcAnnotation.port();
			host = jdbcAnnotation.host();
			userName = jdbcAnnotation.userName();
			passwrod = jdbcAnnotation.password();
			url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?characterEncoding="
					+ encoding;
			System.out.println("JDBCUtil加载注释完成...  url:" + url);
		}
	}

	public JDBCUtil() {
		try {
			checkInterceptor(JDBCUtil.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		JDBCUtil.driver = driver;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		JDBCUtil.dbName = dbName;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		JDBCUtil.encoding = encoding;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		JDBCUtil.port = port;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		JDBCUtil.host = host;
	}

	public static String getPasswrod() {
		return passwrod;
	}

	public static void setPasswrod(String passwrod) {
		JDBCUtil.passwrod = passwrod;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		JDBCUtil.userName = userName;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		JDBCUtil.url = url;
	}

}