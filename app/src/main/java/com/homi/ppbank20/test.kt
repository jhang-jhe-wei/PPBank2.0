package com.homi.ppbank20

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val str = "2020-11-23 12:23:45"
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    val date = simpleDateFormat.parse(str)


// Choose time zone in which you want to interpret your Date
// Choose time zone in which you want to interpret your Date
    val cal: Calendar = Calendar.getInstance()
    cal.setTime(date)

    print(cal.get(Calendar.MONTH))
}