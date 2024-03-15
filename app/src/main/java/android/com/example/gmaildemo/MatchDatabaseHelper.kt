package android.com.example.gmaildemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MatchDatabaseHelper(context: Context) :SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "match_database.db"
        private const val DATABASE_VERSION = 1

        // Define the table name and column names
        private const val TABLE_NAME = "matches_db"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_VERIFIED = "verified"
        private const val COLUMN_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID TEXT PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_VERIFIED INTEGER)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun insertMatch(match:Venues)
    {
        val values = ContentValues()
        values.put(COLUMN_ID,match.id)
        values.put(COLUMN_NAME,match.name)
        values.put(COLUMN_VERIFIED,if(match.verified) 1 else 0)
        val db = writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
        Log.d("Data Saved","Successfully")
    }

    fun deleteMatch(matchId:String){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(matchId))
        db.close()
    }

    fun getAllMatches():List<Venues>{
        val matches = mutableListOf<Venues>()
        val selectQuery = "Select * from $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val verifiedIndex = cursor.getColumnIndex(COLUMN_VERIFIED)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getString(idIndex)
                val name = cursor.getString(nameIndex)
                val isVerified = cursor.getInt(verifiedIndex) == 1

                val match = Venues(id,name, emptyList(), verified= isVerified)
                matches.add(match)

            }while (cursor.moveToNext())

        }
        cursor.close()
        db.close()

        return matches
    }


}