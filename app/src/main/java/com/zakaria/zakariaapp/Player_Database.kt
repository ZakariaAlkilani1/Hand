package com.zakaria.zakariaapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class PlayerDataBase(var context: Context): SQLiteOpenHelper(context, "DATAABSENAME",null,1){
    companion object {
        val TABLE_NAME:String  = "Players"
        val ID_COLUMN:String  = "_id"
        val NAME_COLUMN:String  = "name"
        val SCORE_COLUMN:String  = "Score"
        val LSCORE_COLUMN:String  = "LScore"
        val TSCORE_COLUMN:String  = "TScore"


    }

    private  val CreateTable =
            "CREATE TABLE $TABLE_NAME ( $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $NAME_COLUMN TEXT NOT NULL , $SCORE_COLUMN INTEGER ,$LSCORE_COLUMN INTEGER ,$TSCORE_COLUMN INTEGER)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    fun insertData(Player: String,Lscore:Int,Tscore:Int){
        val db=this.writableDatabase
        var SCo=ContentValues()
        SCo.put(NAME_COLUMN,Player)
//        SCo.put(SCORE_COLUMN,0)
        SCo.put(LSCORE_COLUMN,Lscore)
        SCo.put(TSCORE_COLUMN,Tscore)

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
    fun getPlayer(): Cursor? {
        val db = this.readableDatabase
        return  db.query(TABLE_NAME,null,null,null,null,null,null)
    }
    fun deletePlayer(){
        var db=this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }
}
