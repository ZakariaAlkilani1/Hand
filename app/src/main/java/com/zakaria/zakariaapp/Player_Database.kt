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
            Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()
    }

    fun readData():MutableList<Player>{
        var list : MutableList<Player> = ArrayList()
        var db = this.readableDatabase
        val query ="Select * From " + TABLE_NAME
        val result=db.rawQuery(query,null)
if(result.moveToFirst()){
    do {
        var player = Player()
        player.id = result.getString(result.getColumnIndex(ID_COLUMN)).toInt()
        player.name = result.getString(result.getColumnIndex(NAME_COLUMN))
//        player.score = result.getString(result.getColumnIndex(SCORE_COLUMN)).toInt()
        player.Lscore = result.getString(result.getColumnIndex(LSCORE_COLUMN)).toInt()
        player.Tscore = result.getString(result.getColumnIndex(TSCORE_COLUMN)).toInt()

        list.add(player)
    }while (result.moveToNext())
}
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
        onDowngrade(this.readableDatabase, 2 ,1)
        db.close()
    }

    fun updateLastGame(score:Int , id:Int) {
        val Lasedb = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LSCORE_COLUMN, score)
        val WhereArgs = arrayOf("$id")
        val updatedRows = Lasedb.update(TABLE_NAME,contentValues, ID_COLUMN+" =?" ,WhereArgs)

    }

    fun updateTotalGame(totalscores: Int , id:Int) {
        val Totaldb = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TSCORE_COLUMN, totalscores)
        val whereArgs = arrayOf("$id")
        val updatedRows = Totaldb.update(TABLE_NAME,contentValues, ID_COLUMN+" =?" ,whereArgs)

    }

    fun getTotalGameScores(sizeOfList:Int): IntArray{
        var x = IntArray(sizeOfList)
        val db =  writableDatabase
        val columns = arrayOf(TSCORE_COLUMN)
        val cursor: Cursor = db.query(TABLE_NAME,columns,null,null,null,null,null)
        var i =0
        while (cursor.moveToNext()){
            val index1 =  cursor.getColumnIndex(TSCORE_COLUMN)
            x[i] = cursor.getInt(index1)
            i++
        }
        return x
    }
    fun sortPlayersDecreasing(){
        val db = readableDatabase
        val sortOrder = "$LSCORE_COLUMN DESC"
        val cursor = db.query(
                TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        )
    }
}
