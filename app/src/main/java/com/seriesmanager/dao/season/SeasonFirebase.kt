package com.seriesmanager.dao.season

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.seriesmanager.dao.serie.SerieFirebase.Companion.BD_SERIES
import com.seriesmanager.model.Season

class SeasonFirebase: SeasonDAO {
    companion object {
        val BD_SEASON = "season"
    }

    private val seasonRtDb = Firebase.database.getReference(BD_SERIES)

    private val seasonList = mutableListOf<Season>()

    override fun createSeason(season: Season, serieName: String): Long {
        createOrUpdateSeason(season, serieName)
        return 0L
    }

    override fun findSeason(name: String): Season {
        return seasonList.firstOrNull{
            it.sequence == name
        } ?: Season()
    }

    override fun getSeasons(serieName: String): MutableList<Season> {
        seasonRtDb.child(serieName).child(BD_SEASON).get().addOnSuccessListener { snapshot ->
            seasonList.clear()
            snapshot.children.forEach {
                val season = it.getValue<Season>() ?: Season()
                seasonList.add(season)
            }
        }

        return seasonList
    }

    override fun updateSeason(serie: Season, serieName: String): Int {
        createOrUpdateSeason(serie, serieName)
        return 1
    }

    override fun deleteSeason(name: String, serieName: String): Int {
        seasonRtDb.child(serieName).child(BD_SEASON).child(name).removeValue()
        return 1
    }

    private fun createOrUpdateSeason(season: Season, serieName: String){
        seasonRtDb.child(serieName).child(BD_SEASON).child(season.sequence).setValue(season)
    }
}