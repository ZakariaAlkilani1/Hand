package com.zakaria.zakariaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import com.zakaria.zakariaapp.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var PlayerData: PlayerDataBase
    private lateinit var PlayerCursorAdapter: ListAdapter
    lateinit var binding: ActivityPlayerBinding
    private var history= mutableListOf<String>()
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
        binding.collect.setOnClickListener {
            var arrHoldingScores = IntArray(binding.PLV.size)
            var isAnyScoreNull = false
            for (i in 0 until binding.PLV.size){
                if(PlayerCursorAdapter.getScoreFromEditText(binding.PLV[i]).isNotEmpty())
                    arrHoldingScores[i] = PlayerCursorAdapter.getScoreFromEditText(binding.PLV[i]).toInt()
                else
                    isAnyScoreNull = true;
            }
            if(!isAnyScoreNull){    // if all players' scores are inserted
                //get the total games' scores from the data base
                var lastTotalGameScores = PlayerData.getTotalGameScores(binding.PLV.size)

                //update all players' scores, last games and total scores
                for (i in 0 until binding.PLV.size) {
                    //update data for the player with index i
                    PlayerData.updateLastGame(arrHoldingScores[i], i + 1)
                    PlayerData.updateTotalGame(arrHoldingScores[i] + lastTotalGameScores[i], i + 1)
                    //clear the score value from the edit text
                    PlayerCursorAdapter.clearScoresFromEditText(binding.PLV[i])
                }
                PlayerCursorAdapter.notifyDataSetChanged()
            }else
                Toast.makeText(this,"some scores are null !",Toast.LENGTH_LONG).show()
        }
        binding.Delete.setOnClickListener {
            PlayerData.deletePlayer()
            PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
            PlayerCursorAdapter.notifyDataSetChanged()
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


