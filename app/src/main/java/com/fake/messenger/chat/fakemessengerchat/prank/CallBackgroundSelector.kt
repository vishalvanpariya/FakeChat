package com.fake.messenger.chat.fakemessengerchat.prank

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_call_background_selector.*

class CallBackgroundSelector : AppCompatActivity() {

    lateinit var pref: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_background_selector)

        pref=applicationContext.getSharedPreferences("CustomChat", Context.MODE_PRIVATE)
        editor = pref.edit()

        if (pref.getString("backgroundscreen","first")=="first"){
            firstcheck.isChecked=true
            secondcheck.isChecked=false
        }
        else{
            firstcheck.isChecked=false
            secondcheck.isChecked=true
        }

        firstgif.setOnClickListener {
            firstcheck.isChecked=true
            secondcheck.isChecked=false
            editor.putString("backgroundscreen","first")
            editor.commit()
            startActivity(Intent(this,CallScreen::class.java))
        }

        secondgif.setOnClickListener {
            secondcheck.isChecked=true
            firstcheck.isChecked=false
            editor.putString("backgroundscreen","second")
            editor.commit()
            startActivity(Intent(this,CallScreen::class.java))
        }

        fakechatbutton.setOnClickListener {
            startActivity(Intent(this,CallScreen::class.java).putExtra("iscall","yes").putExtra("isvideocall","no"))
        }
    }
}
