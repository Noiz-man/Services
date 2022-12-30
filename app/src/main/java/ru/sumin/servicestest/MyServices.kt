package ru.sumin.servicestest

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyServices: android.app.Service() {

    val scope = CoroutineScope(Dispatchers.Main)


    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            log("onStartCommand")
            val start = intent?.getIntExtra(START, 0) ?: 0
            for (i in start..start + 100) {
                delay(1000)
                log("$i")
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("myService", message)
    }

    companion object {

        private const val START = "start"

        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyServices::class.java).apply {
                putExtra(START, start)
            }
        }
    }

}