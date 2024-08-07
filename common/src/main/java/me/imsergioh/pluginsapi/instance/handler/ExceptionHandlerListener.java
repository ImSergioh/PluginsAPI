package me.imsergioh.pluginsapi.instance.handler;

import me.imsergioh.pluginsapi.util.GlobalExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;

public interface ExceptionHandlerListener {

    void onException(Timer t, Throwable e);

    default String getStackTraceAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    default boolean isStackTracesEnabled() {
        return GlobalExceptionHandler.LOG_STACK_TRACES;
    }
}
