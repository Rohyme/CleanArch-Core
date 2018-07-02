package com.tripl3dev.domain.managers

import java.text.SimpleDateFormat
import java.util.*


fun String.getDateInMilliSeconds(): Long {

    val dateAndTime = this.removeRange(this.length - 5, this.length - 1)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val myDate = simpleDateFormat.parse(dateAndTime)
    return myDate.time

}