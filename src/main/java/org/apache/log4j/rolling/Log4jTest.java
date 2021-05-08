package org.apache.log4j.rolling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
  public static Logger logger = LoggerFactory.getLogger(Log4jTest.class);


  public static void main(String[] args) throws InterruptedException {
    int i = 0;
    while (true) {
      logger.info("" + i++);
      Thread.sleep(1);
    }
  }
}
