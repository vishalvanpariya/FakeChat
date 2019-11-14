package com.fake.messenger.chat.fakemessengerchat.prank


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_chat_content.*


class ChatContent : AppCompatActivity() {

    class Adapter(var context:Context,var list:MutableList<String>): BaseAdapter() {
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var view=LayoutInflater.from(context).inflate(R.layout.customitem,p2,false)
            var text = view.findViewById<EditText>(R.id.text1)
            text.setText(list[p0])
            text.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    (context as ChatContent).putinlist(p0,str.toString())
                }

            })
            return view
        }

        override fun getItem(p0: Int): Any {
            return list[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

    }

    lateinit var pref:SharedPreferences
    lateinit var editor :SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_content)

        pref=applicationContext.getSharedPreferences("CustomChat", Context.MODE_PRIVATE)
        editor = pref.edit()

        fakechatbutton.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }



        var type= intent.getStringExtra("type")
        if (type=="1st"){
            var list = resources.getStringArray(R.array.chat1).toMutableList()
            chatcontent.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        }
        else if(type=="2nd"){
            var list = resources.getStringArray(R.array.chat2).toMutableList()
            chatcontent.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        }
        else if(type=="3rd"){
            var list = resources.getStringArray(R.array.chat3).toMutableList()
            chatcontent.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        }
        else if(type=="4th"){
            var liststr = pref.getString("custchat","empty")
            if (liststr.equals("empty")) {
                var list = resources.getStringArray(R.array.customchat).toMutableList()
                var adapter = Adapter(this,list!!)
                chatcontent.adapter = adapter

            }
            else {
                var gson = Gson()
                var list = gson.fromJson<MutableList<String>>(liststr,object:TypeToken<MutableList<String>>(){}.type)
                var adapter = Adapter(this,list!!)
                chatcontent.adapter = adapter
            }

        }
    }

    public fun putinlist(position: Int,str:String){
        var liststr = pref.getString("custchat","empty")
        if (liststr.equals("empty")) {
            var list = ArrayList(resources.getStringArray(R.array.customchat).toList())
            list[position] = str
            var gson=Gson()
            var json = gson.toJson(list)
            editor.putString("custchat",json)
            editor.commit()
        }
        else{
            var gson = Gson()
            var list = ArrayList(gson.fromJson<MutableList<String>>(liststr,object:TypeToken<MutableList<String>>(){}.type))
            list[position] = str
            var json = gson.toJson(list)
            editor.putString("custchat",json)
            editor.commit()
        }


    }
}
