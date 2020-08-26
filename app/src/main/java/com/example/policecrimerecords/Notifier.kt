package com.example.policecrimerecords

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notifier(private val context: Context) {

    val CHANNEL_NAME = "Police Crime Records"
    val CHANNEL_ID = "Police Crime Records"
    val CHANNEL_DESC = "Notification from the Police Crime Records app"

    init {
        createNotificationChannel()
    }


   private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }

            val notificationManager : NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private  fun makeNotification(text: String) : Notification{
        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
        builder.setSmallIcon(R.drawable.baseline_place_black_18)
        builder.setContentTitle("Police Crime Records")
        builder.setContentText(text)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT

        val intent = Intent(context, MapsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.putExtra("task",text)

        val pendingintent = PendingIntent.getActivity(context, text.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT )
        builder.setContentIntent(pendingintent)

        return builder.build()
    }

    fun sendNotificatiion(text: String){
        val notification = makeNotification(text)

        with(NotificationManagerCompat.from(context)){
            val notificationId = text.hashCode()
            notify(notificationId,notification)
        }
    }
}