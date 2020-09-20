package com.umair.beacons_plugin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)

fun hasBLEFeature(activity: Context): Boolean {
    activity.packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }
            ?.also {
                return false
            }
    return true
}

fun setUpBlueToothAdapter(content: Context): BluetoothAdapter? {
    val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager =
                content.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    return bluetoothAdapter
}

private val BluetoothAdapter.isDisabled: Boolean
    get() = !isEnabled

fun isBluetoothEnabled(content: Context) {

    setUpBlueToothAdapter(content)?.takeIf { it.isDisabled }?.apply {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableBtIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        content.startActivity(enableBtIntent)
    }
}


fun getReadableTime(timestamp: Long): String {
    val TIME_FORMAT_READABLE = "yyyy-MM-dd HH:mm:ss"
    if (timestamp != 0L) {
        val date1 = Date(timestamp)
        val outFormat = SimpleDateFormat(TIME_FORMAT_READABLE, Locale.ENGLISH)
        return outFormat.format(date1)
    } else
        return "No Time Provided!"
}

fun Service.createNotification(channelId: String, channelName: String, wakeLockTAG: String, title: String, content: String) {
    createNotificationChannel(channelId, channelName)
    val imageId = resources.getIdentifier("ic_launcher", "mipmap", packageName)

    val intent = Intent(this, BeaconsPlugin::class.java)
    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(imageId)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setWhen(System.currentTimeMillis())
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
        newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockTAG).apply {
            setReferenceCounted(false)
            acquire(360000)
        }
    }
    startForeground(18237, notification)
}

private fun Service.createNotificationChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val serviceChannel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }
}

fun Service.acquireWakeLock(intent: Intent?, wakeLockTAG: String) {
    if (intent?.action == "SHUTDOWN") {
        (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockTAG).apply {
                if (isHeld) {
                    release()
                }
            }
        }
        stopForeground(true)
        stopSelf()
    }
}