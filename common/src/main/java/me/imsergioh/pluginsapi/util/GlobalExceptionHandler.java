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
            Thread.setDefaultUncaughtExceptionHandler(HANDLER);
        }
        exceptionListeners.addAll(Arrays.asList(listeners));
        checkSetHandlerThread();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("NEW ERROR! " + e.getMessage());
        for (ExceptionHandlerListener listener : exceptionListeners) {
            System.out.println("Parsing ExceptionListener -> " + listener.getClass().getName());
            listener.onException(thread, e);
        }
    }

    private static void checkSetHandlerThread() {
        if (thread != null) return;
        thread = new Thread(() -> {
            while (true) {
                Thread.getAllStackTraces().keySet().forEach(thread -> {
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
