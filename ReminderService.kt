/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This service waits for a short delay and then shows a reminder notification.
When the user taps the notification, it opens MainActivity.
*/
package com.example.project_3_jennyferparmar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ReminderService : Service() {

    private val channelId = "notes_reminder_channel" // Notification channel id
    private val notificationId = 1001                // Notification id

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Create channel for Android 8+ devices
        createNotificationChannel()

        // Run delay + notification on a background thread
        Thread {
            try {
                Thread.sleep(7000) // Delay before showing notification
            } catch (_: InterruptedException) {
                stopSelf() // Stop service if thread is interrupted
                return@Thread
            }

            // Intent to open MainActivity when notification is tapped
            val openMain = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            // PendingIntent flags (Android 12+ requires IMMUTABLE or MUTABLE)
            val pendingFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                openMain,
                pendingFlags
            )

            // Build the notification
            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Built-in icon
                .setContentTitle("Reminder")                      // Title text
                .setContentText("Don’t forget to check your notes!") // Message text
                .setContentIntent(pendingIntent)                  // Opens MainActivity
                .setAutoCancel(true)                              // Dismiss on tap
                .build()

            // Show notification
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(notificationId, notification)

            // Stop service after notification is shown
            stopSelf()
        }.start()

        // Do not restart service automatically if Android kills it
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        // NotificationChannel is required on Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notes Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        // This is a started service, not a bound service
        return null
    }
}
