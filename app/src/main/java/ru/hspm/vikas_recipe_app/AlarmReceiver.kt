package ru.hspm.vikas_recipe_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Проверка разрешения для Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val granted = ContextCompat.checkSelfPermission(
                context, permission
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!granted) return
        }

        val bigPicture = BitmapFactory.decodeResource(context.resources, R.drawable.bliny)
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.bliny)

        val title = context.getString(R.string.notification_title)
        val text = context.getString(R.string.notification_text)

        val builder = NotificationCompat.Builder(context, "recipes_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setLargeIcon(largeIcon)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bigPicture)
                    .bigLargeIcon(null as Bitmap?)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}
