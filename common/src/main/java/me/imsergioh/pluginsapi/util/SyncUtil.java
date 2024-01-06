package me.imsergioh.pluginsapi.util;

import java.util.concurrent.*;

public class SyncUtil {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "SyncUtil"));
    private static final ScheduledExecutorService scheduledFuture = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "SyncUtil"));


    public static void sync(Runnable runnable) {
        runnable.run();
    }

    public static void async(Runnable runnable) {
        executorService.submit(runnable);
    }

    public static ScheduledFuture<?> later(Runnable runnable, long millis) {
        return scheduledFuture.schedule(runnable, millis, TimeUnit.MILLISECONDS);

    }

}
