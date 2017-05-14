
package com.uci.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScheduleTask {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static Future<Boolean> submit(Callable<Boolean> task) {
        return executorService.submit(task);
    }

}