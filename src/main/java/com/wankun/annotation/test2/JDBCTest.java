package com.wankun.annotation.test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTest {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "select * from mymails";
		try {
			Class.forName(jdbcUtil.getDriver());
			Connection conn = DriverManager.getConnection(jdbcUtil.getUrl(),
					jdbcUtil.getUserName(), jdbcUtil.getPasswrod());
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("id : " + rs.getInt(1) + " name : " + rs.getString(2)
						+ " mail : " + rs.getString(3));
			}
			// 关闭记录集
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// 关闭声明
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// 关闭链接对象
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}