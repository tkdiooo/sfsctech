//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public abstract class ThreadPoolAdvisor {
    private final int SAMPLE_QUEUE_SIZE = 1000;
    private final int EPSYLON = 20;
    private volatile boolean expired;
    private final long testtime = 3000L;

    public ThreadPoolAdvisor() {
    }

    protected void calculateBoundaries(BigDecimal targetUtilization, BigDecimal targetQueueSizeBytes) {
        this.calculateOptimalCapacity(targetQueueSizeBytes);
        Runnable task = this.creatTask();
        this.start(task);
        this.start(task);
        long cputime = this.getCurrentThreadCPUTime();
        this.start(task);
        cputime = this.getCurrentThreadCPUTime() - cputime;
        long waittime = 3000000000L - cputime;
        this.calculateOptimalThreadCount(cputime, waittime, targetUtilization);
    }

    private void calculateOptimalCapacity(BigDecimal targetQueueSizeBytes) {
        long mem = this.calculateMemoryUsage();
        BigDecimal queueCapacity = targetQueueSizeBytes.divide(new BigDecimal(mem), RoundingMode.HALF_UP);
        System.out.println("Target queue memory usage (bytes): " + targetQueueSizeBytes);
        System.out.println("createTask() produced " + this.creatTask().getClass().getName() + " which took " + mem + " bytes in a queue");
        System.out.println("Formula: " + targetQueueSizeBytes + " / " + mem);
        System.out.println("* Recommended queue capacity (bytes): " + queueCapacity);
    }

    private void calculateOptimalThreadCount(long cpu, long wait, BigDecimal targetUtilization) {
        BigDecimal waitTime = new BigDecimal(wait);
        BigDecimal computeTime = new BigDecimal(cpu);
        BigDecimal numberOfCPU = new BigDecimal(Runtime.getRuntime().availableProcessors());
        BigDecimal optimalthreadcount = numberOfCPU.multiply(targetUtilization).multiply((new BigDecimal(1)).add(waitTime.divide(computeTime, RoundingMode.HALF_UP)));
        System.out.println("Number of CPU: " + numberOfCPU);
        System.out.println("Target utilization: " + targetUtilization);
        System.out.println("Elapsed time (nanos): 3000000000");
        System.out.println("Compute time (nanos): " + cpu);
        System.out.println("Wait time (nanos): " + wait);
        System.out.println("Formula: " + numberOfCPU + " * " + targetUtilization + " * (1 + " + waitTime + " / " + computeTime + ")");
        System.out.println("* Optimal thread count: " + optimalthreadcount);
    }

    public void start(Runnable task) {
        long start = 0L;
        int runs = 0;

        do {
            ++runs;
            if (runs > 5) {
                throw new IllegalStateException("Test not accurate");
            }

            this.expired = false;
            start = System.currentTimeMillis();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    ThreadPoolAdvisor.this.expired = true;
                }
            }, 3000L);

            while(!this.expired) {
                task.run();
            }

            start = System.currentTimeMillis() - start;
            timer.cancel();
        } while(Math.abs(start - 3000L) > 20L);

        this.collectGarbage(3);
    }

    private void collectGarbage(int times) {
        for(int i = 0; i < times; ++i) {
            System.gc();

            try {
                Thread.sleep(10L);
            } catch (InterruptedException var4) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    public long calculateMemoryUsage() {
        BlockingQueue<Runnable> queue = this.createWorkQueue();

        for(int i = 0; i < 1000; ++i) {
            queue.add(this.creatTask());
        }

        this.collectGarbage(15);
        long mem0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        queue = this.createWorkQueue();

        for(int i = 0; i < 1000; ++i) {
            queue.add(this.creatTask());
        }

        this.collectGarbage(15);
        long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (mem1 - mem0) / 1000L;
    }

    protected abstract Runnable creatTask();

    protected abstract BlockingQueue<Runnable> createWorkQueue();

    protected abstract long getCurrentThreadCPUTime();
}
