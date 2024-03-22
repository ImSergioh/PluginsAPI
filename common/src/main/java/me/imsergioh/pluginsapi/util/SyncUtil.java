package me.imsergioh.pluginsapi.util;

import java.util.concurrent.*;

public class SyncUtil {

    private static final ScheduledExecutorService scheduledFuture = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "SyncUtil"));


    public static void sync(Runnable runnable) {
        runnable.run();
    }

    public static void async(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    public static CompletableFuture<Void> later(Runnable runnable, long millis) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        scheduledFuture.schedule(() -> {
            try {
                runnable.run();
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }, millis, TimeUnit.MILLISECONDS);

        return future;
    }
}
