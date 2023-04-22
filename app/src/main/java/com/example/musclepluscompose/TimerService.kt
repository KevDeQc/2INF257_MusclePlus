package com.example.musclepluscompose

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import androidx.core.app.NotificationCompat
import java.util.*
import java.util.concurrent.TimeUnit

class TimerService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "my_channel_id"
        const val NOTIFICATION_ID = 1
        const val UPDATE_INTERVAL = 1000L // 1 second
        const val ACTION_UPDATE_ELAPSED_TIME = "com.example.workouttracker.UPDATE_ELAPSED_TIME"
        const val EXTRA_ELAPSED_TIME = "com.example.workouttracker.EXTRA_ELAPSED_TIME"
    }

    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L
    private var handler: Handler? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Save the start time
        startTime = SystemClock.elapsedRealtime()

        // Create the notification channel
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "My Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        // Update the notification with the initial elapsed time
        updateNotification()

        // Start the handler to update the elapsed time periodically
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(timerRunnable, UPDATE_INTERVAL)

        val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running in the foreground.")
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the handler when the service is destroyed
        handler?.removeCallbacks(timerRunnable)
        handler = null
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            // Calculate the elapsed time
            elapsedTime = SystemClock.elapsedRealtime() - startTime

            // Update the notification
            updateNotification()

            // Update the elapsed time state variable in the WorkoutTracker activity
            val intent = Intent(ACTION_UPDATE_ELAPSED_TIME).apply {
                putExtra(EXTRA_ELAPSED_TIME, elapsedTime)
            }
            sendBroadcast(intent)

            // Schedule the next update
            handler?.postDelayed(this, UPDATE_INTERVAL)
        }
    }

    private fun updateNotification() {
        Log.d("TimerService", "Updating notification with elapsed time: $elapsedTime")
        // Update the notification with the current elapsed time
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Workout in progress")
            .setContentText("Time Elapsed: ${formatElapsedTime(elapsedTime)}")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = notificationBuilder.build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun formatElapsedTime(timeMillis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
