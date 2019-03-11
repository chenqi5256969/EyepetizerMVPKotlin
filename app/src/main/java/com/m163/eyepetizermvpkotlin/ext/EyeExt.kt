package com.m163.eyepetizermvpkotlin.ext

import java.util.*


 fun getToday(): String {
    var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    var data = Date()
    var calendar: Calendar = Calendar.getInstance()
    calendar.time = data
    var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
    if (index < 0) {
        index = 0
    }
    return list[index]
}
