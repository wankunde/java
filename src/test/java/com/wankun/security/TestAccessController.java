package com.wankun.security;

import java.io.File;
import java.io.FilePermission;
import java.security.AccessController;

import org.junit.Test;

/**
 * @author wankun
 */
public class TestAccessController {

	@Test
	public void testFilePerm() {
		File f = new File("pom.xml");
		System.out.println(f.getAbsolutePath()+"  "+f.length());
		FilePermission perm = new FilePermission("D:\\workspace_download\\java\\pom.xml", "read");
		AccessController.checkPermission(perm);
	}
}
