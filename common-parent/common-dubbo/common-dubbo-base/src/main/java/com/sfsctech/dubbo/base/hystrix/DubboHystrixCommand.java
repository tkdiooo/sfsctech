package com.sfsctech.dubbo.base.hystrix;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.*;
import com.netflix.hystrix.*;
import com.sfsctech.dubbo.base.constants.DubboConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class DubboHystrixCommand
 *
 * @author 张麒 2018-3-1.
 * @version Description:
 */
public class DubboHystrixCommand extends HystrixCommand<Result> {

    private static Logger logger = LoggerFactory.getLogger(DubboHystrixCommand.class);
    private static final int DEFAULT_THREADPOOL_CORE_SIZE = 30;
    private static final int STATUSTIME = 20000;
    private static final int ROLLINGTIME = 60000;
    private Invoker invoker;
    private Invocation invocation;

    public DubboHystrixCommand(Invoker invoker, Invocation invocation) {
        super(
                // 组名使用模块名称
                Setter.withGroupKey(
                        // 表示所属的group，一个group共用线程池
                        HystrixCommandGroupKey.Factory.asKey(invoker.getInterface().getName()))
                        // 当前执行方法名
                        .andCommandKey(HystrixCommandKey.Factory.asKey(String.format("%s_%d", invocation.getMethodName(), invocation.getArguments() == null ? 0 : invocation.getArguments().length)))
                        // 通用配置
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                // 用于计算百分比的滚动窗口时间长度
                                .withMetricsRollingPercentileWindowInMilliseconds(ROLLINGTIME)
                                // 可统计的滚动窗口的时间长度,这段时间内的执行数据用于熔断器和指标发布
                                .withMetricsRollingStatisticalWindowInMilliseconds(STATUSTIME)
                                // 触发熔断器需要的请求量，10秒钟内至少19此请求失败，熔断器才发挥起作用
                                .withCircuitBreakerRequestVolumeThreshold(20)
                                // 熔断器从打开到半开的等待时间，熔断器中断请求30秒后会进入半打开状态,放部分流量过去重试
                                .withCircuitBreakerSleepWindowInMilliseconds(30000)
                                // 触发熔断的错误率，错误率达到50开启熔断保护
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                // 是否开启超时时间中断抛出异常的功能，使用dubbo的超时，禁用这里
                                .withExecutionTimeoutEnabled(false)
                                // 是否开启熔断功能
                                .withCircuitBreakerEnabled(true)
                        )
                        // 线程池策略时的配置
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                // 线程数量
                                .withCoreSize(getConcurrency(invoker.getUrl()))));
        this.invoker = invoker;
        this.invocation = invocation;
    }

    /**
     * 获取线程池大小
     *
     * @param url
     * @return
     */
    private static int getConcurrency(URL url) {
        if (url != null) {
            int size = url.getParameter(DubboConstants.HYSTRIX_CONCURRENCY, DEFAULT_THREADPOOL_CORE_SIZE);
            if (logger.isDebugEnabled()) {
                logger.debug(DubboConstants.HYSTRIX_CONCURRENCY + ":" + size);
            }
            return size;
        }
        return DEFAULT_THREADPOOL_CORE_SIZE;

    }

    @Override
    protected Result run() {
        return invoker.invoke(invocation);
    }

    @Override
    protected Result getFallback() {
        // TODO 缺少通知监测系统代码
        if (executionResult.isResponseSemaphoreRejected()) {
            return new RpcResult(new RpcException("Rpc流关闭异常", executionResult.getExecutionException()));
        }
        return new RpcResult(new RpcException("Rpc熔断器开启拦截", executionResult.getExecutionException()));
    }
}
