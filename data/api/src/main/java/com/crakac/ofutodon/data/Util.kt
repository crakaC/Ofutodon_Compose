package com.crakac.ofutodon.data

import android.text.format.DateUtils
import java.time.Instant

class Util private constructor() {
    companion object {
        fun parseCreatedAt(source: String): Long {
            return Instant.parse(source).toEpochMilli()
        }

        fun getRelativeTimeSpanString(time: Long): CharSequence {
            return DateUtils.getRelativeTimeSpanString(
                time,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
            )
        }
    }
}
