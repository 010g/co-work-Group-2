package app.appworks.school.stylish

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

object ABTestVersion {
    const val version = "A"


    var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
}