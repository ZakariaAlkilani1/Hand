package com.zakaria.zakariaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
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
            var holdingScores = IntArray(binding.PLV.size)
            var NullScore = false
            for (i in 0 until  binding.PLV.size){
                if(PlayerCursorAdapter.getScoreFromEditText(binding.PLV[i]).isNotEmpty())
                    holdingScores[i] = PlayerCursorAdapter.getScoreFromEditText(binding.PLV[i]).toInt()
                else
                    NullScore = true;
            }
            if(!NullScore){    // if all players' scores are inserted
                //get the total games' scores from the data base
                var lastScores = PlayerData.getTotalGameScores(binding.PLV.size)

                //update all players' scores, last games and total scores
                for (i in 0 until  binding.PLV.size) {
                    //update data for the player with index i
                    PlayerData.updateLastGame(holdingScores[i], i + 1)
                    PlayerData.updateTotalGame(holdingScores[i] + lastScores[i], i + 1)
                    //clear the score value from the edit text
                    PlayerCursorAdapter.clearScoresFromEditText(binding.PLV[i])
                    notifyDataChange()
                }
//                notifyDataChange()
            }else
                Toast.makeText(this,"Please Fill All Data's !",Toast.LENGTH_LONG).show()
        }
        binding.history.setOnClickListener {
            for (i in 0..(PlayerData.readData().size - 1)) {
                var id = PlayerData.readData().get(i).id.toString()
                var name = PlayerData.readData().get(i).name
                var tscore = PlayerData.readData().get(i).Tscore.toString()
                var lscore = PlayerData.readData().get(i).Lscore.toString()


                var intent = Intent(this, history::class.java)
                intent.putExtra("id", id)
                intent.putExtra("name", name)
                intent.putExtra("tscore", tscore)
                intent.putExtra("lscore", lscore)
                startActivity(intent)

            }
        }
        binding.Delete.setOnClickListener {
            DeletePlayer()
        }

    }


    fun customCursorAdapter() {
        PlayerCursorAdapter = ListAdapter(applicationContext, PlayerData.getPlayer())
        binding.PLV.adapter = PlayerCursorAdapter
    }

    private fun SaveDataToDatabase() {
                 PlayerData.insertData(
                binding.playerName.text.toString(), 0, 0)

        binding.apply {
            PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
            PlayerCursorAdapter.notifyDataSetChanged()
        }
    }
    private fun notifyDataChange() {
//        PlayerData.sortPlayersDecreasing()
        PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
        PlayerCursorAdapter.notifyDataSetChanged()
    }
    private fun clearText() {
        binding.apply {
            binding.playerName.setText("")
        }
    }
    private fun DeletePlayer(){
        PlayerData.deletePlayer()
        PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
        PlayerCursorAdapter.notifyDataSetChanged()
    }
//    private fun ReadPlayer(){
//        PlayerData.readData()
//        PlayerCursorAdapter.changeCursor(PlayerData.getPlayer())
//        PlayerCursorAdapter.notifyDataSetChanged()
//    }
}


