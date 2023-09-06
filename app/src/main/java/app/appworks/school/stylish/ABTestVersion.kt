package app.appworks.school.stylish

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date


object ABTestVersion {
    var version = ""

    var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))

    var limitForHomePageFirstUserTrackingApiCall = 1

    var limitForHomePageFirstUserTrackingLeaveApiCall = 1
}

object  ABTestUUID{
    var UUID = ""
}