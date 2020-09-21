package com.umair.beacons_plugin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean
import timber.log.Timber

class BeaconsDiscoveryService : Service() {

    private lateinit var beaconHelper: BeaconHelper

    companion object {

        @JvmStatic
        private val TAG = "BeaconsDiscoveryService"

        @JvmStatic
        private var serviceRunning = false

        @JvmStatic
        private var serviceName = "Beacons Discovery Service"
    }

    override fun onCreate() {
        super.onCreate()
        beaconHelper = BeaconHelper(this)

        Timber.i(coloredMessage("${serviceName} - Lifecyle: Create", "Green"))

        BeaconsPlugin.messenger?.let {
            Log.i(TAG, "$TAG service running.")
            Timber.i(coloredMessage("${serviceName} is running.", "Dark Green"))
            BeaconsPlugin.registerWith(it, beaconHelper, this)
            BeaconsPlugin.sendBLEScannerReadyCallback()
            serviceRunning = true
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.i(coloredMessage("${serviceName} - Lifecyle: Bind", "Green"))
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i(coloredMessage("${serviceName} - Lifecyle: Start Command", "Green"))
        createNotification("${TAG}_ID", TAG, "${TAG}::WAKE_LOCK", "Beacon Scanner", "持續掃描 beacon 中……")
        acquireWakeLock(intent, "${TAG}::WAKE_LOCK")
        return START_STICKY
    }

    override fun onDestroy() {
        Timber.i(coloredMessage("${serviceName} - Lifecyle: Destroy", "Green"))
        super.onDestroy()
        Log.i(TAG, "$TAG service stopped.")
        Timber.i(coloredMessage("${serviceName} is stopped.", "Dark Green"))

        if (serviceRunning) {
            beaconHelper.stopMonitoringBeacons()
        }

        serviceRunning = false
    }
}
