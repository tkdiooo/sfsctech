package com.sfsctech.test.jenkins;

import com.sfsctech.core.web.domain.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class IndexController
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@RestController
public class IndexController {

    private final Logger loggers = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("index")
    public ActionResult<String> index(@RequestParam String name) {
        return ActionResult.forSuccess("这是Spring-Boot-Jenkins项目：" + name);
    }
}
