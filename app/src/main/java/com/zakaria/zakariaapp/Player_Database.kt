package com.zakaria.zakariaapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.AccessControlContext

//val DATABASENAME="MDB"
//val TABLE_NAME ="Players"
//val COL_NAME="name"
//val COL_SCORE="Score"
//val COL_ID="id"
class PlayerDataBase(var context: Context): SQLiteOpenHelper(context, "DATAABSENAME",null,1){
    companion object {
        val TABLE_NAME:String  = "Players"
        val ID_COLUMN:String  = "_id"
        val NAME_COLUMN:String  = "name"
        val SCORE_COLUMN:String  = "Score"


    }

    private  val CreateTable =
            "CREATE TABLE $TABLE_NAME ( $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $NAME_COLUMN TEXT NOT NULL)"


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(Player : Player){
        val db=this.writableDatabase
        var SCo=ContentValues()
        SCo.put(NAME_COLUMN,Player.name)
//        SCo.put(COL_SCORE,Player.score)
        var result = db.insert(TABLE_NAME,null,SCo)
        if(result == -1.toLong())
//            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()    }
    fun readData():MutableList<Player>{
        var list : MutableList<Player> = ArrayList()
        var db = this.readableDatabase
        val query ="Select * From " + TABLE_NAME
        val result=db.rawQuery(query,null)

        result.close()
        db.close()
    return list
    }
}
