package com.sfsctech.config.logback;

import com.sfsctech.config.util.SVNUtil;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

import java.io.File;

/**
 * Class Subversion
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
public class Subversion {

//    private void checkWorkCopy(Project project) {
////        project.setSvnUrl(rb.getString("svn.url"));
//        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
//
//        SVNURL repositoryURL = null;    // trunk
//        try {
//            // eg: http://svn.ambow.com/wlpt/bsp
//            repositoryURL = SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk", false);
//        } catch (SVNException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        File wc = new File(workspace);
//        File wc_project = new File(workspace + "/" + project.getName());
//
//        SVNURL projectURL = null;   // projectName
//        try {
//            projectURL = repositoryURL.appendPath(project.getName(), false);
//        } catch (SVNException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        if (!SVNUtil.isWorkingCopy(wc)) {
//            if (!SVNUtil.isURLExist(projectURL, username, password)) {
//                SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, wc, SVNDepth.EMPTY);
//            } else {
//                SVNUtil.checkout(clientManager, projectURL, SVNRevision.HEAD, wc_project, SVNDepth.INFINITY);
//            }
//        } else {
//            SVNUtil.update(clientManager, wc, SVNRevision.HEAD, SVNDepth.INFINITY);
//        }
//    }
}
