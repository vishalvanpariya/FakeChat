package com.fake.messenger.chat.fakemessengerchat.prank


import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.Ringtone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_call_screen.*
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.*
import java.lang.Exception
import android.util.DisplayMetrics
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import kotlin.math.roundToInt
import android.view.animation.TranslateAnimation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class CallScreen : AppCompatActivity(),SurfaceHolder.Callback{
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
        catch (e:Exception){
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
        catch (e:Exception){
            Log.d("xxxx","${e.message}")
        }
    }

    lateinit var uri:Uri
    lateinit var ringtone: Ringtone

    lateinit var surfaceview:SurfaceView
    lateinit var camera:Camera
    lateinit var surfaceHolder: SurfaceHolder

    lateinit var pref: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_call_screen)

        surfaceview=findViewById(R.id.camera_preview)
        surfaceHolder = surfaceview.holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        pref=applicationContext.getSharedPreferences("CustomChat", Context.MODE_PRIVATE)
        editor = pref.edit()


        val mAnimation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, -1.0f
        )



        if (intent.getStringExtra("isvideocall")!="yes"){
            Glide.with(this)
                .load(resources.getIdentifier("girlimage", "drawable", packageName))
                .apply(RequestOptions.circleCropTransform())
                .into(girlimage)

            mAnimation.duration=500
            mAnimation.repeatCount=-1
            mAnimation.repeatMode=Animation.REVERSE
            mAnimation.interpolator=LinearInterpolator()
            callget.startAnimation(mAnimation)

            if (pref.getString("backgroundscreen","first")=="first"){
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.gif1)
                    .into(gif)
            }
            else{
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.gif2)
                    .into(gif)
            }
        }
        else{
            gif.visibility=View.GONE
            surfaceview.visibility=View.VISIBLE
            surfaceview.layoutParams.height= height
            surfaceview.layoutParams.width = width
            girlimage.visibility=View.GONE
            text.visibility=View.GONE
            incomingcalltext.visibility=View.VISIBLE
            videotext.visibility=View.VISIBLE
            answertext.visibility=View.VISIBLE
            declinetext.visibility=View.VISIBLE
            remind1.visibility=View.VISIBLE
            remind2.visibility=View.VISIBLE
            remind1text.visibility= View.VISIBLE
            remind2text.visibility=View.VISIBLE
        }


        if (intent.getStringExtra("iscall")=="yes") {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(applicationContext, uri)
            ringtone.play()
            callcut.isEnabled=true
            callget.isEnabled=true
        }
        else{
            callcut.isEnabled=false
            callget.isEnabled=false
        }

        callcut.setOnClickListener {
            ringtone.stop()
            finish()
        }

        callget.setOnClickListener {
            if (intent.getStringExtra("isvideocall")=="no") {
                ringtone.stop()
                surfaceview.visibility=View.GONE
                callget.visibility = View.GONE
                videocall.visibility = View.VISIBLE
                text.setText("GirlFriend")
                time.setText("30:45")
                timechange(1845)
                answertext.visibility=View.GONE
                declinetext.visibility=View.GONE
                time.visibility = View.VISIBLE
                if (ContextCompat.checkSelfPermission(applicationContext,
                        Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                    videocall.visibility=View.GONE
                }
                callget.clearAnimation()
                Glide.with(this)
                    .load(resources.getIdentifier("girlimage", "drawable", packageName))
                    .into(gif)
            }
            else{
                ringtone.stop()
                surfaceview.visibility=View.VISIBLE
                surfaceview.layoutParams.height= (0.35*height).roundToInt()
                surfaceview.layoutParams.width = (0.35*width).roundToInt()
                callget.visibility = View.GONE
                videoview.visibility=View.VISIBLE
                gif.visibility=View.GONE
                videocall.visibility=View.GONE
                girlimage.visibility=View.GONE
                text.visibility=View.GONE
                time.visibility=View.GONE
                answertext.visibility=View.GONE
                declinetext.visibility=View.GONE
                incomingcalltext.visibility=View.GONE
                remind1.visibility=View.GONE
                remind2.visibility=View.GONE
                remind1text.visibility= View.GONE
                remind2text.visibility=View.GONE
                videotext.visibility=View.GONE
                val path = "android.resource://" + packageName + "/" + R.raw.fakevideo
                videoview.setVideoURI(Uri.parse(path))
                videoview.start()
                videoview.setOnPreparedListener {
                    it.isLooping=true
                }
            }
        }
        videocall.setOnClickListener {
            videoview.visibility=View.VISIBLE
            gif.visibility=View.GONE
            videocall.visibility=View.GONE
            girlimage.visibility=View.GONE
            text.visibility=View.GONE
            time.visibility=View.GONE
            surfaceview.visibility=View.VISIBLE
            val path = "android.resource://" + packageName + "/" + R.raw.fakevideo
            videoview.setVideoURI(Uri.parse(path))
            videoview.start()
            videoview.setOnPreparedListener {
                it.isLooping=true
            }
        }

    }


    public fun timechange(sec:Int){
        var hr=(sec/3600).toInt()
        var min = ((sec - hr*3600)/60).toInt()
        var second = sec - hr*3600-min*60
        if (hr==0){
            time.setText("${min}:${second}")
        }
        else{
            time.setText("${hr}:${min}:${second}")
        }
        val h = Handler()
        h.postDelayed(object : Runnable {
            override fun run() {
                timechange(sec+1)
            }
        }, 1000)
    }
}
