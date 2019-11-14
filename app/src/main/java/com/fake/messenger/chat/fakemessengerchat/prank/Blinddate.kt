package com.fake.messenger.chat.fakemessengerchat.prank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_blinddate.*

class Blinddate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blinddate)

        bd2.setOnClickListener {
            startActivity(Intent(this,Blinddatevideo::class.java).putExtra("noofdate",2))
        }

        bd3.setOnClickListener {
            startActivity(Intent(this,Blinddatevideo::class.java).putExtra("noofdate",3))
        }
    }
}
