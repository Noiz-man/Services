package ru.sumin.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobServices: JobService() {

    val scope = CoroutineScope(Dispatchers.Main)


    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        scope.launch {
            log("onStartJob")
            for (i in 1..100) {
                delay(1000)
                log("$i")
            }
            jobFinished(p0, true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(message: String) {
        Log.d("myService", message)
    }

    companion object {
        const val JOB_ID = 3
    }

}