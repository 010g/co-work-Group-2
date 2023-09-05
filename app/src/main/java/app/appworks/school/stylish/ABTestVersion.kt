package app.appworks.school.stylish

import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import app.appworks.school.stylish.home.HomeViewModel
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

object ABTestVersion {
    var version = ""

    var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
}

object  ABTestUUID{
    var UUID = ""
}