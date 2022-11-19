package com.lwt.realestatemanager.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler // creating a constructor for our database handler.
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_REALTY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_REALTY + " TEXT,"
                + TYPE_REALTY + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + PRICE_REALTY + " TEXT)")

        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(query)
    }

    // this method is use to add new Realty to our sqlite database.
    fun addNewRealty(
        realtyName: String?,
        realtyType: String?,
        realtyAddress: String?,
        realtyPriceInDollars: String?
    ) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase
        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_REALTY, realtyName)
        values.put(TYPE_REALTY, realtyType)
        values.put(ADDRESS_COL, realtyAddress)
        values.put(PRICE_REALTY, realtyPriceInDollars)
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)
        // at last we are closing our
        // database after adding database.
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "realty"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val TABLE_NAME = "realtydetail"

        // below variable is for our id column.
        private const val ID_REALTY = "id"

        // below variable is for our realty name column
        private const val NAME_REALTY = "name"

        // below variable id for our realty type column.
        private const val TYPE_REALTY = "type"

        // below variable for our realty address column.
        private const val ADDRESS_COL = "address"

        // below variable is for our realty price column.
        private const val PRICE_REALTY = "priceindollars"
    }

    // we have created a new method for reading all the realty.
    fun readRealty(): ArrayList<RealtyModel>? {
        // on below line we are creating a database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorRealty: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // on below line we are creating a new array list.
        val realtyModelArrayList: ArrayList<RealtyModel> = ArrayList()

        // moving our cursor to first position.
        if (cursorRealty.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                realtyModelArrayList.add(
                    RealtyModel(
                        cursorRealty.getString(1),
                        cursorRealty.getString(4),
                        cursorRealty.getString(2),
                        cursorRealty.getString(3)
                    )
                )
            } while (cursorRealty.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorRealty.close()
        return realtyModelArrayList
    }
}
