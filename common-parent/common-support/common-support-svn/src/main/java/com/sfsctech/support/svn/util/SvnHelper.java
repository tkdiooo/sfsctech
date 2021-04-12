package com.sfsctech.support.svn.util;

import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;

/**
 * Class SvnHelper
 *
 * @author 张麒 2017/6/6.
 * @version Description:
 */
public class SvnHelper {

    private final Logger logger = LoggerFactory.getLogger(SvnHelper.class);

    private String uri;
    private String username;
    private String password;

    private SVNClientManager clientManager;
    private ISVNAuthenticationManager authManager;
    private SVNRepository repository;

    public SvnHelper(String uri, String username, String password) throws SVNException {
        this.uri = uri;
        this.username = username;
        this.password = password;
        this.initLibrary();
        this.initISVNAuthenticationManager();
        this.initSVNRepository();
        this.initSVNClientManager();
    }

    /**
     * 通过不同的协议初始化版本库
     */
    private void initLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    /**
     * 初始化SVN鉴权
     */
    private void initISVNAuthenticationManager() {
        this.authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password.toCharArray());
    }

    /**
     * 初始化SVN版本库
     *
     * @throws SVNException
     */
    private void initSVNRepository() throws SVNException {
        this.repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(uri));
        this.repository.setAuthenticationManager(this.authManager);
    }

    /**
     * 初始化SVNClientManager
     */
    private void initSVNClientManager() {
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        this.clientManager = SVNClientManager.newInstance(options, authManager);
    }


    /**
     * Imports an unversioned directory into a repository location denoted by a
     * destination URL
     *
     * @param localPath     a local unversioned directory or singal file that will be imported into a
     *                      repository;
     * @param dstURL        a repository location where the local unversioned directory/file will be
     *                      imported into
     * @param commitMessage
     * @param isRecursive   递归
     * @return
     */
    public SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage, boolean isRecursive) {
        try {
            return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, null, true, true, SVNDepth.fromRecurse(isRecursive));
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return null;
    }

    /**
     * Puts directories and files under version control
     *
     * @param wcPath work copy path
     */
    public void addEntry(File wcPath) {
        try {
            clientManager.getWCClient().doAdd(new File[]{wcPath}, true, false, false, SVNDepth.INFINITY, false, false, true);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
    }

    /**
     * Collects status information on a single Working Copy item
     *
     * @param wcPath local item's path
     * @param remote true to check up the status of the item in the repository,
     *               that will tell if the local item is out-of-date (like '-u' option in the SVN client's
     *               'svn status' command), otherwise false
     * @return
     * @throws SVNException
     */
    public SVNStatus showStatus(File wcPath, boolean remote) {
        try {
            return clientManager.getStatusClient().doStatus(wcPath, remote);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return null;
    }

    /**
     * Commit work copy's change to svn
     *
     * @param wcPath        working copy paths which changes are to be committed
     * @param keepLocks     whether to unlock or not files in the repository
     * @param commitMessage commit log message
     * @return
     * @throws SVNException
     */
    public SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage) {
        try {
            return clientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks, commitMessage, null, null, false, false, SVNDepth.INFINITY);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return null;
    }

    /**
     * Updates a working copy (brings changes from the repository into the working copy).
     *
     * @param wcPath           working copy path
     * @param updateToRevision revision to update to
     * @param depth            update的深度：目录、子目录、文件
     * @return
     * @throws SVNException
     */
    public long update(File wcPath, SVNRevision updateToRevision, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();

        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);

        /*
         * returns the number of the revision wcPath was updated to
         */
        try {
            return updateClient.doUpdate(wcPath, updateToRevision, depth, false, false);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return 0;
    }

    /**
     * recursively checks out a working copy from url into wcDir
     *
     * @param url      a repository location from where a Working Copy will be checked out
     * @param revision the desired revision of the Working Copy to be checked out
     * @param destPath the local path where the Working Copy will be placed
     * @param depth    checkout的深度，目录、子目录、文件
     * @return
     * @throws SVNException
     */
    public long checkout(SVNURL url, SVNRevision revision, File destPath, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is
         */
        try {
            return updateClient.doCheckout(url, destPath, revision, revision, depth, false);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return 0;
    }

    public long doExport(SVNURL url, File destPath, SVNRevision pegRevision, SVNRevision revision, String eolStyle, boolean overwrite, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);

        try {
            return updateClient.doExport(url, destPath, pegRevision, revision, eolStyle, overwrite, depth);
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return 0;
    }

    /**
     * 确定path是否是一个工作空间
     *
     * @param path
     * @return
     */
    public boolean isWorkingCopy(File path) {
        if (!path.exists()) {
            logger.warn("'" + path + "' not exist!");
            return false;
        }
        try {
            if (null == SVNWCUtil.getWorkingCopyRoot(path, false)) {
                return false;
            }
        } catch (SVNException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
        return true;
    }

    /**
     * 确定一个URL在SVN上是否存在
     *
     * @param url
     * @return
     */
    public boolean isURLExist(SVNURL url) {
        try {
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(this.username, this.password.toCharArray());
            svnRepository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1);
            return nodeKind != SVNNodeKind.NONE;
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUri() {
        return uri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SVNClientManager getClientManager() {
        return clientManager;
    }

    public ISVNAuthenticationManager getAuthManager() {
        return authManager;
    }

    public SVNRepository getRepository() {
        return repository;
    }

}
