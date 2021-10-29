package com.seriesmanager.dao

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.seriesmanager.model.Episode

class EpisodeSqlite(context: Context): EpisodeDAO {
    companion object {
        private val TABLE_EPISODE = "episode"

        private val COLUMN_SEQUENCE = "sequence"
        private val COLUMN_NAME = "name"
        private val COLUMN_DURATION = "duration"
        private val COLUMN_WATCHED = "watched"
        private val COLUMN_FOREIGN_SEASON = "season"

        private val CREATE_TABLE_EPISODE_STMT = "CREATE TABLE IF NOT EXISTS $TABLE_EPISODE ("+
                "$COLUMN_SEQUENCE TEXT NOT NULL PRIMARY KEY, "+
                "$COLUMN_NAME TEXT NOT NULL, "+
                "$COLUMN_DURATION TEXT NOT NULL, "+
                "$COLUMN_WATCHED TEXT NOT NULL, "+
                "$COLUMN_FOREIGN_SEASON TEXT NOT NULL, "+
                "FOREIGN KEY ($COLUMN_FOREIGN_SEASON) REFERENCES ${SeasonSqlite.TABLE_SEASON}(${SeasonSqlite.COLUMN_SEQUENCE})"+
                ");"
    }

    private val episodeBd: SQLiteDatabase
    init {
        episodeBd = context.openOrCreateDatabase(SerieSqlite.BD_SERIES_MANAGER, MODE_PRIVATE, null)
        try{
            episodeBd.execSQL(CREATE_TABLE_EPISODE_STMT)
        }catch(se: SQLException){
            Log.e("Episode", se.toString())
        }
    }

    override fun createEpisode(episode: Episode, seasonSequence: String): Long {
        val episodeCv = episodeToContentValues(episode, seasonSequence)
        return episodeBd.insert(TABLE_EPISODE, null, episodeCv)
    }

    override fun findEpisode(episodeSequence: String): Episode {
        TODO("Not yet implemented")
    }

    override fun getEpisodes(seasonSequence: String): MutableList<Episode> {
        val episodeCursor = episodeBd.query(
            TABLE_EPISODE,
            null,
            "$COLUMN_FOREIGN_SEASON = ?",
            arrayOf(seasonSequence),
            null,
            null,
            null,
            null
        )

        val episodeList = mutableListOf<Episode>()

        if(episodeCursor.moveToFirst()){
            while(!episodeCursor.isAfterLast){
                val episode = contentValuesToEpisode(episodeCursor)
                episodeList.add(episode)
                episodeCursor.moveToNext()
            }
        }

        return episodeList
    }

    override fun updateEpisode(episode: Episode, seasonSequence: String): Int {
        val episodeCv = episodeToContentValues(episode, seasonSequence)
        return episodeBd.update(TABLE_EPISODE, episodeCv, "${COLUMN_SEQUENCE} = ?", arrayOf(episode.sequence))
    }

    override fun deleteEpisode(episodeSequence: String): Int {
         return episodeBd.delete(TABLE_EPISODE, "${COLUMN_SEQUENCE} = ?", arrayOf(episodeSequence))
    }

    private fun contentValuesToEpisode(episodeCursor: Cursor): Episode {
        with(episodeCursor){
            return Episode(
                this.getString(this.getColumnIndex(COLUMN_SEQUENCE)),
                this.getString(this.getColumnIndex(COLUMN_NAME)),
                this.getString(this.getColumnIndex(COLUMN_DURATION)),
                this.getString(this.getColumnIndex(COLUMN_WATCHED)) == "1",
            )
        }
    }

    private fun episodeToContentValues(episode: Episode, seasonSequence: String): ContentValues = ContentValues().also {
        with(it){
            put(COLUMN_SEQUENCE, episode.sequence)
            put(COLUMN_NAME, episode.name)
            put(COLUMN_DURATION, episode.duration)
            put(COLUMN_WATCHED, episode.watched)
            put(COLUMN_FOREIGN_SEASON, seasonSequence)
        }
    }
}