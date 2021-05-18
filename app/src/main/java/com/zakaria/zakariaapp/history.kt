package com.zakaria.zakariaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class history : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         lateinit var PlayerData: PlayerDataBase


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        var intent = intent
        val id=intent.getStringExtra("his")
        val name=intent.getStringExtra("name")
        val tscore=intent.getStringExtra("tscore")
        val lscore=intent.getStringExtra("lscore")


        var result=findViewById<TextView>(R.id.viewhistory)
        result.text="\n Name: "+name+"\n Tscore: "+tscore+"\n Lscore: "+lscore
      }

    }

