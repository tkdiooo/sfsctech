package com.sfsctech.support.svn.util;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;

/**
 * Class SvnUtil
 *
 * @author 张麒 2017/6/6.
 * @version Description:
 */
public class SvnUtil {

    private final Logger logger = LoggerFactory.getLogger(SvnUtil.class);

    private SvnHelper svnHelper;

    private String fileSpace;

    public SvnUtil(SvnHelper svnHelper, String fileSpace) {
        this.svnHelper = svnHelper;
        this.fileSpace = fileSpace;
    }

    public String logbackLabel = "logback";


    public void update(String label) {
        Long version = 0L;
        try {
            File workspace = new File(fileSpace);
            if (!SVNWCUtil.isVersionedDirectory(workspace)) {
                SVNURL repositoryURL = SVNURL.parseURIEncoded(svnHelper.getUri()).appendPath(label, false);
                version = svnHelper.checkout(repositoryURL, SVNRevision.HEAD, workspace, SVNDepth.INFINITY);
            } else {
                version = svnHelper.update(workspace, SVNRevision.HEAD, SVNDepth.INFINITY);
            }
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        logger.info("更新工作空间文件：" + fileSpace + "，version：" + version);
    }

    public File getLogbackFile(String name, String profile, String label) {
        String fileName = name + LabelConstants.DASH + profile + ".xml";
        // 工作空间不存在，checkout工作空间
        if (!FileUtil.isExists(fileSpace)) {
            update(label);
        }
        try {
            File file = new File(fileSpace + LabelConstants.FORWARD_SLASH + fileName);
            SVNStatus status = svnHelper.showStatus(file, true);
            // 文件不存在 || 文件需要更新版本
            if (!file.exists() || -1 != status.getRemoteRevision().getNumber()) {
                Long version = svnHelper.update(file, SVNRevision.HEAD, SVNDepth.INFINITY);
                logger.info("更新文件：" + fileName + "，version：" + version + "，至工作空间：" + fileSpace + "");
            }
            return file;
        } catch (Exception e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return null;
    }
}
