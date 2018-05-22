package com.sfsctech.core.logger.logback.rolling;

import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.pattern.LiteralConverter;
import ch.qos.logback.core.rolling.helper.*;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.util.FileSize;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Class PatchedLogArchiveRemover
 *
 * @author 张麒 2018-5-4.
 * @version Description:
 */
public class PatchedLogArchiveRemover extends ContextAwareBase implements ArchiveRemover {
    private static final long UNINITIALIZED = -1L;
    private long lastHeartBeat = -1L;
    private static final long INACTIVITY_TOLERANCE_IN_MILLIS = 2764800000L;
    private static final int MAX_VALUE_FOR_INACTIVITY_PERIODS = 336;
    private final FileNamePattern fileNamePattern;
    private final RollingCalendar rc;
    private int maxHistory = 0;
    private long totalSizeCap = 0L;
    private final boolean parentClean;
    private Converter headTokenConvertor;

    PatchedLogArchiveRemover(FileNamePattern fileNamePattern, RollingCalendar rc) {
        this.fileNamePattern = fileNamePattern;
        this.rc = rc;
        this.parentClean = this.computeParentCleaningFlag(fileNamePattern);
    }

    public void clean(Date now) {
        long nowInMillis = now.getTime();
        int periodsElapsed = this.computeElapsedPeriodsSinceLastClean(nowInMillis);
        this.lastHeartBeat = nowInMillis;
        if (periodsElapsed > 1) {
            this.addInfo("Multiple periods, i.e. " + periodsElapsed + " periods, seem to have elapsed. This is expected at application start.");
        }

        for (int i = 0; i < periodsElapsed; ++i) {
            int offset = this.getPeriodOffsetForDeletionTarget() - i;
            Date dateOfPeriodToClean = this.rc.getEndOfNextNthPeriod(now, offset);
            this.cleanPeriod(dateOfPeriodToClean);
        }

    }

    protected File[] getFilesInPeriod(Date dateOfPeriodToClean) {
        File archive0 = new File(this.fileNamePattern.convertMultipleArguments(new Object[]{dateOfPeriodToClean, 0}));
        File parentDir = this.getParentDir(archive0);
        String stemRegex = this.createStemRegex(dateOfPeriodToClean);
        File[] matchingFileArray = FileFilterUtil.filesInFolderMatchingStemRegex(parentDir, stemRegex);
        return matchingFileArray;
    }

    private String createStemRegex(Date dateOfPeriodToClean) {
        String regex = this.fileNamePatternToRegexForFixedDate(this.fileNamePattern, dateOfPeriodToClean);
        return FileFilterUtil.afterLastSlash(regex);
    }

    private String fileNamePatternToRegexForFixedDate(FileNamePattern fileNamePattern, Date date) {
        StringBuilder buf = new StringBuilder();

        for (Converter p = this.getHeadTokenConvertor(fileNamePattern); p != null; p = p.getNext()) {
            if (p instanceof LiteralConverter) {
                buf.append(p.convert((Object) null));
            } else if (p instanceof IntegerTokenConverter) {
                buf.append("(\\d{1,100})");
            } else if (p instanceof DateTokenConverter) {
                buf.append(p.convert(date));
            }
        }

        return buf.toString();
    }

    private boolean fileExistsAndIsFile(File file2Delete) {
        return file2Delete.exists() && file2Delete.isFile();
    }

    public void cleanPeriod(Date dateOfPeriodToClean) {
        File[] matchingFileArray = this.getFilesInPeriod(dateOfPeriodToClean);
        File[] var3 = matchingFileArray;
        int var4 = matchingFileArray.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File f = var3[var5];
            this.addInfo("deleting " + f);
            f.delete();
        }

