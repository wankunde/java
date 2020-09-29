package com.wankun.security;

import java.io.File;
import java.io.FilePermission;
import java.security.AccessControlException;
import java.security.AccessController;

import org.junit.Test;

/**
 * @author wankun
 */
public class TestAccessController {

  @Test(expected = AccessControlException.class)
  public void testFilePerm() {
    File f = new File("pom.xml");
    FilePermission perm = new FilePermission(f.getAbsolutePath(), "read");
    AccessController.checkPermission(perm);
  }
}
