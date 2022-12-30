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
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyJobIntentServices : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleWork")
        val page = intent.getIntExtra("PAGE", 0)
        for (i in 0..5) {
            Thread.sleep(1000)
            log("$page, $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("myService", message)
    }

    companion object {

        private const val JOB_ID = 4


        fun onStartEnqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentServices::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentServices::class.java).apply {
                putExtra("PAGE", page)
            }
        }
    }

}