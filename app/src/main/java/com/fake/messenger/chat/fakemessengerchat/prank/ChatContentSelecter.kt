package com.fake.messenger.chat.fakemessengerchat.prank

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_chat_content_selecter.*

class ChatContentSelecter : AppCompatActivity() {

    lateinit var pref: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_content_selecter)

        pref=applicationContext.getSharedPreferences("CustomChat", Context.MODE_PRIVATE)
        editor = pref.edit()

        if (pref.getString("chattype","auto").equals("auto")){
            appCompatCheckBox.isChecked = true
            customcheckbox.isChecked=false
        }
        else{
            appCompatCheckBox.isChecked = false
            customcheckbox.isChecked=true
        }

        first.setOnClickListener {
            appCompatCheckBox.isChecked = true
            customcheckbox.isChecked=false
            editor.putString("chattype","auto")
            editor.commit()
            startActivity(Intent(this,ChatContent::class.java).putExtra("type","1st"))
        }
        second.setOnClickListener {
            appCompatCheckBox.isChecked = true
            customcheckbox.isChecked=false
            editor.putString("chattype","auto")
            editor.commit()
            startActivity(Intent(this,ChatContent::class.java).putExtra("type","2nd"))
        }
        third.setOnClickListener {
            appCompatCheckBox.isChecked = true
            customcheckbox.isChecked=false
            editor.putString("chattype","auto")
            editor.commit()
            startActivity(Intent(this,ChatContent::class.java).putExtra("type","3rd"))
        }
        forth.setOnClickListener {
            appCompatCheckBox.isChecked = false
            customcheckbox.isChecked=true
            editor.putString("chattype","custom")
            editor.commit()
            startActivity(Intent(this,ChatContent::class.java).putExtra("type","4th"))
        }
    }
}
