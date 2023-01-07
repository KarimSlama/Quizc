package com.example.quizapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.quizapp.Constants.NOTIFICATION_ID
import com.example.quizapp.Constants.REQUEST_CODE
import com.example.quizapp.MainActivity
import com.example.quizapp.R

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val intent = Intent(applicationContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        applicationContext, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    val quizImage = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)

    val bigImage = NotificationCompat.BigPictureStyle().bigPicture(quizImage).bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
        applicationContext, applicationContext.getString(R.string.quiz_notification_channel_id)
    )

        .setContentTitle(applicationContext.getString(R.string.quiz_timer))
        .setStyle(bigImage)
        .setLargeIcon(quizImage)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.logo)
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}//end sendNotification()

fun NotificationManager.cancelNotification() {
    cancelAll()
}//end cancelNotification()