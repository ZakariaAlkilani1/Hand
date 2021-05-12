package com.zakaria.zakariaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zakaria.zakariaapp.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val player=binding.playerName.text
        binding.addplayer.setOnClickListener{
                Toast.makeText(this,"${player}",Toast.LENGTH_SHORT).show()
        }


    }
}


