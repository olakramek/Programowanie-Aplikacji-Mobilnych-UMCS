package com.example.powiadomienia2


import YourAlarmReceiver
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class WaterNotificationService(
    private val context:Context
){
    private val notificationManager=context.getSystemService(NotificationManager::class.java)



    fun showCustomNotification(
        title: String,
        description: String,
        expandedDescription: String,
        @DrawableRes imageResId: Int,
        @LayoutRes layoutResId: Int
    ) {
        // Create notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "custom_notification_channel",
                "Custom Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationLayout = RemoteViews(context.packageName, layoutResId)
        notificationLayout.setTextViewText(R.id.notification_expanded_description, expandedDescription)
        notificationLayout.setImageViewResource(R.id.notification_image, imageResId)

        val alarmIntent = Intent(context, YourAlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE)

        // Add an action button to the notification
        val action = NotificationCompat.Action(
            R.drawable.ic_alarm_foreground,
            "Set Alarm",
            pendingIntent
        )




        val notification = NotificationCompat.Builder(context, "custom_notification_channel")
            .setSmallIcon(R.drawable.ic_icon_foreground)
            .setColor(0xFFBD5C96.toInt())
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentTitle(title)
            .setContentText(description)
            .setAutoCancel(true)

            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        context.bitmapFromResource(imageResId)
                    )
                    .setSummaryText(expandedDescription)
            )
            .setContentIntent(pendingIntent) // Set the main intent for when the notification is clicked
            .addAction(action) // Add the action button
            .setContent(notificationLayout)

            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}