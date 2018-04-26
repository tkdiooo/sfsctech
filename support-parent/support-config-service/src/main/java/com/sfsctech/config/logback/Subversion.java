package com.sfsctech.config.logback;

import com.sfsctech.constants.LabelConstants;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

/**
 * Class Subversion
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
@Controller
@RequestMapping("logback")
public class Subversion {

    public final Logger logger = LoggerFactory.getLogger(Subversion.class);

    @Autowired
    private SvnUtil svnUtil;

    @GetMapping("/{label}/{name}/{profile}")
    public ResponseEntity<byte[]> index(@PathVariable("name") String name, @PathVariable("profile") String profile, @PathVariable("label") String label) throws IOException {
        logger.info("请求文件信息：{label:" + label + ",name:" + name + ",profile:" + profile + "}");
        File file = svnUtil.getLogbackFile(name, profile, label);
        if (file == null) {
            logger.error("SVN更新 [" + name + LabelConstants.DASH + profile + ".xml] 文件异常");
            return null;
        } else if (!file.exists()) {
            logger.error("请求[" + name + LabelConstants.DASH + profile + ".xml]文件不存在");
            return null;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        }
    }
}
