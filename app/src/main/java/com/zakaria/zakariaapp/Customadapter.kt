package com.zakaria.zakariaapp

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListAdapter(context: Context, cursor: Cursor?): CursorAdapter(context,cursor,0) {
    private class ViewHolder {
        var ID: TextView? = null
        var Name: TextView? = null
        var SCORE: TextView? = null
        var LSCORE: TextView? = null
        var TSCORE: TextView? = null
    }
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(context)
        val newView = layoutInflater.inflate(R.layout.custom_list,parent,false)
        val viewHolder = ViewHolder()
        viewHolder.ID = newView.findViewById(R.id._ID)
        viewHolder.Name = newView.findViewById(R.id.NAME)
        viewHolder.SCORE = newView.findViewById(R.id.SCORE)
        viewHolder.LSCORE = newView.findViewById(R.id.LSCORE)
        viewHolder.TSCORE = newView.findViewById(R.id.TSCORE)

        newView.tag = viewHolder

        return  newView

    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        val viewHolder = view!!.tag as ViewHolder
        viewHolder.ID?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.ID_COLUMN))
        viewHolder.Name?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.NAME_COLUMN))
        viewHolder.SCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.SCORE_COLUMN))
        viewHolder.LSCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.LSCORE_COLUMN))
        viewHolder.TSCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.TSCORE_COLUMN))

        viewHolder.Name?.setOnClickListener{
            val btn = it as TextView
        }

    }

    fun getScoreFromEditText(view: View?):String{
        val viewHolder = view!!.tag as ViewHolder
        val edT = viewHolder.SCORE?.text.toString()
        return edT
    }

    fun clearScoresFromEditText(view: View?){
        val viewHolder = view!!.tag as ViewHolder
        viewHolder.SCORE?.text = ""
    }

    fun getTotalGames(view:View?):String{
        val viewHolder = view!!.tag as ViewHolder
        return viewHolder.TSCORE?.text.toString()
    }

}