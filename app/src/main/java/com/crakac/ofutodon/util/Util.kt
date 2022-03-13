package com.crakac.ofutodon.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class Util private constructor() {
    companion object {
        private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        fun parseCreatedAt(source: String): Long {
            return (sdf.parse(source)?.time ?: 0L) + TimeZone.getDefault().rawOffset
        }

        fun getRelativeTimeSpanString(time: Long): CharSequence {
            return DateUtils.getRelativeTimeSpanString(
                time,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS
            )
        }
    }
}