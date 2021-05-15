package com.zakaria.zakariaapp

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListAdapter(context: Context, cursor: Cursor?): CursorAdapter(context,cursor,0) {
    private class ViewHolder {
        var textViewID: TextView? = null
        var textViewName: TextView? = null
        var textViewSCORE: TextView? = null
        var textViewLSCORE: TextView? = null
        var textViewTSCORE: TextView? = null
    }
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(context)
        val newView = layoutInflater.inflate(R.layout.custom_list,parent,false)
        val viewHolder = ViewHolder()
        viewHolder.textViewID = newView.findViewById(R.id._ID)
        viewHolder.textViewName = newView.findViewById(R.id.NAME)
        viewHolder.textViewSCORE = newView.findViewById(R.id.SCORE)
        viewHolder.textViewLSCORE = newView.findViewById(R.id.LSCORE)
        viewHolder.textViewTSCORE = newView.findViewById(R.id.TSCORE)

        newView.tag = viewHolder

        return  newView

    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        val viewHolder = view!!.tag as ViewHolder
        viewHolder.textViewID?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.ID_COLUMN))
        viewHolder.textViewName?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.NAME_COLUMN))
        viewHolder.textViewSCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.SCORE_COLUMN))
        viewHolder.textViewLSCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.LSCORE_COLUMN))
        viewHolder.textViewTSCORE?.text = cursor?.getString(cursor.getColumnIndex(PlayerDataBase.TSCORE_COLUMN))

        viewHolder.textViewName?.setOnClickListener{
            val btn = it as TextView
        }

    }

    fun getScoreFromEditText(view: View?):String{
        val viewHolder = view!!.tag as ViewHolder
        val edT = viewHolder.textViewSCORE?.text.toString()
        return edT
    }

    fun clearScoresFromEditText(view: View?){
        val viewHolder = view!!.tag as ViewHolder
        viewHolder.textViewSCORE?.text = ""
    }

    fun getTotalGames(view:View?):String{
        val viewHolder = view!!.tag as ViewHolder
        return viewHolder.textViewTSCORE?.text.toString()
    }

}