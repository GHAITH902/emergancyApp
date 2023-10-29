package ghaith.fattoum.myfirstapptry2

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // UI Views
    private lateinit var showNotificationBtn: Button

    private companion object {
        //there could be more notifications so it could be used as notification identity
        private const val CHANNEL_ID = "channel01"
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init Ui Views
        showNotificationBtn = findViewById(R.id.showNotificationBtn)
        val move : Button = this.findViewById(R.id.move)
        move.setOnClickListener{
            val intent = Intent(this, AnotherActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
        // handle click, show notification

        showNotificationBtn.setOnClickListener {
            showNotification()
            //TODO(finish the code)
        }

        //var builder = NotificationCompat.Builder(this, Channel.)
        //.setSmallIcon(R.drawable.download)
        // .setContentTitle("fire alarm go to the safe zone")
        //.setContentText("if you are in the class move with the teacher, if you are in the res go to the myp cort")
        // .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun showNotification() {
        createNotificationChannel()
        val date = Date()
        val notificationId = SimpleDateFormat("ddhhmmss", Locale.US).format(date).toInt()
        // handle notification click start second activity by tapping notification.
        val mainIntent = Intent(this, AnotherActivity::class.java)
        mainIntent.putExtra("KEY_NAME", "Atif Pervaiz")
        mainIntent.putExtra("KEY_EMAIL", "Ghaithfattoum3@gmai.com")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivities(this, 1,
            arrayOf(mainIntent), PendingIntent.FLAG_IMMUTABLE)




        val notificationBulder = NotificationCompat.Builder(this, CHANNEL_ID)
        notificationBulder.setSmallIcon(R.drawable.ic_notification)
        notificationBulder.setContentTitle("fire")
        notificationBulder.setContentText("go to the safe zone")
        notificationBulder.priority = NotificationCompat.PRIORITY_MAX
        //cancel notification on click
        notificationBulder.setAutoCancel(true)
        //Add click Intent
        notificationBulder.setContentIntent(mainPendingIntent)


        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
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
            return
        }
        notificationManagerCompat.notify(notificationId, notificationBulder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MyNotification"
            val description = "My Notification channel description"
            //importance of your notification
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }


    }

}

