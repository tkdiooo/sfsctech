package com.sfsctech.core.logger.rmt.es.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * Class ESAppender
 *
 * @author 张麒 2018-11-19.
 * @version Description:
 */
public class ESAppender extends AppenderBase<ILoggingEvent> {

//    @Autowired(required=false)
//    private ElasticsearchTemplate esTemplate;

    @Override
    protected void append(ILoggingEvent event) {
//        esTemplate.index
    }
}
