package ru.sumin.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.app.job.JobWorkItem
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobServices : JobService() {

    val scope = CoroutineScope(Dispatchers.Main)


    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scope.launch {
                var workItem = p0?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra("PAGE", 0)
                    for (i in 1..5) {
                        delay(1000)
                        log("$page $i")
                    }
                    p0?.completeWork(workItem)
                    workItem = p0?.dequeueWork()
                }
                jobFinished(p0, true)
            }
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

        fun newIntent(page: Int): Intent {
            val intent = Intent().apply {
                putExtra("PAGE", page)
            }
            return intent
        }

    }

}