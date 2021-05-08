package org.apache.log4j.rolling;

import org.apache.log4j.rolling.helper.ActionBase;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class PurgeExpiredLogsAction extends ActionBase {

  private int maxBackupIndex;
  private String fileNamePrefix;
  private String lastFileName;
  private int lastFileNameLength;

  public PurgeExpiredLogsAction(int maxBackupIndex, String fileNamePrefix, String lastFileName) {
    assert lastFileName != null && lastFileName.length() > 0;
    if (maxBackupIndex > 0) {
      assert fileNamePrefix != null && fileNamePrefix.length() > 0;
    }

    this.maxBackupIndex = maxBackupIndex;
    this.fileNamePrefix = fileNamePrefix;
    this.lastFileName = lastFileName;
    this.lastFileNameLength = new File(lastFileName).getName().length();
  }

  @Override
  public boolean execute() {
    try {
      if (maxBackupIndex > 0) {
        assert fileNamePrefix != null && fileNamePrefix.length() > 0;

        File lastFile = new File(lastFileName).getCanonicalFile();
        System.out.println("lastFileName : " + lastFileName + " lastFile:" + lastFile);
        File logDirectory = null;
        if (lastFile.isFile()) {
          logDirectory = lastFile.getParentFile();
        } else if (lastFile.isDirectory()) {
          logDirectory = lastFile;
        }
        assert logDirectory != null;
        File[] logFiles = logDirectory.listFiles((dir, name) ->
            name.startsWith(fileNamePrefix) && name.length() == lastFileNameLength);
        int purgeFileNumber = logFiles.length - maxBackupIndex;
        if (purgeFileNumber > 0) {
          Arrays.stream(logFiles)
              .sorted(Comparator.comparing(File::getName))
              .limit(purgeFileNumber)
              .forEach(File::delete);
        }
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
