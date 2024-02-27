package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.instance.handler.ExceptionHandlerListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final boolean LOG_STACK_TRACES = false;

    private static GlobalExceptionHandler HANDLER;
    private static Thread thread;
    private static final Set<ExceptionHandlerListener> exceptionListeners = new HashSet<>();

    public static void registerListener(ExceptionHandlerListener... listeners) {
        if (HANDLER == null) {
            HANDLER = new GlobalExceptionHandler();
        }
        exceptionListeners.addAll(Arrays.asList(listeners));
        checkSetHandlerThread();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(t, e);
    }

    private void handle(Thread thread, Throwable e) {
        for (ExceptionHandlerListener listener : exceptionListeners) {
            listener.onException(thread, e);
        }
    }

    private static void checkSetHandlerThread() {
        if (exceptionListeners.isEmpty()) return;
        if (thread != null) return;
        thread = new Thread(() -> {
            while (true) {
                Thread.getAllStackTraces().keySet().forEach(thread -> {
                    if (thread.getUncaughtExceptionHandler() instanceof GlobalExceptionHandler) return;
                    thread.setUncaughtExceptionHandler(HANDLER);
                });
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

}
