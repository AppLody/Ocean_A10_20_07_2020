package com.oceanbrasil.ocean_a10_20_07_2020

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationCreator {

    fun create(context: Context, title: String, body: String) {
        // Obtenção do Serviço de Notificação do Android
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Criação do canal, apenas para API maior ou igual ao Android.OREO
        val channelId = "OCEAN_MAIN"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Ocean - Canal Principal"
            val channelDescription = "Ocean - Canal utilizado para as principais notícias do app."

            val channel = notificationManager.getNotificationChannel(channelId)

            if (channel == null) {
                val newChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                newChannel.description = channelDescription
                newChannel.enableVibration(true)
                newChannel.enableLights(true)
                newChannel.vibrationPattern = longArrayOf(300, 400, 500, 400, 300)

                notificationManager.createNotificationChannel(newChannel)
            }
        }

        // Preparar a intent da notificação
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // Criação da notificação
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Envio da notificação
        val notification = builder.build()
        val notificationId = 1
        notificationManager.notify(notificationId, notification)
    }
}
