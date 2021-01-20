package com.example.instabus.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteQueryBuilder
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DBHelper(context: Context): SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {
    companion object {
        private val DB_NAME = "database.db"
        private val DB_VER = 1

        private val TBL_NAME = "Picture"
        private val COl_ID_STATION = "ID_station"
        private val COl_TITLE = "Title"
        private val COl_IMAGE = "Image"
    }

    @Throws(SQLiteException::class)
    fun addBitmap(id: Int, title:String, image:ByteArray) {
        val database = this.writableDatabase
        val cv = ContentValues()

        cv.put(COl_TITLE, title)
        cv.put(COl_IMAGE, image)
        cv.put(COl_ID_STATION, id)
        database.insert(TBL_NAME, null, cv)
    }

    @Throws(SQLiteException::class)
    fun getBitmapByStation(id:Int): ByteArray?
    {
        val db = this.writableDatabase
        val qb = SQLiteQueryBuilder()

        val sqlSelect = arrayOf(COl_IMAGE)

        qb.tables = TBL_NAME
        val c = qb.query(db, sqlSelect, "ID_station = ?", arrayOf(COl_ID_STATION), null, null, null)

        var result:ByteArray? = null
        if(c.moveToFirst())
        {
            do {
                result = c.getBlob(c.getColumnIndex(COl_IMAGE))
            }while(c.moveToNext())
        }
        return result
    }

}