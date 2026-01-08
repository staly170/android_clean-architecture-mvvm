package com.nestpay.pg.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.sql.Date

@ProvidedTypeConverter
class OrderTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}