        if (this.parentClean && matchingFileArray.length > 0) {
            File parentDir = this.getParentDir(matchingFileArray[0]);
            this.removeFolderIfEmpty(parentDir);
        }

    }

    void capTotalSize(Date now) {
        long totalSize = 0L;
        long totalRemoved = 0L;

        for (int offset = 0; offset < this.maxHistory; ++offset) {
            Date date = this.rc.getEndOfNextNthPeriod(now, -offset);
            File[] matchingFileArray = this.getFilesInPeriod(date);
            this.descendingSortByLastModified(matchingFileArray);
            File[] var9 = matchingFileArray;
            int var10 = matchingFileArray.length;

            for (int var11 = 0; var11 < var10; ++var11) {
                File f = var9[var11];
                long size = f.length();
                if (totalSize + size > this.totalSizeCap) {
                    this.addInfo("Deleting [" + f + "] of size " + new FileSize(size));
                    totalRemoved += size;
                    f.delete();
                }

                totalSize += size;
            }
        }

        this.addInfo("Removed  " + new FileSize(totalRemoved) + " of files");
    }

    private void descendingSortByLastModified(File[] matchingFileArray) {
        Arrays.sort(matchingFileArray, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long l1 = f1.lastModified();
                long l2 = f2.lastModified();
                if (l1 == l2) {
                    return 0;
                } else {
                    return l2 < l1 ? -1 : 1;
                }
            }
        });
    }

    File getParentDir(File file) {
        File absolute = file.getAbsoluteFile();
        File parentDir = absolute.getParentFile();
        return parentDir;
    }

    int computeElapsedPeriodsSinceLastClean(long nowInMillis) {
        long periodsElapsed;
        if (this.lastHeartBeat == -1L) {
            this.addInfo("日志框架初始化后第一次清理..");
            periodsElapsed = this.rc.periodBarriersCrossed(nowInMillis, nowInMillis + 2764800000L);
            periodsElapsed = Math.min(periodsElapsed, 336L);
        } else {
            periodsElapsed = this.rc.periodBarriersCrossed(this.lastHeartBeat, nowInMillis);
        }

        return (int) periodsElapsed;
    }

    boolean computeParentCleaningFlag(FileNamePattern fileNamePattern) {
        DateTokenConverter<Object> dtc = fileNamePattern.getPrimaryDateTokenConverter();
        if (dtc.getDatePattern().indexOf(47) != -1) {
            return true;
        } else {
            Converter p;
            for (p = this.getHeadTokenConvertor(fileNamePattern); p != null && !(p instanceof DateTokenConverter); p = p.getNext()) {
                ;
            }

            for (; p != null; p = p.getNext()) {
                if (p instanceof LiteralConverter) {
                    String s = p.convert((Object) null);
                    if (s.indexOf(47) != -1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    void removeFolderIfEmpty(File dir) {
        this.removeFolderIfEmpty(dir, 0);
    }

    private void removeFolderIfEmpty(File dir, int depth) {
        if (depth < 3) {
            if (dir.isDirectory() && FileFilterUtil.isEmptyDirectory(dir)) {
                this.addInfo("deleting folder [" + dir + "]");
                dir.delete();
                this.removeFolderIfEmpty(dir.getParentFile(), depth + 1);
            }

        }
    }

    public void setMaxHistory(int maxHistory) {
        this.maxHistory = maxHistory;
    }

    protected int getPeriodOffsetForDeletionTarget() {
        return -this.maxHistory - 1;
    }

    public void setTotalSizeCap(long totalSizeCap) {
        this.totalSizeCap = totalSizeCap;
    }

    public String toString() {
        return "c.q.l.core.rolling.helper.TimeBasedArchiveRemover";
    }

    public Future<?> cleanAsynchronously(Date now) {
        PatchedLogArchiveRemover.ArchiveRemoverRunnable runnable = new PatchedLogArchiveRemover.ArchiveRemoverRunnable(now);
        ExecutorService executorService = this.context.getScheduledExecutorService();
        Future<?> future = executorService.submit(runnable);
        return future;
    }

    private Converter<Object> getHeadTokenConvertor(FileNamePattern fileNamePattern) {
        try {
            if (this.headTokenConvertor == null) {
                synchronized (this) {
                    if (this.headTokenConvertor == null) {
                        Field field = FileNamePattern.class.getDeclaredField("headTokenConverter");
                        field.setAccessible(true);
                        this.headTokenConvertor = (Converter) field.get(fileNamePattern);
                        field.setAccessible(false);
                    }
                }
            }
        } catch (Exception var6) {
            this.addError("获取headTokenConvertor时发生异常");
        }

        return this.headTokenConvertor;
    }

    public class ArchiveRemoverRunnable implements Runnable {
        Date now;

        ArchiveRemoverRunnable(Date now) {
            this.now = now;
        }

        public void run() {
            PatchedLogArchiveRemover.this.clean(this.now);
            if (PatchedLogArchiveRemover.this.totalSizeCap != 0L && PatchedLogArchiveRemover.this.totalSizeCap > 0L) {
                PatchedLogArchiveRemover.this.capTotalSize(this.now);
            }

        }
    }
}
