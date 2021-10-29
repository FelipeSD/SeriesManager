package com.seriesmanager.dao

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.seriesmanager.model.Serie

class SerieSqlite(context: Context): SerieDAO {
    companion object {
        val BD_SERIES_MANAGER = "series_manager"

        val TABLE_SERIE = "serie"

        val COLUMN_NAME = "name"
        private val COLUMN_RELEASE = "release_year"
        private val COLUMN_PRODUCER = "producer"
        private val COLUMN_GENRE = "genre"

        private val CREATE_TABLE_SERIE_STMT = "CREATE TABLE IF NOT EXISTS $TABLE_SERIE ("+
                "$COLUMN_NAME TEXT NOT NULL PRIMARY KEY, "+
                "$COLUMN_GENRE TEXT NOT NULL, "+
                "$COLUMN_RELEASE TEXT NOT NULL, "+
                "$COLUMN_PRODUCER TEXT NOT NULL );"
    }

    private val seriesBd: SQLiteDatabase
    init {
        seriesBd = context.openOrCreateDatabase(BD_SERIES_MANAGER, MODE_PRIVATE, null)
        try{
            seriesBd.execSQL(CREATE_TABLE_SERIE_STMT)
        }catch (se: SQLException){
            Log.e("Series", se.toString())
        }
    }

    override fun createSerie(serie: Serie): Long {
        val serieCv = serieToContentValues(serie)
        return seriesBd.insert(TABLE_SERIE, null, serieCv)
    }

    override fun findSerie(name: String): Serie {
        val serieCursor = seriesBd.query(
            true,
            TABLE_SERIE,
            null,
            "$COLUMN_NAME = ?",
            arrayOf(name),
            null,
            null,
            null,
            null
        )

        return if(serieCursor.moveToFirst()){
            with(serieCursor){
                Serie(
                    getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(COLUMN_RELEASE)),
                    getString(getColumnIndexOrThrow(COLUMN_PRODUCER)),
                    getString(getColumnIndexOrThrow(COLUMN_GENRE)),
                )
            }
        }else{
            Serie()
        }
    }

    override fun getSeries(): MutableList<Serie> {
        val seriesCursor = seriesBd.query(
            TABLE_SERIE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val seriesList = mutableListOf<Serie>()

        if(seriesCursor.moveToFirst()){
            while (!seriesCursor.isAfterLast){
                val serie = contentValuesToSerie(seriesCursor)
                seriesList.add(serie)
                seriesCursor.moveToNext()
            }
        }

        return seriesList
    }

    override fun updateSerie(serie: Serie): Int {
        val serieCv = serieToContentValues(serie)
        return seriesBd.update(TABLE_SERIE, serieCv, "${COLUMN_NAME} = ?", arrayOf(serie.name))
    }

    override fun deleteSerie(name: String): Int {
        return seriesBd.delete(TABLE_SERIE, "${COLUMN_NAME} = ?", arrayOf(name))
    }

    private fun contentValuesToSerie(serieCursor: Cursor): Serie {
        with(serieCursor){
            return Serie(
                this.getString(this.getColumnIndex(COLUMN_NAME)),
                this.getString(this.getColumnIndex(COLUMN_RELEASE)),
                this.getString(this.getColumnIndex(COLUMN_PRODUCER)),
                this.getString(this.getColumnIndex(COLUMN_GENRE)),
            )
        }
    }

    private fun serieToContentValues(serie: Serie): ContentValues = ContentValues().also {
        with(it){
            put(COLUMN_NAME, serie.name)
            put(COLUMN_GENRE, serie.genre)
            put(COLUMN_PRODUCER, serie.producer)
            put(COLUMN_RELEASE, serie.releaseYear)
        }
    }
}