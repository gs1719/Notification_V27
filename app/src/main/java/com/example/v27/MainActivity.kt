package com.example.v27

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.v27.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "channelId"
    val CHANNEL_NAME = "channelName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
//        Calling Notification channel
        createNotifyChannel()

//        Pending Content
        intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

//        setting the tittle text icon etc for notifications
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("30 Days Of App Dev Tutorial 27")
            .setContentText("Congratulation")
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//                setting intent to see what happens when we click on notification
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        binding.textView.apply {
            text = "Notifications"
            textSize = 16f
        }
        binding.button.apply {
            text = "Click For Notification"
            setOnClickListener {
//            only checking the permission of notification is given or not in manifest file
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(
                        this@MainActivity,
                        "Give Notification Permissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//            creating notification
                notificationManager.notify(0, notification)
            }
        }
    }


    //creating notification channel
    private fun createNotifyChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
//                Setting Importance
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
//                Show something here android version
                description = "This is my notification"

            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//          creating separate Notification channel
            manager.createNotificationChannel(channel)

        }
    }
}
