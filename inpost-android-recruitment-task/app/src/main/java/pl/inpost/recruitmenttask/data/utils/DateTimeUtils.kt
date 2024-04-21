package pl.inpost.recruitmenttask.data.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime?.zonedDateTimeToTimestampUTC(): Long? {
    return this?.withZoneSameInstant(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
}

fun Long?.timestampToZonedDateTimeUTC(zoneId: ZoneId = ZoneOffset.UTC): ZonedDateTime? {
    return this?.let {
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(it), zoneId)
    }
}

fun Long?.timestampToUserDefaultZonedDateTime(): ZonedDateTime? {
    return this.timestampToZonedDateTimeUTC(ZoneId.systemDefault())
}

fun ZonedDateTime.formatFullShipmentDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("EE | dd.MM.yy | HH:mm", Locale.getDefault())
    return this.format(formatter)
}

fun getUserCurrentTimeInUTC(): ZonedDateTime {
    return ZonedDateTime.now(ZoneOffset.UTC)
}