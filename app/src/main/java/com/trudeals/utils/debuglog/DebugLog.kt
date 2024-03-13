package com.trudeals.utils.debuglog

import android.util.Log

/**
 * This is free and unencumbered software released into the public domain.
 * <p/>
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * <p/>
 * For more information, please refer to <http://unlicense.org/>
 */


/**
 * Create a simple and more understandable Android logs.
 */
object DebugLog {
    var className: String? = null
    var methodName: String? = null
    var lineNumber = 0
    val isDebuggable: Boolean
        get() = true

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("[")
        buffer.append(methodName)
        buffer.append(":")
        buffer.append(lineNumber)
        buffer.append("]")
        buffer.append(log)
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(tag: String?, message: String?) {
        if (!isDebuggable) return

        // Throwable instance must be created before any methods
        getMethodNames(Throwable().stackTrace)
        Log.e(tag, message!!)
    }

    fun e(message: String) {
        e(className, createLog(message))
    }

    fun i(tag: String?, message: String?) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.i(tag, message!!)
    }

    fun i(message: String) {
        i(className, createLog(message))
    }

    fun d(tag: String?, message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.d(tag, createLog(message))
    }

    fun d(message: String) {
        d(className, createLog(message))
    }

    fun v(tag: String?, message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.v(tag, createLog(message))
    }

    fun v(message: String) {
        v(className, createLog(message))
    }

    fun w(tag: String?, message: String?) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.w(tag, message!!)
    }

    fun w(message: String) {
        w(className, createLog(message))
    }

    fun wtf(tag: String?, message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.wtf(tag, createLog(message))
    }

    fun wtf(message: String) {
        wtf(className, createLog(message))
    }
}
