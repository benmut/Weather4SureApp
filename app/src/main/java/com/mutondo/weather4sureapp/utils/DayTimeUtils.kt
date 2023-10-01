package com.mutondo.weather4sureapp.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class DayTimeUtils {

    companion object {
        fun getDayOfWeek(timestamp: Long): String {
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
        }

        fun getTimeFromTimestamp(timestamp: Long): String {
            return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}