package com.rousetime.android_startup.utils

import android.util.Log
import com.rousetime.android_startup.model.LoggerLevel

/**
 * Created by idisfkj on 2020/7/24.
 * Email: idisfkj@gmail.com.
 */
internal object StartupLogUtils {

    private const val TAG = "StartupTrack"
    private const val MAX_LOG_LENGTH = 4000

    var level: LoggerLevel = LoggerLevel.NONE

    fun e(message: String) {
        if (level >= LoggerLevel.ERROR) log(TAG, Log.DEBUG, message)
    }

    fun d(message: String) {
        if (level >= LoggerLevel.DEBUG) log(TAG, Log.DEBUG, message)
    }

    private fun log(tag: String, priority: Int, message: String) {
        if (message.length < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(TAG, message)
            } else {
                Log.println(priority, tag, message)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }
}