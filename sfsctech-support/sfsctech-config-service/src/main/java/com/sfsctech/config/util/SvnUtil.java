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
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
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

    public final Logger logger = LoggerFactory.getLogger(SvnUtil.class);

    @Autowired
    private SvnHelper svnHelper;

    @Value("${logback.file.space}")
    private String filespace;


    public boolean update(String label) {
        try {
            File workspace = new File(filespace);
            SVNURL repositoryURL = SVNURL.parseURIEncoded(svnHelper.getUri()).appendPath(label, false);
            if (!SVNWCUtil.isVersionedDirectory(workspace)) {
                svnHelper.checkout(repositoryURL, SVNRevision.HEAD, workspace, SVNDepth.INFINITY);
            } else {
                svnHelper.update(workspace, SVNRevision.HEAD, SVNDepth.INFINITY);
            }
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
            return false;
        }
        return true;
    }

    public String download(String name, String profile, String label) {
        String fileName = name + LabelConstants.DASH + profile + ".xml";
        File filePath = new File(filespace + LabelConstants.FORWARD_SLASH + fileName);
        if (!FileUtil.isExists(filespace)) {
            update(label);
        } else {

        }
        try {
            SVNURL repositoryURL = SVNURL.parseURIEncoded(svnHelper.getUri()).appendPath(label, false).appendPath(fileName, false);
            SVNInfo info = svnHelper.getClientManager().getWCClient().doInfo(repositoryURL, SVNRevision.HEAD, SVNRevision.HEAD);
            SVNInfo info2 = svnHelper.getClientManager().getWCClient().doInfo(filePath, SVNRevision.HEAD);
            System.out.println(FileUtil.getFileMD5(filePath));
            System.out.println(FileUtil.getFileMD5(info.getFile()));

//            info.getCommittedRevision().getNumber();
//            FileUtil
//            result = info.getCommittedRevision().getNumber();
            svnHelper.doExport(repositoryURL, filePath, SVNRevision.HEAD, SVNRevision.HEAD, "downloadFile", true, SVNDepth.INFINITY);
        } catch (Exception e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return filePath.getPath();
    }
}
