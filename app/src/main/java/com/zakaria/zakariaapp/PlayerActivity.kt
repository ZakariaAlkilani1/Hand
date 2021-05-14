package com.zakaria.zakariaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zakaria.zakariaapp.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var PlayerData: PlayerDataBase
    private lateinit var PlayerCursorAdapter: ListAdapter
    lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val player = binding.playerName.text
        val context = this
        var db = PlayerDataBase(context)
        PlayerData = PlayerDataBase(applicationContext)
        customCursorAdapter();

        binding.addplayer.setOnClickListener {
            if (player.toString().length >= 2) {
                SaveDataToDatabase()
                clearText()
            } else {
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun customCursorAdapter() {
        PlayerCursorAdapter = ListAdapter(applicationContext, PlayerData.getPlayer())
        binding.PLV.adapter = PlayerCursorAdapter
    }

    private fun SaveDataToDatabase() {
//        var dlt =PlayerData.deletePlayer()
        val result = PlayerData.insertData(
                binding.playerName.text.toString(), 0, 0)

        binding.apply {
            PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
            PlayerCursorAdapter.notifyDataSetChanged()
        }
    }

    private fun clearText() {
        binding.apply {
            binding.playerName.setText("")
        }
    }
}


