package com.zakaria.zakariaapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

val DATABASENAME="MDB"
val TABLE_NAME ="Players"
val COL_NAME="name"
val COL_SCORE="Score"
val COL_ID="id"
class PlayerDataBase(var context: Context): SQLiteOpenHelper(context, DATABASENAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val CreateTable="CREATE TABLE" + TABLE_NAME +
                COL_ID +"INTEGER PRIMARY KEY AUTOINCREMENT"+
                COL_NAME +"TEXT NOT NULL"+
                COL_SCORE +"INT NOT NULL"
        db?.execSQL(CreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(Player : Player){
        val db=this.writableDatabase
        var SCo=ContentValues()
        SCo.put(COL_NAME,Player.name)
        SCo.put(COL_SCORE,Player.score)
        var result = db.insert(TABLE_NAME,null,SCo)
    }
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
