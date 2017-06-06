package com.sfsctech.config.logback;

import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.util.FileUtil;
import com.sfsctech.config.util.SvnHelper;
import com.sfsctech.config.util.SvnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;

/**
 * Class Subversion
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
@Controller
public class Subversion {

    public final Logger logger = LoggerFactory.getLogger(Subversion.class);

    @Autowired
    private SvnUtil svnUtil;

    @GetMapping("/{label}/{name}/{profile}")
    public String index(@PathVariable("name") String name, @PathVariable("profile") String profile, @PathVariable("label") String label) {
        System.out.println(svnUtil.download(name, profile, label));
        return "";
    }
//
//    public void download() {
//        try {
//            File wc_project = new File("d:/logback-deve.xml");
//
//            SVNUpdateClient updateClient = svnHelper.getClientManager().getUpdateClient();
//            updateClient.setIgnoreExternals(false);
//            SVNURL repositoryURL = SVNURL.parseURIEncoded(svnHelper.getUri()).appendPath("framework", false).appendPath("logback-deve.xml", false);
//            updateClient.doExport(repositoryURL, wc_project, SVNRevision.HEAD, SVNRevision.HEAD, "downloadModel", true, SVNDepth.INFINITY);
//        } catch (SVNException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//        }
//    }

//    public boolean updateProjectFromSvn(Project project) {
//        if(null == project || null == rb.getString("svn.url")){
//            return false;
//        }
//        project.setSvnUrl(rb.getString("svn.url"));
//
//        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
//        if (null == clientManager) {
//            logger.error("SVN login error! >>> url:" + project.getSvnUrl()
//                    + " username:" + username + " password:" + password);
//            return false;
//        }
//
//        // 注册一个更新事件处理器
//        clientManager.getCommitClient().setEventHandler(new UpdateEventHandler());
//
//        SVNURL repositoryURL = null;
//        try {
//            // eg: http://svn.ambow.com/wlpt/bsp
//            repositoryURL = SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk/"+project.getName(), false);
//        } catch (SVNException e) {
//            logger.error(e.getMessage(),e);
//            return false;
//        }
//
//        File ws = new File(new File(workspace), project.getName());
//        if(!SVNWCUtil.isVersionedDirectory(ws)){
//            SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, new File(workspace), SVNDepth.INFINITY);
//        }else{
//            SVNUtil.update(clientManager, ws, SVNRevision.HEAD, SVNDepth.INFINITY);
//        }
//        return true;
//    }
}
