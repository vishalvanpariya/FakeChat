package com.fake.messenger.chat.fakemessengerchat.prank

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 104)
        }


        chatwithgf.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }

        chatwithgfimage.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }

        privacypolicy.setOnClickListener {
            //Privacy Policy
        }

        callscreen.setOnClickListener {
            startActivity(Intent(this,CallBackgroundSelector::class.java))
        }

        chatselect.setOnClickListener {
            startActivity(Intent(this,ChatContentSelecter::class.java))
        }

        blinddate.setOnClickListener {
            startActivity(Intent(this,Blinddate::class.java))
        }

    }

    override fun onRestart() {
        super.onRestart()
        privacypolicy.visibility=View.GONE
        chatwithgf.visibility=View.GONE
        chatselect.visibility=View.VISIBLE
        callscreen.visibility=View.VISIBLE
        chatwithgfimage.visibility=View.VISIBLE
        blinddate.visibility=View.VISIBLE
    }
}
