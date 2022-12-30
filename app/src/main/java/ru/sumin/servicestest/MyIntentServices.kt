package ru.sumin.servicestest

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentServices : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
        notificationChannel()
        startForeground(NOTIFICATION_ID, notification())
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0..3) {
            Thread.sleep(1000)
            log("$i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }



    private fun notificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun notification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Заголовок")
        .setContentText("Текст")
        .setSmallIcon(R.drawable.ic_launcher_foreground).build()

    private fun log(message: String) {
        Log.d("myService", message)
    }

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_NAME"
        private const val NOTIFICATION_ID = 2
        private const val NAME = "name"


        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentServices::class.java)
        }
    }

}