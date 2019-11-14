package com.fake.messenger.chat.fakemessengerchat.prank

import android.hardware.Camera
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlinx.android.synthetic.main.activity_blinddatevideo.*
import java.lang.Exception

class Blinddatevideo : AppCompatActivity(),SurfaceHolder.Callback {
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        camera.stopPreview()
        camera.release()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        try {
            camera= Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        }
        catch (e: Exception){
            Log.d("xxxx","${e.message}")
        }
        var parameters=camera.parameters
        parameters.previewFrameRate=20
        parameters.previewSize.width=352
        parameters.previewSize.height=288
        camera.parameters=parameters
        camera.setDisplayOrientation(90)
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
        }
        catch (e: Exception){
            Log.d("xxxx","${e.message}")
        }
    }

    lateinit var surfaceview:SurfaceView
    lateinit var surfaceHolder:SurfaceHolder
    lateinit var camera:Camera

    lateinit var player:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blinddatevideo)

        surfaceview=findViewById(R.id.camera_preview)
        surfaceHolder = surfaceview.holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        var wtemp=width/5

        player= MediaPlayer.create(this,R.raw.beep)
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.setLooping(true)
        player.start()

        camera_preview.layoutParams.width = wtemp * 3
        if (intent.getIntExtra("noofdate",0)==2) {
            camera_preview.layoutParams.height = 450
            chatimage.visibility=View.VISIBLE
            chat3image.visibility=View.GONE
            rose3.visibility=View.GONE
            mic3.visibility=View.GONE
            text3.visibility=View.GONE
            text3no.visibility=View.GONE
        }
        if (intent.getIntExtra("noofdate",0)==3) {
            camera_preview.layoutParams.height = 550
            gf3.visibility=View.VISIBLE
            chatimage.visibility=View.GONE
            chat3image.visibility=View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}
