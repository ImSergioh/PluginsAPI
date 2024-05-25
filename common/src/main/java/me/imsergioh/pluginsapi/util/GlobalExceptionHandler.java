package me.imsergioh.pluginsapi.util;

import me.imsergioh.pluginsapi.instance.handler.ExceptionHandlerListener;

import java.util.*;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final boolean LOG_STACK_TRACES = false;

    private static GlobalExceptionHandler HANDLER;
    private static Timer timer;
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
            listener.onException(timer, e);
        }
    }

    private static void checkSetHandlerThread() {
        if (timer != null) return;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread.getAllStackTraces().keySet().forEach(thread -> {
                    thread.setUncaughtExceptionHandler(HANDLER);
                });
            }
        }, 0, 1500);
    }

}
