package com.seriesmanager.dao.season

import com.seriesmanager.model.Season

interface SeasonDAO {
    fun createSeason(season: Season, serieName: String): Long
    fun findSeason(name: String): Season
    fun getSeasons(serieName: String): MutableList<Season>
    fun updateSeason(serie: Season, serieName: String): Int
    fun deleteSeason(name: String, serieName: String): Int
}