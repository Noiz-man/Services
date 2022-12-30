package ru.sumin.servicestest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.sumin.servicestest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var page = 0

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.simpleService.setOnClickListener {
            stopService(MyForegroundServices.newIntent(this))
        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this,
                MyForegroundServices.newIntent(this))
        }

        binding.intentService.setOnClickListener {
            ContextCompat.startForegroundService(this, MyIntentServices.newIntent(this))
        }

        binding.jobSheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobServices::class.java)
            val jobInfo = JobInfo.Builder(MyJobServices.JOB_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()
            val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val intent = MyJobServices.newIntent(page++)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                scheduler.enqueue(jobInfo, JobWorkItem(intent))
            }
        }

        binding.jobIntentService.setOnClickListener {
            MyJobIntentServices.onStartEnqueue(this, page++)
        }
    }
}
