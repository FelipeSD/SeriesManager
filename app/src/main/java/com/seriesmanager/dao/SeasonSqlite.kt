package com.seriesmanager.dao

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.seriesmanager.dao.serie.SerieSqlite
import com.seriesmanager.model.Season

class SeasonSqlite(context: Context): SeasonDAO {
    companion object {
        val TABLE_SEASON = "season"

        val COLUMN_SEQUENCE = "sequence"
        private val COLUMN_RELEASE = "release_year"
        private val COLUMN_EPISODES = "number_episodes"
        private val COLUMN_FOREIGN_SERIE = "serie"

        private val CREATE_TABLE_SEASON_STMT = "CREATE TABLE IF NOT EXISTS $TABLE_SEASON ("+
                "$COLUMN_SEQUENCE TEXT NOT NULL PRIMARY KEY, "+
                "$COLUMN_RELEASE TEXT NOT NULL, "+
                "$COLUMN_EPISODES TEXT NOT NULL, "+
                "$COLUMN_FOREIGN_SERIE TEXT NOT NULL, "+
                "FOREIGN KEY ($COLUMN_FOREIGN_SERIE) REFERENCES ${SerieSqlite.TABLE_SERIE}(${SerieSqlite.COLUMN_NAME})"+
                ");"
    }

    private val seasonBd: SQLiteDatabase
    init {
        seasonBd = context.openOrCreateDatabase(SerieSqlite.BD_SERIES_MANAGER, MODE_PRIVATE, null)
        try {
            seasonBd.execSQL(CREATE_TABLE_SEASON_STMT)
        }catch (se: SQLException){
            Log.e("Season", se.toString())
        }
    }

    override fun createSeason(season: Season, serieName: String): Long {
        val seasonCv = seasonToContentValues(season, serieName)
        return seasonBd.insert(TABLE_SEASON, null, seasonCv)
    }

    override fun findSeason(sequence: String): Season {
        TODO("Not yet implemented")
    }

    override fun getSeasons(serieName: String): MutableList<Season> {
        val seasonCursor = seasonBd.query(
            TABLE_SEASON,
            null,
            "$COLUMN_FOREIGN_SERIE = ?",
            arrayOf(serieName),
            null,
            null,
            null,
            null
        )

        val seasonList = mutableListOf<Season>()

        if(seasonCursor.moveToFirst()){
            while (!seasonCursor.isAfterLast){
                val season = contentValuesToSeason(seasonCursor)
                seasonList.add(season)
                seasonCursor.moveToNext()
            }
        }

        return seasonList
    }

    override fun updateSeason(season: Season, serieName: String): Int {
        val seasonCv = seasonToContentValues(season, serieName)
        return seasonBd.update(TABLE_SEASON, seasonCv, "${COLUMN_SEQUENCE} = ?", arrayOf(season.sequence))
    }

    override fun deleteSeason(sequence: String): Int {
        return seasonBd.delete(TABLE_SEASON, "${COLUMN_SEQUENCE} = ?", arrayOf(sequence))
    }

     private fun contentValuesToSeason(seasonCursor: Cursor): Season {
        with(seasonCursor){
            return Season(
                this.getString(this.getColumnIndex(COLUMN_SEQUENCE)),
                this.getString(this.getColumnIndex(COLUMN_RELEASE)),
                this.getString(this.getColumnIndex(COLUMN_EPISODES)),
            )
        }
    }

    private fun seasonToContentValues(season: Season, serieName: String): ContentValues = ContentValues().also {
        with(it){
            put(COLUMN_SEQUENCE, season.sequence)
            put(COLUMN_RELEASE, season.releaseYear)
            put(COLUMN_EPISODES, season.numberEpisodes)
            put(COLUMN_FOREIGN_SERIE, serieName)
        }
    }
}