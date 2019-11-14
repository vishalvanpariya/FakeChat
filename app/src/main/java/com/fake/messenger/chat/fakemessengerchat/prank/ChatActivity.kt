package com.fake.messenger.chat.fakemessengerchat.prank

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.AudioManager
import android.media.MediaPlayer
import android.text.style.LineHeightSpan
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.gfchatitem.*


class ChatActivity : AppCompatActivity() {

    class Msg(val msg:String,val isgf: Boolean)

    lateinit var list:LinkedList<String>
    lateinit var chatlist:LinkedList<Msg>
    lateinit var adapter:RecyaclerAdapter

    lateinit var pref: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    lateinit var player:MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        player= MediaPlayer.create(this,R.raw.beep)
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.setLooping(true)

        pref=applicationContext.getSharedPreferences("CustomChat", Context.MODE_PRIVATE)
        editor = pref.edit()

        phone.setOnClickListener {
            startActivity(Intent(this,CallScreen::class.java).putExtra("iscall","yes").putExtra("isvideocall","no"))
        }

        videochat.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 104)
            }
            else {
                startActivity(
                    Intent(this, CallScreen::class.java).putExtra(
                        "iscall",
                        "yes"
                    ).putExtra("isvideocall", "yes")
                )
            }
        }


        var temp = Random().nextInt(3)+1

        if (pref.getString("chattype","empty")=="custom"){
            list = LinkedList(resources.getStringArray(R.array.customchat).toList())
        }
        else if (temp==1) {
            list = LinkedList(resources.getStringArray(R.array.chat1).toList())
        }
        else if (temp==2) {
            list = LinkedList(resources.getStringArray(R.array.chat2).toList())
        }
        else if (temp==3) {
            list = LinkedList(resources.getStringArray(R.array.chat3).toList())
        }


        send.setOnClickListener {
            chatlist.add(Msg(chat.text.toString(),false))
            adapter.notifyDataSetChanged()
            val h = Handler()
            h.postDelayed(object : Runnable {
                override fun run() {
                    adapter.addobj(Msg(list[0],true))
                    player.start()
                    chatrecycler.smoothScrollToPosition(chatlist.size)
                    h.postDelayed(object :Runnable{
                        override fun run() {
                            player.pause()
                        }

                    },1000)
                }
            }, 1000)
            list.removeAt(0)
            if (list.size==0){
                var temp = Random().nextInt(3)+1

                if (pref.getString("type","empty")=="custom"){
                    list = LinkedList(resources.getStringArray(R.array.customchat).toList())
                }
                else if (temp==1) {
                    list = LinkedList(resources.getStringArray(R.array.chat1).toList())
                }
                else if (temp==2) {
                    list = LinkedList(resources.getStringArray(R.array.chat2).toList())
                }
                else if (temp==3) {
                    list = LinkedList(resources.getStringArray(R.array.chat3).toList())
                }
            }
            chat.setText("")
            chatrecycler.smoothScrollToPosition(chatlist.size)
            var gson =Gson()
            var json = gson.toJson(chatlist)
            editor.putString("chatlist",json)
            editor.commit()
        }
        sendbutton.setOnClickListener {
            chatlist.add(Msg(chat.text.toString(),false))
            adapter.notifyDataSetChanged()
            val h = Handler()
            h.postDelayed(object : Runnable {
                override fun run() {
                    adapter.addobj(Msg(list[0],true))
                    player.start()
                    chatrecycler.smoothScrollToPosition(chatlist.size)
                    h.postDelayed(object :Runnable{
                        override fun run() {
                            player.pause()
                        }

                    },1000)
                }
            }, 1000)
            list.removeAt(0)
            if (list.size==0){
                var temp = Random().nextInt(3)+1

                if (pref.getString("type","empty")=="custom"){
                    list = LinkedList(resources.getStringArray(R.array.customchat).toList())
                }
                else if (temp==1) {
                    list = LinkedList(resources.getStringArray(R.array.chat1).toList())
                }
                else if (temp==2) {
                    list = LinkedList(resources.getStringArray(R.array.chat2).toList())
                }
                else if (temp==3) {
                    list = LinkedList(resources.getStringArray(R.array.chat3).toList())
                }
            }
            chat.setText("")
            chatrecycler.smoothScrollToPosition(chatlist.size)
            var gson =Gson()
            var json = gson.toJson(chatlist)
            editor.putString("chatlist",json)
            editor.commit()
        }

        fakechatbutton.setOnClickListener {
            chatlist.add(Msg(chat.text.toString(),true))
            chat.setText("")
            chatrecycler.smoothScrollToPosition(chatlist.size)
            var gson =Gson()
            var json = gson.toJson(chatlist)
            editor.putString("chatlist",json)
            editor.commit()
        }

        chat.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    fakechatbutton.visibility = View.VISIBLE
                    sendbutton.visibility = View.VISIBLE
                    send.visibility = View.VISIBLE
                }
                else{
                    fakechatbutton.visibility = View.GONE
                    sendbutton.visibility = View.GONE
                    send.visibility = View.GONE
                }
            }

        })

        chatlist = LinkedList<Msg>()
        if (pref.getString("chatlist","empty").equals("empty")) {
            chatlist.add(Msg(list[0], true))
            list.removeAt(0)
        }
        else{
            var gson = Gson()
            var json = pref.getString("chatlist","empty")
            chatlist=gson.fromJson<LinkedList<Msg>>(json,object: TypeToken<LinkedList<Msg>>(){}.type)
            chatlist.add(Msg(list[0], true))
            list.removeAt(0)
        }

        adapter=RecyaclerAdapter(this, chatlist)
        chatrecycler.adapter = adapter
        chatrecycler.layoutManager = LinearLayoutManager(this)
        chatrecycler.smoothScrollToPosition(chatlist.size)
        val h = Handler()
        h.postDelayed(object : Runnable {
            override fun run() {
                player.start()
                chatrecycler.smoothScrollToPosition(chatlist.size)
                h.postDelayed(object :Runnable{
                    override fun run() {
                        player.pause()
                    }

                },1000)
            }
        }, 1000)

        var gson =Gson()
        var json = gson.toJson(chatlist)
        editor.putString("chatlist",json)
        editor.commit()
    }

    class RecyaclerAdapter(var context:Context,var list:LinkedList<Msg>) : RecyclerView.Adapter<RecyaclerAdapter.Holder>() {

        var width:Int
        var height:Int
        init {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            lateinit var image:ImageView
            lateinit var text:TextView
            lateinit var mymsg:TextView
            init {
                image = itemView.findViewById<ImageView>(R.id.image)
                text = itemView.findViewById<TextView>(R.id.gfmsg)
                mymsg = itemView.findViewById(R.id.mymsg)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyaclerAdapter.Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.gfchatitem,parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        public fun addobj(msg:Msg){
            list.add(msg)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: RecyaclerAdapter.Holder, position: Int) {
            Glide.with(context)
                .load(context.resources.getIdentifier("girlimage","drawable",context.packageName))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image)

            holder.text.setText(list[position].msg)
            holder.mymsg.setText(list[position].msg)

            holder.mymsg.maxWidth= (width*0.80).toInt()
            holder.text.maxWidth= (width*0.80).toInt()


            if (list[position].isgf){
                holder.image.visibility= View.VISIBLE
                holder.text.visibility= View.VISIBLE
                holder.mymsg.visibility=View.GONE
            }
            else{
                holder.image.visibility= View.GONE
                holder.text.visibility= View.GONE
                holder.mymsg.visibility=View.VISIBLE
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,"Witout Camera You can not use VideoCall",Toast.LENGTH_SHORT).show()
        }
        else{
            startActivity(
                Intent(this, CallScreen::class.java).putExtra(
                    "iscall",
                    "yes"
                ).putExtra("isvideocall", "yes")
            )
        }
    }
}
