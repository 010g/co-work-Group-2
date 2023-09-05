package app.appworks.school.stylish

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import app.appworks.school.stylish.data.source.StylishRepository
import app.appworks.school.stylish.util.ServiceLocator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.properties.Delegates

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 */
class StylishApplication : Application() {

    // Depends on the flavor,
    val stylishRepository: StylishRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: StylishApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("token123", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                Log.d("token123", token)
            })
//token log here initiation callback log for token when its completed
// Add the notification channel creation code here
// establish a link between our app and the Android Device.
// We can use this channel to set the behavior of the notifications

        val channel = NotificationChannel(
            "1",
            "Group 2 Android Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
