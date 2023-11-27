package ru.matruk_aleksandr.audioplayeraston

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.matruk_aleksandr.audioplayeraston.MusicService.LocalBinder


class MainActivity : AppCompatActivity() {
    private var musicService: MusicService? = null
    private var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    fun onPlayClick(view: View?) {
        if (isBound) {
            musicService!!.play()
        }
    }

    fun onPauseClick(view: View?) {
        if (isBound) {
            musicService!!.pause()
        }
    }

    fun onStopClick(view: View?) {
        if (isBound) {
            musicService!!.stop()
            unbindService(connection)
            isBound = false
        }
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocalBinder
            musicService = binder.service
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }
}