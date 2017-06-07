package com.sfsctech.config.util;

import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.util.FileUtil;
import com.sfsctech.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Component
public class SvnUtil {

    private final Logger logger = LoggerFactory.getLogger(SvnUtil.class);

    @Autowired
    private SvnHelper svnHelper;

    @Value("${logback.file.space}")
    private String filespace;

    public String logbackLabel = "logback";


    public void update(String label) {
        Long version = 0L;
        try {
            File workspace = new File(filespace);
            if (!SVNWCUtil.isVersionedDirectory(workspace)) {
                SVNURL repositoryURL = SVNURL.parseURIEncoded(svnHelper.getUri()).appendPath(label, false);
                version = svnHelper.checkout(repositoryURL, SVNRevision.HEAD, workspace, SVNDepth.INFINITY);
            } else {
                version = svnHelper.update(workspace, SVNRevision.HEAD, SVNDepth.INFINITY);
            }
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        logger.info("更新工作空间文件：" + filespace + "，version：" + version);
    }

    public File getLogbackFile(String name, String profile, String label) {
        String fileName = name + LabelConstants.DASH + profile + ".xml";
        // 工作空间不存在，checkout工作空间
        if (!FileUtil.isExists(filespace)) {
            update(label);
        }
        try {
            File file = new File(filespace + LabelConstants.FORWARD_SLASH + fileName);
            SVNStatus status = svnHelper.showStatus(file, true);
            // 文件不存在 || 文件需要更新版本
            if (!file.exists() || -1 != status.getRemoteRevision().getNumber()) {
                Long version = svnHelper.update(file, SVNRevision.HEAD, SVNDepth.INFINITY);
                logger.info("更新文件：" + fileName + "，version：" + version + "，至工作空间：" + filespace + "");
            }
            return file;
        } catch (Exception e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return null;
    }
}
