package com.sfsctech.config.logback;

import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.util.FileUtil;
import com.sfsctech.config.util.SvnHelper;
import com.sfsctech.config.util.SvnUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.io.IOException;

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
    public ResponseEntity<byte[]> index(@PathVariable("name") String name, @PathVariable("profile") String profile, @PathVariable("label") String label) throws IOException {
        logger.info("请求文件信息：{label:" + label + ",name:" + name + ",profile:" + profile + "}");
        File file = svnUtil.getLogbackFile(name, profile, label);
        if (null != file) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        }
        return null;
    }
}
