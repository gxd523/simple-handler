package com.gxd.demo.handler

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        syncBarrierTest()
    }

    private fun syncBarrierTest() {
        SyncBarrierObj.sendSyncMessage()
        SyncBarrierObj.sendAsyncMessage()
        val token = SyncBarrierObj.sendSyncBarrier()

        Handler(Looper.getMainLooper()).postDelayed({
            SyncBarrierObj.removeSyncBarrier(token)
        }, 2000)
    }
}