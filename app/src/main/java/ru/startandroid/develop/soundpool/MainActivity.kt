package ru.startandroid.develop.soundpool

import android.media.AudioManager
import android.media.SoundPool
import android.media.SoundPool.OnLoadCompleteListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnLoadCompleteListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
        sp!!.setOnLoadCompleteListener(this)

        soundIdShort = sp!!.load(this, R.raw.shot, 1)
        Log.d(LOG_TAG, "soundIdShot = $soundIdShort")

        try {
            soundIdException = sp!!.load(assets.openFd("explosion.ogg"), 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(LOG_TAG, "soundIdExplosion = $soundIdException")
    }

    fun onClick(view: View?) {
        streamIDShot = sp!!.play(soundIdShort!!, 1F, 1F, 0, 9, 1F)
        streamIDExplosion = sp!!.play(soundIdException!!, 1F, 1F, 0, 5, 1F)
        try {
            TimeUnit.SECONDS.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        sp!!.pause(streamIDShot!!)

        try {
            TimeUnit.SECONDS.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        sp!!.resume(streamIDShot!!)
    }

    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        Log.d(LOG_TAG, "onLoadComplete, sampleId = $sampleId, status = $status")
    }

    companion object {
        const val LOG_TAG = "myLogs"
        const val MAX_STREAMS = 5

        var sp: SoundPool? = null
        var soundIdShort: Int? = null
        var soundIdException: Int? = null

        var streamIDShot: Int? = null
        var streamIDExplosion: Int? = null
    }
}