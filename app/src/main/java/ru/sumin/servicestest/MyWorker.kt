package ru.sumin.servicestest

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        log("StartDoWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0..5) {
            Thread.sleep(1000)
            log("$page, $i")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("myService", message)
    }

    companion object {

        private const val JOB_ID = 4
        private const val PAGE = "page"
        const val WORK_NAME = "work name"

        fun getRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(PAGE to page))
                .setConstraints(getConstrains())
                .build()
        }

        fun getConstrains() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

    }
}
